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
public class Example10Test 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public Example10Test( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */


    static OPMGraph graph1;




    public void testDCProposal() throws Exception 
    {
        OPMFactory oFactory=new OPMFactory();

        Collection<Account> black=Collections.singleton(oFactory.newAccount("black"));
        Collection<Account> orange=Collections.singleton(oFactory.newAccount("orange"));
        
        List<Account> black_orange=new LinkedList();
        //black_orange.addAll(orange);
        black_orange.addAll(black);


        Process p=oFactory.newProcess("p",
                                       black,
                                       "P");


        Agent ag=oFactory.newAgent("C",
                                   black,
                                   "C");





        Artifact a1=oFactory.newArtifact("a1",
                                         black,
                                         "A1");
        Artifact a2=oFactory.newArtifact("a2",
                                         black,
                                         "A2");


        Used u1=oFactory.newUsed(p,oFactory.newRole("r1"),a1,black);

        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a2,oFactory.newRole("r2"),p,black);


        WasDerivedFrom wd1=oFactory.newWasDerivedFrom("wd1",a2,a1,"wasSameResourceAs",black);
        
        WasControlledBy wc1=oFactory.newWasControlledBy("wc1",p,oFactory.newRole("contributor"),
                                                        ag,
                                                        "wasActionOf",
                                                        black);


        OPMGraph graph=oFactory.newOPMGraph(black_orange,
                                            new Overlaps[] { },
                                            new Process[] {p},
                                            new Artifact[] {a1, a2},
                                            new Agent[] { ag },
                                            new Object[] {u1,
                                                          wg1,
                                                          wc1,
                                                          wd1,} );




        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/dc1.xml"),graph,true);

        
        //System.out.println(sw);

        graph1=graph;
        System.out.println("testOPM1 asserting True");
        assertTrue( true );


        OPMToDot toDot=new OPMToDot();
        
        toDot.convert(graph1,"target/dc1.dot", "target/dc1.pdf");


        
    }
    
}
