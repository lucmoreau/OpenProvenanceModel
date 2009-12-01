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
public class PC1Test 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PC1Test( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */


    static OPMGraph graph1;




    public void testPC1() throws JAXBException
    {
        OPMFactory oFactory=new OPMFactory();

        Collection<Account> black=Collections.singleton(oFactory.newAccount("black"));
        
        Process p1=oFactory.newProcess("p1",
                                       black,
                                       "PC1 Workflow");



        Agent ag1=oFactory.newAgent("ag1",
                                    black,
                                    "Mike");


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
        Artifact a6=oFactory.newArtifact("a6",
                                         black,
                                         "Atlas Y Graphic");
        Artifact a7=oFactory.newArtifact("a7",
                                         black,
                                         "Atlas Z Graphic");

        Used u1=oFactory.newUsed(p1,oFactory.newRole("img1"),a1,black);
        Used u2=oFactory.newUsed(p1,oFactory.newRole("hdr1"),a2,black);
        Used u3=oFactory.newUsed(p1,oFactory.newRole("imgRef"),a3,black);
        Used u4=oFactory.newUsed(p1,oFactory.newRole("hdrRef"),a4,black);


        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a5,oFactory.newRole("x"),p1,black);
        WasGeneratedBy wg2=oFactory.newWasGeneratedBy(a6,oFactory.newRole("y"),p1,black);
        WasGeneratedBy wg3=oFactory.newWasGeneratedBy(a7,oFactory.newRole("z"),p1,black);


        WasDerivedFrom wd1x=oFactory.newWasDerivedFrom(a5,a1,black);
        WasDerivedFrom wd2x=oFactory.newWasDerivedFrom(a5,a2,black);
        WasDerivedFrom wd3x=oFactory.newWasDerivedFrom(a5,a3,black);
        WasDerivedFrom wd4x=oFactory.newWasDerivedFrom(a5,a4,black);

        WasDerivedFrom wd1y=oFactory.newWasDerivedFrom(a6,a1,black);
        WasDerivedFrom wd2y=oFactory.newWasDerivedFrom(a6,a2,black);
        WasDerivedFrom wd3y=oFactory.newWasDerivedFrom(a6,a3,black);
        WasDerivedFrom wd4y=oFactory.newWasDerivedFrom(a6,a4,black);

        WasDerivedFrom wd1z=oFactory.newWasDerivedFrom(a7,a1,black);
        WasDerivedFrom wd2z=oFactory.newWasDerivedFrom(a7,a2,black);
        WasDerivedFrom wd3z=oFactory.newWasDerivedFrom(a7,a3,black);
        WasDerivedFrom wd4z=oFactory.newWasDerivedFrom(a7,a4,black);



        WasControlledBy wc1=oFactory.newWasControlledBy(p1,oFactory.newRole("user"),ag1,black);





        OPMGraph graph=oFactory.newOPMGraph(black,
                                            new Overlaps[] { },
                                            new Process[] {p1},
                                            new Artifact[] {a1,a2,a3,a4,a5//,a6,a7
                                                           },
                                            new Agent[] { ag1 },
                                            new Object[] {wc1,u1,u2,u3,u4,
                                                          wg1,//wg2,wg3,
                                                          wd1x,wd2x,wd3x,wd4x,//wd1y,wd2y,wd3y,wd4y,wd1z,wd2z,wd3z,wd4z,
                                                          } );





        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/pc1.xml"),graph,true);

        
        //System.out.println(sw);

        graph1=graph;
        System.out.println("test PC1 asserting True");
        assertTrue( true );


        
    }
    

    /** Produces a dot representation
     * of created graph. */
    public void testPC1Conversion() throws java.io.FileNotFoundException,  java.io.IOException   {
        OPMToDot toDot=new OPMToDot();
        
        toDot.convert(graph1,"target/pc1.dot", "target/pc1.pdf");
    }

}
