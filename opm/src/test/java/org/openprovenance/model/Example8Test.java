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
public class Example8Test 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public Example8Test( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */


    static OPMGraph graph1;




    public void testCollectionProposal1() throws Exception
    {
        OPMFactory oFactory=new OPMFactory();
        CollectionFactory cFactory=new CollectionFactory(oFactory);

        Collection<Account> black=Collections.singleton(oFactory.newAccount("black"));
        Collection<Account> orange=Collections.singleton(oFactory.newAccount("orange"));
        
        Process p1=oFactory.newProcess("setG",
                                       black,
                                       "setG");


        Process p2=oFactory.newProcess("append",
                                       black,
                                       "append");



        List<Account> black_orange=new LinkedList();
        black_orange.addAll(orange);
        black_orange.addAll(black);


        Artifact a1=oFactory.newArtifact("a1",
                                         black,
                                         "a1:[]");
        Artifact a2=oFactory.newArtifact("a2",
                                         black,
                                         "a2:[]");
        Artifact a3=oFactory.newArtifact("a3",
                                         black,
                                         "a3:[]");


        Artifact g1=oFactory.newArtifact("g1",
                                         black,
                                         "g1");
        Artifact g2=oFactory.newArtifact("g2",
                                         black,
                                         "g2");


        Artifact f1=oFactory.newArtifact("f1",
                                         orange,
                                         "f1");
        Artifact f2=oFactory.newArtifact("f2",
                                         orange,
                                         "f2");

        Artifact f3=oFactory.newArtifact("f3",
                                         orange,
                                         "f3");

        Artifact b1=oFactory.newArtifact("b1",
                                         black,
                                         "b1:[]");
        Artifact b2=oFactory.newArtifact("b2",
                                         black,
                                         "b2:[]");

        Artifact b3=oFactory.newArtifact("b3",
                                         black,
                                         "b3:[]");


        Artifact c1=oFactory.newArtifact("c1",
                                         orange,
                                         "c1");
        Artifact c2=oFactory.newArtifact("c2",
                                         orange,
                                         "c2");

        Artifact c3=oFactory.newArtifact("c3",
                                         orange,
                                         "c3");


        
        Used u1=oFactory.newUsed(p1,oFactory.newRole("old"),a2,black);
        Used u2=oFactory.newUsed(p1,oFactory.newRole("value"),g1,black);


        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a3,oFactory.newRole("updated"),p1,black);


        WasDerivedFrom wd1=oFactory.newWasDerivedFrom("wd1",a3,a2,"wasUpdated",black);
        WasDerivedFrom wd2=cFactory.newWasIdenticalTo("wd2",a2,a1,black);
        
        WasDerivedFrom wd3=cFactory.newWasIdenticalTo("wd3",g2,g1,black);
        WasDerivedFrom wd4=cFactory.newContained("wd4",a3,g2,black);

        WasDerivedFrom wd5=cFactory.newWasIdenticalTo("wd5",f2,f1,orange);
        WasDerivedFrom wd6=cFactory.newWasIdenticalTo("wd6",f3,f2,orange);
        WasDerivedFrom wd7=cFactory.newContained("wd7",a2,f2,orange);
        WasDerivedFrom wd8=cFactory.newContained("wd8",a3,f3,orange);
        WasDerivedFrom wd9=cFactory.newContained("wd9",a1,f1,orange);


        WasDerivedFrom wd10=oFactory.newWasDerivedFrom("wd10",b2,b1,"wasAppendedTo",black);
        WasDerivedFrom wd10b=oFactory.newWasDerivedFrom("wd10b",b2,a1,"wasAppendedTo",black);
        WasDerivedFrom wd11=oFactory.newWasDerivedFrom("wd11",b3,b2,"updated",black);
        
        WasDerivedFrom wd12=cFactory.newContained("wd12",b2,a2,black);
        WasDerivedFrom wd13=cFactory.newContained("wd13",b3,a3,black);

        WasDerivedFrom wd14=cFactory.newContained("wd14",b1,c1,orange);
        WasDerivedFrom wd15=cFactory.newContained("wd15",b2,c2,orange);
        WasDerivedFrom wd16=cFactory.newContained("wd16",b3,c3,orange);

        WasDerivedFrom wd17=cFactory.newWasIdenticalTo("wd17",c3,c2,orange);
        WasDerivedFrom wd18=cFactory.newWasIdenticalTo("wd18",c2,c1,orange);




        Used u3=oFactory.newUsed(p2,oFactory.newRole("last"),a1,black);
        Used u4=oFactory.newUsed(p2,oFactory.newRole("first"),b1,black);


        WasGeneratedBy wg2=oFactory.newWasGeneratedBy(b2,oFactory.newRole("result"),p2,black);


        OPMGraph graph=oFactory.newOPMGraph(black_orange,
                                            new Overlaps[] { },
                                            new Process[] {p1, p2},
                                            new Artifact[] {a1,a2,a3, g1, g2, f1, f2, f3, b1, b2, b3, c1, c2, c3},
                                            new Agent[] {  },
                                            new Object[] {u1,u2,u3, u4,
                                                          wg1, wg2, 
                                                          wd1,wd2, wd3, wd4,wd5,wd6,wd7,wd8, wd9, wd10, wd10b, wd11, wd12, wd13, wd14, wd15, wd16, wd17, wd18} );




        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/collection1.xml"),graph,true);

        
        //System.out.println(sw);

        graph1=graph;
        System.out.println("testOPM1 asserting True");
        assertTrue( true );




        OPMToDot toDot=new OPMToDot();        
        toDot.convert(graph1,"target/collection1.dot", "target/collection1.pdf");

        
    }
    
}
