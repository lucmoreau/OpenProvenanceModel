package org.openprovenance.jena;

import static org.openprovenance.jena.TripleStore.OPMO_NS;
import static org.openprovenance.jena.TripleStore.OPMV_NS;


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


public class Jena1Test extends TestCase {

    public static String PC1_NS="http://www.ipaw.info/pc1/";

    public Jena1Test (String testName) {
        super(testName);
    }


    public void testOPMV1() {

        TripleStore ts= new TripleStore();
        ts.setup();


        ts.loadOPMVOntology();


        // read the files
        ts.readFile( "file:src/test/resources/pc1-time.n3", "N3" );

        
        // print validation report
        ValidityReport report = ts.validate();
        ts.printIterator( report.getReports(), "Validation Results" );


        Model model=ts.getModel();

        // print superclasses
        Resource c = model.getResource( OPMV_NS + "Artifact" );         
        ts.printIterator(model.listObjectsOfProperty(c, RDFS.subClassOf), "All super classes of " + c.getLocalName());


        
    }



    public void testOPMV2() {

        TripleStore ts= new TripleStore();
        ts.setup();


        ts.loadOPMVOntology();
        ts.loadOPMOOntology();


        // read the files
        ts.readFile( "file:src/test/resources/pc1-time.n3", "N3" );

        
        // print validation report
        ValidityReport report = ts.validate();
        ts.printIterator( report.getReports(), "Validation Results" );

        Querier q=ts.getQuerier();
        q.addPrefixes("pc1",PC1_NS);

        ResultSet results=q.getProcesses();
        ResultSetFormatter.out(System.out, results);


        results=q.getArtifacts();
        ResultSetFormatter.out(System.out, results);


        results=q.getUsedArtifacts("pc1:p1");
        ResultSetFormatter.out(System.out, results);

    }


    


    // public void testQuery2() throws java.io.FileNotFoundException, java.io.IOException {
        
    //     // Create a new query
    //     String queryString = 
    //         "PREFIX opm: <http://openprovenance.org/ontology#> " +
    //         "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
    //         "SELECT ?proc " +
    //         "WHERE {" +
    //         "      ?proc rdf:type opm:Process " +
    //         "      }";

    //     runQuery(queryString);
    // }


    // public void testQuery3() throws java.io.FileNotFoundException, java.io.IOException {
        
    //     // Create a new query
    //     String queryString = 
    //         "PREFIX opm: <http://openprovenance.org/ontology#> " +
    //         "PREFIX eg: <http://example.com/reproduce2/>  " +
    //         "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
    //         "SELECT ?a1 ?a2 " +
    //         "WHERE {" +
    //         "      ?a1 opm:_wasDerivedFrom_star ?a2 " +
    //         "      }";

    //     runQuery(queryString);
    // }

    // public void runQuery (String queryString) {
    //     Query query = QueryFactory.create(queryString);

    //     // Execute the query and obtain results
    //     QueryExecution qe = QueryExecutionFactory.create(query, theModel);
    //     ResultSet results = qe.execSelect();

    //     // Output query results	
    //     ResultSetFormatter.out(System.out, results, query);

    //     // Important - free up resources used running the query
    //     qe.close();
    // }


    // public void testQuery4() {

    //     Queries q=new Queries(theModel);

    //     ResultSet results=q.getUsedArtifacts("eg:p2");
    //     ResultSetFormatter.out(System.out, results);
    //     q.close();

    //     results=q.getGeneratedArtifacts("eg:p2");
    //     ResultSetFormatter.out(System.out, results);
    //     q.close();
        
    // }


}
