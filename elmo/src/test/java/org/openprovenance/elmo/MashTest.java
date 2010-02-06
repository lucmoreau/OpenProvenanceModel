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

public class MashTest extends org.openprovenance.model.MashTest {

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

    public MashTest( String testName )
    {
        super( testName );
        if (!initialized) {
            initializeElmo();
            initialized=true;
        }
    }

    Collection<String[]> prefixes=Collections.singleton(new String[]{"ex",TEST_NS});

    static OPMGraph graph1;

    public void testMash1() throws Exception {
        //super.testMash1();
    }
                                   

    public void testMashSaveToN3() throws Exception {
        super.testMash1();
        
        // reset counter to ensure that auto allocated ids are the same
        //RdfOPMFactory.count=0;
        //graph1=makeMash1Graph(oFactory);
        
        File file = new File("target/mash.n3");
        assert manager!=null;
        rHelper.dumpToRDF(file,(SesameManager)manager,RDFFormat.N3,prefixes);
    }


    GraphComparator gCompare=new GraphComparator();

    public void testCompareMashGraphs() throws Exception {

        System.out.println("Running testCompareMashGraphs");

        ElmoManager manager = factory.createElmoManager();

        gCompare.testCompareGraphs("target/mash.xml",
                                   "target/mash.n3",
                                   TEST_NS,
                                   RDFFormat.N3,
                                   rHelper,
                                   manager,
                                   "target/mash-normalised-xml.xml",
                                   "target/mash-normalised-rdf.xml");

    }

    public void testCompareMashGraphCopies() throws Exception {

        System.out.println("Running testCompareMashGraphCopies");
        RdfOPMFactory oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,TEST_NS));

        gCompare.testCompareGraphCopies(oFactory,
                                        "target/mash.xml",
                                        "target/mash-graph3.xml",
                                        "target/mash-normalised-graph1.xml",
                                        "target/mash-normalised-graph3.xml");

    }




}