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

public class PC1FullTest extends org.openprovenance.model.PC1FullTest {

    static String TEST_NS="http://www.ipaw.info/pc1/";

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

    public PC1FullTest( String testName )
    {
        super( testName );
        if (!initialized) {
            initializeElmo();
            initialized=true;
        }
    }

    Collection<String[]> prefixes=Collections.singleton(new String[]{"pc1",TEST_NS});

    static OPMGraph graph1;

    public void testPC1FullSaveToN3() throws Exception {
        // Note, I am reconstructing the graph, to be sure that its
        // facade conrresponds to the current manager (the maven
        // tester plugin seem to create a new instance of this tester
        // object for each test)
        super.testPC1Full();

        //graph1=makePC1FullGraph(oFactory);
        
        File file = new File("target/pc1-full.n3");
        assert manager!=null;
        rHelper.dumpToRDF(file,(SesameManager)manager,RDFFormat.N3,prefixes);
    }


    GraphComparator gCompare=new GraphComparator();

    public void testComparePC1FullGraphs() throws Exception {

        System.out.println("Running testComparePC1FullGraphs");

        ElmoManager manager = factory.createElmoManager();

        gCompare.testCompareGraphs("target/pc1-full.xml",
                                   "target/pc1-full.n3",
                                   TEST_NS,
                                   RDFFormat.N3,
                                   rHelper,
                                   manager,
                                   "target/pc1-full-normalised-xml.xml",
                                   "target/pc1-full-normalised-rdf.xml");

    }

    public void testCopyPC1Full() throws java.io.FileNotFoundException,  java.io.IOException   {
        // override this test
    }

    public void testComparePC1FullGraphCopies() throws Exception {

        System.out.println("Running testComparePC1FullGraphCopies");
        RdfOPMFactory oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,TEST_NS));

        gCompare.testCompareGraphCopies(oFactory,
                                        "target/pc1-full.xml",
                                        "target/pc1-full-graph3.xml",
                                        "target/pc1-full-normalised-graph1.xml",
                                        "target/pc1-full-normalised-graph3.xml");

    }



}