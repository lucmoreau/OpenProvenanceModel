package org.openprovenance.elmo;

import java.io.File;
import java.util.Collection;
import java.util.Collections;

import org.openprovenance.model.OPMGraph;
import org.openprovenance.model.OPMSerialiser;

import org.openrdf.elmo.ElmoModule;
import org.openrdf.elmo.ElmoManagerFactory;
import org.openrdf.elmo.ElmoManager;
import org.openrdf.elmo.sesame.SesameManagerFactory;
import org.openrdf.elmo.sesame.SesameManager;

import org.openrdf.rio.RDFFormat;
import javax.xml.bind.JAXBException;

public class Gov1Test extends org.openprovenance.model.Gov1Test {

    static String TEST_NS="http://example.com/gov1/";

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

    public Gov1Test( String testName )
    {
        super( testName );
        if (!initialized) {
            initializeElmo();
            initialized=true;
        }
    }

    Collection<String[]> prefixes=Collections.singleton(new String[]{"ex",TEST_NS});

    static OPMGraph graph1;

    public void testGov1() throws javax.xml.bind.JAXBException, java.io.FileNotFoundException, java.io.IOException {
        OPMGraph graph=makeGov1Graph(oFactory);

        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/gov1.xml"),graph,true);

        
        //System.out.println(sw);

        graph1=graph;
        System.out.println("testOPM1 asserting True");
        assertTrue( true );


    }

    public void testGov1SaveToN3() throws Exception {
        // reset counter to ensure that auto allocated ids are the same
        RdfOPMFactory.count=0;
        //graph1=makeGov1Graph(oFactory);
        
        File file = new File("target/gov1.n3");
        assert manager!=null;
        rHelper.dumpToRDF(file,(SesameManager)manager,RDFFormat.N3,prefixes);
    }

    GraphComparator gCompare=new GraphComparator();

    public void testCompareGov1Graphs() throws Exception {

        System.out.println("Running testCompareGov1Graphs");

        ElmoManager manager = factory.createElmoManager();

        gCompare.testCompareGraphs("target/gov1.xml",
                                   "target/gov1.n3",
                                   TEST_NS,
                                   RDFFormat.N3,
                                   rHelper,
                                   manager,
                                   "target/gov1-normalised-xml.xml",
                                   "target/gov1-normalised-rdf.xml");

    }

    public void testCompareGov1GraphCopies() throws Exception {

        System.out.println("Running testCompareGov1GraphCopies");
        RdfOPMFactory oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,TEST_NS));

        gCompare.testCompareGraphCopies(oFactory,
                                        "target/gov1.xml",
                                        "target/gov1-graph3.xml",
                                        "target/gov1-normalised-graph1.xml",
                                        "target/gov1-normalised-graph3.xml");

    }

    public void testGovSignature() throws Exception {
        // reset counter to ensure that auto allocated ids are the same
        RdfOPMFactory.count=0;
        multiplePlots=false;
        super.testGovSignature();
    }

    public void testCheckGovSignature() throws Exception {
        super.testCheckGovSignature();
    }




    public void testGov1SigSaveToN3() throws Exception {
        // reset counter to ensure that auto allocated ids are the same
        RdfOPMFactory.count=0;
        //graph1=makeGov1Graph(oFactory);
        
        File file = new File("target/gov1sig.n3");
        assert manager!=null;
        rHelper.dumpToRDF(file,(SesameManager)manager,RDFFormat.N3,prefixes);
    }


    public void testCompareGov1SigGraphs() throws Exception {

        System.out.println("Running testCompareGov1Graphs");

        ElmoManager manager = factory.createElmoManager();

        gCompare.testCompareGraphs("target/gov-signature.xml",
                                   "target/gov1sig.n3",
                                   TEST_NS,
                                   RDFFormat.N3,
                                   rHelper,
                                   manager,
                                   "target/gov1sig-normalised-xml.xml",
                                   "target/gov1sig-normalised-rdf.xml");

    }



}