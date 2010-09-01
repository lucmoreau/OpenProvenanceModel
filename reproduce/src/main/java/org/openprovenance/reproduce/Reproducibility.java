package org.openprovenance.reproduce;
import org.w3c.dom.Document;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Literal;


import org.openprovenance.model.OPMFactory;
import org.openprovenance.model.OPMGraph;
import org.openprovenance.model.Artifact;
import org.openprovenance.model.Process;
import org.openprovenance.model.IndexedOPMGraph;

public class Reproducibility {
    final String theNS;

    final Utilities u;
    final OPMFactory oFactory;
    final GraphGenerator gGenerator;
    final Model theModel;
    final IndexedOPMGraph graph;

    final PrimitiveEnvironment primEnv;


    public Reproducibility(String theNS,
                           OPMFactory oFactory,
                           GraphGenerator gGenerator,
                           PrimitiveEnvironment primEnv,
                           Model theModel,
                           IndexedOPMGraph graph) {
        this.theNS=theNS;
        this.oFactory=oFactory;
        u=new Utilities(oFactory);
        this.gGenerator=gGenerator;
        this.theModel=theModel;
        this.graph=graph;
        this.primEnv=primEnv;
    }

    public String localName(String uri, String ns) {
        return uri.substring(ns.length());
    }

    public void invokeProcess(Process p) throws java.io.IOException, org.jaxen.JaxenException, org.xml.sax.SAXException {

        String process=theNS+p.getId(); 
        
        String name=localName(process,theNS);
        HashMap<String,Artifact> inputs=new HashMap();
        HashMap<String,Artifact> outputs=new HashMap();
        HashMap<String,Artifact> args=new HashMap();
        Queries q=new Queries(theModel); 
        List<Literal> results = q.getResourcePropertyAsLiterals(process,
                                                                "http://openprovenance.org/primitives#primitive");
        String primitiveName=results.get(0).getString();
        q.close();

        Process p2=gGenerator.newProcess(p);
        
        q=new Queries(theModel); 
        //        q.addPrefixes("pc1",theNS);
        ResultSet results2 = q.getUsedArtifactsAndRoles("<" + process + ">");
        while (results2.hasNext()) {
            QuerySolution qs=results2.next();
            String artifactUri=qs.getResource("?a").getURI();
            String role=qs.getLiteral("?r").getString();
            Artifact a=graph.getArtifact(localName(artifactUri,theNS));
            inputs.put(role,a);
        }
        q.close();

        q=new Queries(theModel); 
        //        q.addPrefixes("pc1",theNS);
        ResultSet results3 = q.getGeneratedArtifactsAndRoles("<" + process + ">");
        while (results3.hasNext()) {
            QuerySolution qs=results3.next();
            String artifactUri=qs.getResource("?a").getURI();
            String role=qs.getLiteral("?r").getString();
            Artifact a=graph.getArtifact(localName(artifactUri,theNS));
            outputs.put(role,a);
        }
        q.close();


        args.putAll(inputs);
        args.putAll(outputs);
        
        String primitive=primEnv.get(primitiveName);
        invokePrimitive(primitive,
                        args,
                        name);

        HashMap<String,String> aMap=gGenerator.getArtifactMap();

        IndexedOPMGraph iGraph=(IndexedOPMGraph) gGenerator.getNewGraph();
        for (String r: inputs.keySet()) {
            Artifact a2=iGraph.getArtifact(aMap.get(inputs.get(r).getId()));
            if (a2!=null) {
                gGenerator.addUsed(oFactory.newUsed(p2,
                                                    oFactory.newRole(r),
                                                    a2,
                                                    null));
            }
        }

        for (String r: outputs.keySet()) {
            Artifact a2=iGraph.getArtifact(aMap.get(outputs.get(r).getId()));
            if (a2!=null) {
                gGenerator.addWasGeneratedBy(oFactory.newWasGeneratedBy(a2,
                                                                        oFactory.newRole(r),
                                                                        p2,
                                                                        null));
            }
        }

    }
        
    ExecuteFactory eFact=new ExecuteFactory();

    public void invokePrimitive(String primitive,
                                HashMap<String,Artifact> args,
                                String name)
        throws java.io.IOException, org.jaxen.JaxenException, org.xml.sax.SAXException {

        System.out.println("Invoking primitive " + primitive);
        Execute exec=eFact.newInstance(primitive,oFactory,gGenerator);
        Object o=exec.prepareInvocationArguments(primitive,args);
        exec.invoke(o,name,primitive, u);
    }


    

}
