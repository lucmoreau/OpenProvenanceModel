package org.openprovenance.reproduce;

import java.io.File;
import java.util.Collection;
import java.util.Collections;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


import org.openprovenance.model.OPMGraph;
import org.openprovenance.model.Agent;
import org.openprovenance.model.Process;
import org.openprovenance.model.Artifact;

import org.openprovenance.elmo.RdfObjectFactory;
import org.openprovenance.elmo.RdfOPMFactory;
import org.openprovenance.elmo.RepositoryHelper;
import org.openrdf.elmo.ElmoModule;
import org.openrdf.elmo.ElmoManagerFactory;
import org.openrdf.elmo.ElmoManager;
import org.openrdf.elmo.sesame.SesameManagerFactory;
import org.openrdf.elmo.sesame.SesameManager;

import org.openrdf.rio.RDFFormat;
import javax.xml.bind.JAXBException;

public class Reproduce2Test extends org.openprovenance.model.Reproduce1Test {

    static String TEST_NS="http://example.com/reproduce2/";

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

    public Reproduce2Test( String testName )
    {
        super( testName );
        if (!initialized) {
            initializeElmo();
            initialized=true;
        }
    }

    Collection<String[]> prefixes=Collections.singleton(new String[]{"ex",TEST_NS});

    static OPMGraph graph1;



    public void decorate(Artifact a1,
                         Artifact a2,
                         Artifact a3,
                         Process p1,
                         Process p2,
                         Agent ag1,
                         Agent ag2) {

        oFactory.addAnnotation(ag1,
                               oFactory.newPName(Utilities.swift_URI_PREFIX+ "greeting"));
        oFactory.addAnnotation(ag2,
                               oFactory.newPName(Utilities.swift_URI_PREFIX+ "countwords"));

        oFactory.addAnnotation(ag1,
                               oFactory.newType("http://openprovenance.org/primitives#primitive"));
        oFactory.addAnnotation(ag2,
                               oFactory.newType("http://openprovenance.org/primitives#primitive"));

        oFactory.addAnnotation(a1,
                               oFactory.newType("http://openprovenance.org/primitives#String"));
        Document doc=oFactory.builder.newDocument();
        Element el=doc.createElementNS(APP_NS,"app:ignore");
        //Element el2=doc.createElementNS(APP_NS,"app:image");
        //el.appendChild(el2);
        el.appendChild(doc.createTextNode("Hello People!"));
        doc.appendChild(el);
        oFactory.addValue(a1,el,"http://www.w3.org/TR/xmlschema-2/#string");


        oFactory.addAnnotation(a2,
                               oFactory.newType("http://openprovenance.org/primitives#File"));
        oFactory.addAnnotation(a3,
                               oFactory.newType("http://openprovenance.org/primitives#File"));

        oFactory.addAnnotation(a2,
                               oFactory.newEmbeddedAnnotation("an13","http://openprovenance.org/primitives#path", "foo.txt", null));


        oFactory.addAnnotation(a3,
                               oFactory.newEmbeddedAnnotation("an14","http://openprovenance.org/primitives#path", "bar.txt", null));


        oFactory.addAnnotation(p1,
                               oFactory.newType("http://openprovenance.org/primitives#greeting"));
        oFactory.addAnnotation(p2,
                               oFactory.newType("http://openprovenance.org/primitives#countwords"));


    };

    public void testReproduce1() throws javax.xml.bind.JAXBException {
        //super.testReproduce2();
    }

    public void testReproduce1Copy() throws java.io.FileNotFoundException,  java.io.IOException   {
        
    }


    public void testReproduce2SaveToN3() throws Exception {
        super.testReproduce1();

        
        File file = new File("target/reproduce2.n3");
        assert manager!=null;
        rHelper.dumpToRDF(file,(SesameManager)manager,RDFFormat.N3,prefixes);
    }

    public void testReproduce2SaveToRDFXML() throws Exception {

        
        File file = new File("target/reproduce2.xml");
        assert manager!=null;
        rHelper.dumpToRDF(file,(SesameManager)manager,RDFFormat.RDFXML,prefixes);
    }

    // GraphComparator gCompare=new GraphComparator();

    // public void testCompareReproduce2Graphs() throws Exception {

    //     System.out.println("Running testCompareReproduce2Graphs");

    //     ElmoManager manager = factory.createElmoManager();

    //     gCompare.testCompareGraphs("target/reproduce2.xml",
    //                                "target/reproduce2.n3",
    //                                TEST_NS,
    //                                RDFFormat.N3,
    //                                rHelper,
    //                                manager,
    //                                "target/reproduce2-normalised-xml.xml",
    //                                "target/reproduce2-normalised-rdf.xml");

    // }

    // public void testCompareReproduce2GraphCopies() throws Exception {

    //     System.out.println("Running testCompareReproduce2GraphCopies");
    //     RdfOPMFactory oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,TEST_NS));

    //     gCompare.testCompareGraphCopies(oFactory,
    //                                     "target/reproduce2.xml",
    //                                     "target/reproduce2-graph3.xml",
    //                                     "target/reproduce2-normalised-graph1.xml",
    //                                     "target/reproduce2-normalised-graph3.xml");

    // }




}