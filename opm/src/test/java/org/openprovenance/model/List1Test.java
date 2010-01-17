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
public class List1Test 
    extends TestCase
{

    static public OPMFactory oFactory=new OPMFactory();

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public List1Test( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( List1Test.class );
    }

    static OPMGraph graph1;
    static OPMGraph graph2;

    /** Creates and serialises an OPM graph. */
    public void testList1() throws JAXBException,  java.io.FileNotFoundException,  java.io.IOException
    {
        OPMFactory oFactory=new OPMFactory();
        OPMGraph graph=makeList1Graph(oFactory);

        
        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        StringWriter sw=new StringWriter();
        serial.serialiseOPMGraph(sw,graph,true);
        serial.serialiseOPMGraph(new File("target/list1.xml"),graph,true);

        graph1=graph;

        assertTrue( true );

        OPMToDot toDot=new OPMToDot("src/test/resources/collectionConfig.xml");
        
        toDot.convert(graph1,"target/list1.dot", "target/list1.pdf");

        toDot=new OPMToDot("src/test/resources/collectionConfig1.xml");
        
        toDot.convert(graph1,"target/list2.dot", "target/list2.pdf");

        toDot=new OPMToDot("src/test/resources/collectionConfig2.xml");
        
        toDot.convert(graph1,"target/list3.dot", "target/list3.pdf");

        toDot=new OPMToDot("src/test/resources/collectionConfig3.xml");
        
        toDot.convert(graph1,"target/list4.dot", "target/list4.pdf");


    }

    public OPMGraph makeList1Graph(OPMFactory oFactory) throws JAXBException,  java.io.FileNotFoundException,  java.io.IOException
    {
        CollectionFactory cFactory=new CollectionFactory(oFactory);


        Collection<Account> summary=Collections.singleton(oFactory.newAccount("summary"));
        Collection<Account> detailed=Collections.singleton(oFactory.newAccount("detailed"));
        Collection<Account> superdetailed=Collections.singleton(oFactory.newAccount("superdetailed"));
        
        Process p1=oFactory.newProcess("p1",
                                       summary,
                                       "http://process.org/add1ToAll");
        Process p2=oFactory.newProcess("p2",
                                       detailed,
                                       "http://process.org/accessor");
        Process p3=oFactory.newProcess("p3",
                                       detailed,
                                       "http://process.org/plus1");
        Process p4=oFactory.newProcess("p4",
                                       detailed,
                                       "http://process.org/plus1");
        Process p5=oFactory.newProcess("p5",
                                       detailed,
                                       "http://process.org/constructor");

        Process p6=oFactory.newProcess("p6",
                                       superdetailed,
                                       "plus2");

        Process p7=oFactory.newProcess("p7",
                                       superdetailed,
                                       "minus1");

        Process p8=oFactory.newProcess("p8",
                                       superdetailed,
                                       "minus1");

        Process p9=oFactory.newProcess("p9",
                                       superdetailed,
                                       "plus2");


        List<Account> summary_detailed=new LinkedList();
        summary_detailed.addAll(summary);
        summary_detailed.addAll(detailed);


        List<Account> summary_super_detailed=new LinkedList();
        summary_super_detailed.addAll(summary);
        summary_super_detailed.addAll(detailed);
        summary_super_detailed.addAll(superdetailed);

        Artifact a1=oFactory.newArtifact("a1",
                                         summary_detailed,
                                         "(2,6)");
        Artifact a2=oFactory.newArtifact("a2",
                                         summary_detailed,
                                         "(3,7)");
        Artifact a3=oFactory.newArtifact("a3",
                                         detailed,
                                         "2");
        Artifact a4=oFactory.newArtifact("a4",
                                         detailed,
                                         "6");
        Artifact a5=oFactory.newArtifact("a5",
                                         detailed,
                                         "3");
        Artifact a6=oFactory.newArtifact("a6",
                                         detailed,
                                         "7");

        Used u1=oFactory.newUsed(p1,oFactory.newRole("in"),a1,summary);
        Used u2=oFactory.newUsed(p2,oFactory.newRole("pair"),a1,detailed);
        Used u3=oFactory.newUsed(p3,oFactory.newRole("in"),a3,detailed);
        Used u4=oFactory.newUsed(p4,oFactory.newRole("in"),a4,detailed);
        Used u5=oFactory.newUsed(p5,oFactory.newRole("left"),a5,detailed);
        Used u6=oFactory.newUsed(p5,oFactory.newRole("right"),a6,detailed);

        Used u7=oFactory.newUsed(p6,oFactory.newRole("in"),a3,superdetailed);
        Used u8=oFactory.newUsed(p8,oFactory.newRole("in"),a4,superdetailed);




        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a2,oFactory.newRole("out"),p1,summary);
        WasGeneratedBy wg2=oFactory.newWasGeneratedBy(a3,oFactory.newRole("left"),p2,detailed);
        WasGeneratedBy wg3=oFactory.newWasGeneratedBy(a4,oFactory.newRole("right"),p2,detailed);
        WasGeneratedBy wg4=oFactory.newWasGeneratedBy(a5,oFactory.newRole("out"),p3,detailed);
        WasGeneratedBy wg5=oFactory.newWasGeneratedBy(a6,oFactory.newRole("out"),p4,detailed);
        WasGeneratedBy wg6=oFactory.newWasGeneratedBy(a2,oFactory.newRole("pair"),p5,detailed);
        WasGeneratedBy wg7=oFactory.newWasGeneratedBy(a5,oFactory.newRole("out"),p7,superdetailed);
        WasGeneratedBy wg8=oFactory.newWasGeneratedBy(a6,oFactory.newRole("out"),p9,superdetailed);

        Overlaps ov1=oFactory.newOverlaps(summary_detailed);

        WasDerivedFrom wdf0=oFactory.newWasDerivedFrom("wdf0",a2,a1,"add1",summary);

        WasDerivedFrom wdf1=oFactory.newWasDerivedFrom("wdf1",a3,a1,"first",detailed);
        WasDerivedFrom wdf2=oFactory.newWasDerivedFrom("wdf2",a4,a1,"second",detailed);
        WasDerivedFrom wdf3=oFactory.newWasDerivedFrom("wdf3",a5,a3,"add1",detailed);
        WasDerivedFrom wdf4=oFactory.newWasDerivedFrom("wdf4",a6,a4,"add1",detailed);
        WasDerivedFrom wdf5=cFactory.newContained("wdf5",a2,a5,detailed);
        WasDerivedFrom wdf6=cFactory.newContained("wdf6",a2,a6,detailed);

        WasTriggeredBy wtb1=oFactory.newWasTriggeredBy(p7,p6,superdetailed);
        WasTriggeredBy wtb2=oFactory.newWasTriggeredBy(p9,p8,superdetailed);


        OPMGraph graph=oFactory.newOPMGraph(summary_detailed,
                                            new Overlaps[] { ov1 },
                                            new Process[] {p1,p2,p3,p4,p5, p6, p7, p8, p9},
                                            new Artifact[] {a1,a2,a3,a4,a5,a6},
                                            new Agent[] {},
                                            new Object[] {u1,u2,u3,u4,u5,u6,u7,u8,
                                                          wg1,wg2,wg3,wg4,wg5,wg6,wg7,wg8,
                                                          wdf0, wdf1, wdf2, wdf3, wdf4, wdf5, wdf6,
                                                          wtb1, wtb2} );




        return graph;



    }


    

}
