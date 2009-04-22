package org.openprovenance.model;
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


/**
 * Unit test for simple App.
 */
public class Example5Test 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public Example5Test( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */


    static OPMGraph graph1;
    static OPMGraph graph2;




    public void testCreateSenderReceiverGraph() throws JAXBException
    {
        OPMFactory oFactory=new OPMFactory();

        Collection<Account> black=Collections.singleton(oFactory.newAccount("black"));
        Collection<Account> original=Collections.singleton(oFactory.newAccount("original"));
        Collection<Account> sender=Collections.singleton(oFactory.newAccount("sender"));
        Collection<Account> receiver=Collections.singleton(oFactory.newAccount("receiver"));
        Collection<Account> allAccounts=new LinkedList();
        allAccounts.addAll(black);
        allAccounts.addAll(original);
        allAccounts.addAll(sender);
        allAccounts.addAll(receiver);
        
        
        Process p1=oFactory.newProcess("p1",
                                       black,
                                       "P1");
        Process p2=oFactory.newProcess("p2",
                                       black,
                                       "P2");

        Process p3=oFactory.newProcess("p3",
                                       black,
                                       "message producer");
        Process p4=oFactory.newProcess("p4",
                                       black,
                                       "message receiver");


        List<Account> sender_receiver=new LinkedList();
        sender_receiver.addAll(sender);
        sender_receiver.addAll(receiver);

        Artifact a1=oFactory.newArtifact("a1",
                                         sender,
                                         "A1s");
        Artifact a2=oFactory.newArtifact("a2",
                                         sender,
                                         "A2s");
        Artifact a3=oFactory.newArtifact("a3",
                                         sender,
                                         "A3s");

        Artifact a4=oFactory.newArtifact("a4",
                                         sender_receiver,
                                         "msg");

        Artifact a5=oFactory.newArtifact("a5",
                                         receiver,
                                         "A1r");
        Artifact a6=oFactory.newArtifact("a6",
                                         receiver,
                                         "A2r");
        Artifact a7=oFactory.newArtifact("a7",
                                         receiver,
                                         "A3r");

        Artifact a8=oFactory.newArtifact("a8",
                                         original,
                                         "A1");
        Artifact a9=oFactory.newArtifact("a9",
                                         original,
                                         "A2");
        Artifact a10=oFactory.newArtifact("a10",
                                         original,
                                         "A3");


        Used u1=oFactory.newUsed(p3,oFactory.newRole("in"),a1,sender);
        Used u2=oFactory.newUsed(p3,oFactory.newRole("in"),a2,sender);
        Used u3=oFactory.newUsed(p3,oFactory.newRole("in"),a3,sender);

        Used u4=oFactory.newUsed(p4,oFactory.newRole("in"),a4,receiver);

        Used u5=oFactory.newUsed(p2,oFactory.newRole("in"),a5,receiver);
        Used u6=oFactory.newUsed(p2,oFactory.newRole("in"),a6,receiver);
        Used u7=oFactory.newUsed(p2,oFactory.newRole("in"),a7,receiver);


        Used u8=oFactory.newUsed(p2,oFactory.newRole("in"),a8,original);
        Used u9=oFactory.newUsed(p2,oFactory.newRole("in"),a9,original);
        Used u10=oFactory.newUsed(p2,oFactory.newRole("in"),a10,original);


        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a4,oFactory.newRole("out"),p3,sender);
        WasGeneratedBy wg2=oFactory.newWasGeneratedBy(a5,oFactory.newRole("out"),p4,receiver);
        WasGeneratedBy wg3=oFactory.newWasGeneratedBy(a6,oFactory.newRole("out"),p4,receiver);
        WasGeneratedBy wg4=oFactory.newWasGeneratedBy(a7,oFactory.newRole("out"),p4,receiver);

        WasGeneratedBy wg5=oFactory.newWasGeneratedBy(a1,oFactory.newRole("out"),p1,sender);
        WasGeneratedBy wg6=oFactory.newWasGeneratedBy(a2,oFactory.newRole("out"),p1,sender);
        WasGeneratedBy wg7=oFactory.newWasGeneratedBy(a3,oFactory.newRole("out"),p1,sender);

        WasGeneratedBy wg8=oFactory.newWasGeneratedBy(a8,oFactory.newRole("out"),p1,original);
        WasGeneratedBy wg9=oFactory.newWasGeneratedBy(a9,oFactory.newRole("out"),p1,original);
        WasGeneratedBy wg10=oFactory.newWasGeneratedBy(a10,oFactory.newRole("out"),p1,original);



        WasDerivedFrom wd1=oFactory.newWasDerivedFrom(a5,a1,sender_receiver);
        WasDerivedFrom wd2=oFactory.newWasDerivedFrom(a6,a2,sender_receiver);
        WasDerivedFrom wd3=oFactory.newWasDerivedFrom(a7,a3,sender_receiver);

        //WasTriggeredBy wt1=oFactory.newWasTriggeredBy(p3,p4,black);
        //Overlaps ov1=oFactory.newOverlaps(black_black);

        


        OPMGraph graph=oFactory.newOPMGraph(allAccounts,
                                            new Overlaps[] {  },
                                            new Process[] {p1,p2,p3,p4},
                                            new Artifact[] {a1,a2,a3,a4,a5,a6, a7, a8, a9, a10},
                                            new Agent[] {},
                                            new Object[] {u1,u2,u3,u4,u5,u6,u7,u8,u9,u10, 
                                                          wg1,wg2,wg3,wg4,wg5,wg6,wg7,wg8,wg9,wg10,
                                                          wd1,wd2,wd3
                                                         } );





        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/pasoa1.xml"),graph,true);

        
        //System.out.println(sw);

        graph1=graph;
        System.out.println("testOPM1 asserting True");
        assertTrue( true );


        
    }
    
    /** Deserialises an OPM graph. */
    public void testLoadAnotherGraph() throws JAXBException    {
        OPMDeserialiser deserial=OPMDeserialiser.getThreadOPMDeserialiser();
        OPMGraph graph=deserial.deserialiseOPMGraph(new File("src/test/resources/example3.xml"));

        graph2=graph;

    }

    /** Checks that the graph read from the file is the same as the
     * one created. */
    public void testGraphEquality() throws JAXBException    {
        assertTrue (graph1.equals(graph2));
    }

    /** Produces a dot representation
     * of created graph. */
    public void testConversion6() throws java.io.FileNotFoundException,  java.io.IOException   {
        OPMToDot toDot=new OPMToDot("src/test/resources/pasoaConfig.xml");        
        toDot.convert(graph1,"target/pasoa1.dot", "target/pasoa1.pdf");
    }




}
