package org.openprovenance.reproduce;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;

import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


abstract public class Jena1Test extends TestCase {

    public Jena1Test (String testName) {
        super(testName);
    }


    public void testQuery() throws java.io.FileNotFoundException, java.io.IOException {
        
        // Open the bloggers RDF graph from the filesystem
        InputStream in = new FileInputStream(new File("src/test/resources/reproduce2.rdf"));

        // Create an empty in-memory model and populate it from the graph
        Model model = ModelFactory.createMemModelMaker().createModel("name");
        model.read(in,null); // null base URI, since model URIs are absolute
        in.close();

        // Create a new query
        String queryString = 
            "PREFIX opm: <http://openprovenance.org/ontology#> " +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
            "SELECT ?proc " +
            "WHERE {" +
            "      ?proc rdf:type opm:Process " +
            "      }";

        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        // Output query results	
        ResultSetFormatter.out(System.out, results, query);

        // Important - free up resources used running the query
        qe.close();
    }


}