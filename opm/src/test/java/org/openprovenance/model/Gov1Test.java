package org.openprovenance.model;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.util.Collection;
import java.net.URI;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.JAXBException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.openprovenance.model.collections.CollectionFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.crypto.dsig.XMLSignature;

import org.openprovenance.model.security.Signer;


/**
 * Unit test for simple App.
 */
public class Gov1Test 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public Gov1Test( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */


    static OPMGraph graph1;

    static public OPMFactory oFactory=new OPMFactory();



    public void testGov1() throws Exception
    {
        OPMGraph graph=makeGov1Graph(oFactory);

        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/gov1.xml"),graph,true);

        
        //System.out.println(sw);

        graph1=graph;
        System.out.println("testOPM1 asserting True");
        assertTrue( true );




        OPMToDot toDot=new OPMToDot("src/test/resources/gov1.xml");        
        toDot.convert(graph1,"target/gov1.dot", "target/gov1.pdf");

        toDot=new OPMToDot("src/test/resources/gov2.xml");        
        toDot.convert(graph1,"target/gov2.dot", "target/gov2.pdf");

        toDot=new OPMToDot("src/test/resources/gov3.xml");        
        toDot.convert(graph1,"target/gov3.dot", "target/gov3.pdf");

        toDot=new OPMToDot("src/test/resources/gov4.xml");        
        toDot.convert(graph1,"target/gov4.dot", "target/gov4.pdf");


        //toDot.convert(graph1,"target/gov1.dot", "jpg", "target/gov1.jpg");
        //toDot.convert(graph1,"target/gov1.dot", "svg", "target/gov1.svg");


    }

    public OPMGraph makeGov1Graph(OPMFactory oFactory) throws Exception {

        CollectionFactory cFactory=new CollectionFactory(oFactory);

        Collection<Account> detailedLevel=Collections.singleton(oFactory.newAccount("detailedLevel"));
        Collection<Account> processLevel=Collections.singleton(oFactory.newAccount("processLevel"));
        Collection<Account> dataLevel=Collections.singleton(oFactory.newAccount("dataLevel"));

        List<Account> allLevels=new LinkedList();
        allLevels.addAll(dataLevel);
        allLevels.addAll(processLevel);
        allLevels.addAll(detailedLevel);

        List<Account> uptoProcessLevel=new LinkedList();
        uptoProcessLevel.addAll(processLevel);
        uptoProcessLevel.addAll(detailedLevel);




        Agent ag1=oFactory.newAgent("ag1",
                                    uptoProcessLevel,
                                    "Jeni");
        OTime otime1=oFactory.newInstantaneousTimeNow();
        
        Process p1=oFactory.newProcess("p1",
                                       uptoProcessLevel,
                                       "download");

        OTime otime2=oFactory.newInstantaneousTimeNow();
        Process p2=oFactory.newProcess("p2",
                                       uptoProcessLevel,
                                       "unzip");

        OTime otime3=oFactory.newInstantaneousTimeNow();
        Process p3=oFactory.newProcess("p3",
                                       uptoProcessLevel,
                                       "transform");
        OTime otime4=oFactory.newInstantaneousTimeNow();
        Process p4=oFactory.newProcess("p4",
                                       uptoProcessLevel,
                                       "select");
        OTime otime5=oFactory.newInstantaneousTimeNow();
        Process p5=oFactory.newProcess("p5",
                                       uptoProcessLevel,
                                       "access");




        Artifact a1=oFactory.newArtifact("a1",
                                         allLevels,
                                         "zip file");
        Artifact a2=oFactory.newArtifact("a2",
                                         allLevels,
                                         "local zip file");
        Artifact a3=oFactory.newArtifact("a3",
                                         allLevels,
                                         "GOR csv file");
        Artifact a4=oFactory.newArtifact("a4",
                                         allLevels,
                                         "region xslt");
        Artifact a5=oFactory.newArtifact("a5",
                                         allLevels,
                                         "GOR rdf file");
        Artifact a6=oFactory.newArtifact("a6",
                                         allLevels,
                                         "Government Office Regions");
        Artifact a7=oFactory.newArtifact("a7",
                                         allLevels,
                                         "Region H");

        oFactory.addAnnotation(a6,
                               oFactory.newPName("http://statistics.data.gov.uk/id/region"));
        oFactory.addAnnotation(a6,
                               oFactory.newType("http://rdfs.org/ns/void#DataSet"));
        oFactory.addAnnotation(a6,
                               oFactory.newEmbeddedAnnotation("ea1",
                                                              "foaf:homepage",
                                                              "http://statistics.data.gov.uk/doc/region",
                                                              null));

        oFactory.addAnnotation(a5,
                               oFactory.newType("http://rdfs.org/ns/void#DataSet"));
        oFactory.addReference(a5,
                              "http://www.jenitennison.com/log/2009-10-24/cache/GOR_DEC_2008_EN_NC.rdf",
                              "http://www.iana.org/assignments/media-types/application/xsl+rdf",
                              "CEDDDDEA");


        oFactory.addAnnotation(a3,
                               oFactory.newType("http://www.iana.org/assignments/media-types/application/excel"));
        oFactory.addReference(a3,
                              "http://www.jenitennison.com/log/2009-10-24/cache/GOR_DEC_2008_EN_NC.csv",
                              "http://www.iana.org/assignments/media-types/text/csv",
                              "HCEAAAC");


        oFactory.addAnnotation(a4,
                               oFactory.newType("http://www.w3.org/1999/XSL/Transform"));
        oFactory.addReference(a4,
                              "http://www.jenitennison.com/log/2009-10-24/cache/region.xsl",
                              "http://www.iana.org/assignments/media-types/application/xslt+xml",
                              "CDEBGFH");


        oFactory.addAnnotation(a2,
                               oFactory.newType("http://www.iana.org/assignments/media-types/application/zip"));
        oFactory.addReference(a2,
                              "http://www.jenitennison.com/log/2009-10-24/cache/government-office-regions.zip",
                              "http://www.iana.org/assignments/media-types/application/zip",
                              "EDABGFH");


        oFactory.addAnnotation(a1,
                               oFactory.newType("http://www.iana.org/assignments/media-types/application/zip"));
        oFactory.addReference(a1,
                              "http://www.ons.gov.uk/about-statistics/geography/products/geog-products-area/names-codes/administrative/government-office-regions.zip",
                              "http://www.iana.org/assignments/media-types/application/zip",
                              "EDABGFH");

        Artifact a3_A2=oFactory.newArtifact("a3_A2",
                                            detailedLevel,
                                            "A");
        Artifact a3_B2=oFactory.newArtifact("a3_B2",
                                            detailedLevel,
                                            "North East");
	
        Artifact a3_A8=oFactory.newArtifact("a3_A8",
                                            detailedLevel,
                                            "H");
        Artifact a3_B8=oFactory.newArtifact("a3_B8",
                                            detailedLevel,
                                            "London");
	
        Artifact a5_t1=oFactory.newArtifact("a5_t1",
                                            detailedLevel,
                                            "A is North East");
        Artifact a5_t2=oFactory.newArtifact("a5_t2",
                                            detailedLevel,
                                            "H is London");

        Artifact a6_t1=oFactory.newArtifact("a6_t1",
                                            detailedLevel,
                                            "A is North East");
        Artifact a6_t2=oFactory.newArtifact("a6_t2",
                                            detailedLevel,
                                            "H is London");
	
        
        Used u1=oFactory.newUsed(p1,oFactory.newRole("src"),a1,uptoProcessLevel);

        Used u2=oFactory.newUsed(p2,oFactory.newRole("in"),a2,uptoProcessLevel);

        Used u3=oFactory.newUsed(p3,oFactory.newRole("in"),a3,uptoProcessLevel);

        Used u4=oFactory.newUsed(p3,oFactory.newRole("in"),a4,uptoProcessLevel);

        Used u5=oFactory.newUsed(p4,oFactory.newRole("in"),a5,uptoProcessLevel);

        Used u6=oFactory.newUsed(p5,oFactory.newRole("in"),a6,uptoProcessLevel);




        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a2,oFactory.newRole("dst"),p1,uptoProcessLevel);

        WasGeneratedBy wg2=oFactory.newWasGeneratedBy(a3,oFactory.newRole("out"),p2,uptoProcessLevel);

        WasGeneratedBy wg3=oFactory.newWasGeneratedBy(a5,oFactory.newRole("out"),p3,uptoProcessLevel);

        WasGeneratedBy wg4=oFactory.newWasGeneratedBy(a6,oFactory.newRole("out"),p4,uptoProcessLevel);

        WasGeneratedBy wg5=oFactory.newWasGeneratedBy(a7,oFactory.newRole("out"),p5,uptoProcessLevel);


        WasDerivedFrom wd1=cFactory.newWasIdenticalTo("wd1",a2,a1,allLevels);
        WasDerivedFrom wd2=cFactory.newWasPartOf("wd2",a3,a2,allLevels);
        WasDerivedFrom wd3=oFactory.newWasDerivedFrom("wd3",a5,a3,"wasTransformationOf",allLevels);
        WasDerivedFrom wd3b=oFactory.newWasDerivedFrom("wd3",a5,a4,"wasStyledBy",allLevels);
        WasDerivedFrom wd4=oFactory.newWasDerivedFrom("wd4",a6,a5,"wasExtractedFrom",allLevels);
        WasDerivedFrom wd5=cFactory.newWasPartOf("wd5",a7,a6,allLevels);


        WasControlledBy wc1=oFactory.newWasControlledBy(p1,oFactory.newRole("user"),ag1,uptoProcessLevel);
        WasControlledBy wc2=oFactory.newWasControlledBy(p2,oFactory.newRole("user"),ag1,uptoProcessLevel);
        WasControlledBy wc3=oFactory.newWasControlledBy(p3,oFactory.newRole("user"),ag1,uptoProcessLevel);
        WasControlledBy wc4=oFactory.newWasControlledBy(p4,oFactory.newRole("user"),ag1,uptoProcessLevel);
        WasControlledBy wc5=oFactory.newWasControlledBy(p5,oFactory.newRole("user"),ag1,uptoProcessLevel);

        wc1.setStartTime(otime1);
        wc2.setStartTime(otime2);
        wc3.setStartTime(otime3);
        wc4.setStartTime(otime4);
        wc5.setStartTime(otime5);


        WasDerivedFrom wd3_A2=cFactory.newWasPartOf("wd3_A2",a3_A2,a3,detailedLevel);
        WasDerivedFrom wd3_B2=cFactory.newWasPartOf("wd3_B2",a3_B2,a3,detailedLevel);
        WasDerivedFrom wd3_A8=cFactory.newWasPartOf("wd3_A8",a3_A8,a3,detailedLevel);
        WasDerivedFrom wd3_B8=cFactory.newWasPartOf("wd3_B8",a3_B8,a3,detailedLevel);

        WasDerivedFrom wd5_t1a=oFactory.newWasDerivedFrom("wd5t1a",a5_t1,a3_A2,"hasSubjet",detailedLevel);        
        WasDerivedFrom wd5_t1b=oFactory.newWasDerivedFrom("wd5t1b",a5_t1,a3_B2,"hasObject",detailedLevel);        
        WasDerivedFrom wd5_t2a=oFactory.newWasDerivedFrom("wd5t2a",a5_t2,a3_A8,"hasSubjet",detailedLevel);        
        WasDerivedFrom wd5_t2b=oFactory.newWasDerivedFrom("wd5t2b",a5_t2,a3_B8,"hasObject",detailedLevel);        

        WasDerivedFrom wd5_t1=cFactory.newContained("wd5_t1",a5,a5_t1,detailedLevel);
        WasDerivedFrom wd5_t2=cFactory.newContained("wd5_t2",a5,a5_t2,detailedLevel);
        WasDerivedFrom wd6_t1i=cFactory.newWasIdenticalTo("wd6_t1i",a6_t1,a5_t1,detailedLevel);
        WasDerivedFrom wd6_t2i=cFactory.newWasIdenticalTo("wd6_t2i",a6_t2,a5_t2,detailedLevel);
        WasDerivedFrom wd6_t1=cFactory.newContained("wd6_t1",a6,a6_t1,detailedLevel);
        WasDerivedFrom wd6_t2=cFactory.newContained("wd6_t2",a6,a6_t2,detailedLevel);

        WasDerivedFrom wd7_t2=cFactory.newWasIdenticalTo("wd7_t2",a7,a6_t2,detailedLevel);

        OPMGraph graph=oFactory.newOPMGraph(allLevels,
                                            new Overlaps[] { },
                                            new Process[] {p1, p2, p3, p4, p5},
                                            new Artifact[] {a1,a2, a3, a4, a5, a6, a7, a3_A2, a3_B2, a3_A8, a3_B8, a5_t1, a5_t2, a6_t1, a6_t2},
                                            new Agent[] { ag1 },
                                            new Object[] {u1,u2, u3, u4, u5, u6, 
                                                          wg1, wg2, wg3, wg4, wg5,
                                                          wc1, wc2, wc3, wc4, wc5,
                                                          wd1, wd2, wd3, wd3b, wd4, wd5,
                                                          wd3_A2, wd3_B2, wd3_A8, wd3_B8, wd5_t1a, wd5_t1b, wd5_t2a, wd5_t2b, wd5_t1, wd5_t2,
                                                          wd6_t1, wd6_t2, wd6_t1i, wd6_t2i, wd7_t2} );




        return graph;
    }

    public List<Node> getSignature(String file) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        Document doc =
            dbf.newDocumentBuilder().parse(new FileInputStream(file));
        
        // Find Signature elements
        NodeList nl = 
            doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");

        List<Node> res=new LinkedList();
        for (int i=0; i<nl.getLength(); i++) {
            res.add(nl.item(i));
        }
        return res;
    }

    public void testGovSignature() throws JAXBException, Exception
    {

        OPMGraph graph=makeGov1Graph(oFactory);
        graph1=graph;


        getKeystoreConfig();
        Signer sig=new Signer(props.getProperty("build.keystoretype"),
                              props.getProperty("build.keystore"),
                              props.getProperty("build.keystorepass"),
                              "jeni",
                              props.getProperty("build.keypass"),
                              "Jeni");



        sig.sign(graph);
        
        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/gov-signature.xml"),graph,false);

        
        System.out.println("Gov Signature Test asserting True");
        assertTrue( true );

    }

    public void testCheckGovSignature() throws Exception {
        List<Node> nl=getSignature("target/gov-signature.xml");
        assertTrue(new Signer().validate(nl.get(nl.size()-1)));
    }

    static java.util.Properties props=null;
    static java.util.Properties getKeystoreConfig () throws java.io.IOException {
        if (props==null) {
            props = new java.util.Properties();
            java.net.URL url = Gov1Test.class.getClassLoader().getResource("keystore.config");
            props.load(url.openStream());
            System.out.println("Properties " + props);
        }
        return props;
    }
    
}
