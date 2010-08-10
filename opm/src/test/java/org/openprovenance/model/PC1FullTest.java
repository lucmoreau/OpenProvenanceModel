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
public class PC1FullTest 
    extends TestCase
{
    public static OPMFactory oFactory=new OPMFactory();

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PC1FullTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */


    static OPMGraph graph1;


    public void testPC1Full() throws JAXBException
    {
        OPMFactory oFactory=new OPMFactory();

        OPMGraph graph=makePC1FullGraph(oFactory);

        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/pc1-full.xml"),graph,true);

        
        //System.out.println(sw);

        graph1=graph;
        System.out.println("PC1Full Test asserting True");
        assertTrue( true );


    }

    public OPMGraph makePC1FullGraph(OPMFactory oFactory)
    {

        Collection<Account> black=Collections.singleton(oFactory.newAccount("black"));
        

        Process p0=oFactory.newProcess("p0",
                                       black,
                                       "PC1Full Workflow");

        Process p1=oFactory.newProcess("p1",
                                       black,
                                       "align_warp 1");
        Process p2=oFactory.newProcess("p2",
                                       black,
                                       "align_warp 2");
        Process p3=oFactory.newProcess("p3",
                                       black,
                                       "align_warp 3");
        Process p4=oFactory.newProcess("p4",
                                       black,
                                       "align_warp 4");
        Process p5=oFactory.newProcess("p5",
                                       black,
                                       "Reslice 1");
        Process p6=oFactory.newProcess("p6",
                                       black,
                                       "Reslice 2");
        Process p7=oFactory.newProcess("p7",
                                       black,
                                       "Reslice 3");
        Process p8=oFactory.newProcess("p8",
                                       black,
                                       "Reslice 4");
        Process p9=oFactory.newProcess("p9",
                                       black,
                                       "Softmean");
        Process p10=oFactory.newProcess("p10",
                                       black,
                                       "Slicer 1");
        Process p11=oFactory.newProcess("p11",
                                       black,
                                       "Slicer 2");
        Process p12=oFactory.newProcess("p12",
                                       black,
                                       "Slicer 3");
        Process p13=oFactory.newProcess("p13",
                                       black,
                                       "Convert 1");
        Process p14=oFactory.newProcess("p14",
                                       black,
                                       "Convert 2");
        Process p15=oFactory.newProcess("p15",
                                       black,
                                       "Convert 3");

        Agent ag1=oFactory.newAgent("ag1",
                                    black,
                                    "John Doe");


        Artifact a1=oFactory.newArtifact("a1",
                                         black,
                                         "Reference Image");
        Artifact a2=oFactory.newArtifact("a2",
                                         black,
                                         "Reference Header");

        Artifact a3=oFactory.newArtifact("a3",
                                         black,
                                         "Anatomy I1");
        Artifact a4=oFactory.newArtifact("a4",
                                         black,
                                         "Anatomy H1");
        Artifact a5=oFactory.newArtifact("a5",
                                         black,
                                         "Anatomy I2");
        Artifact a6=oFactory.newArtifact("a6",
                                         black,
                                         "Anatomy H2");
        Artifact a7=oFactory.newArtifact("a7",
                                         black,
                                         "Anatomy I3");
        Artifact a8=oFactory.newArtifact("a8",
                                         black,
                                         "Anatomy H3");
        Artifact a9=oFactory.newArtifact("a9",
                                         black,
                                         "Anatomy I4");
        Artifact a10=oFactory.newArtifact("a10",
                                          black,
                                          "Anatomy H4");



        Artifact a11=oFactory.newArtifact("a11",
                                          black,
                                          "Warp Params1");
        Artifact a12=oFactory.newArtifact("a12",
                                          black,
                                          "Warp Params2");
        Artifact a13=oFactory.newArtifact("a13",
                                          black,
                                          "Warp Params3");
        Artifact a14=oFactory.newArtifact("a14",
                                          black,
                                          "Warp Params4");


        Artifact a15=oFactory.newArtifact("a15",
                                         black,
                                         "Resliced I1");
        Artifact a16=oFactory.newArtifact("a16",
                                         black,
                                         "Resliced H1");
        Artifact a17=oFactory.newArtifact("a17",
                                         black,
                                         "Resliced I2");
        Artifact a18=oFactory.newArtifact("a18",
                                         black,
                                         "Resliced H2");
        Artifact a19=oFactory.newArtifact("a19",
                                         black,
                                         "Resliced I3");
        Artifact a20=oFactory.newArtifact("a20",
                                         black,
                                         "Resliced H3");
        Artifact a21=oFactory.newArtifact("a21",
                                         black,
                                         "Resliced I4");
        Artifact a22=oFactory.newArtifact("a22",
                                          black,
                                          "Resliced H4");



        Artifact a23=oFactory.newArtifact("a23",
                                          black,
                                          "Atlas Image");

        Artifact a24=oFactory.newArtifact("a24",
                                          black,
                                          "Atlas Header");

        Artifact a25=oFactory.newArtifact("a25",
                                          black,
                                          "Atlas X Slice");
        Artifact a26=oFactory.newArtifact("a26",
                                          black,
                                          "Atlas Y Slice");
        Artifact a27=oFactory.newArtifact("a27",
                                          black,
                                          "Atlas Z Slice");


        Artifact a28=oFactory.newArtifact("a28",
                                          black,
                                          "Atlas X Graphic");
        Artifact a29=oFactory.newArtifact("a29",
                                          black,
                                          "Atlas Y Graphic");
        Artifact a30=oFactory.newArtifact("a30",
                                          black,
                                          "Atlas Z Graphic");

        Used u1=oFactory.newUsed(p1,oFactory.newRole("img"),a3,black);
        Used u2=oFactory.newUsed(p1,oFactory.newRole("hdr"),a4,black);
        Used u3=oFactory.newUsed(p1,oFactory.newRole("imgRef"),a1,black);
        Used u4=oFactory.newUsed(p1,oFactory.newRole("hdrRef"),a2,black);
        Used u5=oFactory.newUsed(p2,oFactory.newRole("img"),a5,black);
        Used u6=oFactory.newUsed(p2,oFactory.newRole("hdr"),a6,black);
        Used u7=oFactory.newUsed(p2,oFactory.newRole("imgRef"),a1,black);
        Used u8=oFactory.newUsed(p2,oFactory.newRole("hdrRef"),a2,black);
        Used u9=oFactory.newUsed(p3,oFactory.newRole("img"),a7,black);
        Used u10=oFactory.newUsed(p3,oFactory.newRole("hdr"),a8,black);
        Used u11=oFactory.newUsed(p3,oFactory.newRole("imgRef"),a1,black);
        Used u12=oFactory.newUsed(p3,oFactory.newRole("hdrRef"),a2,black);
        Used u13=oFactory.newUsed(p4,oFactory.newRole("img"),a9,black);
        Used u14=oFactory.newUsed(p4,oFactory.newRole("hdr"),a10,black);
        Used u15=oFactory.newUsed(p4,oFactory.newRole("imgRef"),a1,black);
        Used u16=oFactory.newUsed(p4,oFactory.newRole("hdrRef"),a2,black);

        Used u17=oFactory.newUsed(p5,oFactory.newRole("in"),a11,black);
        Used u18=oFactory.newUsed(p6,oFactory.newRole("in"),a12,black);
        Used u19=oFactory.newUsed(p7,oFactory.newRole("in"),a13,black);
        Used u20=oFactory.newUsed(p8,oFactory.newRole("in"),a14,black);

        Used u21=oFactory.newUsed(p9,oFactory.newRole("i1"),a15,black);
        Used u22=oFactory.newUsed(p9,oFactory.newRole("h1"),a16,black);
        Used u23=oFactory.newUsed(p9,oFactory.newRole("i2"),a17,black);
        Used u24=oFactory.newUsed(p9,oFactory.newRole("h2"),a18,black);
        Used u25=oFactory.newUsed(p9,oFactory.newRole("i3"),a19,black);
        Used u26=oFactory.newUsed(p9,oFactory.newRole("h3"),a20,black);
        Used u27=oFactory.newUsed(p9,oFactory.newRole("i4"),a21,black);
        Used u28=oFactory.newUsed(p9,oFactory.newRole("h4"),a22,black);

        Used u29=oFactory.newUsed(p10,oFactory.newRole("img"),a23,black);
        Used u30=oFactory.newUsed(p10,oFactory.newRole("hdr"),a24,black);
        Used u31=oFactory.newUsed(p11,oFactory.newRole("img"),a23,black);
        Used u32=oFactory.newUsed(p11,oFactory.newRole("hdr"),a24,black);
        Used u33=oFactory.newUsed(p12,oFactory.newRole("img"),a23,black);
        Used u34=oFactory.newUsed(p12,oFactory.newRole("hdr"),a24,black);

        Used u35=oFactory.newUsed(p13,oFactory.newRole("in"),a25,black);
        Used u36=oFactory.newUsed(p14,oFactory.newRole("in"),a26,black);
        Used u37=oFactory.newUsed(p15,oFactory.newRole("in"),a27,black);




        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a11,oFactory.newRole("out"),p1,black);
        WasGeneratedBy wg2=oFactory.newWasGeneratedBy(a12,oFactory.newRole("out"),p2,black);
        WasGeneratedBy wg3=oFactory.newWasGeneratedBy(a13,oFactory.newRole("out"),p3,black);
        WasGeneratedBy wg4=oFactory.newWasGeneratedBy(a14,oFactory.newRole("out"),p4,black);

        WasGeneratedBy wg5=oFactory.newWasGeneratedBy(a15,oFactory.newRole("img"),p5,black);
        WasGeneratedBy wg6=oFactory.newWasGeneratedBy(a16,oFactory.newRole("hdr"),p5,black);
        WasGeneratedBy wg7=oFactory.newWasGeneratedBy(a17,oFactory.newRole("img"),p6,black);
        WasGeneratedBy wg8=oFactory.newWasGeneratedBy(a18,oFactory.newRole("hdr"),p6,black);
        WasGeneratedBy wg9=oFactory.newWasGeneratedBy(a19,oFactory.newRole("img"),p7,black);
        WasGeneratedBy wg10=oFactory.newWasGeneratedBy(a20,oFactory.newRole("hdr"),p7,black);
        WasGeneratedBy wg11=oFactory.newWasGeneratedBy(a21,oFactory.newRole("img"),p8,black);
        WasGeneratedBy wg12=oFactory.newWasGeneratedBy(a22,oFactory.newRole("hdr"),p8,black);

        WasGeneratedBy wg13=oFactory.newWasGeneratedBy(a23,oFactory.newRole("img"),p9,black);
        WasGeneratedBy wg14=oFactory.newWasGeneratedBy(a24,oFactory.newRole("hdr"),p9,black);

        WasGeneratedBy wg15=oFactory.newWasGeneratedBy(a25,oFactory.newRole("out"),p10,black);
        WasGeneratedBy wg16=oFactory.newWasGeneratedBy(a26,oFactory.newRole("out"),p11,black);
        WasGeneratedBy wg17=oFactory.newWasGeneratedBy(a27,oFactory.newRole("out"),p12,black);

        WasGeneratedBy wg18=oFactory.newWasGeneratedBy(a28,oFactory.newRole("out"),p13,black);
        WasGeneratedBy wg19=oFactory.newWasGeneratedBy(a29,oFactory.newRole("out"),p14,black);
        WasGeneratedBy wg20=oFactory.newWasGeneratedBy(a30,oFactory.newRole("out"),p15,black);


        WasDerivedFrom wd1=oFactory.newWasDerivedFrom(a11,a1,black);
        WasDerivedFrom wd2=oFactory.newWasDerivedFrom(a11,a2,black);
        WasDerivedFrom wd3=oFactory.newWasDerivedFrom(a11,a3,black);
        WasDerivedFrom wd4=oFactory.newWasDerivedFrom(a11,a4,black);
        WasDerivedFrom wd5=oFactory.newWasDerivedFrom(a12,a1,black);
        WasDerivedFrom wd6=oFactory.newWasDerivedFrom(a12,a2,black);
        WasDerivedFrom wd7=oFactory.newWasDerivedFrom(a12,a5,black);
        WasDerivedFrom wd8=oFactory.newWasDerivedFrom(a12,a6,black);
        WasDerivedFrom wd9=oFactory.newWasDerivedFrom(a13,a1,black);
        WasDerivedFrom wd10=oFactory.newWasDerivedFrom(a13,a2,black);
        WasDerivedFrom wd11=oFactory.newWasDerivedFrom(a13,a7,black);
        WasDerivedFrom wd12=oFactory.newWasDerivedFrom(a13,a8,black);
        WasDerivedFrom wd13=oFactory.newWasDerivedFrom(a14,a1,black);
        WasDerivedFrom wd14=oFactory.newWasDerivedFrom(a14,a2,black);
        WasDerivedFrom wd15=oFactory.newWasDerivedFrom(a14,a9,black);
        WasDerivedFrom wd16=oFactory.newWasDerivedFrom(a14,a10,black);


        WasDerivedFrom wd17=oFactory.newWasDerivedFrom(a15,a11,black);
        WasDerivedFrom wd18=oFactory.newWasDerivedFrom(a16,a11,black);
        WasDerivedFrom wd19=oFactory.newWasDerivedFrom(a17,a12,black);
        WasDerivedFrom wd20=oFactory.newWasDerivedFrom(a18,a12,black);
        WasDerivedFrom wd21=oFactory.newWasDerivedFrom(a19,a13,black);
        WasDerivedFrom wd22=oFactory.newWasDerivedFrom(a20,a13,black);
        WasDerivedFrom wd23=oFactory.newWasDerivedFrom(a21,a14,black);
        WasDerivedFrom wd24=oFactory.newWasDerivedFrom(a22,a14,black);


        WasDerivedFrom wd25=oFactory.newWasDerivedFrom(a23,a15,black);
        WasDerivedFrom wd26=oFactory.newWasDerivedFrom(a23,a16,black);
        WasDerivedFrom wd27=oFactory.newWasDerivedFrom(a23,a17,black);
        WasDerivedFrom wd28=oFactory.newWasDerivedFrom(a23,a18,black);
        WasDerivedFrom wd29=oFactory.newWasDerivedFrom(a23,a19,black);
        WasDerivedFrom wd30=oFactory.newWasDerivedFrom(a23,a20,black);
        WasDerivedFrom wd31=oFactory.newWasDerivedFrom(a23,a21,black);
        WasDerivedFrom wd32=oFactory.newWasDerivedFrom(a23,a22,black);

        WasDerivedFrom wd33=oFactory.newWasDerivedFrom(a24,a15,black);
        WasDerivedFrom wd34=oFactory.newWasDerivedFrom(a24,a16,black);
        WasDerivedFrom wd35=oFactory.newWasDerivedFrom(a24,a17,black);
        WasDerivedFrom wd36=oFactory.newWasDerivedFrom(a24,a18,black);
        WasDerivedFrom wd37=oFactory.newWasDerivedFrom(a24,a19,black);
        WasDerivedFrom wd38=oFactory.newWasDerivedFrom(a24,a20,black);
        WasDerivedFrom wd39=oFactory.newWasDerivedFrom(a24,a21,black);
        WasDerivedFrom wd40=oFactory.newWasDerivedFrom(a24,a22,black);

        WasDerivedFrom wd41=oFactory.newWasDerivedFrom(a25,a23,black);
        WasDerivedFrom wd42=oFactory.newWasDerivedFrom(a25,a24,black);
        WasDerivedFrom wd43=oFactory.newWasDerivedFrom(a26,a23,black);
        WasDerivedFrom wd44=oFactory.newWasDerivedFrom(a26,a24,black);
        WasDerivedFrom wd45=oFactory.newWasDerivedFrom(a27,a23,black);
        WasDerivedFrom wd46=oFactory.newWasDerivedFrom(a27,a24,black);

        WasDerivedFrom wd47=oFactory.newWasDerivedFrom(a28,a25,black);
        WasDerivedFrom wd48=oFactory.newWasDerivedFrom(a29,a26,black);
        WasDerivedFrom wd49=oFactory.newWasDerivedFrom(a30,a27,black);



        WasControlledBy wc1=oFactory.newWasControlledBy(p1,oFactory.newRole("user"),ag1,black);



        OPMGraph graph=oFactory.newOPMGraph(black,
                                            new Overlaps[] { },
                                            new Process[] {p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15},
                                            new Artifact[] {a1,a2,a5,a6,a3,a4,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20,
                                                            a21,a22,a23,a24,a25,a26,a27,a28,a29,a30},
                                            new Agent[] { //ag1
                                                        },
                                            new Object[] {u1,u2,u3,u4,u5,u6,u7,u8,u9,u10,u11,u12,u13,u14,u15,u16,u17,u18,u19,u20,
                                                          u21,u22,u23,u24,u25,u26,u27,u28,u29,u30,u31,u32,u33,u34,u35,u36,u37,
                                                          wg1,wg2,wg3,wg4,wg5,wg6,wg7,wg8,wg9,wg10,wg11,wg12,wg13,wg14,
                                                          wg15,wg16,wg17,wg18,wg19,wg20,
                                                          wd1,wd2,wd3,wd4,wd5,wd6,wd7,wd8,wd9,wd10,wd11,wd12,wd13,wd14,wd15,wd16,
                                                          wd17,wd18,wd19,wd20,wd21,wd22,wd23,wd24,wd25,wd26,wd27,wd28,wd29,wd30,wd31,
                                                          wd32,wd33,wd34,wd35,wd36,wd37,wd38,wd39,wd40,wd41,wd42,wd43,wd44,wd45,wd46,
                                                          wd47,wd48,wd49
                                                          //wc1,
                                            } );



        return graph;
    }
    

    /** Produces a dot representation
     * of created graph. */
    public void testPC1FullConversion() throws java.io.FileNotFoundException,  java.io.IOException   {
        OPMToDot toDot=new OPMToDot(true); // with roles
        
        toDot.convert(graph1,"target/pc1-full.dot", "target/pc1-full.pdf");
    }




    public void testCopyPC1Full() throws java.io.FileNotFoundException,  java.io.IOException   {
        OPMFactory oFactory=new OPMFactory();

        OPMGraph graph2=oFactory.newOPMGraph(graph1);

        assertTrue( "self graph1 differ", graph1.equals(graph1) );        

        assertTrue( "self graph2 differ", graph2.equals(graph2) );        

        assertTrue( "graph1 graph2 differ", graph1.equals(graph2) );        
        
    }

}
