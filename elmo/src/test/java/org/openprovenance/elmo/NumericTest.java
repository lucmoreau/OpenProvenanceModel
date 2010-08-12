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

public class NumericTest extends org.openprovenance.model.NumericTest {

    static String TEST_NS="http://www.ipaw.info/num/";

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

    public NumericTest( String testName )
    {
        super( testName );
        if (!initialized) {
            initializeElmo();
            initialized=true;
        }
    }

    Collection<String[]> prefixes=Collections.singleton(new String[]{"num",TEST_NS});

    static OPMGraph graph1;

    public void testNumericSaveToN3() throws Exception {
        // Note, I am reconstructing the graph, to be sure that its
        // facade conrresponds to the current manager (the maven
        // tester plugin seem to create a new instance of this tester
        // object for each test)
        super.testNumeric();

        //graph1=makeNumericGraph(oFactory);
        
        File file = new File("target/numeric.n3");
        assert manager!=null;
        rHelper.dumpToRDF(file,(SesameManager)manager,RDFFormat.N3,prefixes);
    }


    GraphComparator gCompare=new GraphComparator();

    public void testCompareNumericGraphs() throws Exception {

        System.out.println("Running testCompareNumericGraphs");

        ElmoManager manager = factory.createElmoManager();

        gCompare.testCompareGraphs("target/numeric.xml",
                                   "target/numeric.n3",
                                   TEST_NS,
                                   RDFFormat.N3,
                                   rHelper,
                                   manager,
                                   "target/numeric-normalised-xml.xml",
                                   "target/numeric-normalised-rdf.xml");

    }

    public void testCopyNumeric() throws java.io.FileNotFoundException,  java.io.IOException   {
        // override this test
    }

    public void testCompareNumericGraphCopies() throws Exception {

        System.out.println("Running testCompareNumericGraphCopies");
        RdfOPMFactory oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,TEST_NS));

        gCompare.testCompareGraphCopies(oFactory,
                                        "target/numeric.xml",
                                        "target/numeric-graph3.xml",
                                        "target/numeric-normalised-graph1.xml",
                                        "target/numeric-normalised-graph3.xml");

    }



}