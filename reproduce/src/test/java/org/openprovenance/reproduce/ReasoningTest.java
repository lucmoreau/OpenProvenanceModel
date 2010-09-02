package org.openprovenance.reproduce;

import javax.xml.bind.JAXBException;

import java.util.Iterator;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Hashtable;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.vocabulary.RDFS;

import org.openprovenance.model.OPMToDot;
import org.openprovenance.model.OPMFactory;
import org.openprovenance.model.OPMDeserialiser;
import org.openprovenance.model.OPMSerialiser;
import org.openprovenance.model.OPMGraph;
import org.openprovenance.model.IndexedOPMGraph;
import org.openprovenance.model.Artifact;
import org.openprovenance.model.Process;
import org.openprovenance.model.Account;

import org.openrdf.elmo.ElmoModule;
import org.openrdf.elmo.ElmoManagerFactory;
import org.openrdf.elmo.ElmoManager;
import org.openrdf.elmo.sesame.SesameManagerFactory;
import org.openrdf.elmo.sesame.SesameManager;
import org.openrdf.rio.RDFFormat;
import org.openprovenance.elmo.RdfOPMFactory;
import org.openprovenance.elmo.RdfObjectFactory;
import org.openprovenance.elmo.RepositoryHelper;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Reproducibility of PC1
 */
public class ReasoningTest extends TestCase {
    public static String NUM_NS="http://www.ipaw.info/num/";

    static OPMFactory oFactory=new OPMFactory();

    public PrimitiveEnvironment primEnv=new OpenProvenanceEnvironment().mock();


    static ElmoManager        manager=null;
    static ElmoManagerFactory factory=null;
    static RepositoryHelper   rHelper=null;
    static ElmoModule         module =null;
    static boolean initialized=false;
    static void initializeElmo() {
        module = new ElmoModule();
        rHelper=new RepositoryHelper();
        rHelper.registerConcepts(module);
        factory = new SesameManagerFactory(module);
        manager = factory.createElmoManager();
        oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,NUM_NS),manager);
        RdfOPMFactory.count=1000;

    }


    public ReasoningTest (String testName) {
        super(testName);
        if (!initialized) {
            initializeElmo();
            initialized=true;
        }
    }

    public void testWithModelReasoning() {
        loadModel();
    }
    
    static Model theModel;
    static Model theNewModel;
    static IndexedOPMGraph graph;

    public void loadModel() {
        
        // ontologies that will be used
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
        model.read( "file:src/test/resources/numeric-reasoning.n3", "N3" );
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
        try {
            loadOPMGraph();
        } catch (JAXBException je) {
        }

    }

    OPMFactory originalOFactory=new OPMFactory();

    public void loadOPMGraph() throws JAXBException    {
        OPMDeserialiser deserial=OPMDeserialiser.getThreadOPMDeserialiser();
        OPMGraph graph1=deserial.deserialiseOPMGraph(new File("src/test/resources/numeric-reasoning.xml"));

        // the graph here is constructed for convenience, since it's easier to navigate this than the triple store
        // hence, we use the normal factory.
        graph=new IndexedOPMGraph(originalOFactory,
                                  graph1);
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

    public void testReasoningQuery2() throws java.io.FileNotFoundException, java.io.IOException {
        
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


    public void testReasoningQuery3() throws java.io.FileNotFoundException, java.io.IOException {
        System.out.println("Print _used");
        // Create a new query
        String queryString = 
            "PREFIX opm: <http://openprovenance.org/ontology#> " +
            "PREFIX pc1: <http://www.ipaw.info/pc1/>  " +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
            "SELECT ?p ?a " +
            "WHERE {" +
            "      ?p opm:_used ?a " +
            "      }";

        runQuery(queryString);
    }


    public void testReasoningQuery3b() throws java.io.FileNotFoundException, java.io.IOException {
        System.out.println("Print _wasDerivedFrom_star");
        // Create a new query
        String queryString = 
            "PREFIX opm: <http://openprovenance.org/ontology#> " +
            "PREFIX pc1: <http://www.ipaw.info/pc1/>  " +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
            "SELECT ?a1 ?a2 " +
            "WHERE {" +
            "      ?a1 opm:_wasDerivedFrom_star ?a2 " +
            "      }";

        runQuery(queryString);
    }


    public void testReasoningQuery3c() throws java.io.FileNotFoundException, java.io.IOException {
        System.out.println("Print effectUsed-1");
        // Create a new query
        String queryString = 
            "PREFIX opm: <http://openprovenance.org/ontology#> " +
            "PREFIX pc1: <http://www.ipaw.info/pc1/>  " +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
            "SELECT ?u ?p " +
            "WHERE {" +
            "      ?u opm:effectUsed-1 ?p " +
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


    public void testReasoningQuery4() {

        Queries q=new Queries(theModel);
        q.addPrefixes("num",NUM_NS);

        List ll=q.getUsedArtifactsAsResources("num:p1");
        System.out.println(" found " + ll);
        q.close();
        assertTrue(ll.size()==2);


        ll=q.getGeneratedArtifactsAsResources("num:p2");
        System.out.println(" found " + ll);
        q.close();
        assertTrue(ll.size()==1);
        
    }


    public void testReasoningOrderedProcesses() {
        Queries q=new Queries(theModel);
        q.addPrefixes("num",NUM_NS);

        List<Resource> results = q.getProcessesAsResources(NUM_NS);

        System.out.println("Found Processes " + results);

        q.close();
    }


    public void testReasoningInputArtifacts() {
        Queries q=new Queries(theModel);
        q.addPrefixes("num",NUM_NS);

        List<Resource> results = q.getInputArtifactsAsResources(NUM_NS);

        System.out.println("==> Found Input Artifacts " + results);

        q.close();
    }


    public void testReasoningQuery5() {
        Queries q=new Queries(theModel);
        q.addPrefixes("num",NUM_NS);

        List<Resource> results = q.getProcessesWithPropAsResources("http://openprovenance.org/primitives#primitive",
                                                                   "http://openprovenance.org/primitives#align_warp");

        System.out.println("==> Found Processes for Primitive " + results);

        q.close();
    }


    public void testReasoningQuery6() {
        Queries q=new Queries(theModel);
        q.addPrefixes("num",NUM_NS);

        List<Literal> results = q.getResourcePropertyAsLiterals("http://www.ipaw.info/pc1/p1",
                                                                  "http://openprovenance.org/primitives#primitive");

        System.out.println("==> Found Value for property " + results);

        q.close();
    }


    public void testReasoningQuery7() {
        Queries q=new Queries(theModel);
        q.addPrefixes("num",NUM_NS);

        ResultSet results = q.getUsedArtifactsAndRoles("num:p1");
        ResultSetFormatter.out(System.out, results);

        q.close();
    }



    public void testReasoningQuery8() throws java.io.FileNotFoundException, java.io.IOException {
        System.out.println("Print ProcessPlus");
        // Create a new query
        String queryString = 
            "PREFIX opm: <http://openprovenance.org/ontology#> " +
            "PREFIX prim: <http://openprovenance.org/primitives#> " +
            "PREFIX pc1: <http://www.ipaw.info/pc1/>  " +
            "PREFIX num: <http://www.ipaw.info/num/>  " +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
            "SELECT ?p ?r " +
            "WHERE {" +
            "      ?p a opm:Role. " +
            "      ?p prim:isKindOf ?r. " +
            "      }";

        runQuery(queryString);
    }



    public void testReasoningQuery9() throws java.io.FileNotFoundException, java.io.IOException {
        System.out.println("Print ProcessPlus");
        // Create a new query
        String queryString = 
            "PREFIX opm: <http://openprovenance.org/ontology#> " +
            "PREFIX prim: <http://openprovenance.org/primitives#> " +
            "PREFIX pc1: <http://www.ipaw.info/pc1/>  " +
            "PREFIX num: <http://www.ipaw.info/num/>  " +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
            "SELECT ?a1 ?rt1 ?a2 ?rt2  ?p ?d ?dt " +
            "WHERE {" +
            "      ?a1 a opm:Artifact. " +
            "      ?a2 a opm:Artifact. " +
            "      ?a1 opm:_wasGeneratedBy ?p. " +
            "      ?p  opm:_used ?a2. " +
            "      ?a1  opm:effectWasGeneratedBy-1 ?g. " +
            "      ?g   opm:cause ?p. " +
            "      ?g   opm:role ?r1. " +
            "      ?r1  prim:isKindOf ?rt1. " +
            "      ?p   opm:effectUsed-1 ?u. " +
            "      ?u   opm:cause ?a2. " +
            "      ?u   opm:role ?r2. " +
            "      ?r2  prim:isKindOf ?rt2. " +
            "      ?p   prim:isKindOf ?pt. " +
            "      ?pt  ?rel ?d. " +
            "      ?d   prim:effects ?rt1. " +
            "      ?d   prim:causedBy ?rt2. " +
            "      ?d   rdf:type ?dt. " +
            "      }";

        runQuery(queryString);
    }



    public void testReasoningQuery10() throws java.io.FileNotFoundException, java.io.IOException {
        System.out.println("Print Dummy Inference");
        // Create a new query
        String queryString = 
            "PREFIX opm: <http://openprovenance.org/ontology#> " +
            "PREFIX prim: <http://openprovenance.org/primitives#> " +
            "PREFIX pc1: <http://www.ipaw.info/pc1/>  " +
            "PREFIX num: <http://www.ipaw.info/num/>  " +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
            "SELECT ?a1 ?a2 " +
            "WHERE {" +
            "      ?a1 prim:dummy ?a2. " +
            "      }";

        runQuery(queryString);
    }




    static List<Account> black=new LinkedList();


    public Hashtable<String,String> initPathTable(String where) {
        Hashtable<String,String> pathTable=new Hashtable();
        pathTable.put("a1",  where + "reference.img");
        pathTable.put("a2",  where + "reference.hdr");
        pathTable.put("a3",  where + "anatomy1.img");
        pathTable.put("a4",  where + "anatomy1.hdr");
        pathTable.put("a5",  where + "anatomy2.img");
        pathTable.put("a6",  where + "anatomy2.hdr");
        pathTable.put("a7",  where + "anatomy3.img");
        pathTable.put("a8",  where + "anatomy3.hdr");
        pathTable.put("a9",  where + "anatomy4.img");
        pathTable.put("a10", where + "anatomy4.hdr");

        return pathTable;

    }


    public void testReasoningDumpStore() throws Exception {
        System.out.println("Now saving triple store ");
        //theModel.write(new FileOutputStream("target/reasoning-inferred.n3"),"N3");
        System.out.println("Now saving triple store Done");
    }
    

}
