package org.openprovenance.rdf;

import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBElement;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.List;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;


import org.openprovenance.model.OPMGraph; 
import org.openprovenance.model.Edge; 
import org.openprovenance.model.Overlaps; 
import org.openprovenance.model.Account; 
import org.openprovenance.model.AccountRef; 
import org.openprovenance.model.Property; 
import org.openprovenance.model.Processes; 
import org.openprovenance.model.Node; 
import org.openprovenance.model.Agent; 
import org.openprovenance.model.Process; 
import org.openprovenance.model.Role; 
import org.openprovenance.model.Artifact; 
import org.openprovenance.model.Used; 
import org.openprovenance.model.EmbeddedAnnotation; 
import org.openprovenance.model.WasGeneratedBy; 
import org.openprovenance.model.WasTriggeredBy; 
import org.openprovenance.model.WasDerivedFrom; 
import org.openprovenance.model.WasControlledBy; 
import org.openprovenance.model.OPMUtilities; 
import org.openprovenance.model.OPMFactory; 
import org.openprovenance.model.OPMSerialiser; 

import org.tupeloproject.kernel.SubjectFacade;
import org.tupeloproject.provenance.ProvenanceAccount;
import org.tupeloproject.provenance.ProvenanceRole;
import org.tupeloproject.provenance.ProvenanceAgent;
import org.tupeloproject.provenance.ProvenanceProcess;
import org.tupeloproject.provenance.ProvenanceArtifact;
import org.tupeloproject.provenance.ProvenanceUsedArc;
import org.tupeloproject.provenance.ProvenanceGeneratedArc;
import org.tupeloproject.provenance.ProvenanceTriggeredArc;
import org.tupeloproject.provenance.ProvenanceDerivedArc;
import org.tupeloproject.provenance.ProvenanceControlledArc;
import org.tupeloproject.provenance.impl.ProvenanceContextFacade;
import org.tupeloproject.provenance.impl.RdfProvenanceElement;
import org.tupeloproject.provenance.impl.RdfProvenanceAccount;
import org.tupeloproject.provenance.impl.RdfProvenanceRole;
import org.tupeloproject.provenance.impl.RdfProvenanceArc;
import org.tupeloproject.rdf.Resource;
import org.tupeloproject.rdf.Triple;
import org.tupeloproject.rdf.xml.RdfXml;
import org.tupeloproject.kernel.Context;
import org.tupeloproject.kernel.UnionContext;
import org.tupeloproject.kernel.impl.ResourceContext;
import org.tupeloproject.kernel.impl.MemoryContext;
import org.tupeloproject.kernel.impl.FileContext;
import org.tupeloproject.kernel.impl.BasicLocalContext;
import org.tupeloproject.util.Xml;
import org.tupeloproject.kernel.OperatorException; 


/**
TODO: support for graph-level annotations 
*/

public class OPMRdf2Xml {

    static String NULL_ACCOUNT=OPMXml2Rdf.NULL_ACCOUNT;


    
    OPMUtilities u=new OPMUtilities();
    OPMFactory pFactory=OPMFactory.getFactory();

    public OPMGraph convert (String filename) throws OperatorException, IOException {
        return convert(new FileInputStream(new File(filename)));
    }
    
    public OPMGraph convert (InputStream in) throws OperatorException, IOException {
        return convert(RdfXml.parse(in));
    }

    HashMap<String,Account> accountTable=new HashMap();
    HashMap<String,Process> processTable=new HashMap();
    HashMap<String,Artifact> artifactTable=new HashMap();
    HashMap<String,Agent> agentTable=new HashMap();

    Set<ProvenanceUsedArc> usedArcs=new HashSet();
    Set<ProvenanceGeneratedArc> generatedArcs=new HashSet();
    Set<ProvenanceTriggeredArc> triggeredArcs=new HashSet();
    Set<ProvenanceControlledArc> controlledArcs=new HashSet();
    Set<ProvenanceDerivedArc> derivedArcs=new HashSet();
    
    Resource hasAnnotation=Resource.uriRef(OPMXml2Rdf.OPM_HAS_ANNOTATION);
    Resource hasAccount=Resource.uriRef(OPMXml2Rdf.OPM_HAS_ACCOUNT);

    public OPMGraph convert (Set<Triple> triples) throws OperatorException, IOException {

        Collection<Account> accounts=new LinkedList();
        Collection<Overlaps> overlaps=new LinkedList();
        Collection<Process> processes=new LinkedList();
        Collection<Artifact> artifacts=new LinkedList();
        Collection<Agent> agents=new LinkedList();
        Collection<Object> edges=new LinkedList();

        OPMGraph graph=pFactory.newOPMGraph(accounts,
                                            overlaps,
                                            processes,
                                            artifacts,
                                            agents,
                                            edges);


        MemoryContext mc = new MemoryContext(); //MemoryContext

        
        ResourceContext rc = new ResourceContext("http://example.org/data/","/provenanceExample/");
        Context context = new UnionContext();
        context.addChild(mc);
        context.addChild(rc);

        mc.addTriples(triples);


        ProvenanceContextFacade pcf = new ProvenanceContextFacade(mc);

        Collection<Resource> done=new LinkedList();

        // Find all elements
        for (Triple triple: triples) {

            Resource subject=triple.getSubject();
            if (done.contains(subject)) continue;
            done.add(subject);
            RdfProvenanceElement element=pcf.getElement(subject);
            if (element!=null) {
                //System.out.println("found element " + element);

                //System.out.println("Subject is " + subject);

                if (element instanceof ProvenanceArtifact) {
                    Artifact a=getArtifact((ProvenanceArtifact) element);
                    Collection<ProvenanceUsedArc> used=pcf.getUsedBy((ProvenanceArtifact) element);
                    if (used!=null) {
                        usedArcs.addAll(used);
                    }
                    Collection<ProvenanceGeneratedArc> generated=pcf.getGeneratedBy((ProvenanceArtifact) element);
                    if (generated!=null) {
                        generatedArcs.addAll(generated);
                    }
                    Collection<ProvenanceDerivedArc> derived=pcf.getDerivedFrom((ProvenanceArtifact) element);
                    if (derived!=null) {
                        derivedArcs.addAll(derived);
                    }

                    
                    SubjectFacade sf=new SubjectFacade(subject, mc) ;
                    java.util.Set<Resource> annotations=sf.getObjects(hasAnnotation);
                    pFactory.addCompactAnnotation(a,convertAnnotations(annotations,pcf,mc));

                }

                if (element instanceof ProvenanceProcess) {
                    Process p=getProcess((ProvenanceProcess)element);
                    //System.out.println("found element " + p);
                    Collection<ProvenanceTriggeredArc> triggered=pcf.getTriggeredBy((ProvenanceProcess) element);
                    if (triggered!=null) {
                        triggeredArcs.addAll(triggered);
                    }

                    SubjectFacade sf=new SubjectFacade(subject, mc) ;
                    java.util.Set<Resource> annotations=sf.getObjects(hasAnnotation);
                    pFactory.addCompactAnnotation(p,convertAnnotations(annotations,pcf,mc));

                }


                if (element instanceof ProvenanceAgent) {
                    Agent ag=getAgent((ProvenanceAgent)element);
                    //System.out.println("found element " + ag);
                    Collection<ProvenanceControlledArc> controlled=pcf.getControlled((ProvenanceAgent) element);
                    if (controlled!=null) {
                        controlledArcs.addAll(controlled);
                    }


                    SubjectFacade sf=new SubjectFacade(subject, mc) ;
                    java.util.Set<Resource> annotations=sf.getObjects(hasAnnotation);
                    pFactory.addCompactAnnotation(ag,convertAnnotations(annotations,pcf,mc));
                }

                if (element instanceof ProvenanceAccount) {
                    Account acc=getAccount((ProvenanceAccount)element);

                    SubjectFacade sf=new SubjectFacade(subject, mc) ;
                    java.util.Set<Resource> annotations=sf.getObjects(hasAnnotation);
                    pFactory.addCompactAnnotation(acc,convertAnnotations(annotations,pcf,mc));

                }

            } else {
                
            }
        }

        populateGraph(graph,pcf,mc);

        return graph;
    }


    public List<EmbeddedAnnotation> convertAnnotations(Set<Resource> annotations,
                                                       ProvenanceContextFacade pcf,
                                                       Context context)
        throws org.tupeloproject.kernel.OperatorException {

        List<EmbeddedAnnotation> embedded=new LinkedList();
        for (Resource annotation: annotations) {
            SubjectFacade af=new SubjectFacade(annotation, context) ;


            
            java.util.Set<Resource> accounts=af.getObjects(hasAccount);
            List<AccountRef> accRefs=new LinkedList();
            for (Resource account: accounts) {
                RdfProvenanceAccount rdfA=pcf.getAccount(account);
                Account a=getAccount(rdfA);
                accRefs.add(pFactory.newAccountRef(a));
            }

            String id=deUrify(annotation.getUri().toString());


            Set<Triple> triples=af.getTriples();
            List<Property> properties=new LinkedList();

            List<JAXBElement<? extends EmbeddedAnnotation>> subAnnotations=new LinkedList();
            for (Triple t: triples) {
                Resource predicate=t.getPredicate();
                String predicateUri=predicate.getUri().toString();
                // search for the property!!
                if ((!(predicateUri.equals(OPMXml2Rdf.OPM_HAS_ACCOUNT)))
                    &&
                    (!(predicateUri.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")))
                    &&
                    (!(predicateUri.equals(OPMXml2Rdf.OPM_HAS_ANNOTATION)))
                    ) {

                    Resource object=t.getObject();
                    Object o=object.asObject();
                    properties.add(pFactory.newProperty(predicateUri,o));
                } else if (predicateUri.equals(OPMXml2Rdf.OPM_HAS_ANNOTATION)) {
                    System.out.println("*********************** Found annotation of annotation !");
                    Resource annotationOfAnnotation=t.getObject();
                    Set<Resource> rl=new HashSet();
                    rl.add(annotationOfAnnotation);
                    for (EmbeddedAnnotation ann: convertAnnotations(rl,pcf,context)) {
                        subAnnotations.add(pFactory.compactAnnotation(ann));
                    };
                }
            }

            EmbeddedAnnotation e=pFactory.newEmbeddedAnnotation(id, properties, accRefs, null);
            pFactory.addAnnotations(e,subAnnotations);
            embedded.add(e);
        }


        return embedded;
    }

    void populateGraph(OPMGraph graph,
                       ProvenanceContextFacade pcf,
                       MemoryContext mc) throws org.tupeloproject.kernel.OperatorException {
        Set<String> artifactUris=artifactTable.keySet();
        for (String uri: artifactUris) {
            graph.getArtifacts().getArtifact().add(artifactTable.get(uri));
        }

        Set<String> processUris=processTable.keySet();
        for (String uri: processUris) {
            graph.getProcesses().getProcess().add(processTable.get(uri));
        }

        Set<String> agentUris=agentTable.keySet();
        for (String uri: agentUris) {
            graph.getAgents().getAgent().add(agentTable.get(uri));
        }

        for (ProvenanceUsedArc used: usedArcs) {
            ProvenanceArtifact pArtifact=used.getArtifact();
            Artifact artifact=getArtifact(pArtifact);
            ProvenanceProcess pProcess=used.getProcess();
            Process process=getProcess(pProcess);
            ProvenanceRole pRole=used.getRole();
            Role role=pFactory.newRole(pRole.getName());
            ProvenanceAccount pAccount=used.getAccount();
            Account account=getAccount(pAccount);

            Used u2=pFactory.newUsed(process,role,artifact,Collections.singleton(account));
            graph.getDependencies().getUsedOrWasGeneratedByOrWasTriggeredBy().add(u2);

            RdfProvenanceArc rpa=(RdfProvenanceArc) used;
            Resource subject=rpa.getSubject();
            SubjectFacade sf=new SubjectFacade(subject, mc) ;
            java.util.Set<Resource> annotations=sf.getObjects(hasAnnotation);
            pFactory.addCompactAnnotation(u2,convertAnnotations(annotations,pcf,mc));

            RdfProvenanceRole rdfRole=(RdfProvenanceRole) pRole;
            Resource roleSubject=rdfRole.getSubject();
            SubjectFacade roleSf=new SubjectFacade(roleSubject, mc) ;
            java.util.Set<Resource> roleAnnotations=roleSf.getObjects(hasAnnotation);
            pFactory.addCompactAnnotation(role,convertAnnotations(roleAnnotations,pcf,mc));


            
        }


        for (ProvenanceGeneratedArc generated: generatedArcs) {
            ProvenanceArtifact pArtifact=generated.getArtifact();
            Artifact artifact=getArtifact(pArtifact);
            ProvenanceProcess pProcess=generated.getProcess();
            Process process=getProcess(pProcess);
            ProvenanceRole pRole=generated.getRole();
            Role role=pFactory.newRole(pRole.getName());
            ProvenanceAccount pAccount=generated.getAccount();
            Account account=getAccount(pAccount);

            WasGeneratedBy g2=pFactory.newWasGeneratedBy(artifact,role,process,Collections.singleton(account));
            graph.getDependencies().getUsedOrWasGeneratedByOrWasTriggeredBy().add(g2);

            RdfProvenanceArc rpa=(RdfProvenanceArc) generated;
            Resource subject=rpa.getSubject();
            SubjectFacade sf=new SubjectFacade(subject, mc) ;
            java.util.Set<Resource> annotations=sf.getObjects(hasAnnotation);
            pFactory.addCompactAnnotation(g2,convertAnnotations(annotations,pcf,mc));



            RdfProvenanceRole rdfRole=(RdfProvenanceRole) pRole;
            Resource roleSubject=rdfRole.getSubject();
            SubjectFacade roleSf=new SubjectFacade(roleSubject, mc) ;
            java.util.Set<Resource> roleAnnotations=roleSf.getObjects(hasAnnotation);
            pFactory.addCompactAnnotation(role,convertAnnotations(roleAnnotations,pcf,mc));


            
        }


        for (ProvenanceControlledArc controlled: controlledArcs) {
            ProvenanceAgent pAgent=controlled.getAgent();
            Agent agent=getAgent(pAgent);
            ProvenanceProcess pProcess=controlled.getProcess();
            Process process=getProcess(pProcess);
            ProvenanceRole pRole=controlled.getRole();
            Role role=pFactory.newRole(pRole.getName());
            ProvenanceAccount pAccount=controlled.getAccount();
            Account account=getAccount(pAccount);

            WasControlledBy g2=pFactory.newWasControlledBy(process,role,agent,Collections.singleton(account));
            graph.getDependencies().getUsedOrWasGeneratedByOrWasTriggeredBy().add(g2);

            RdfProvenanceRole rdfRole=(RdfProvenanceRole) pRole;
            Resource roleSubject=rdfRole.getSubject();
            SubjectFacade roleSf=new SubjectFacade(roleSubject, mc) ;
            java.util.Set<Resource> roleAnnotations=roleSf.getObjects(hasAnnotation);
            pFactory.addCompactAnnotation(role,convertAnnotations(roleAnnotations,pcf,mc));

            
        }


        for (ProvenanceDerivedArc derived: derivedArcs) {
            ProvenanceArtifact pArtifact1=derived.getAntecedent();
            Artifact artifact1=getArtifact(pArtifact1);
            ProvenanceArtifact pArtifact2=derived.getConsequent();
            Artifact artifact2=getArtifact(pArtifact2);
            ProvenanceAccount pAccount=derived.getAccount();
            Account account=getAccount(pAccount);

            WasDerivedFrom d2=pFactory.newWasDerivedFrom(artifact2,artifact1,Collections.singleton(account));
            graph.getDependencies().getUsedOrWasGeneratedByOrWasTriggeredBy().add(d2);

            RdfProvenanceArc rpa=(RdfProvenanceArc) derived;
            Resource subject=rpa.getSubject();
            SubjectFacade sf=new SubjectFacade(subject, mc) ;
            java.util.Set<Resource> annotations=sf.getObjects(hasAnnotation);
            pFactory.addCompactAnnotation(d2,convertAnnotations(annotations,pcf,mc));

        }


        for (ProvenanceTriggeredArc triggered: triggeredArcs) {
            ProvenanceProcess pProcess1=triggered.getAntecedent();
            Process process1=getProcess(pProcess1);
            ProvenanceProcess pProcess2=triggered.getConsequent();
            Process process2=getProcess(pProcess2);
            ProvenanceAccount pAccount=triggered.getAccount();
            Account account=getAccount(pAccount);

            WasTriggeredBy t2=pFactory.newWasTriggeredBy(process2,process1,Collections.singleton(account));
            graph.getDependencies().getUsedOrWasGeneratedByOrWasTriggeredBy().add(t2);


            RdfProvenanceArc rpa=(RdfProvenanceArc) triggered;
            Resource subject=rpa.getSubject();
            SubjectFacade sf=new SubjectFacade(subject, mc) ;
            java.util.Set<Resource> annotations=sf.getObjects(hasAnnotation);
            pFactory.addCompactAnnotation(t2,convertAnnotations(annotations,pcf,mc));


        }

        Set<String> accountNames=accountTable.keySet();
        for (String name: accountNames) {
            graph.getAccounts().getAccount().add(accountTable.get(name));
        }


        
    }

    Account getAccount(ProvenanceAccount pa) {
        String name=pa.getName();

        Account acc=accountTable.get(name);
        if (acc==null) {
            Account a=pFactory.newAccount(name);
            accountTable.put(name,a);
            return a;
        } else {
            return acc;
        }
    }

    Agent getAgent(ProvenanceAgent pa) {
        String name=pa.getName();
        String uri=((RdfProvenanceElement)pa).getSubject().getUri().toString();
        String id=deUrify(uri);

        Agent ag=agentTable.get(uri);
        if (ag==null) {
            Agent a=pFactory.newAgent(id, null);
            agentTable.put(uri,a);
            return a;
        } else {
            return ag;
        }
    }

    Artifact getArtifact(ProvenanceArtifact pa) {
        String name=pa.getName();
        String uri=((RdfProvenanceElement)pa).getSubject().getUri().toString();
        String id=deUrify(uri);

        Artifact a=artifactTable.get(uri);
        if (a==null) {
            Artifact a2=pFactory.newArtifact(id, null);
            artifactTable.put(uri,a2);
            return a2;
        } else {
            return a;
        }
    }


    Process getProcess(ProvenanceProcess pa) {
        String name=pa.getName();
        String uri=((RdfProvenanceElement)pa).getSubject().getUri().toString();
        String id=deUrify(uri);

        Process p=processTable.get(uri);
        if (p==null) {
            Process a=pFactory.newProcess(id, null);
            processTable.put(uri,a);
            return a;
        } else {
            return p;
        }
    }
        
        


    public String deUrify(String id) {
        if (id.startsWith(OPMXml2Rdf.URI_PREFIX)) {
            return id.substring(OPMXml2Rdf.URI_PREFIX.length(),id.length());
        } else {
            return id;
        }
            
    }


    public void convert (String inFilename, String outFilename) throws OperatorException, IOException, JAXBException {
        OPMGraph graph=convert(inFilename);

        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File(outFilename),graph,true);

    }

    public static void main(String [] args) throws OperatorException, IOException, JAXBException {
        if ((args==null) || (args.length!=2)) {
            System.out.println("Usage: opmrdf2xml fileIn fileOut");
            return;
        }
        OPMRdf2Xml converter=new OPMRdf2Xml();
        converter.convert(args[0],args[1]);
    }

    
}