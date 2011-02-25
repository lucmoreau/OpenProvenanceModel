package org.openprovenance.jena;

import static org.openprovenance.jena.TripleStore.OPMO_NS;
import static org.openprovenance.jena.TripleStore.OPMV_NS;

import java.util.List;
import java.util.LinkedList;

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


/** Check owl-based reasoning on Starbucks example. */

public class StarbucksTest extends TestCase {

    public static String Starbucks_NS="http://example.com/starbucks/";

    public StarbucksTest (String testName) {
        super(testName);
    }

    TripleStore ts;
    Querier q;

    public void setUp () {
        System.out.println("**** Setting up*");
        ts= new TripleStore();

        q=ts.setUpReasonerForFile("file:src/test/resources/starbucks2.n3", "N3", "st",Starbucks_NS);
    }

    public void testNodes() {

        List<Resource> resources=q.getProcessesAsResources();

        assertTrue((resources!=null) && resources.size()==4);


        resources=q.getArtifactsAsResources();
        assertTrue((resources!=null) && resources.size()==6);

        resources=q.getAgentsAsResources();
        assertTrue((resources!=null) && resources.size()==0);
    }
    
    public void checkExpected(List<Resource> resources, List<String> expected, String reason) {
        System.out.println(reason + ": " + resources);
        assertTrue(reason, (resources!=null) && (expected!=null) && resources.size()==expected.size());
        for (Resource res: resources) {
            String uri=res.getURI();
            assertTrue(reason, expected.contains(uri));
        }
    }


    public void testEdges() {

        List<Resource> resources;
        List<String> expected;

        // The following tests check for the presence of opmv edges
        // inferred from the opmo ontology

        resources=q.getUsedArtifactsAsResources("st:p2");
        assertTrue((resources!=null) && resources.size()==2);

        resources=q.getGeneratedArtifactsAsResources("st:p2");
        assertTrue((resources!=null) && resources.size()==1);

        //wdf

        resources=q.getDerivedFromArtifactsAsResources("st:a4");
        expected=new LinkedList();
        expected.add("http://example.com/starbucks/a3");
        expected.add("http://example.com/starbucks/a6");
        checkExpected(resources,expected,"check wdf from a4");

        resources=q.getDerivedFromArtifactsAsResources("st:a6");
        expected=new LinkedList();
        expected.add("http://example.com/starbucks/a2");
        expected.add("http://example.com/starbucks/a1");
        checkExpected(resources,expected,"check wdf from a6");

        resources=q.getDerivedFromArtifactsAsResources("st:a3");
        expected=new LinkedList();
        expected.add("http://example.com/starbucks/a1");
        checkExpected(resources,expected,"check wdf from a3");


        resources=q.getDerivedFromArtifactsAsResources("st:a5");
        expected=new LinkedList();
        expected.add("http://example.com/starbucks/a1");
        checkExpected(resources,expected,"check wdf from a5");

        resources=q.getDerivedFromArtifactsAsResources("st:a1");
        expected=new LinkedList();
        checkExpected(resources,expected,"check wdf from a1");

        // wdf*

        resources=q.getDerivedFromStarArtifactsAsResources("st:a6");
        expected=new LinkedList();
        expected.add("http://example.com/starbucks/a2");
        expected.add("http://example.com/starbucks/a1");
        checkExpected(resources,expected,"check wdf* from a6");


        resources=q.getDerivedFromStarArtifactsAsResources("st:a3");
        expected=new LinkedList();
        expected.add("http://example.com/starbucks/a1");
        checkExpected(resources,expected,"check wdf* from a3");

        resources=q.getDerivedFromStarArtifactsAsResources("st:a1");
        expected=new LinkedList();
        checkExpected(resources,expected,"check wdf* from a1");

        resources=q.getDerivedFromStarArtifactsAsResources("st:a4");
        expected=new LinkedList();
        expected.add("http://example.com/starbucks/a6");
        expected.add("http://example.com/starbucks/a3");
        checkExpected(resources,expected,"==> PROBLEM WITH wdf* from a4");
        System.out.println("==> NOTE how in the previous example a2 and a1 were not found");



        // used*


        resources=q.getUsedStarArtifactsAsResources("st:p2");
        expected=new LinkedList();
        expected.add("http://example.com/starbucks/a6");
        expected.add("http://example.com/starbucks/a3");
        expected.add("http://example.com/starbucks/a1");
        expected.add("http://example.com/starbucks/a2");
        checkExpected(resources,expected,"check used* from p2");


        //

        ResultSet results=q.getCauseWasDerivedFromArtifacts("st:a3");
        ResultSetFormatter.out(System.out, results);

    }


    


}
