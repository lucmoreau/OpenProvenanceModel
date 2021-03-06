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
public class StarbucksTest 
    extends TestCase
{
    public static OPMFactory oFactory=new OPMFactory();

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public StarbucksTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */


    static OPMGraph graph1;


    public void testStarbucks() throws JAXBException
    {
        OPMFactory oFactory=new OPMFactory();

        OPMGraph graph=makeStarbucksGraph(oFactory);

        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/starbucks.xml"),graph,true);

        
        //System.out.println(sw);

        graph1=graph;
        System.out.println("Starbucks Test asserting True");
        assertTrue( true );


    }

    public OPMGraph makeStarbucksGraph(OPMFactory oFactory)
    {

        Collection<Account> detailedAccount=Collections.singleton(oFactory.newAccount("detailedAccount"));
        Collection<Account> summaryAccount=Collections.singleton(oFactory.newAccount("summaryAccount"));


        List<Account> bothAccounts=new LinkedList();
        bothAccounts.addAll(detailedAccount);
        bothAccounts.addAll(summaryAccount);

        
        Process p1=oFactory.newProcess("p1",
                                       detailedAccount,
                                       "Take Order");

        Process p2=oFactory.newProcess("p2",
                                       detailedAccount,
                                       "Make Coffee");

        Process p3=oFactory.newProcess("p3",
                                       detailedAccount,
                                       "Provide other Beverage");

        Process p4=oFactory.newProcess("p4",
                                       summaryAccount,
                                       "Get Drink");


        Artifact a1=oFactory.newArtifact("a1",
                                         detailedAccount,
                                         "order");
        Artifact a2=oFactory.newArtifact("a2",
                                         detailedAccount,
                                         "cash");
        Artifact a3=oFactory.newArtifact("a3",
                                         detailedAccount,
                                         "annotated cup");
        Artifact a4=oFactory.newArtifact("a4",
                                         detailedAccount,
                                         "latte");
        Artifact a5=oFactory.newArtifact("a5",
                                         detailedAccount,
                                         "juice");

        Used u1=oFactory.newUsed(p1,oFactory.newRole("order"),a1,detailedAccount);
        Used u2=oFactory.newUsed(p1,oFactory.newRole("money"),a2,detailedAccount);
        Used u3=oFactory.newUsed(p2,oFactory.newRole("cup"),a3,detailedAccount);

        Used u4=oFactory.newUsed(p4,oFactory.newRole("order"),a1,summaryAccount);

        UsedStar u5=oFactory.newUsedStar(p3,a1,detailedAccount);
        UsedStar u6=oFactory.newUsedStar(p3,a2,detailedAccount);


        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a3,oFactory.newRole("cup"),p1,detailedAccount);
        WasGeneratedBy wg2=oFactory.newWasGeneratedBy(a4,oFactory.newRole("coffee"),p2,detailedAccount);
        WasGeneratedBy wg3=oFactory.newWasGeneratedBy(a5,oFactory.newRole("juice"),p3,detailedAccount);

        WasGeneratedBy wg4=oFactory.newWasGeneratedBy(a5,oFactory.newRole("juice"),p4,summaryAccount);


        WasDerivedFrom wd1=oFactory.newWasDerivedFrom(a4,a3,detailedAccount);

        WasDerivedFrom wd2=oFactory.newWasDerivedFrom(a3,a1,detailedAccount);
        WasDerivedFromStar wd3=oFactory.newWasDerivedFromStar(a5,a1,detailedAccount);
        //WasDerivedFromStar wd4=oFactory.newWasDerivedFromStar(a4,a1,detailedAccount);
        WasDerivedFromStar wd5=oFactory.newWasDerivedFromStar(a3,a2,detailedAccount);
        WasDerivedFromStar wd6=oFactory.newWasDerivedFromStar(a5,a2,detailedAccount);

        WasTriggeredBy wt1=oFactory.newWasTriggeredBy(p3,p1,detailedAccount);




        OPMGraph graph=oFactory.newOPMGraph(bothAccounts,
                                            new Overlaps[] { },
                                            new Process[] {p1, p2, p3, p4},
                                            new Artifact[] {a1,a2,a3,a4,a5},
                                            new Agent[] { },
                                            new Object[] {u1,u2,u3,u4,u5,u6,
                                                          wg1,wg2,wg3,wg4,
                                                          wt1,
                                                          wd1,wd2,wd3,wd5,wd6
                                            } );



        return graph;
    }
    

    /** Produces a dot representation
     * of created graph. */
    public void testStarbucksConversion() throws java.io.FileNotFoundException,  java.io.IOException   {
        OPMToDot toDot=new OPMToDot("src/test/resources/starbucksConfig.xml");
        toDot.convert(graph1,"target/starbucks.dot", "target/starbucks.pdf");

        toDot=new OPMToDot("src/test/resources/starbucksConfig2.xml");
        toDot.convert(graph1,"target/starbucks1.dot", "target/starbucks1.pdf");
    }




    public void testStarbucksCopy() throws java.io.FileNotFoundException,  java.io.IOException   {
        OPMFactory oFactory=new OPMFactory();

        OPMGraph graph2=oFactory.newOPMGraph(graph1);

        assertTrue( "self graph1 differ", graph1.equals(graph1) );        

        assertTrue( "self graph2 differ", graph2.equals(graph2) );        

        assertTrue( "graph1 graph2 differ", graph1.equals(graph2) );        
        
    }

}
