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

public class PharmaTest extends org.openprovenance.model.PharmaTest {

    static String TEST_NS="http://example.com/";

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

    public PharmaTest( String testName )
    {
        super( testName );
        if (!initialized) {
            initializeElmo();
            initialized=true;
        }
    }

    Collection<String[]> prefixes=Collections.singleton(new String[]{"ex",TEST_NS});

    static OPMGraph graph1;

    public void testPharma() throws JAXBException {
        super.testPharma();
    }
                                   

    public void testPharmaSaveToN3() throws Exception {
        //super.testPharma();
        
        // reset counter to ensure that auto allocated ids are the same
        RdfOPMFactory.count=0;
        graph1=makePharmaGraph(oFactory);
        
        File file = new File("target/pharma.n3");
        assert manager!=null;
        rHelper.dumpToRDF(file,(SesameManager)manager,RDFFormat.N3,prefixes);
    }


    GraphComparator gCompare=new GraphComparator();

    public void testComparePharmaGraphs() throws Exception {

        System.out.println("Running testComparePharmaGraphs");

        ElmoManager manager = factory.createElmoManager();

        gCompare.testCompareGraphs("target/pharma.xml",
                                   "target/pharma.n3",
                                   TEST_NS,
                                   RDFFormat.N3,
                                   rHelper,
                                   manager,
                                   "target/pharma-normalised-xml.xml",
                                   "target/pharma-normalised-rdf.xml");

    }

    public void testComparePharmaGraphCopies() throws Exception {

        System.out.println("Running testComparePharmaGraphCopies");
        RdfOPMFactory oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,TEST_NS));

        gCompare.testCompareGraphCopies(oFactory,
                                        "target/pharma.xml",
                                        "target/pharma-graph3.xml",
                                        "target/pharma-normalised-graph1.xml",
                                        "target/pharma-normalised-graph3.xml");

    }




}