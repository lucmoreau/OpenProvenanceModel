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

import org.openprovenance.model.extension.OPMExtendedFactory;


/**
 * Unit test for simple App.
 */
public class Example7Test 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public Example7Test( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */


    static OPMGraph graph1;




    public void testIndexedGraph() throws JAXBException
    {
        OPMExtendedFactory oFactory=new OPMExtendedFactory();

        Collection<Account> black=Collections.singleton(oFactory.newAccount("black"));
        Collection<Account> orange=Collections.singleton(oFactory.newAccount("orange"));
        
        Process p1=oFactory.newProcess("p1",
                                       orange,
                                       "http://process.org/bake");
        Process p2=oFactory.newProcess("p2",
                                       black,
                                       "http://process.org/split");
        Process p3=oFactory.newProcess("p3",
                                       black,
                                       "http://process.org/badBake");
        Process p4=oFactory.newProcess("p4",
                                       black,
                                       "http://process.org/fry");


        List<Account> black_orange=new LinkedList();
        black_orange.addAll(orange);
        black_orange.addAll(black);

        Agent ag1=oFactory.newAgent("ag1",
                                    black_orange,
                                    "John");


        Artifact a1=oFactory.newArtifact("a1",
                                         black_orange,
                                         "100g butter");
        Artifact a2=oFactory.newArtifact("a2",
                                         black_orange,
                                         "2 eggs");
        Artifact a3=oFactory.newArtifact("a3",
                                         black_orange,
                                         "100g sugar");
        Artifact a4=oFactory.newArtifact("a4",
                                         black_orange,
                                         "100g flour");
        Artifact a5=oFactory.newArtifact("a5",
                                         black_orange,
                                         "1 cake");
        Artifact a6=oFactory.newArtifact("a6",
                                         black,
                                         "1 egg");
        Artifact a7=oFactory.newArtifact("a7",
                                         black,
                                         "1 egg");
        Artifact a8=oFactory.newArtifact("a8",
                                         black,
                                         "fried egg");

        Used u1=oFactory.newUsed(p1,oFactory.newRole("in"),a1,orange);
        Used u2=oFactory.newUsed(p1,oFactory.newRole("in"),a2,orange);
        Used u3=oFactory.newUsed(p1,oFactory.newRole("in"),a3,orange);
        Used u4=oFactory.newUsed(p1,oFactory.newRole("in"),a4,orange);

        Used u5=oFactory.newUsed(p3,oFactory.newRole("in"),a1,black);
        Used u6=oFactory.newUsed(p3,oFactory.newRole("in"),a6,black);
        Used u7=oFactory.newUsed(p3,oFactory.newRole("in"),a3,black);
        Used u8=oFactory.newUsed(p3,oFactory.newRole("in"),a4,black);
        Used u8bis=oFactory.newUsed(p3,oFactory.newRole("in"),a4,orange);


        Used u9=oFactory.newUsed(p4,oFactory.newRole("in"),a7,black);

        Used u10=oFactory.newUsed(p2,oFactory.newRole("in"),a2,black);




        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a5,oFactory.newRole("out"),p1,orange);
        WasGeneratedBy wg1bis=oFactory.newWasGeneratedBy(a5,oFactory.newRole("out"),p1,black);
        WasGeneratedBy wg2=oFactory.newWasGeneratedBy(a5,oFactory.newRole("out"),p3,black);
        WasGeneratedBy wg3=oFactory.newWasGeneratedBy(a8,oFactory.newRole("out"),p4,black);
        WasGeneratedBy wg4=oFactory.newWasGeneratedBy(a6,oFactory.newRole("out"),p2,black);
        WasGeneratedBy wg5=oFactory.newWasGeneratedBy(a7,oFactory.newRole("out"),p2,black);



        NamedWasDerivedFrom wd1=oFactory.newNamedWasDerivedFrom(a5,a1,"wasBakedFrom",orange);
        NamedWasDerivedFrom wd2=oFactory.newNamedWasDerivedFrom(a5,a2,"wasBakedFrom",orange);
        NamedWasDerivedFrom wd3=oFactory.newNamedWasDerivedFrom(a5,a3,"wasBakedFrom",orange);
        NamedWasDerivedFrom wd4=oFactory.newNamedWasDerivedFrom(a5,a4,"wasBakedFrom",orange);

        NamedWasDerivedFrom wd5=oFactory.newNamedWasDerivedFrom(a5,a1,"wasBakedFrom",black);
        NamedWasDerivedFrom wd6=oFactory.newNamedWasDerivedFrom(a5,a6,"wasBakedFrom",black);
        NamedWasDerivedFrom wd7=oFactory.newNamedWasDerivedFrom(a5,a3,"wasBakedFrom",black);
        NamedWasDerivedFrom wd8=oFactory.newNamedWasDerivedFrom(a5,a4,"wasBakedFrom",black);

        WasDerivedFrom wd9=oFactory.newWasDerivedFrom(a6,a2,black);
        WasDerivedFrom wd10=oFactory.newWasDerivedFrom(a7,a2,black);

        WasDerivedFrom wd11=oFactory.newWasDerivedFrom(a8,a7,black);

        WasTriggeredBy wt1=oFactory.newWasTriggeredBy(p3,p4,black);


        WasControlledBy wc1=oFactory.newWasControlledBy(p1,oFactory.newRole("baker"),ag1,orange);
        WasControlledBy wc2=oFactory.newWasControlledBy(p3,oFactory.newRole("baker"),ag1,black);

        Overlaps ov1=oFactory.newOverlaps(black_orange);

        


        OPMGraph graph=oFactory.newOPMGraph(black_orange,
                                            new Overlaps[] { ov1 },
                                            new Process[] {p1,p2,p3,p4},
                                            new Artifact[] {a1,a2,a3,a4,a5,a6, a7, a8},
                                            new Agent[] { ag1 },
                                            new Object[] {u1,u2,u3,u4,u5,u6,u7, u8, u8bis, u9, u10,
                                                          wg1,wg1bis,wg2,wg3,wg4,wg5,
                                                          wd1,wd2,wd3,wd4,wd5,wd6,wd7,wd8,wd9,wd10,wd11,
                                                          wt1,
                                                          wc1, wc2} );





        IndexedOPMGraph graph2=new IndexedOPMGraph(oFactory,graph);
        System.out.println("testOPM1 asserting True");

        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/indexed.xml"),graph2,true);

        assertTrue( true );


    }
    
}
