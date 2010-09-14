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
 * Unit test for simple Provenance Challenge 1 like workflow.
 */
public class PharmaTest 
    extends TestCase
{
    public static OPMFactory oFactory=new OPMFactory();

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PharmaTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */


    static OPMGraph graph1;

    public void testPharma() throws JAXBException
    {

        OPMGraph graph=makePharmaGraph(oFactory);

        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/pharma.xml"),graph,true);

        
        //System.out.println(sw);

        graph1=graph;
        System.out.println("Pharma Test asserting True");
        assertTrue( true );


    }

    public OPMGraph makePharmaGraph(OPMFactory oFactory)
    {

        Collection<Account> black=Collections.singleton(oFactory.newAccount("black"));
        
        Process p1=oFactory.newProcess("p1",
                                       black,
                                       "online sales");

        Process p2=oFactory.newProcess("p2",
                                       black,
                                       "ship medicine");

        Process p3=oFactory.newProcess("p3",
                                       black,
                                       "manage stock");



        Artifact a1=oFactory.newArtifact("a1",
                                         black,
                                         "{\\\"alice\\\", \\\"25 upper st\\\", \\\"01/01/75\\\",\\n \\\"female\\\",123456,\\\"clomid\\\",1}");

        Artifact a2=oFactory.newArtifact("a2",
                                         black,
                                         "{\\\"alice\\\", \\\"25 upper st\\\",\\\"clomid\\\",1}");
        Artifact a3=oFactory.newArtifact("a3",
                                         black,
                                         "successful delivery");
        Artifact a4=oFactory.newArtifact("a4",
                                         black,
                                         "{\\\"alice\\\", \\\"clomid\\\",1}");
        Artifact a5=oFactory.newArtifact("a5",
                                         black,
                                         "{\\\"alice\\\", \\\"clomid\\\",1}");

        Used u1=oFactory.newUsed(p2,oFactory.newRole("in"),a2,black);
        Used u2=oFactory.newUsed(p3,oFactory.newRole("in"),a4,black);


        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a1,oFactory.newRole("out"),p1,black);
        WasGeneratedBy wg2=oFactory.newWasGeneratedBy(a3,oFactory.newRole("out"),p2,black);
        WasGeneratedBy wg3=oFactory.newWasGeneratedBy(a5,oFactory.newRole("out"),p3,black);


        WasDerivedFrom wd1=oFactory.newWasDerivedFrom(a2,a1,black);
        WasDerivedFrom wd2=oFactory.newWasDerivedFrom(a4,a1,black);


        WasTriggeredBy wt1=oFactory.newWasTriggeredBy(p2,p1,black);
        WasTriggeredBy wt2=oFactory.newWasTriggeredBy(p3,p1,black);



        OPMGraph graph=oFactory.newOPMGraph(black,
                                            new Overlaps[] { },
                                            new Process[] {p1,p2,p3},
                                            new Artifact[] {a1,a2,a3,a4,a5},
                                            new Agent[] { },
                                            new Object[] {u1,u2,
                                                          wg1,wg2,wg3,
                                                          wd1,wd2,
                                                          wt1,wt2} );



        return graph;
    }
    

    /** Produces a dot representation
     * of created graph. */
    public void testPharmaConversion() throws java.io.FileNotFoundException,  java.io.IOException   {
        OPMToDot toDot=new OPMToDot(); 
        
        toDot.convert(graph1,"target/pharma.dot", "target/pharma.pdf");
    }




    public void NotestCopy() throws java.io.FileNotFoundException,  java.io.IOException   {
        OPMFactory oFactory=new OPMFactory();

        OPMGraph graph2=oFactory.newOPMGraph(graph1);

        assertTrue( "self graph1 differ", graph1.equals(graph1) );        

        assertTrue( "self graph2 differ", graph2.equals(graph2) );        

        assertTrue( "graph1 graph2 differ", graph1.equals(graph2) );        
        
    }

}
