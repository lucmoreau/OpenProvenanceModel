/* This example is inspired by Clark & Parsia example to use Pellet with Jena,
   and by an IBM example to write sparql queries in Jena. */


package org.openprovenance.reproduce;

import java.util.Iterator;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.mindswap.pellet.jena.PelletReasonerFactory;

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


/**
 * An example to show how to use PelletReasoner as a Jena reasoner. The reasoner can
 * be directly attached to a plain RDF <code>Model</code> or attached to an <code>OntModel</code>.
 * This program shows how to do both of these operations and achieve the exact same results. 
 * 
 * @author Evren Sirin
 */
abstract public class Jena3Test extends TestCase {
    public Jena3Test (String testName) {
        super(testName);
    }

    public void testWithDefaultModel1() {
        usageWithDefaultModel();
    }
    
    static Model theModel;

    public static void usageWithDefaultModel() {
        System.out.println("Results with plain RDF Model");
        System.out.println("----------------------------");
        System.out.println();
        
        // ontology that will be used
        String ont1 = "file:src/test/resources/opm.owl";
        String ont2 = "file:src/test/resources/primitives3.owl";

        String ns1 = "http://openprovenance.org/ontology#";
        String ns2 = "http://openprovenance.org/primitives#";
        
  	    // create Pellet reasoner
        Reasoner reasoner = PelletReasonerFactory.theInstance().create();
        
        // create an empty model
        Model emptyModel = ModelFactory.createDefaultModel( );
        
        // create an inferencing model using Pellet reasoner
        InfModel model = ModelFactory.createInfModel( reasoner, emptyModel );
            
        // read the files
        model.read( "file:src/test/resources/reproduce2.rdf" );
        model.read( ont1 );
        model.read( ont2 );
        
        // print validation report
        ValidityReport report = model.validate();
        printIterator( report.getReports(), "Validation Results" );
        
        // print superclasses
        Resource c = model.getResource( ns1 + "Artifact" );         
        printIterator(model.listObjectsOfProperty(c, RDFS.subClassOf), "All super classes of " + c.getLocalName());

        c = model.getResource( ns2 + "Integer" );         
        printIterator(model.listObjectsOfProperty(c, RDFS.subClassOf), "All super classes of " + c.getLocalName());
        
        theModel=model;
    }


    
    public static void printIterator(Iterator<?> i, String header) {
        System.out.println(header);
        for(int c = 0; c < header.length(); c++)
            System.out.print("=");
        System.out.println();
        
        if(i.hasNext()) {
	        while (i.hasNext()) 
	            System.out.println( i.next() );
        }       
        else
            System.out.println("<EMPTY>");
        
        System.out.println();
    }

    public void testQuery2() throws java.io.FileNotFoundException, java.io.IOException {
        
        // Create a new query
        String queryString = 
            "PREFIX opm: <http://openprovenance.org/ontology#> " +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
            "SELECT ?proc " +
            "WHERE {" +
            "      ?proc rdf:type opm:Process " +
            "      }";

        runQuery(queryString);
    }


    public void testQuery3() throws java.io.FileNotFoundException, java.io.IOException {
        
        // Create a new query
        String queryString = 
            "PREFIX opm: <http://openprovenance.org/ontology#> " +
            "PREFIX eg: <http://example.com/reproduce2/>  " +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
            "SELECT ?a1 ?a2 " +
            "WHERE {" +
            "      ?a1 opm:_wasDerivedFrom_star ?a2 " +
            "      }";

        runQuery(queryString);
    }

    public void runQuery (String queryString) {
        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, theModel);
        ResultSet results = qe.execSelect();

        // Output query results	
        ResultSetFormatter.out(System.out, results, query);

        // Important - free up resources used running the query
        qe.close();
    }


    public void testQuery4() {

        Queries q=new Queries(theModel);

        ResultSet results=q.getUsedArtifacts("eg:p2");
        ResultSetFormatter.out(System.out, results);
        q.close();

        results=q.getGeneratedArtifacts("eg:p2");
        ResultSetFormatter.out(System.out, results);
        q.close();
        
    }


}
