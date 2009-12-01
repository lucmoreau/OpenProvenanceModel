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
import org.openprovenance.model.collections.CollectionFactory;


/**
 * Unit test for simple App.
 */
public class CycleTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CycleTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CycleTest.class );
    }

    static OPMGraph graph1;
    static OPMGraph graph2;

    /** Creates and serialises an OPM graph. */

    public void testCycle1() throws JAXBException,  java.io.FileNotFoundException,  java.io.IOException
    {
        OPMFactory oFactory=new OPMFactory();
        CollectionFactory cFactory=new CollectionFactory(oFactory);


        Collection<Account> summary=Collections.singleton(oFactory.newAccount("summary"));
        Collection<Account> detailed=Collections.singleton(oFactory.newAccount("detailed"));
        Collection<Account> superdetailed=Collections.singleton(oFactory.newAccount("superdetailed"));

        List<Account> summary_detailed=new LinkedList();
        summary_detailed.addAll(summary);
        summary_detailed.addAll(detailed);

        
        Process p1=oFactory.newProcess("p1",
                                       summary,
                                       "p1");
        Process p1a=oFactory.newProcess("p1a",
                                        detailed,
                                        "p1a");
        Process p1b=oFactory.newProcess("p1b",
                                        detailed,
                                        "p1b");
        Process p2=oFactory.newProcess("p2",
                                       detailed,
                                       "p2");






        Artifact a0=oFactory.newArtifact("a0",
                                         summary_detailed,
                                         "a0");
        Artifact a1=oFactory.newArtifact("a1",
                                         summary_detailed,
                                         "a1");
        Artifact a2=oFactory.newArtifact("a2",
                                         summary_detailed,
                                         "a2");
        Artifact a3=oFactory.newArtifact("a3",
                                         summary_detailed,
                                         "a3");


        Used u0=oFactory.newUsed(p1a,oFactory.newRole("in"),a0,detailed);
        Used u4=oFactory.newUsed(p1,oFactory.newRole("in"),a0,summary);
        Used u1=oFactory.newUsed(p1,oFactory.newRole("in"),a2,summary);
        Used u2=oFactory.newUsed(p2,oFactory.newRole("in"),a1,detailed);
        Used u3=oFactory.newUsed(p1b,oFactory.newRole("in"),a2,detailed);



        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a2,oFactory.newRole("out"),p2,detailed);
        WasGeneratedBy wg2=oFactory.newWasGeneratedBy(a1,oFactory.newRole("out"),p1a,detailed);
        WasGeneratedBy wg3=oFactory.newWasGeneratedBy(a1,oFactory.newRole("out"),p1,summary);
        WasGeneratedBy wg4=oFactory.newWasGeneratedBy(a3,oFactory.newRole("out"),p1b,detailed);
        WasGeneratedBy wg5=oFactory.newWasGeneratedBy(a3,oFactory.newRole("out"),p1,summary);


        Overlaps ov1=oFactory.newOverlaps(summary_detailed);



        WasDerivedFrom wdf1=oFactory.newWasDerivedFrom(a3,a2,summary_detailed);
        WasDerivedFrom wdf2=oFactory.newWasDerivedFrom(a2,a1,detailed);
        WasDerivedFrom wdf3=oFactory.newWasDerivedFrom(a1,a0,summary_detailed);


//         WasDerivedFrom wdf2=oFactory.newWasDerivedFrom("wdf2",a4,a1,"second",detailed);
//         WasDerivedFrom wdf3=oFactory.newWasDerivedFrom("wdf3",a5,a3,"add1",detailed);
//         WasDerivedFrom wdf4=oFactory.newWasDerivedFrom("wdf4",a6,a4,"add1",detailed);
//         WasDerivedFrom wdf5=cFactory.newContained("wdf5",a2,a5,detailed);
//         WasDerivedFrom wdf6=cFactory.newContained("wdf6",a2,a6,detailed);




        OPMGraph graph=oFactory.newOPMGraph(summary_detailed,
                                            new Overlaps[] { ov1 },
                                            new Process[] {p1,p1a,p1b,p2},
                                            new Artifact[] {a0, a1,a2,a3},
                                            null,
                                            new Object[] {u0, u1,u2,u3,u4,
                                                          wg1,wg2,wg3,wg4,wg5
                                            } );




        OPMGraph graph2=oFactory.newOPMGraph(summary_detailed,
                                            new Overlaps[] { ov1 },
                                            new Process[] {p1,p1a,p1b,p2},
                                            new Artifact[] {a0, a1,a2,a3},
                                            null,
                                            new Object[] {u0, u1,u2,u3,u4,
                                                          wg1,wg2,wg3,wg4,wg5,
                                                          wdf1,wdf2,wdf3
                                            } );





        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        StringWriter sw=new StringWriter();
        serial.serialiseOPMGraph(sw,graph,true);
        serial.serialiseOPMGraph(new File("target/cycle1.xml"),graph,true);

        graph1=graph;

        assertTrue( true );

        OPMToDot toDot=new OPMToDot("src/test/resources/collectionConfig.xml");
        
        toDot.convert(graph1,"target/cycle1.dot", "target/cycle1.pdf");

        toDot=new OPMToDot("src/test/resources/collectionConfig1.xml");
        
        toDot.convert(graph1,"target/cycle2.dot", "target/cycle2.pdf");

        toDot=new OPMToDot("src/test/resources/collectionConfig2.xml");
        
        toDot.convert(graph1,"target/cycle3.dot", "target/cycle3.pdf");

        toDot=new OPMToDot("src/test/resources/collectionConfig3.xml");
        
        toDot.convert(graph1,"target/cycle4.dot", "target/cycle4.pdf");


        // no cycle


        sw=new StringWriter();
        serial.serialiseOPMGraph(sw,graph2,true);
        serial.serialiseOPMGraph(new File("target/nocycle1.xml"),graph,true);


        assertTrue( true );

        toDot=new OPMToDot("src/test/resources/collectionConfig.xml");
        
        toDot.convert(graph2,"target/nocycle1.dot", "target/nocycle1.pdf");

        toDot=new OPMToDot("src/test/resources/collectionConfig1.xml");
        
        toDot.convert(graph2,"target/nocycle2.dot", "target/nocycle2.pdf");

        toDot=new OPMToDot("src/test/resources/collectionConfig2.xml");
        
        toDot.convert(graph2,"target/nocycle3.dot", "target/nocycle3.pdf");

        toDot=new OPMToDot("src/test/resources/collectionConfig3.xml");
        
        toDot.convert(graph2,"target/nocycle4.dot", "target/nocycle4.pdf");




    }


    

}
