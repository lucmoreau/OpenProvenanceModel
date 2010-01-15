package org.openprovenance.elmo;
import java.io.File;
import java.io.StringWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.io.Reader;
import java.io.FileReader;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
//import javax.xml.bind.JAXBException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.xml.namespace.QName;

import org.openprovenance.rdf.Node;
import org.openprovenance.rdf.Edge;


import org.openrdf.elmo.ElmoModule;
import org.openrdf.elmo.ElmoManagerFactory;
import org.openrdf.elmo.ElmoManager;
import org.openrdf.elmo.sesame.SesameManagerFactory;
import org.openrdf.elmo.sesame.SesameManager;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.rdfxml.RDFXMLWriter;
import org.openrdf.rio.n3.N3Writer;
import org.openrdf.rio.ntriples.NTriplesWriter;
import org.openrdf.rio.RDFFormat;

import org.openprovenance.model.IndexedOPMGraph;
import org.openprovenance.model.OPMSerialiser;
import org.openprovenance.model.OPMDeserialiser;
import org.openprovenance.model.OPMFactory;
import org.openprovenance.model.Normalise;
import org.openprovenance.model.Artifact;
import org.openprovenance.model.Agent;
import org.openprovenance.model.Process;
import org.openprovenance.model.Used;
import org.openprovenance.model.Overlaps;
import org.openprovenance.model.Annotation;
import org.openprovenance.model.Account;
import org.openprovenance.model.OPMGraph;
import org.openprovenance.model.WasGeneratedBy;
import org.openprovenance.model.WasDerivedFrom;
import org.openprovenance.model.WasTriggeredBy;
import org.openprovenance.model.WasControlledBy;


/**
 * Unit test for Elmo.
 */
public class ElmoTest 
    extends TestCase
{

    static String TEST_NS="http://example.com/";
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ElmoTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */

    static ElmoManager manager;
    static ElmoManagerFactory factory;
    static RepositoryHelper rHelper;

    static OPMGraph graph1;
    static OPMGraph graph2;

    public void testElmo1() throws Exception {
        assert Edge.class.isInterface();
        assert Node.class.isInterface();

        ElmoModule module = new ElmoModule();

        rHelper=new RepositoryHelper();
        rHelper.registerConcepts(module);

        //module.addBehaviour(RdfArtifact.class);

        // what is this?
        //module.addFactory(RdfObjectFactory.class);

        factory = new SesameManagerFactory(module);
        manager = factory.createElmoManager();


        OPMFactory oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,TEST_NS));

        Account acc1=oFactory.newAccount("acc1");
        assert (acc1 instanceof RdfAccount);

        Account acc2=oFactory.newAccount("acc2");
        assert (acc2 instanceof RdfAccount);

        Collection<Account> accl1=Collections.singleton(acc1);
        Collection<Account> accl2=Collections.singleton(acc2);
        Collection<Account> accl12=new LinkedList();
        accl12.add(acc1);
        accl12.add(acc2);
        
        Artifact a1=oFactory.newArtifact("a1",accl1, "a1");
        assert (a1 instanceof Artifact);
        assert (a1 instanceof RdfArtifact);
        //assert (a1 instanceof Node);

        Artifact a2=oFactory.newArtifact("a2",accl1, "a2");
        assert (a2 instanceof Artifact);
        //assert (a2 instanceof Node);


        Agent ag1=oFactory.newAgent("ag1",accl2, "ag1");
        assert (ag1 instanceof RdfAgent);
        //assert (ag1 instanceof Node);

        Process p1=oFactory.newProcess("p1",accl2, "p1");
        assert (p1 instanceof RdfProcess);
        //assert (p1 instanceof Node);

        Process p2=oFactory.newProcess("p2",accl2, "p2");
        assert (p2 instanceof RdfProcess);
        //assert (p2 instanceof Node);
        
        Used u1=oFactory.newUsed("u1",p1,oFactory.newRole("r1","r1"),a1,accl2);
        assert (u1 instanceof Used);
        assert (u1 instanceof RdfUsed);
        //assert (u1 instanceof Edge);

        u1.setTime(oFactory.newInstantaneousTimeNow());


        WasGeneratedBy wg1=oFactory.newWasGeneratedBy("g1",a1,oFactory.newRole("r2","r2"),p1,accl12);
        assert (wg1 instanceof RdfWasGeneratedBy);
        //assert (wg1 instanceof Edge);

        wg1.setTime(oFactory.newInstantaneousTimeNow());

        WasDerivedFrom wd1=oFactory.newWasDerivedFrom("d1",a2,a1,accl2);
        assert (wd1 instanceof RdfWasDerivedFrom);
        //assert (wd1 instanceof Edge);


        WasTriggeredBy wt1=oFactory.newWasTriggeredBy("t1",p2,p1,accl12);
        assert (wt1 instanceof RdfWasTriggeredBy);
        //assert (wt1 instanceof Edge);

        WasControlledBy wc1=oFactory.newWasControlledBy("c1",p1,oFactory.newRole("r3","r3"),ag1,accl2);
        assert (wc1 instanceof RdfWasControlledBy);
        //assert (wc1 instanceof Edge);

        wc1.setStartTime(oFactory.newInstantaneousTimeNow());
        wc1.setEndTime(oFactory.newInstantaneousTimeNow());

        Annotation an1=oFactory.newAnnotation("an1",a1,"prop1","val1",accl1);

        Annotation an2=oFactory.newAnnotation("an2",a1,"prop2","val2",accl1);
        //

        oFactory.addAnnotation(a2,
                               oFactory.newEmbeddedAnnotation("an11","http://property.org/hasQuality", "bad", accl1));


        OPMGraph graph=oFactory.newOPMGraph("gr1",
                                            accl12,
                                            new Overlaps[] { },
                                            new Process[] {p1,p2},
                                            new Artifact[] {a1,a2},
                                            new Agent[] { ag1 },
                                            new Object[] {u1,
                                                          wg1,
                                                          wd1,
                                                          wc1,
                                                          wt1},
                                            new Annotation[] {an1,an2} );


        oFactory.addAnnotation(graph,
                               oFactory.newEmbeddedAnnotation("an12","http://property.org/graphKind", "joli", null));

        assert (graph instanceof RdfOPMGraph);

        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/repository.xml"),graph,true);

        OPMDeserialiser deserial=OPMDeserialiser.getThreadOPMDeserialiser();
        graph1=deserial.deserialiseOPMGraph(new File("target/repository.xml"));
    }

    public void setPrefixes(RDFHandler serialiser) throws org.openrdf.rio.RDFHandlerException {
            serialiser.handleNamespace("ex",TEST_NS);
    }

    Collection<String[]> prefixes=Collections.singleton(new String[]{"ex",TEST_NS});

    public void testDumptoRDFXML() throws Exception {
        File file = new File("target/repository.rdf");
        assert manager!=null;
        rHelper.dumpToRDF(file,(SesameManager)manager,RDFFormat.RDFXML,prefixes);
    }


    public void testDumpToN3() throws Exception {
        File file = new File("target/repository.n3");
        assert manager!=null;
        rHelper.dumpToRDF(file,(SesameManager)manager,RDFFormat.N3,prefixes);
    }


    public void testDumpToNTRIPLES() throws Exception {
        File file = new File("target/repository.ntriples");
        assert manager!=null;
        rHelper.dumpToRDF(file,(SesameManager)manager,RDFFormat.NTRIPLES,prefixes);
    }


    
    public void testRead() throws Exception {
        File file = new File("target/repository.rdf");
        assert manager!=null;

        ElmoManager manager = factory.createElmoManager();
        RdfOPMFactory oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,TEST_NS),
                                                 manager);

        rHelper.readFromRDF(file,null,(SesameManager)manager,RDFFormat.RDFXML);
        QName qname = new QName(TEST_NS, "gr1");

        OPMGraph oGraph=readOPMGraph(manager,
                                     oFactory,
                                     qname);

        OPMSerialiser.getThreadOPMSerialiser().serialiseOPMGraph(new File("target/foo.txt"),oGraph,true);
        graph2=oGraph;
    }

    public OPMGraph readOPMGraph(ElmoManager manager,
                                 RdfOPMFactory oFactory,
                                 QName qname) {
        Object o=manager.find(qname);
        org.openprovenance.rdf.OPMGraph gr=(org.openprovenance.rdf.OPMGraph)o;
        return oFactory.newOPMGraph(gr);

    }

    public void testCompareGraphs() throws Exception{ 
        OPMFactory oFactory=new OPMFactory();  // use a regular factory, not an rdf one
        
        Normalise normaliser=new Normalise(oFactory);

        // graph1: xml graph
        // graph2, from rdf graph
        normaliser.embedExternalAnnotations(graph1);


        //no need, since by nature, they are embedded in rdf
        //normaliser.embedExternalAnnotations(graph2);

        normaliser.sortGraph(graph1);
        normaliser.sortGraph(graph2);


        OPMSerialiser.getThreadOPMSerialiser().serialiseOPMGraph(new File("target/normalised-xml.xml"),graph1,true);
        OPMSerialiser.getThreadOPMSerialiser().serialiseOPMGraph(new File("target/normalised-rdf.xml"),graph2,true);



        assertTrue( "self graph differ", graph1.equals(graph1) );

        assertTrue( "self graph2  differ", graph2.equals(graph2) );

        assertTrue( "accounts differ", graph1.getAccounts().getAccount().equals(graph2.getAccounts().getAccount()) );

        assertTrue( "account overalps differ", graph1.getAccounts().getOverlaps().equals(graph2.getAccounts().getOverlaps()) );

        assertTrue( "accounts elements differ", graph1.getAccounts().equals(graph2.getAccounts()) );

        assertTrue( "processes elements differ", graph1.getProcesses().equals(graph2.getProcesses()) );

        assertTrue( "artifacts elements differ", graph1.getArtifacts().equals(graph2.getArtifacts()) );

        assertTrue( "edges elements differ", graph1.getCausalDependencies().equals(graph2.getCausalDependencies()) );

        if (graph1.getAgents()!=null && graph2.getAgents()!=null) {
            assertTrue( "agents elements differ", graph1.getAgents().equals(graph2.getAgents()) );
        } else {
            if (graph1.getAgents()!=null) {
                assertTrue( "agents elements differ, graph not null",  graph1.getAgents().getAgent().isEmpty());
                graph1.setAgents(null);
            } else if (graph2.getAgents()!=null) {
                assertTrue( "agents elements differ, graph not null",  graph2.getAgents().getAgent().isEmpty());
                graph2.setAgents(null);
            }
        }

        assertTrue( "graph differ", graph1.equals(graph2) );


    }

    public void testCompareGraphCopys() throws Exception {
        RdfOPMFactory oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,TEST_NS));

        OPMDeserialiser deserial=OPMDeserialiser.getThreadOPMDeserialiser();
        OPMGraph graph1=deserial.deserialiseOPMGraph(new File("target/repository.xml"));

        RdfOPMGraph graph3=oFactory.newOPMGraph(graph1);

        OPMSerialiser.getThreadOPMSerialiser().serialiseOPMGraph(new File("target/graph3.xml"),graph3,true);

        Normalise normaliser=new Normalise(oFactory);

        normaliser.sortGraph(graph1);
        normaliser.sortGraph(graph3);

        //normaliser.noAnnotation(graph1);
        //normaliser.noAnnotation(graph3);


        OPMSerialiser.getThreadOPMSerialiser().serialiseOPMGraph(new File("target/normalised-graph1.xml"),graph1,true);
        OPMSerialiser.getThreadOPMSerialiser().serialiseOPMGraph(new File("target/normalised-graph3.xml"),graph3,true);


        System.out.println("Now comparing");


        assertTrue( "self graph differ", graph1.equals(graph1) );

        assertTrue( "self graph3  differ", graph3.equals(graph3) );

        assertTrue( "accounts differ", graph1.getAccounts().getAccount().equals(graph3.getAccounts().getAccount()) );

        assertTrue( "account overalps differ", graph1.getAccounts().getOverlaps().equals(graph3.getAccounts().getOverlaps()) );

        assertTrue( "accounts elements differ", graph1.getAccounts().equals(graph3.getAccounts()) );

        assertTrue( "processes elements differ", graph1.getProcesses().equals(graph3.getProcesses()) );

        assertTrue( "artifacts elements differ", graph1.getArtifacts().equals(graph3.getArtifacts()) );

        assertTrue( "edges elements differ", graph1.getCausalDependencies().equals(graph3.getCausalDependencies()) );

        if (graph1.getAgents()!=null && graph3.getAgents()!=null) {
            assertTrue( "agents elements differ", graph1.getAgents().equals(graph3.getAgents()) );
        } else {
            if (graph1.getAgents()!=null) {
                assertTrue( "agents elements differ, graph not null",  graph1.getAgents().getAgent().isEmpty());
                graph1.setAgents(null);
            } else if (graph3.getAgents()!=null) {
                assertTrue( "agents elements differ, graph not null",  graph3.getAgents().getAgent().isEmpty());
                graph3.setAgents(null);
            }
        }


        assertTrue( "copy of graph differs from original", graph1.equals(graph3) );
    }
}


