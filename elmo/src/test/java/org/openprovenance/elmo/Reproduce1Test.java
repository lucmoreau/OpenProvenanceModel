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

public class Reproduce1Test extends org.openprovenance.model.Reproduce1Test {

    static String TEST_NS="http://example.com/reproduce1/";

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

    public Reproduce1Test( String testName )
    {
        super( testName );
        if (!initialized) {
            initializeElmo();
            initialized=true;
        }
    }

    Collection<String[]> prefixes=Collections.singleton(new String[]{"ex",TEST_NS});

    static OPMGraph graph1;



    public void testReproduce1() throws javax.xml.bind.JAXBException {
        //super.testReproduce1();
    }

    public void testReproduce1Copy() throws java.io.FileNotFoundException,  java.io.IOException   {
        
    }


    public void testReproduce1SaveToN3() throws Exception {
        super.testReproduce1();

        
        File file = new File("target/reproduce1.n3");
        assert manager!=null;
        rHelper.dumpToRDF(file,(SesameManager)manager,RDFFormat.N3,prefixes);
    }

    GraphComparator gCompare=new GraphComparator();

    public void testCompareReproduce1Graphs() throws Exception {

        System.out.println("Running testCompareReproduce1Graphs");

        ElmoManager manager = factory.createElmoManager();

        gCompare.testCompareGraphs("target/reproduce1.xml",
                                   "target/reproduce1.n3",
                                   TEST_NS,
                                   RDFFormat.N3,
                                   rHelper,
                                   manager,
                                   "target/reproduce1-normalised-xml.xml",
                                   "target/reproduce1-normalised-rdf.xml");

    }

    public void testCompareReproduce1GraphCopies() throws Exception {

        System.out.println("Running testCompareReproduce1GraphCopies");
        RdfOPMFactory oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,TEST_NS));

        gCompare.testCompareGraphCopies(oFactory,
                                        "target/reproduce1.xml",
                                        "target/reproduce1-graph3.xml",
                                        "target/reproduce1-normalised-graph1.xml",
                                        "target/reproduce1-normalised-graph3.xml");

    }




}