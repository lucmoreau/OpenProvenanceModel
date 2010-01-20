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

public class List1Test extends org.openprovenance.model.List1Test {

    static String TEST_NS="http://example.com/list1/";

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

    public List1Test( String testName )
    {
        super( testName );
        if (!initialized) {
            initializeElmo();
            initialized=true;
        }
    }

    Collection<String[]> prefixes=Collections.singleton(new String[]{"ex",TEST_NS});

    static OPMGraph graph1;


    public void testList1() throws javax.xml.bind.JAXBException {
        //super.testList1();
    }


    public void testList1SaveToN3() throws Exception {
        super.testList1();

        
        File file = new File("target/list1.n3");
        assert manager!=null;
        rHelper.dumpToRDF(file,(SesameManager)manager,RDFFormat.N3,prefixes);
    }

    GraphComparator gCompare=new GraphComparator();

    public void testCompareList1Graphs() throws Exception {

        System.out.println("Running testCompareList1Graphs");

        ElmoManager manager = factory.createElmoManager();

        gCompare.testCompareGraphs("target/list1.xml",
                                   "target/list1.n3",
                                   TEST_NS,
                                   RDFFormat.N3,
                                   rHelper,
                                   manager,
                                   "target/list1-normalised-xml.xml",
                                   "target/list1-normalised-rdf.xml");

    }

    public void testCompareList1GraphCopies() throws Exception {

        System.out.println("Running testCompareList1GraphCopies");
        RdfOPMFactory oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,TEST_NS));

        gCompare.testCompareGraphCopies(oFactory,
                                        "target/list1.xml",
                                        "target/list1-graph3.xml",
                                        "target/list1-normalised-graph1.xml",
                                        "target/list1-normalised-graph3.xml");

    }




}


