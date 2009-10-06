package org.openprovenance.rdf;

import javax.xml.bind.JAXBException;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.Collection;


import org.openprovenance.model.OPMGraph; 
import org.openprovenance.model.Edge; 
import org.openprovenance.model.Account; 
import org.openprovenance.model.AccountId; 
import org.openprovenance.model.Id; 
import org.openprovenance.model.Processes; 
import org.openprovenance.model.Node; 
import org.openprovenance.model.Agent; 
import org.openprovenance.model.Process; 
import org.openprovenance.model.Artifact; 
import org.openprovenance.model.Used; 
import org.openprovenance.model.Role; 
import org.openprovenance.model.WasGeneratedBy; 
import org.openprovenance.model.WasTriggeredBy; 
import org.openprovenance.model.WasDerivedFrom; 
import org.openprovenance.model.WasControlledBy; 
import org.openprovenance.model.OPMUtilities; 
import org.openprovenance.model.OPMDeserialiser; 

import org.openprovenance.model.extension.NamedWasDerivedFrom; 


import org.tupeloproject.provenance.ProvenanceAccount;
import org.tupeloproject.provenance.ProvenanceRole;
import org.tupeloproject.provenance.ProvenanceAgent;
import org.tupeloproject.provenance.ProvenanceProcess;
import org.tupeloproject.provenance.ProvenanceArtifact;
import org.tupeloproject.provenance.impl.ProvenanceContextFacade;
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
import org.apache.log4j.Logger;

import org.tupeloproject.provenance.ProvenanceDerivedArc;
import org.tupeloproject.provenance.impl.RdfProvenanceArtifact;


public class OPMXml2Rdf {
    static Logger logger = Logger.getLogger(OPMXml2Rdf.class);

    static String NULL_ACCOUNT="_null";
    static String UNKNOWN_ROLE="_unknown";
    
    OPMUtilities u=new OPMUtilities();

    public void convert (OPMGraph graph, String filename) throws OperatorException, IOException {
        convert(graph,new FileOutputStream(new File(filename)));
    }
    
    public void convert (OPMGraph graph, OutputStream out) throws OperatorException, IOException {
      

        //BasicLocalContext mc = new BasicLocalContext(); //MemoryContext
        //mc.setPath("target/foo.rdf");

        MemoryContext mc = new MemoryContext(); 
        
        ResourceContext rc = new ResourceContext("http://example.org/data/","/provenanceExample/");
        Context context = new UnionContext();
        context.addChild(mc);
        context.addChild(rc);



        ProvenanceContextFacade pcf = new ProvenanceContextFacade(mc);

        HashMap<String,ProvenanceAccount> accountTable=new HashMap();
        HashMap<String,ProvenanceProcess> processTable=new HashMap();
        HashMap<String,ProvenanceArtifact> artifactTable=new HashMap();
        HashMap<String,ProvenanceAgent> agentTable=new HashMap();

        Resource nullRes=Resource.uriRef(urify(NULL_ACCOUNT));
        ProvenanceAccount nullRdfAccount=pcf.newAccount(NULL_ACCOUNT,nullRes);
        pcf.assertAccount(nullRdfAccount);
        accountTable.put(NULL_ACCOUNT,nullRdfAccount);

        if (graph.getAccounts()!=null) {
            for (Account acc: graph.getAccounts().getAccount()) {
                Resource res=Resource.resource(urify(acc.getId()));
                ProvenanceAccount rdfAccount=pcf.newAccount(acc.getId(),res);
                pcf.assertAccount(rdfAccount);
                accountTable.put(acc.getId(),rdfAccount);
            }
        }


        if (graph.getProcesses()!=null) {
            for (Process p: graph.getProcesses().getProcess()) {
                Resource res=Resource.uriRef(urify(p.getId()));
                ProvenanceProcess rdfProcess;
                if (p.getValue() instanceof String) {
                    rdfProcess = pcf.newProcess((String)p.getValue(),res);
                } else {
                    logger.warn("how to serialise process value? " + p.getValue().toString());
                    rdfProcess = pcf.newProcess(p.getValue().toString(),res);
                }
                pcf.assertProcess(rdfProcess);
                processTable.put(p.getId(),rdfProcess);
            }
        }

        if (graph.getArtifacts()!=null) {
            for (Artifact a: graph.getArtifacts().getArtifact()) {
                Resource res=Resource.uriRef(urify(a.getId()));
                ProvenanceArtifact rdfArtifact;
                if (a.getValue() instanceof String) {
                    rdfArtifact = pcf.newArtifact((String)a.getValue(),res);
                } else {
                    logger.warn("how to serialise artifact value? " + a.getValue().toString());
                    rdfArtifact = pcf.newArtifact("hello",res);
                }
                pcf.assertArtifact(rdfArtifact);
                artifactTable.put(a.getId(),rdfArtifact);
            }
        }


        if (graph.getAgents()!=null) {
            for (Agent a: graph.getAgents().getAgent()) {
                Resource res=Resource.uriRef(urify(a.getId()));
                ProvenanceAgent rdfAgent;
                if (a.getValue() instanceof String) {
                    rdfAgent = pcf.newAgent((String)a.getValue(),res);
                } else {
                    logger.warn("how to serialise agent value?");
                    rdfAgent = pcf.newAgent(a.getValue().toString(),res);
                }
                pcf.assertAgent(rdfAgent);
                agentTable.put(a.getId(),rdfAgent);
            }
        }

        List<Edge> edges=u.getEdges(graph);
        for (Edge e: edges) {
            List<AccountId> accounts= e.getAccount();
            String causeId=((Node)e.getCause().getRef()).getId();
            String effectId=((Node)e.getEffect().getRef()).getId();

            if (e instanceof Used) {
                ProvenanceArtifact cause=artifactTable.get(causeId);
                ProvenanceProcess effect=processTable.get(effectId);
                Role thisRole=((Used)e).getRole();
                ProvenanceRole role;
                if (thisRole!=null) {
                    role=pcf.newRole(thisRole.getValue());
                } else {
                    role=pcf.newRole(UNKNOWN_ROLE);
                }
                if (accounts.isEmpty()) {
                    ProvenanceAccount account=accountTable.get(NULL_ACCOUNT);
                    pcf.assertUsed(effect, cause, role, account);
                } else {
                    for (AccountId aid: accounts) {
                        ProvenanceAccount account=accountTable.get(((Account)aid.getRef()).getId());
                        pcf.assertUsed(effect, cause, role, account);
                    }
                }
            }
            else {
                if (e instanceof WasGeneratedBy) {
                    ProvenanceProcess cause=processTable.get(causeId);
                    ProvenanceArtifact effect=artifactTable.get(effectId);
                    Role thisRole=((WasGeneratedBy)e).getRole();
                    ProvenanceRole role;
                    if (thisRole!=null) {
                        role=pcf.newRole(thisRole.getValue());
                    } else {
                        role=pcf.newRole(UNKNOWN_ROLE);
                    }
                    if (accounts.isEmpty()) {
                        ProvenanceAccount account=accountTable.get(NULL_ACCOUNT);
                        pcf.assertGeneratedBy(effect, cause, role, account);
                    } else {
                        for (AccountId aid: accounts) {
                            ProvenanceAccount account=accountTable.get(((Account)aid.getRef()).getId());
                            pcf.assertGeneratedBy(effect, cause, role, account);
                        }
                    }
                }
                else {
                    if (e instanceof WasTriggeredBy) {
                        ProvenanceProcess cause=processTable.get(causeId);
                        ProvenanceProcess effect=processTable.get(effectId);
                        if (accounts.isEmpty()) {
                            ProvenanceAccount account=accountTable.get(NULL_ACCOUNT);
                            pcf.assertTriggeredBy(effect, cause, account);
                        } else {
                            for (AccountId aid: accounts) {
                                ProvenanceAccount account=accountTable.get(((Account)aid.getRef()).getId());
                                pcf.assertTriggeredBy(effect, cause, account);
                            }
                        }
                    }
                    else {
                        if (e instanceof WasDerivedFrom) {
                            ProvenanceArtifact cause=artifactTable.get(causeId);
                            ProvenanceArtifact effect=artifactTable.get(effectId);
                            if (accounts.isEmpty()) {
                                ProvenanceAccount account=accountTable.get(NULL_ACCOUNT);
                                pcf.assertDerivedFrom(effect, cause, account);
                            } else {
                                for (AccountId aid: accounts) {
                                    ProvenanceAccount account=accountTable.get(((Account)aid.getRef()).getId());
                                    pcf.assertDerivedFrom(effect, cause, account);
                                }
                            }
                            if (e instanceof NamedWasDerivedFrom) {
                                NamedWasDerivedFrom nwdf=(NamedWasDerivedFrom) e;
                                String type=nwdf.getType();
                                Collection<ProvenanceDerivedArc> derived=pcf.getDerivedFrom(effect);
                                ProvenanceDerivedArc me=null;
                                for (ProvenanceDerivedArc a: derived) {
                                    if (a.getAntecedent().equals(cause)
                                        && a.getConsequent().equals(effect)) {
                                        System.out.println("Found " + a);
                                        System.out.println("Found " + ((RdfProvenanceArtifact)cause).getSubject());
                                        System.out.println("Found " + ((RdfProvenanceArtifact)effect).getSubject());
                                        break;
                                    }
                                }
                                //System.out.println("triples " + mc.getTriples());
                            }
                        }
                        else {
                            if (e instanceof WasControlledBy) {
                                ProvenanceAgent cause=agentTable.get(causeId);
                                ProvenanceProcess effect=processTable.get(effectId);
                                ProvenanceRole role = pcf.newRole(((WasControlledBy)e).getRole().getValue());
                                if (accounts.isEmpty()) {
                                    ProvenanceAccount account=accountTable.get(NULL_ACCOUNT);
                                    pcf.assertControlledBy(effect, cause, role, account);
                                } else {
                                    for (AccountId aid: accounts) {
                                        ProvenanceAccount account=accountTable.get(((Account)aid.getRef()).getId());
                                        pcf.assertControlledBy(effect, cause, role, account);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Set<Triple> triplesToWrite = mc.getTriples();
        RdfXml.write(triplesToWrite, out);

    }

    public static String URI_PREFIX="id:";

    public String urify(String id) {
        return  URI_PREFIX + id.replaceAll("#","_");
    }


    public void convert (String inFilename, String outFilename) throws OperatorException, IOException, JAXBException {
        OPMDeserialiser deserial=OPMDeserialiser.getThreadOPMDeserialiser();
        OPMGraph graph=deserial.deserialiseOPMGraph(new File(inFilename));
        convert(graph,outFilename);
    }
    

    public static void main(String [] args) throws OperatorException, IOException, JAXBException {
        if ((args==null) || (args.length!=2)) {
            System.out.println("Usage: opmxml2rdf fileIn fileOut");
            return;
        }
        OPMXml2Rdf converter=new OPMXml2Rdf();
        converter.convert(args[0],args[1]);
    }


    
}