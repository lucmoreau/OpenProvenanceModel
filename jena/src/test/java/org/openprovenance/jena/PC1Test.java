package org.openprovenance.jena;

import static org.openprovenance.jena.TripleStore.OPMO_NS;
import static org.openprovenance.jena.TripleStore.OPMV_NS;

import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.vocabulary.RDFS;


/** Check owl-based reasoning on PC1. */

public class PC1Test extends TestCase {

    public static String PC1_NS="http://www.ipaw.info/pc1/";

    public PC1Test (String testName) {
        super(testName);
    }

    TripleStore ts;
    Querier q;

    public void setUp () {
        System.out.println("**** Setting up*");
        ts= new TripleStore();

        q=ts.setUpReasonerForFile("file:src/test/resources/pc1-time.n3", "N3", "pc1",PC1_NS);
    }

    public void testNodes() {

        List<Resource> resources=q.getProcessesAsResources();

        assertTrue((resources!=null) && resources.size()==1);


        resources=q.getArtifactsAsResources();
        assertTrue((resources!=null) && resources.size()==5);

        resources=q.getAgentsAsResources();
        assertTrue((resources!=null) && resources.size()==1);

    }

    public void testEdges() {

        List<Resource> resources;

        // The following tests check for the presence of opmv edges
        // inferred from the opmo ontology

        resources=q.getUsedArtifactsAsResources("pc1:p1");
        assertTrue((resources!=null) && resources.size()==4);

        resources=q.getGeneratedArtifactsAsResources("pc1:p1");
        assertTrue((resources!=null) && resources.size()==1);

        resources=q.getDerivedFromArtifactsAsResources("pc1:a5");
        System.out.println("Resources " + resources);
        assertTrue((resources!=null) && resources.size()==4);


        resources=q.getDerivedFromStarArtifactsAsResources("pc1:a5");
        System.out.println("Resources " + resources);
        assertTrue((resources!=null) && resources.size()==4);

        ResultSet results=q.getCauseWasDerivedFromArtifacts("pc1:a3");
        ResultSetFormatter.out(System.out, results);

    }

    public void testSave() {
        // this fails? Why?
        //ts.write(System.out);
    }

    


}
