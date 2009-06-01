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
        OPMExtendedFactory oFactory=new OPMExtendedFactory();

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
                                         "[a1]");
        Artifact a2=oFactory.newArtifact("a2",
                                         black,
                                         "[a2]");
        Artifact a3=oFactory.newArtifact("a3",
                                         black,
                                         "[a3]");


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
                                         "[b1]");
        Artifact b2=oFactory.newArtifact("b2",
                                         black,
                                         "[b2]");

        Artifact b3=oFactory.newArtifact("b3",
                                         black,
                                         "[b3]");


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


        NamedWasDerivedFrom wd1=oFactory.newNamedWasDerivedFrom(a3,a2,"wasUpdated",black);
        NamedWasDerivedFrom wd2=oFactory.newNamedWasDerivedFrom(a2,a1,"wasAliasOf",black);

        NamedWasDerivedFrom wd3=oFactory.newNamedWasDerivedFrom(g2,g1,"wasAliasOf",black);
        NamedWasDerivedFrom wd4=oFactory.newNamedWasDerivedFrom(a3,g2,"contained",black);

        NamedWasDerivedFrom wd5=oFactory.newNamedWasDerivedFrom(f2,f1,"wasAliasOf",orange);
        NamedWasDerivedFrom wd6=oFactory.newNamedWasDerivedFrom(f3,f2,"wasAliasOf",orange);
        NamedWasDerivedFrom wd7=oFactory.newNamedWasDerivedFrom(a2,f2,"contained",orange);
        NamedWasDerivedFrom wd8=oFactory.newNamedWasDerivedFrom(a3,f3,"contained",orange);
        NamedWasDerivedFrom wd9=oFactory.newNamedWasDerivedFrom(a1,f1,"contained",orange);


        NamedWasDerivedFrom wd10=oFactory.newNamedWasDerivedFrom(b2,b1,"wasAppendedTo",black);
        NamedWasDerivedFrom wd10b=oFactory.newNamedWasDerivedFrom(b2,a1,"wasAppendedTo",black);
        NamedWasDerivedFrom wd11=oFactory.newNamedWasDerivedFrom(b3,b2,"updated",black);

        NamedWasDerivedFrom wd12=oFactory.newNamedWasDerivedFrom(b2,a2,"contained",black);
        NamedWasDerivedFrom wd13=oFactory.newNamedWasDerivedFrom(b3,a3,"contained",black);

        NamedWasDerivedFrom wd14=oFactory.newNamedWasDerivedFrom(b1,c1,"contained",orange);
        NamedWasDerivedFrom wd15=oFactory.newNamedWasDerivedFrom(b2,c2,"contained",orange);
        NamedWasDerivedFrom wd16=oFactory.newNamedWasDerivedFrom(b3,c3,"contained",orange);

        NamedWasDerivedFrom wd17=oFactory.newNamedWasDerivedFrom(c3,c2,"wasAliasOf",orange);
        NamedWasDerivedFrom wd18=oFactory.newNamedWasDerivedFrom(c2,c1,"wasAliasOf",orange);




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
