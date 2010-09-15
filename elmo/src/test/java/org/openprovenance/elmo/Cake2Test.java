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

public class Cake2Test extends org.openprovenance.model.Cake2Test {

    static String TEST_NS="http://example.com/cake2/";

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

    public Cake2Test( String testName )
    {
        super( testName );
        if (!initialized) {
            initializeElmo();
            initialized=true;
        }
    }

    Collection<String[]> prefixes=Collections.singleton(new String[]{"ex",TEST_NS});

    static OPMGraph graph1;


    public void testCake2() throws javax.xml.bind.JAXBException {
        //super.testCake2();
    }


    public void testCake2SaveToN3() throws Exception {
        super.testCake2();

        
        File file = new File("target/cake2.n3");
        assert manager!=null;
        rHelper.dumpToRDF(file,(SesameManager)manager,RDFFormat.N3,prefixes);
    }




    GraphComparator gCompare=new GraphComparator();

    public void testCompareCake2Graphs() throws Exception {

        System.out.println("Running testCompareCake2Graphs");

        ElmoManager manager = factory.createElmoManager();

        gCompare.testCompareGraphs("target/cake2.xml",
                                   "target/cake2.n3",
                                   TEST_NS,
                                   RDFFormat.N3,
                                   rHelper,
                                   manager,
                                   "target/cake2-normalised-xml.xml",
                                   "target/cake2-normalised-rdf.xml");

    }

    public void testCompareCake2GraphCopies() throws Exception {

        System.out.println("Running testCompareCake2GraphCopies");
        RdfOPMFactory oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,TEST_NS));

        gCompare.testCompareGraphCopies(oFactory,
                                        "target/cake2.xml",
                                        "target/cake2-graph3.xml",
                                        "target/cake2-normalised-graph1.xml",
                                        "target/cake2-normalised-graph3.xml");

    }




}