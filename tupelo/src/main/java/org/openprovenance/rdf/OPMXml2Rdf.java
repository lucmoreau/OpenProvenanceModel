package org.openprovenance.rdf;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;

import java.util.List;
import java.util.Set;
import java.util.HashMap;


import org.openprovenance.model.OPMGraph; 
import org.openprovenance.model.Edge; 
import org.openprovenance.model.Account; 
import org.openprovenance.model.AccountId; 
import org.openprovenance.model.Id; 
import org.openprovenance.model.Processes; 
import org.openprovenance.model.Node; 
import org.openprovenance.model.Used; 
import org.openprovenance.model.Process; 
import org.openprovenance.model.Artifact; 
import org.openprovenance.model.WasGeneratedBy; 
import org.openprovenance.model.OPMUtilities; 


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


public class OPMXml2Rdf {

    static String NULL_ACCOUNT="_null";
    
    OPMUtilities u=new OPMUtilities();

    public void convert (OPMGraph graph) throws OperatorException, IOException {
      

        BasicLocalContext mc = new BasicLocalContext(); //MemoryContext
        mc.setPath("target/foo.rdf");
        
        ResourceContext rc = new ResourceContext("http://example.org/data/","/provenanceExample/");
        Context context = new UnionContext();
        context.addChild(mc);
        context.addChild(rc);



        ProvenanceContextFacade pcf = new ProvenanceContextFacade(mc);

        HashMap<String,ProvenanceAccount> accountTable=new HashMap();
        HashMap<String,ProvenanceProcess> processTable=new HashMap();
        HashMap<String,ProvenanceArtifact> artifactTable=new HashMap();
        HashMap<String,ProvenanceAgent> agentTable=new HashMap();

        if (graph.getAccounts()!=null) {
            for (Account acc: graph.getAccounts().getAccount()) {
                Resource res=Resource.uriRef(urify(acc.getId()));
                ProvenanceAccount rdfAccount=pcf.newAccount(acc.getId(),res);
                pcf.assertAccount(rdfAccount);
                accountTable.put(acc.getId(),rdfAccount);
            }
            Resource res=Resource.uriRef(urify(NULL_ACCOUNT));
            ProvenanceAccount rdfAccount=pcf.newAccount(NULL_ACCOUNT,res);
            pcf.assertAccount(rdfAccount);
            accountTable.put(NULL_ACCOUNT,rdfAccount);
        }


        if (graph.getProcesses()!=null) {
            for (Process p: graph.getProcesses().getProcess()) {
                Resource res=Resource.uriRef(urify(p.getId()));
                ProvenanceProcess rdfProcess = pcf.newProcess((String)p.getValue(),res);
                pcf.assertProcess(rdfProcess);
                processTable.put(p.getId(),rdfProcess);
            }
        }

        if (graph.getArtifacts()!=null) {
            for (Artifact a: graph.getArtifacts().getArtifact()) {
                Resource res=Resource.uriRef(urify(a.getId()));
                ProvenanceArtifact rdfArtifact = pcf.newArtifact((String)a.getValue(),res);
                pcf.assertArtifact(rdfArtifact);
                artifactTable.put(a.getId(),rdfArtifact);
            }
        }

        List<Edge> edges=u.getEdges(graph);
        for (Edge e: edges) {
            List<AccountId> accounts= e.getAccount();
            String causeId=((Node)e.getCause().getId()).getId();
            String effectId=((Node)e.getEffect().getId()).getId();

            if (e instanceof Used) {
                ProvenanceArtifact cause=artifactTable.get(causeId);
                ProvenanceProcess effect=processTable.get(effectId);
                ProvenanceRole role = pcf.newRole(((Used)e).getRole().getValue());
                if (accounts.isEmpty()) {
                    ProvenanceAccount account=accountTable.get(NULL_ACCOUNT);
                    pcf.assertUsed(effect, cause, role, account);
                } else {
                    for (AccountId aid: accounts) {
                    ProvenanceAccount account=accountTable.get(((Account)aid.getId()).getId());
                    pcf.assertUsed(effect, cause, role, account);
                    }
                }
            }


            if (e instanceof WasGeneratedBy) {
                ProvenanceProcess cause=processTable.get(causeId);
                ProvenanceArtifact effect=artifactTable.get(effectId);
                ProvenanceRole role = pcf.newRole(((WasGeneratedBy)e).getRole().getValue());
                if (accounts.isEmpty()) {
                    ProvenanceAccount account=accountTable.get(NULL_ACCOUNT);
                    pcf.assertGeneratedBy(effect, cause, role, account);
                } else {
                    for (AccountId aid: accounts) {
                    ProvenanceAccount account=accountTable.get(((Account)aid.getId()).getId());
                    pcf.assertGeneratedBy(effect, cause, role, account);
                    }
                }
            }
                                       
        }


        /* 
        Resource sheet = Resource.uriRef("http://example.org/data/style.xsl");
        Resource doc = Resource.uriRef("http://example.org/data/doc.xml");
 
        ProvenanceArtifact docArtifact = pcf.newArtifact("source doc", doc);
        ProvenanceArtifact sheetArtifact = pcf.newArtifact("stylesheet", sheet);
 
        ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
        //Xml.transform(context.read(doc), context.read(sheet), outBuffer);
        ByteArrayInputStream inBuffer = new ByteArrayInputStream(outBuffer.toByteArray());

        Resource result = Resource.uriRef("http://example.org/data/result.xml");
        context.write(result, inBuffer);
 
        // the process has completed, now let's record the provenance
        ProvenanceArtifact resultArtifact = pcf.newArtifact("transform result");
        ProvenanceProcess transformProcess = pcf.newProcess("xslt transform");
 
        pcf.assertArtifact(sheetArtifact);
        pcf.assertArtifact(docArtifact);
        pcf.assertProcess(transformProcess);
        pcf.assertArtifact(resultArtifact);
 
        // the input document and stylesheet are two different kinds of inputs for the transform
        // process, so each has its own role
        ProvenanceRole inputDocumentRole = pcf.newRole("input document");
        ProvenanceRole stylesheetRole = pcf.newRole("stylesheet");
        ProvenanceRole outputRole = pcf.newRole("output");
 
        //pcf.assertUsed(transformProcess, docArtifact, inputDocumentRole, account);
        //pcf.assertUsed(transformProcess, sheetArtifact, stylesheetRole, account);
        //pcf.assertGeneratedBy(resultArtifact, transformProcess, outputRole, account);

        */

        Set<Triple> triplesToWrite = mc.getTriples();
        RdfXml.write(triplesToWrite, new FileOutputStream(new File("target/foo.xml")));

    }

    public String urify(String id) {
        return "id:" + id;
    }
        
}