package org.openprovenance.model;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.JAXBException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;



/**
 * Unit test for simple Provenance Challenge 1 like workflow.
 */

public class Reproduce1Test extends TestCase
{

    public static String APP_NS="http://openprovenance.org/reproduce";

    public static OPMFactory oFactory=new OPMFactory();

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public Reproduce1Test( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */


    static OPMGraph graph1;


    public void testReproduce1() throws JAXBException
    {

        OPMGraph graph=makeReproduce1Graph(oFactory);


        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/reproduce1.xml"),graph,true);

        
        //System.out.println(sw);

        graph1=graph;
        System.out.println("Reproduce1 Test asserting True");
        assertTrue( true );

        

    }

    public void decorate(Artifact a1,
                         Artifact a2,
                         Artifact a3,
                         Process p1,
                         Process p2,
                         Agent ag1,
                         Agent ag2) {};
    
    public OPMGraph makeReproduce1Graph(OPMFactory oFactory)
    {

        Collection<Account> black=Collections.singleton(oFactory.newAccount("black"));

        Agent ag1=oFactory.newAgent("ag1",
                                    black,
                                    "Greeting Script");

        Agent ag2=oFactory.newAgent("ag2",
                                    black,
                                    "CountWords Script");
        
        Process p1=oFactory.newProcess("p1",
                                       black,
                                       "greeting");

        Process p2=oFactory.newProcess("p2",
                                       black,
                                       "countwords");


        Artifact a1=oFactory.newArtifact("a1",
                                         black,
                                         "Input String1");

        Artifact a2=oFactory.newArtifact("a2",
                                         black,
                                         "Message File");

        Artifact a3=oFactory.newArtifact("a3",
                                         black,
                                         "Count File");


        decorate(a1,a2,a3,p1,p2,ag1,ag2);


        // Document doc=oFactory.builder.newDocument();
        // Element el=doc.createElementNS(APP_NS,"app:ignore");
        // Element el2=doc.createElementNS(APP_NS,"app:image");
        // el.appendChild(el2);
        // el2.appendChild(doc.createTextNode("http://www.ipaw.info/challenge/anatomy1.img"));
        // doc.appendChild(el);


        
        // oFactory.addValue(a1,el,"http://www.ipaw.info/imagePointer");


      
        // Document doc_=oFactory.builder.newDocument();
        // Element el_=doc_.createElementNS(APP_NS,"app:ignore");
        // el_.appendChild(doc_.createCDATASection("   {\"menu\": { \"id\": \"file\", \"value\": \"File\"}}"));
        // doc_.appendChild(el_);


        // oFactory.addValue(a1,"   {\"menu\": { \"id\": \"file\", \"value\": \"File\"}}","mime:application/json");


        // oFactory.addValue(a1,10,"http://www.ipaw.info/imagePointer3");

        // oFactory.addValue(a1,"foo","http://www.ipaw.info/imagePointer4");

        // oFactory.addValue(a1,1.0,"http://www.ipaw.info/imagePointer5");




        Used u1=oFactory.newUsed(p1,oFactory.newRole("in"),a1,black);
        Used u2=oFactory.newUsed(p2,oFactory.newRole("filename"),a2,black);


        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a2,oFactory.newRole("out"),p1,black);
        WasGeneratedBy wg2=oFactory.newWasGeneratedBy(a3,oFactory.newRole("out"),p2,black);


        WasDerivedFrom wd1=oFactory.newWasDerivedFrom(a2,a1,black);
        WasDerivedFrom wd2=oFactory.newWasDerivedFrom(a3,a2,black);

        WasControlledBy wcb1=oFactory.newWasControlledBy(p1,oFactory.newRole("script"),ag1,black);
        WasControlledBy wcb2=oFactory.newWasControlledBy(p2,oFactory.newRole("script"),ag2,black);


        OPMGraph graph=oFactory.newOPMGraph(black,
                                            new Overlaps[] { },
                                            new Process[] {p1,p2},
                                            new Artifact[] {a1,a2,a3},
                                            new Agent[] {ag1,ag2 },
                                            new Object[] {u1,u2,
                                                          wg1, wg2,
                                                          wd1,wd2,
                                                          wcb1,wcb2} );



        return graph;

   }
    

    /** Produces a dot representation
     * of created graph. */
    public void testReproduce1Conversion() throws java.io.FileNotFoundException,  java.io.IOException   {
        OPMToDot toDot=new OPMToDot(true); // with roles
        
        toDot.convert(graph1,"target/reproduce1.dot", "target/reproduce1.pdf");
    }




    public void testReproduce1Copy() throws java.io.FileNotFoundException,  java.io.IOException   {
        OPMFactory oFactory=new OPMFactory();

        OPMGraph graph2=oFactory.newOPMGraph(graph1);

        assertTrue( "self graph1 differ", graph1.equals(graph1) );        

        assertTrue( "self graph2 differ", graph2.equals(graph2) );        

        assertTrue( "graph1 graph2 differ", graph1.equals(graph2) );        
        
    }



}
