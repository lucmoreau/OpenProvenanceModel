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

public class PC1AnnotationTest extends org.openprovenance.model.PC1AnnotationTest {

    static String TEST_NS="http://example.com/pc1-annotation/";

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

    public PC1AnnotationTest( String testName )
    {
        super( testName );
        if (!initialized) {
            initializeElmo();
            initialized=true;
        }
    }

    Collection<String[]> prefixes=Collections.singleton(new String[]{"ex",TEST_NS});

    static OPMGraph graph1;



    public void testPC1Annotation() throws javax.xml.bind.JAXBException {
        //super.testPC1Annotation();
    }

    public void testPC1AnnotationCopy() throws java.io.FileNotFoundException,  java.io.IOException   {
        
    }


    public void testPC1AnnotationSaveToN3() throws Exception {
        super.testPC1Annotation();

        
        File file = new File("target/pc1-annotation.n3");
        assert manager!=null;
        rHelper.dumpToRDF(file,(SesameManager)manager,RDFFormat.N3,prefixes);
    }

    GraphComparator gCompare=new GraphComparator();

    public void testComparePC1AnnotationGraphs() throws Exception {

        System.out.println("Running testComparePC1AnnotationGraphs");

        ElmoManager manager = factory.createElmoManager();

        gCompare.testCompareGraphs("target/pc1-annotation.xml",
                                   "target/pc1-annotation.n3",
                                   TEST_NS,
                                   RDFFormat.N3,
                                   rHelper,
                                   manager,
                                   "target/pc1-annotation-normalised-xml.xml",
                                   "target/pc1-annotation-normalised-rdf.xml");

    }

    public void testComparePC1AnnotationGraphCopies() throws Exception {

        System.out.println("Running testComparePC1AnnotationGraphCopies");
        RdfOPMFactory oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,TEST_NS));

        gCompare.testCompareGraphCopies(oFactory,
                                        "target/pc1-annotation.xml",
                                        "target/pc1-annotation-graph3.xml",
                                        "target/pc1-annotation-normalised-graph1.xml",
                                        "target/pc1-annotation-normalised-graph3.xml");

    }




}