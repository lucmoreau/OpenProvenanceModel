package org.openprovenance.jena;

import java.util.Iterator;
import java.io.InputStream;
import java.io.OutputStream;

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

/** A class to set up a Jena triple store and a pellet reasoner  + a some helper functions.
    This code is inspired by Clark & Parsia example to use Pellet with Jena,
   and by an IBM example to write sparql queries in Jena. */

public class TripleStore {
    public static String OPMO_NS="http://openprovenance.org/model/opmo#";
    public static String OPMV_NS="http://purl.org/net/opmv/ns#";

    Reasoner reasoner;
    InfModel model;

    public void setUp() {
  	    // create Pellet reasoner
        reasoner = PelletReasonerFactory.theInstance().create();

        // create an empty model
        Model emptyModel = ModelFactory.createDefaultModel( );

        // create an inferencing model using Pellet reasoner
        model = ModelFactory.createInfModel( reasoner, emptyModel );
    }
    /** Initializes reasoner with OPMV and OPMO ontology, and loads rdf file.
        @param file url to file
        @param lang rdf language
        @param prefix prefix
        @param ns namespace
        @return Querier object
    */

    public Querier setUpReasonerForFile (String file, String lang, String prefix, String ns) {
        setUp();

        loadOPMVOntology();
        loadOPMOOntology();


        // read the files
        readFile( file, lang );

        Querier q=getQuerier();
        q.addPrefixes(prefix,ns);
        return q;
    }


    public void readFile (String url, String lang) {
        model.read(url,lang);
    }

    public void readFiles (String [] urls, String lang) {
        for (int i=0; i< urls.length; i++) {
            readFile(urls[i],lang);
        }
    }

    public void readResource (String name, String base,String lang) {
        InputStream stream = TripleStore.class.getResourceAsStream(name);
        model.read(stream,base,lang);
    }



    public static void printIterator(Iterator<?> i, String header) {
        System.out.println(header);
        for(int c = 0; c < header.length(); c++) {
            System.out.print("=");
        }
        
        System.out.println();
        
        if(i.hasNext()) {
	        while (i.hasNext()) 
	            System.out.println( i.next() );
        }       
        else
            System.out.println("<EMPTY>");
        
        System.out.println();
    }

    public ValidityReport validate() {
        return model.validate();
    }

    public Model getModel () {
        return model;
    }

    public void loadOPMVOntology() {
        readResource("../../../opmv-20101005.owl",null,"TURTLE");
    }

    public void loadOPMOOntology() {
        readResource("../../../opmo-20101012.owl",null,"RDF/XML");
    }


    public void runQueryAndFormatResult (String queryString) {
        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        // Output query results	
        ResultSetFormatter.out(System.out, results, query);

        // Important - free up resources used running the query
        qe.close();
    }
    public void write(OutputStream os) {
        model.write(os);
    }

    public Querier getQuerier () {
        return new Querier(model);
    }


}