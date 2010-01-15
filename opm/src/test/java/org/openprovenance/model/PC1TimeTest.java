package org.openprovenance.model;
import java.io.File;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Date;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.JAXBException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit test for simple Provenance Challenge 1 like workflow.
 */
public class PC1TimeTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PC1TimeTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */


    static OPMGraph graph1;




    public void testPC1Time() throws JAXBException, InterruptedException
    {
        OPMFactory oFactory=new OPMFactory();

        Collection<Account> black=Collections.singleton(oFactory.newAccount("black"));
        
        Process p1=oFactory.newProcess("p1",
                                       black,
                                       "PC1 Workflow");



        Agent ag1=oFactory.newAgent("ag1",
                                    black,
                                    "John Doe");


        OTime otime0=oFactory.newInstantaneousTimeNow(); // testing with a now
        Thread.sleep(1000);

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

        Date date2=new Date(); //get the current time
        Used u1=oFactory.newUsed(p1,oFactory.newRole("img1"),a1,black);
        Used u2=oFactory.newUsed(p1,oFactory.newRole("hdr1"),a2,black);
        Used u3=oFactory.newUsed(p1,oFactory.newRole("imgRef"),a3,black);
        Used u4=oFactory.newUsed(p1,oFactory.newRole("hdrRef"),a4,black);

        OTime otime1=oFactory.newInstantaneousTime("2010-01-15T08:25:05.204Z"); // testing with a string date
        u1.setTime(otime1);
        u2.setTime(otime1);
        u3.setTime(otime1);
        u4.setTime(otime1);

        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a5,oFactory.newRole("x"),p1,black);

        OTime otime2=oFactory.newInstantaneousTime(date2);
        Thread.sleep(1000);
        wg1.setTime(otime2);



        WasDerivedFrom wd1=oFactory.newWasDerivedFrom(a5,a1,black);
        WasDerivedFrom wd2=oFactory.newWasDerivedFrom(a5,a2,black);
        WasDerivedFrom wd3=oFactory.newWasDerivedFrom(a5,a3,black);
        WasDerivedFrom wd4=oFactory.newWasDerivedFrom(a5,a4,black);

        OTime otime3=oFactory.newInstantaneousTimeNow(); // testing with a now


        WasControlledBy wc1=oFactory.newWasControlledBy(p1,oFactory.newRole("user"),ag1,black);
        wc1.setStartTime(otime0);
        wc1.setEndTime(otime3);


        OPMGraph graph=oFactory.newOPMGraph(black,
                                            new Overlaps[] { },
                                            new Process[] {p1},
                                            new Artifact[] {a1,a2,a3,a4,a5},
                                            new Agent[] { ag1 },
                                            new Object[] {u1,u2,u3,u4,
                                                          wg1,
                                                          wd1,wd2,wd3,wd4,
                                                          wc1} );





        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/pc1-time.xml"),graph,true);

        
        //System.out.println(sw);

        graph1=graph;
        System.out.println("PC1Time Test asserting True");
        assertTrue( true );
        
    }
    

    /** Produces a dot representation
     * of created graph. */
    public void testPC1TimeConversion() throws java.io.FileNotFoundException,  java.io.IOException   {
        OPMToDot toDot=new OPMToDot(true); // with roles
        
        toDot.convert(graph1,"target/pc1-time.dot", "target/pc1-time.pdf");
    }

}
