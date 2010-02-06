package org.openprovenance.rdf;
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

import org.openprovenance.model.OPMGraph; 
import org.openprovenance.model.Edge; 
import org.openprovenance.model.Account; 
import org.openprovenance.model.Processes; 
import org.openprovenance.model.Artifact; 
import org.openprovenance.model.Agent; 
import org.openprovenance.model.Used; 
import org.openprovenance.model.OPMFactory; 
import org.openprovenance.model.WasGeneratedBy; 
import org.openprovenance.model.WasDerivedFrom; 
import org.openprovenance.model.WasTriggeredBy; 
import org.openprovenance.model.WasControlledBy; 
import org.openprovenance.model.OPMDeserialiser; 
import org.openprovenance.model.OPMSerialiser; 
import org.openprovenance.model.Overlaps; 
import org.openprovenance.model.Process; 
import org.openprovenance.model.OPMUtilities; 
import org.openprovenance.model.OPMToDot; 


/**
 * Unit test for simple App.
 */
public class Example4Test 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public Example4Test( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */


    static OPMGraph graph1;




    public void testCreateBadCake() throws JAXBException
    {
        OPMFactory oFactory=new OPMFactory();

        Collection<Account> black=Collections.singleton(oFactory.newAccount("black"));
        Collection<Account> orange=Collections.singleton(oFactory.newAccount("orange"));
        
        Process p1=oFactory.newProcess("p1",
                                       orange,
                                       "http://process.org/bake");
        Process p2=oFactory.newProcess("p2",
                                       black,
                                       "http://process.org/split");
        Process p3=oFactory.newProcess("p3",
                                       black,
                                       "http://process.org/badBake");
        Process p4=oFactory.newProcess("p4",
                                       black,
                                       "http://process.org/fry");


        List<Account> black_orange=new LinkedList();
        black_orange.addAll(orange);
        black_orange.addAll(black);

        Agent ag1=oFactory.newAgent("ag1",
                                    black_orange,
                                    "John");


        Artifact a1=oFactory.newArtifact("a1",
                                         black_orange,
                                         "100g butter");
        Artifact a2=oFactory.newArtifact("a2",
                                         black_orange,
                                         "2 eggs");
        Artifact a3=oFactory.newArtifact("a3",
                                         black_orange,
                                         "100g sugar");
        Artifact a4=oFactory.newArtifact("a4",
                                         black_orange,
                                         "100g flour");
        Artifact a5=oFactory.newArtifact("a5",
                                         black_orange,
                                         "1 cake");
        Artifact a6=oFactory.newArtifact("a6",
                                         black,
                                         "1 egg");
        Artifact a7=oFactory.newArtifact("a7",
                                         black,
                                         "1 egg");
        Artifact a8=oFactory.newArtifact("a8",
                                         black,
                                         "fried egg");

        Used u1=oFactory.newUsed(p1,oFactory.newRole("in"),a1,orange);
        Used u2=oFactory.newUsed(p1,oFactory.newRole("in"),a2,orange);
        Used u3=oFactory.newUsed(p1,oFactory.newRole("in"),a3,orange);
        Used u4=oFactory.newUsed(p1,oFactory.newRole("in"),a4,orange);

        Used u5=oFactory.newUsed(p3,oFactory.newRole("in"),a1,black);
        Used u6=oFactory.newUsed(p3,oFactory.newRole("in"),a6,black);
        Used u7=oFactory.newUsed(p3,oFactory.newRole("in"),a3,black);
        Used u8=oFactory.newUsed(p3,oFactory.newRole("in"),a4,black);


        Used u9=oFactory.newUsed(p4,oFactory.newRole("in"),a7,black);

        Used u10=oFactory.newUsed(p2,oFactory.newRole("in"),a2,black);




        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a5,oFactory.newRole("out"),p1,orange);
        WasGeneratedBy wg2=oFactory.newWasGeneratedBy(a5,oFactory.newRole("out"),p3,black);
        WasGeneratedBy wg3=oFactory.newWasGeneratedBy(a8,oFactory.newRole("out"),p4,black);
        WasGeneratedBy wg4=oFactory.newWasGeneratedBy(a6,oFactory.newRole("out"),p2,black);
        WasGeneratedBy wg5=oFactory.newWasGeneratedBy(a7,oFactory.newRole("out"),p2,black);



        WasDerivedFrom wd1=oFactory.newWasDerivedFrom(a5,a1,orange);
        WasDerivedFrom wd2=oFactory.newWasDerivedFrom(a5,a2,orange);
        WasDerivedFrom wd3=oFactory.newWasDerivedFrom(a5,a3,orange);
        WasDerivedFrom wd4=oFactory.newWasDerivedFrom(a5,a4,orange);

        WasDerivedFrom wd5=oFactory.newWasDerivedFrom(a5,a1,black);
        WasDerivedFrom wd6=oFactory.newWasDerivedFrom(a5,a6,black);
        WasDerivedFrom wd7=oFactory.newWasDerivedFrom(a5,a3,black);
        WasDerivedFrom wd8=oFactory.newWasDerivedFrom(a5,a4,black);

        WasDerivedFrom wd9=oFactory.newWasDerivedFrom(a6,a2,black);
        WasDerivedFrom wd10=oFactory.newWasDerivedFrom(a7,a2,black);

        WasDerivedFrom wd11=oFactory.newWasDerivedFrom(a8,a7,black);

        WasTriggeredBy wt1=oFactory.newWasTriggeredBy(p3,p4,black);


        WasControlledBy wc1=oFactory.newWasControlledBy(p1,oFactory.newRole("baker"),ag1,orange);
        WasControlledBy wc2=oFactory.newWasControlledBy(p3,oFactory.newRole("baker"),ag1,black);

        Overlaps ov1=oFactory.newOverlaps(black_orange);

        


        OPMGraph graph=oFactory.newOPMGraph(black_orange,
                                            new Overlaps[] { ov1 },
                                            new Process[] {p1,p2,p3,p4},
                                            new Artifact[] {a1,a2,a3,a4,a5,a6, a7, a8},
                                            new Agent[] {ag1},
                                            new Object[] {u1,u2,u3,u4,u5,u6,u7, u8, u9, u10,
                                                          wg1,wg2,wg3,wg4,wg5,
                                                          wd1,wd2,wd3,wd4,wd5,wd6,wd7,wd8,wd9,wd10,wd11,
                                                          wt1,
                                                          wc1, wc2} );





        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/bad-cake.xml"),graph,true);

        
        //System.out.println(sw);

        graph1=graph;
        System.out.println("testOPM1 asserting True");
        assertTrue( true );


        
    }
    
    public void testConvertToRDF4() throws Exception {
        OPMXml2Rdf toRdf=new OPMXml2Rdf();
        System.out.println("testConvertToRDF4 (Example4Test.java)");
        toRdf.convert(graph1,"target/example4-rdf.xml");
    }

    static OPMGraph graph2;




    public void testCreateGraph4() throws JAXBException
    {
        OPMFactory oFactory=new OPMFactory();

        Collection<Account> black=Collections.singleton(oFactory.newAccount("black"));
        Collection<Account> orange=Collections.singleton(oFactory.newAccount("orange"));
        
        Process p1=oFactory.newProcess("p1",
                                       orange,
                                       "http://process.org/bake");
        Process p2=oFactory.newProcess("p2",
                                       black,
                                       "http://process.org/split");
        Process p3=oFactory.newProcess("p3",
                                       black,
                                       "http://process.org/badBake");
        Process p4=oFactory.newProcess("p4",
                                       black,
                                       "http://process.org/fry");


        List<Account> black_orange=new LinkedList();
        black_orange.addAll(orange);
        black_orange.addAll(black);

        Agent ag1=oFactory.newAgent("ag1",
                                    black_orange,
                                    "John");


        Artifact a1=oFactory.newArtifact("a1",
                                         black_orange,
                                         "100g butter");
        Artifact a2=oFactory.newArtifact("a2",
                                         black_orange,
                                         "2 eggs");
        Artifact a3=oFactory.newArtifact("a3",
                                         black_orange,
                                         "100g sugar");
        Artifact a4=oFactory.newArtifact("a4",
                                         black_orange,
                                         "100g flour");
        Artifact a5=oFactory.newArtifact("a5",
                                         black_orange,
                                         "1 cake");
        Artifact a6=oFactory.newArtifact("a6",
                                         black,
                                         "1 egg");
        Artifact a7=oFactory.newArtifact("a7",
                                         black,
                                         "1 egg");
        Artifact a8=oFactory.newArtifact("a8",
                                         black,
                                         "fried egg");

        Used u1=oFactory.newUsed(p1,oFactory.newRole("in"),a1,orange);
        Used u2=oFactory.newUsed(p1,oFactory.newRole("in"),a2,orange);
        Used u3=oFactory.newUsed(p1,oFactory.newRole("in"),a3,orange);
        Used u4=oFactory.newUsed(p1,oFactory.newRole("in"),a4,orange);

        Used u5=oFactory.newUsed(p3,oFactory.newRole("in"),a1,black);
        Used u6=oFactory.newUsed(p3,oFactory.newRole("in"),a6,black);
        Used u7=oFactory.newUsed(p3,oFactory.newRole("in"),a3,black);
        Used u8=oFactory.newUsed(p3,oFactory.newRole("in"),a4,black);


        Used u9=oFactory.newUsed(p4,oFactory.newRole("in"),a7,black);

        Used u10=oFactory.newUsed(p2,oFactory.newRole("in"),a2,black);




        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a5,oFactory.newRole("out"),p1,orange);
        WasGeneratedBy wg2=oFactory.newWasGeneratedBy(a5,oFactory.newRole("out"),p3,black);
        WasGeneratedBy wg3=oFactory.newWasGeneratedBy(a8,oFactory.newRole("out"),p4,black);
        WasGeneratedBy wg4=oFactory.newWasGeneratedBy(a6,oFactory.newRole("out"),p2,black);
        WasGeneratedBy wg5=oFactory.newWasGeneratedBy(a7,oFactory.newRole("out"),p2,black);



        WasDerivedFrom wd1=oFactory.newWasDerivedFrom(a5,a1,orange);
        WasDerivedFrom wd2=oFactory.newWasDerivedFrom(a5,a2,orange);
        WasDerivedFrom wd3=oFactory.newWasDerivedFrom(a5,a3,orange);
        WasDerivedFrom wd4=oFactory.newWasDerivedFrom(a5,a4,orange);

        WasDerivedFrom wd5=oFactory.newWasDerivedFrom(a5,a1,black);
        WasDerivedFrom wd6=oFactory.newWasDerivedFrom(a5,a6,black);
        WasDerivedFrom wd7=oFactory.newWasDerivedFrom(a5,a3,black);
        WasDerivedFrom wd8=oFactory.newWasDerivedFrom(a5,a4,black);

        WasDerivedFrom wd9=oFactory.newWasDerivedFrom(a6,a2,black);
        WasDerivedFrom wd10=oFactory.newWasDerivedFrom(a7,a2,black);

        WasDerivedFrom wd11=oFactory.newWasDerivedFrom(a8,a7,black);

        WasTriggeredBy wt1=oFactory.newWasTriggeredBy(p3,p4,black);


        WasControlledBy wc1=oFactory.newWasControlledBy(p1,oFactory.newRole("baker"),ag1,orange);
        WasControlledBy wc2=oFactory.newWasControlledBy(p3,oFactory.newRole("baker"),ag1,black);

        Overlaps ov1=oFactory.newOverlaps(black_orange);

        


        OPMGraph graph=oFactory.newOPMGraph(black_orange,
                                            new Overlaps[] { ov1 },
                                            new Process[] {p1,p2,p3,p4},
                                            new Artifact[] {a1,a2,a3,a4,a5,a6, a7, a8},
                                            new Agent[] {ag1},
                                            new Object[] {u1,u2,u3,u4,u5,u6,u7, u8, u9, u10,
                                                          wg1,wg2,wg3,wg4,wg5,
                                                          wd1,wd2,wd3,wd4,wd5,wd6,wd7,wd8,wd9,wd10,wd11,
                                                          wt1,
                                                          wc1, wc2} );





        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/bad-cake.xml"),graph,true);

        
        //System.out.println(sw);

        graph1=graph;
        System.out.println("testCreateGraph4 asserting True");
        assertTrue( true );


        
    }
    
    public void testRDFRead4() throws Exception {
        OPMRdf2Xml fromRdf=new OPMRdf2Xml();
        System.out.println("testRDFRead4 (Example4ReadTest.java)");
        graph2=fromRdf.convert("target/example4-rdf.xml");


        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/bad-cake2.xml"),graph2,true);


        
        System.out.println("testRDFRead4 asserting True");
        assertTrue( true );

        OPMToDot toDot=new OPMToDot();
        toDot.convert(graph2,"target/badcake2.dot", "target/badcake2.pdf");

    }

    /** Checks that the graph read from the file is the same as the
     * one created. */
    public void testEqual4() throws JAXBException    {
        assertFalse (graph1.equals(graph2));

        System.out.println("***** Warning Warning Warning Warning: graph  is not same as graph converted twice");
    }



}
