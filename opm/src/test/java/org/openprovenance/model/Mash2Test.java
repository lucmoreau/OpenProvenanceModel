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
import org.openprovenance.model.extension.ExtOPMToDot;
import org.openprovenance.model.collections.CollectionFactory;
import org.openprovenance.model.extension.NamedWasDerivedFrom;
import org.openprovenance.model.extension.NamedWasControlledBy;

/**
 * Unit test for simple App.
 */
public class Mash2Test 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public Mash2Test( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */


    static OPMGraph graph1;

    static String wasSameResourceAs="sameAs";


    public void testMashWasDerivedFromOnly() throws Exception 
    {
        OPMExtendedFactory oFactory=new OPMExtendedFactory();
        CollectionFactory cFactory=new CollectionFactory(oFactory);

        Collection<Account> black=Collections.singleton(oFactory.newAccount("black"));
        Collection<Account> orange=Collections.singleton(oFactory.newAccount("orange"));
        
        List<Account> black_orange=new LinkedList();
        //black_orange.addAll(orange);
        black_orange.addAll(black);


        Process trails=oFactory.newProcess("trails",
                                           black,
                                           "Trails (Service)");

        Process photos=oFactory.newProcess("photos",
                                           black,
                                           "Photos (Service)");

        Process blogs=oFactory.newProcess("blogs",
                                          black,
                                          "Blogs (Service)");

        Process trails2=oFactory.newProcess("trails2",
                                           black,
                                           "Trails (cache)");

        Process photos2=oFactory.newProcess("photos2",
                                           black,
                                           "Photos (cache)");

        Process blogs2=oFactory.newProcess("blogs2",
                                          black,
                                          "Blogs (cache)");


        Process query=oFactory.newProcess("query",
                                          black,
                                          "Q Engine");

        Process select=oFactory.newProcess("select",
                                          black,
                                          "Select");

        Process mash=oFactory.newProcess("mash",
                                          black,
                                          "Mash");


        Agent ag=oFactory.newAgent("C",
                                   black,
                                   "User");


        Agent tProvider=oFactory.newAgent("tp",
                                          black,
                                          "Trails Provider");

        Agent pProvider=oFactory.newAgent("pp",
                                          black,
                                          "Photos Provider");

        Agent bProvider=oFactory.newAgent("bp",
                                          black,
                                          "Blogs Provider");


        Artifact q=oFactory.newArtifact("q",
                                         black,
                                         "q");

        Artifact map=oFactory.newArtifact("map",
                                         black,
                                         "map");
        Artifact mashup=oFactory.newArtifact("mashup",
                                         black,
                                         "mashup");


        Artifact t1=oFactory.newArtifact("t1",
                                         black,
                                         "t1");
        Artifact t2=oFactory.newArtifact("t2",
                                         black,
                                         "t2");
        Artifact t3=oFactory.newArtifact("t3",
                                         black,
                                         "t3");


        Artifact ph1=oFactory.newArtifact("ph1",
                                          black,
                                          "p1");
        Artifact ph2=oFactory.newArtifact("ph2",
                                          black,
                                          "p2");
        Artifact ph3=oFactory.newArtifact("ph3",
                                          black,
                                          "p3");


        Artifact b1=oFactory.newArtifact("b1",
                                          black,
                                          "b1");
        Artifact b2=oFactory.newArtifact("b2",
                                         black,
                                         "b2");
        Artifact b3=oFactory.newArtifact("b3",
                                         black,
                                         "b3");



        Artifact ta1=oFactory.newArtifact("ta1",
                                         black,
                                         "ta1");
        Artifact ta2=oFactory.newArtifact("ta2",
                                         black,
                                         "ta2");
        Artifact ta3=oFactory.newArtifact("ta3",
                                         black,
                                         "ta3");


        Artifact pha1=oFactory.newArtifact("phap1",
                                          black,
                                          "pa1");
        Artifact pha2=oFactory.newArtifact("pha2",
                                          black,
                                          "pa2");
        Artifact pha3=oFactory.newArtifact("pha3",
                                           black,
                                           "pa3");
        

        Artifact ba1=oFactory.newArtifact("ba1",
                                          black,
                                          "ba1");
        Artifact ba2=oFactory.newArtifact("ba2",
                                          black,
                                          "ba2");
        Artifact ba3=oFactory.newArtifact("ba3",
                                          black,
                                          "ba3");


        Artifact tb2=oFactory.newArtifact("tb2",
                                          black,
                                          "tb2");

        Artifact phb3=oFactory.newArtifact("phb3",
                                           black,
                                           "pb3");

        Artifact bb3=oFactory.newArtifact("bb2",
                                          black,
                                          "bb2");

        Artifact phb4=oFactory.newArtifact("phb4",
                                           black,
                                           "pb4");

        Artifact bb4=oFactory.newArtifact("bb4",
                                          black,
                                          "bc2");



        
//         Used u1=oFactory.newUsed(trails,oFactory.newRole("r"),t1,black);
//         Used u2=oFactory.newUsed(trails,oFactory.newRole("r"),t2,black);
//         Used u3=oFactory.newUsed(trails,oFactory.newRole("r"),t3,black);

//         Used u4=oFactory.newUsed(photos,oFactory.newRole("r"),ph1,black);
//         Used u5=oFactory.newUsed(photos,oFactory.newRole("r"),ph2,black);
//         Used u6=oFactory.newUsed(photos,oFactory.newRole("r"),ph3,black);

//         Used u7=oFactory.newUsed(blogs,oFactory.newRole("r"),b1,black);
//         Used u8=oFactory.newUsed(blogs,oFactory.newRole("r"),b2,black);
//         //Used u9=oFactory.newUsed(blogs,oFactory.newRole("r"),b3,black);


//         Used u_1=oFactory.newUsed(trails2,oFactory.newRole("r"),ta1,black);
//         Used u_2=oFactory.newUsed(trails2,oFactory.newRole("r"),ta2,black);
//         Used u_3=oFactory.newUsed(trails2,oFactory.newRole("r"),ta3,black);

//         Used u_4=oFactory.newUsed(photos2,oFactory.newRole("r"),pha1,black);
//         Used u_5=oFactory.newUsed(photos2,oFactory.newRole("r"),pha2,black);
//         Used u_6=oFactory.newUsed(photos2,oFactory.newRole("r"),pha3,black);

//         Used u_7=oFactory.newUsed(blogs2,oFactory.newRole("r"),ba1,black);
//         Used u_8=oFactory.newUsed(blogs2,oFactory.newRole("r"),ba2,black);
//         //Used u_9=oFactory.newUsed(blogs2,oFactory.newRole("r"),ba3,black);


//         Used u10=oFactory.newUsed(select,oFactory.newRole("r4"),tb2,black);
//         WasGeneratedBy wg10=oFactory.newWasGeneratedBy(q,oFactory.newRole("r5"),select,black);


//         Used u11=oFactory.newUsed(query,oFactory.newRole("r6"),q,black);
//         Used u11a=oFactory.newUsed(query,oFactory.newRole("r6a"),phb3,black);
//         Used u11b=oFactory.newUsed(query,oFactory.newRole("r6b"),bb3,black);


//         Used u12=oFactory.newUsed(mash,oFactory.newRole("r7"),map,black);
//         Used u13=oFactory.newUsed(mash,oFactory.newRole("r8"),phb4,black);
//         Used u14=oFactory.newUsed(mash,oFactory.newRole("r9"),bb4,black);

//         WasGeneratedBy wg15=oFactory.newWasGeneratedBy(mashup,oFactory.newRole("r10"),mash,black);





//         WasGeneratedBy wg11=oFactory.newWasGeneratedBy(phb4,oFactory.newRole("r7"),query,black);
//         WasGeneratedBy wg_11=oFactory.newWasGeneratedBy(bb4,oFactory.newRole("r7"),query,black);

//         WasGeneratedBy wg1=oFactory.newWasGeneratedBy(ta1,oFactory.newRole("r2"),trails,black);
//         WasGeneratedBy wg2=oFactory.newWasGeneratedBy(ta2,oFactory.newRole("r2"),trails,black);
//         WasGeneratedBy wg3=oFactory.newWasGeneratedBy(ta3,oFactory.newRole("r2"),trails,black);

//         WasGeneratedBy wg4=oFactory.newWasGeneratedBy(pha1,oFactory.newRole("r2"),photos,black);
//         WasGeneratedBy wg5=oFactory.newWasGeneratedBy(pha2,oFactory.newRole("r2"),photos,black);
//         WasGeneratedBy wg6=oFactory.newWasGeneratedBy(pha3,oFactory.newRole("r2"),photos,black);

//         WasGeneratedBy wg7=oFactory.newWasGeneratedBy(ba1,oFactory.newRole("r2"),blogs,black);
//         WasGeneratedBy wg8=oFactory.newWasGeneratedBy(ba2,oFactory.newRole("r2"),blogs,black);
//         //WasGeneratedBy wg9=oFactory.newWasGeneratedBy(ba3,oFactory.newRole("r2"),blogs,black);


//         WasGeneratedBy wg_2=oFactory.newWasGeneratedBy(tb2,oFactory.newRole("r3"),trails2,black);
//         WasGeneratedBy wg_6=oFactory.newWasGeneratedBy(phb3,oFactory.newRole("r3"),photos2,black);
//         WasGeneratedBy wg_9=oFactory.newWasGeneratedBy(bb3,oFactory.newRole("r3"),blogs2,black);


        NamedWasDerivedFrom wd1=oFactory.newNamedWasDerivedFrom(ba1,b1,wasSameResourceAs,black);
        NamedWasDerivedFrom wd2=oFactory.newNamedWasDerivedFrom(ba2,b2,wasSameResourceAs,black);
        //NamedWasDerivedFrom wd3=oFactory.newNamedWasDerivedFrom(ba3,b3,wasSameResourceAs,black);

        NamedWasDerivedFrom wd4=oFactory.newNamedWasDerivedFrom(ta1,t1,wasSameResourceAs,black);
        NamedWasDerivedFrom wd5=oFactory.newNamedWasDerivedFrom(ta2,t2,wasSameResourceAs,black);
        NamedWasDerivedFrom wd6=oFactory.newNamedWasDerivedFrom(ta3,t3,wasSameResourceAs,black);

        NamedWasDerivedFrom wd7=oFactory.newNamedWasDerivedFrom(pha1,ph1,wasSameResourceAs,black);
        NamedWasDerivedFrom wd8=oFactory.newNamedWasDerivedFrom(pha2,ph2,wasSameResourceAs,black);
        NamedWasDerivedFrom wd9=oFactory.newNamedWasDerivedFrom(pha3,ph3,wasSameResourceAs,black);

        NamedWasDerivedFrom wd_5=oFactory.newNamedWasDerivedFrom(tb2,ta2,wasSameResourceAs,black);
        NamedWasDerivedFrom wd_8=oFactory.newNamedWasDerivedFrom(phb3,pha3,wasSameResourceAs,black);
        NamedWasDerivedFrom wd_3=oFactory.newNamedWasDerivedFrom(bb3,ba2,wasSameResourceAs,black);

        NamedWasDerivedFrom wdb_8=oFactory.newNamedWasDerivedFrom(phb4,phb3,wasSameResourceAs,black);
        NamedWasDerivedFrom wdb_3=oFactory.newNamedWasDerivedFrom(bb4,bb3,wasSameResourceAs,black);

        NamedWasDerivedFrom wdb__8=oFactory.newNamedWasDerivedFrom(phb4,q,"selectedBy",black);
        NamedWasDerivedFrom wdb__3=oFactory.newNamedWasDerivedFrom(bb4,q,"selectedBy",black);


        NamedWasDerivedFrom wd12=oFactory.newNamedWasDerivedFrom(mashup,phb4,"contains",black);
        NamedWasDerivedFrom wd13=oFactory.newNamedWasDerivedFrom(mashup,bb4,"contains",black);
        NamedWasDerivedFrom wd14=oFactory.newNamedWasDerivedFrom(mashup,map,"contains",black);

        NamedWasDerivedFrom wd15=oFactory.newNamedWasDerivedFrom(q,tb2,"parameterizedBy",black);

        NamedWasControlledBy wc1=oFactory.newNamedWasControlledBy(select,oFactory.newRole("contributor"),
                                                                  ag,
                                                                  "wasActionOf",
                                                                  black);


//         NamedWasControlledBy wc2=oFactory.newNamedWasControlledBy(photos,oFactory.newRole("contributor"),
//                                                                   pProvider,
//                                                                   "wasHostedBy",
//                                                                   black);
//         NamedWasControlledBy wc3=oFactory.newNamedWasControlledBy(trails,oFactory.newRole("contributor"),
//                                                                   tProvider,
//                                                                   "wasHostedBy",
//                                                                   black);
//         NamedWasControlledBy wc4=oFactory.newNamedWasControlledBy(blogs,oFactory.newRole("contributor"),
//                                                                   bProvider,
//                                                                   "wasHostedBy",
//                                                                   black);


        OPMGraph graph=oFactory.newOPMGraph(black_orange,
                                            new Overlaps[] { },
                                            new Process[] { }, //trails, blogs, photos, trails2, blogs2, photos2, select, query, mash },
                                            new Artifact[] {t2, ta2, b2, ph3, pha3, ba2,  tb2, phb3, bb3, phb4, bb4, q , mashup, map},
                                            new Agent[] { }, //ag, tProvider, bProvider, pProvider },
                                            new Object[] {//u1, u2, u3, u_1, u_2, u_3, u4, u5, u6, u7, u8, u_4, u_5, u_6, u_7, u_8, u10, u11, u11a, u11b, u12, u13, u14,
                                                          //wg1, wg2, wg3, wg4, wg5, wg6, wg7, wg8,  wg_2, wg_6, wg_9, wg10, wg11, wg_11, wg15,
                                                          wd2, wd5, wd9, wd_5, wd_3, wd_8, wdb_8, wdb_3, wdb__8, wdb__3, wd12, wd13, wd14, wd15
                                                          //wc1, wc2, wc3, wc4
                                            } );




        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/mash.xml"),graph,true);

        
        //System.out.println(sw);

        graph1=graph;
        System.out.println("testOPM1 asserting True");
        assertTrue( true );


        OPMToDot toDot=new ExtOPMToDot();
        
        toDot.convert(graph1,"target/mash2.dot", "target/mash2.pdf");


        
    }
    
}
