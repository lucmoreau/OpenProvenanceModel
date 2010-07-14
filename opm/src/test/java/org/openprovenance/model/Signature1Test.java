package org.openprovenance.model;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.StringWriter;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.JAXBException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.xml.crypto.dsig.XMLSignature;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.openprovenance.model.security.Signer;

/**
 * Unit test for simple Provenance Challenge 1 like workflow.
 */
public class Signature1Test 
    extends TestCase
{

    public static String APP_NS="http://www.ipaw.info/pc1";

    public static OPMFactory oFactory=new OPMFactory();

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public Signature1Test( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */


    static OPMGraph graph1;


    public void testSignature1() throws JAXBException, Exception
    {

        OPMGraph graph=makeSignature1Graph(oFactory);
        getKeystoreConfig();
        Signer sig=new Signer(oFactory,
                              props.getProperty("build.keystoretype"),
                              props.getProperty("build.keystore"),
                              props.getProperty("build.keystorepass"),
                              props.getProperty("build.alias"),
                              props.getProperty("build.keypass"),
                              "the first tester program");



        sig.sign(graph);
        
        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/signature1.xml"),graph,false);

        
        //System.out.println(sw);

        graph1=graph;
        System.out.println("Signature1 Test asserting True");
        assertTrue( true );

        

    }


    public void testSignature2() throws JAXBException, Exception
    {

        getKeystoreConfig();
        Signer sig=new Signer(oFactory,
                              props.getProperty("build.keystoretype"),
                              props.getProperty("build.keystore"),
                              props.getProperty("build.keystorepass"),
                              props.getProperty("build.alias"),
                              props.getProperty("build.keypass"),
                              "the second tester program");



        sig.sign(graph1);
        
        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/signature2.xml"),graph1,false);

        
        System.out.println("Signature2 Test asserting True");
        assertTrue( true );

        

    }

    public OPMGraph makeSignature1Graph(OPMFactory oFactory)
    {

        Collection<Account> black=Collections.singleton(oFactory.newAccount("black"));
        
        Process p1=oFactory.newProcess("p1",
                                       black,
                                       "PC1 Workflow");



        Agent ag1=oFactory.newAgent("ag1",
                                    black,
                                    "John Doe");



        Artifact a1=oFactory.newArtifact("a1",
                                         black,
                                         "Anatomy Image1");
        Artifact a2=oFactory.newArtifact("a2",
                                         black,
                                         "Anatomy Header1");
        Artifact a3=oFactory.newArtifact("a3",
                                         black,
                                         "Reference Image");
        Artifact a4=oFactory.newArtifact("a4",
                                         black,
                                         "Reference Header");
        Artifact a5=oFactory.newArtifact("a5",
                                         black,
                                         "Atlas X Graphic");


        Document doc=oFactory.builder.newDocument();
        Element el=doc.createElementNS(APP_NS,"app:ignore");
        Element el2=doc.createElementNS(APP_NS,"app:image");
        el.appendChild(el2);
        el2.appendChild(doc.createTextNode("http://www.ipaw.info/challenge/anatomy1.img"));
        doc.appendChild(el);


        
        oFactory.addValue(a1,el,"http://www.ipaw.info/imagePointer");


      
        Document doc_=oFactory.builder.newDocument();
        Element el_=doc_.createElementNS(APP_NS,"app:ignore");
        el_.appendChild(doc_.createCDATASection("   {\"menu\": { \"id\": \"file\", \"value\": \"File\"}}"));
        doc_.appendChild(el_);


        oFactory.addValue(a1,"   {\"menu\": { \"id\": \"file\", \"value\": \"File\"}}","mime:application/json");


        oFactory.addValue(a1,10,"http://www.ipaw.info/imagePointer3");

        oFactory.addValue(a1,"foo","http://www.ipaw.info/imagePointer4");

        oFactory.addValue(a1,1.0,"http://www.ipaw.info/imagePointer5");




        Used u1=oFactory.newUsed(p1,oFactory.newRole("img1"),a1,black);
        Used u2=oFactory.newUsed(p1,oFactory.newRole("hdr1"),a2,black);
        Used u3=oFactory.newUsed(p1,oFactory.newRole("imgRef"),a3,black);
        Used u4=oFactory.newUsed(p1,oFactory.newRole("hdrRef"),a4,black);


        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a5,oFactory.newRole("x"),p1,black);


        WasDerivedFrom wd1=oFactory.newWasDerivedFrom(a5,a1,black);
        WasDerivedFrom wd2=oFactory.newWasDerivedFrom(a5,a2,black);
        WasDerivedFrom wd3=oFactory.newWasDerivedFrom(a5,a3,black);
        WasDerivedFrom wd4=oFactory.newWasDerivedFrom(a5,a4,black);

        WasControlledBy wc1=oFactory.newWasControlledBy(p1,oFactory.newRole("user"),ag1,black);



        OPMGraph graph=oFactory.newOPMGraph(black,
                                            new Overlaps[] { },
                                            new Process[] {p1},
                                            new Artifact[] {a1,a2,a3,a4,a5},
                                            new Agent[] { ag1 },
                                            new Object[] {u1,u2,u3,u4,
                                                          wg1,
                                                          wd1,wd2,wd3,wd4,
                                                          wc1} );



        return graph;

   }
    

    /** Produces a dot representation
     * of created graph. */
    public void testSignature1Conversion() throws java.io.FileNotFoundException,  java.io.IOException   {
        OPMToDot toDot=new OPMToDot(true); // with roles
        
        toDot.convert(graph1,"target/signature1.dot", "target/signature1.pdf");
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

    public void testCheckLastSignature1() throws Exception {
        List<Node> nl=getSignature("target/signature1.xml");
        assertTrue(new Signer(oFactory).validate(nl.get(nl.size()-1)));
    }

    public void testCheckLastSignature2() throws Exception {
        List<Node> nl=getSignature("target/signature2.xml");
        // signature is invalidated because of the extra signature we have added
        assertFalse(new Signer(oFactory).validate(nl.get(0)));
        assertTrue(new Signer(oFactory).validate(nl.get(nl.size()-1)));
    }




    public void NOtestSignature1Copy() throws java.io.FileNotFoundException,  java.io.IOException   {
        OPMFactory oFactory=new OPMFactory();

        OPMGraph graph2=oFactory.newOPMGraph(graph1);

        assertTrue( "self graph1 differ", graph1.equals(graph1) );        

        assertTrue( "self graph2 differ", graph2.equals(graph2) );        

        assertTrue( "graph1 graph2 differ", graph1.equals(graph2) );        
        
    }

    static java.util.Properties props=null;
    static java.util.Properties getKeystoreConfig () throws java.io.IOException {
        if (props==null) {
            props = new java.util.Properties();
            java.net.URL url = Signature1Test.class.getClassLoader().getResource("keystore.config");
            props.load(url.openStream());
            System.out.println("Properties " + props);
        }
        return props;
    }


}
