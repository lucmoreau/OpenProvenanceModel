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
public class Cake1Test 
    extends TestCase
{

    static public OPMFactory oFactory=new OPMFactory();

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public Cake1Test( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */


    static OPMGraph graph1;



    public void testCake1() throws JAXBException
    {

        OPMGraph graph=makeCake1Graph(oFactory);



        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/cake1.xml"),graph,true);

        
        //System.out.println(sw);

        graph1=graph;
        System.out.println("test Cake1 asserting True");
        assertTrue( true );



    }
    
    public OPMGraph makeCake1Graph(OPMFactory oFactory) 
    {

        Collection<Account> black=Collections.singleton(oFactory.newAccount("black"));
        
        Process p1=oFactory.newProcess("p1",
                                       black,
                                       "http://process.org/bake");



        Agent ag1=oFactory.newAgent("ag1",
                                    black,
                                    "John");


        Artifact a1=oFactory.newArtifact("a1",
                                         black,
                                         "100g butter");
        Artifact a2=oFactory.newArtifact("a2",
                                         black,
                                         "2 eggs");
        Artifact a3=oFactory.newArtifact("a3",
                                         black,
                                         "100g sugar");
        Artifact a4=oFactory.newArtifact("a4",
                                         black,
                                         "100g flour");
        Artifact a5=oFactory.newArtifact("a5",
                                         black,
                                         "cake");

        Used u1=oFactory.newUsed(p1,oFactory.newRole("butter"),a1,black);
        Used u2=oFactory.newUsed(p1,oFactory.newRole("egg"),a2,black);
        Used u3=oFactory.newUsed(p1,oFactory.newRole("sugar"),a3,black);
        Used u4=oFactory.newUsed(p1,oFactory.newRole("flour"),a4,black);


        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a5,oFactory.newRole("cake"),p1,black);


        WasDerivedFrom wd1=oFactory.newWasDerivedFrom(a5,a1,black);
        WasDerivedFrom wd2=oFactory.newWasDerivedFrom(a5,a2,black);
        WasDerivedFrom wd3=oFactory.newWasDerivedFrom(a5,a3,black);
        WasDerivedFrom wd4=oFactory.newWasDerivedFrom(a5,a4,black);


        WasControlledBy wc1=oFactory.newWasControlledBy(p1,oFactory.newRole("baker"),ag1,black);





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
    public void testCake1Conversion() throws java.io.FileNotFoundException,  java.io.IOException   {
        OPMToDot toDot=new OPMToDot(true); // with roles
        
        toDot.convert(graph1,"target/cake1.dot", "target/cake1.pdf");
    }

}
