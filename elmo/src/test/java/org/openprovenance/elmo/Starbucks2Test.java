package org.openprovenance.elmo;

import java.io.File;
import java.util.Collection;
import java.util.Collections;

import org.openprovenance.model.OPMGraph;

import org.openrdf.elmo.ElmoModule;
import org.openrdf.elmo.ElmoManagerFactory;
import org.openrdf.elmo.ElmoManager;
import org.openrdf.elmo.sesame.SesameManagerFactory;
import org.openrdf.elmo.sesame.SesameManager;

import org.openrdf.rio.RDFFormat;
import javax.xml.bind.JAXBException;

public class Starbucks2Test extends org.openprovenance.model.Starbucks2Test {

    static String TEST_NS="http://example.com/starbucks/";

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
        oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,TEST_NS));

    }

    public Starbucks2Test( String testName )
    {
        super( testName );
        if (!initialized) {
            initializeElmo();
            initialized=true;
        }
    }

    Collection<String[]> prefixes=Collections.singleton(new String[]{"st",TEST_NS});


    public void testStarbucks2() throws JAXBException {
        testStarbucks2(false);
    }

    public void testStarbucks2SaveToN3() throws Exception {

        // reset counter to ensure that auto allocated ids are the same
        RdfOPMFactory.count=0;

        // Note, I am reconstructing the graph, to be sure that its
        // facade conrresponds to the current manager (the maven
        // tester plugin seem to create a new instance of this tester
        // object for each test)
        //super.testStarbucks2();

        // reset counter to ensure that auto allocated ids are the same
        RdfOPMFactory.count=0;

        graph1=makeStarbucksGraph(oFactory,false);
        
        File file = new File("target/starbucks2.n3");
        assert manager!=null;
        rHelper.dumpToRDF(file,(SesameManager)manager,RDFFormat.N3,prefixes);
    }


    GraphComparator gCompare=new GraphComparator();

    public void testCompareStarbucks2Graphs() throws Exception {

        System.out.println("Running testCompareStarbucks2Graphs");

        ElmoManager manager = factory.createElmoManager();

        gCompare.testCompareGraphs("target/starbucks2.xml",
                                   "target/starbucks2.n3",
                                   TEST_NS,
                                   RDFFormat.N3,
                                   rHelper,
                                   manager,
                                   "target/starbucks2-normalised-xml.xml",
                                   "target/starbucks2-normalised-rdf.xml");

    }
    public void testStarbucks2Copy() throws java.io.FileNotFoundException,  java.io.IOException   {}

    public void DONT_DO_testCompareStarbucks2GraphCopies() throws Exception {

        System.out.println("Running testCompareStarbucks2GraphCopies");
        RdfOPMFactory oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,TEST_NS));

        gCompare.testCompareGraphCopies(oFactory,
                                        "target/starbucks2.xml",
                                        "target/starbucks2-graph3.xml",
                                        "target/starbucks2-normalised-graph1.xml",
                                        "target/starbucks2-normalised-graph3.xml");

    }



}