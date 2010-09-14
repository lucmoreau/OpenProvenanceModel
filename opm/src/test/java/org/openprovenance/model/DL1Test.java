package org.openprovenance.model;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;



/**
 * Unit test for simple Provenance Challenge 1 like workflow.
 */
public class DL1Test 
    extends TestCase
{

    public static String APP_NS="http://www.ipaw.info/dl1";

    public static OPMFactory oFactory=new OPMFactory();

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DL1Test( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */


    static OPMGraph graph1;


    public void testDL1() throws JAXBException
    {

        OPMGraph graph=makeDL1Graph(oFactory);


        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/dl1.xml"),graph,true);

        
        //System.out.println(sw);

        graph1=graph;
        System.out.println("DL1 Test asserting True");
        assertTrue( true );

        

    }

    public OPMGraph makeDL1Graph(OPMFactory oFactory)
    {

        Collection<Account> black=Collections.singleton(oFactory.newAccount("black"));
        Collection<Account> summary=Collections.singleton(oFactory.newAccount("summary"));
        Collection<Account> collection1=Collections.singleton(oFactory.newAccount("collection1"));
        Collection<Account> collection2=Collections.singleton(oFactory.newAccount("collection2"));


        List<Account> allAccounts=new LinkedList();
        allAccounts.addAll(black);
        allAccounts.addAll(summary);
        allAccounts.addAll(collection1);
        allAccounts.addAll(collection2);

        
        Process p1=oFactory.newProcess("p1",
                                       black,
                                       "Query");

        Process r1=oFactory.newProcess("r1",
                                       black,
                                       "Restore");
        Process s1=oFactory.newProcess("s1",
                                       black,
                                       "Select");
        Process ed1=oFactory.newProcess("ed1",
					black,
					"Edit");
        Process g11=oFactory.newProcess("g11",
					black,
					"Generate");
        Process g12=oFactory.newProcess("g12",
					black,
					"Generate");

        Process rt1=oFactory.newProcess("rt1",
					black,
					"Retrieve");

        Process sum1=oFactory.newProcess("sum1",
					black,
					"Summarize");



        Agent hist1=oFactory.newAgent("hist1",
				      black,
				      "Historian1");

        Artifact docdb=oFactory.newArtifact("docdb",
					    black,
					    "DocDB");
        Artifact q=oFactory.newArtifact("q",
					black,
					"Query");
	

        Artifact d1=oFactory.newArtifact("d1",
                                         black,
                                         "D1");
        Artifact t1=oFactory.newArtifact("t1",
                                         black,
                                         "T1");
        Artifact v1=oFactory.newArtifact("v1",
                                         black,
                                         "V1");
        Artifact tds1=oFactory.newArtifact("tds1",
					   black,
					   "3Ds1");
        Artifact td11=oFactory.newArtifact("td11",
					  black,
					  "3D1.1");
        Artifact td12=oFactory.newArtifact("td12",
					   black,
					   "3D1.2");
        Artifact m1=oFactory.newArtifact("m1",
                                         black,
                                         "Map1");
        Artifact md1=oFactory.newArtifact("md1",
                                         black,
                                         "MD1");

        Artifact ov1=oFactory.newArtifact("ov1",
					  black,
					  "OV1");
        Artifact rv1=oFactory.newArtifact("rv1",
					  black,
					  "RV1");

        Artifact sd1=oFactory.newArtifact("sd1",
                                         summary,
                                         "SD1");
        Artifact st1=oFactory.newArtifact("st1",
                                         black,
                                         "ST1");
        Artifact sv1=oFactory.newArtifact("sv1",
                                         black,
                                         "SV1");
        Artifact stds1=oFactory.newArtifact("stds1",
					   black,
					   "S3Ds1");
        Artifact std11=oFactory.newArtifact("std11",
					  black,
					  "S3D1.1");
        Artifact std12=oFactory.newArtifact("std12",
					   black,
					   "S3D1.2");
        Artifact sm1=oFactory.newArtifact("sm1",
                                         black,
                                         "SMap1");
        Artifact smd1=oFactory.newArtifact("smd1",
                                         black,
                                         "SMD1");
        Artifact ot1=oFactory.newArtifact("ot1",
                                         black,
                                         "OT1");
        Artifact vp11=oFactory.newArtifact("vp11",
					   black,
					   "VP11");
        Artifact vp12=oFactory.newArtifact("vp12",
					   black,
					   "VP12");
        Artifact sd11=oFactory.newArtifact("sd11",
					   black,
					   "SD11");
        Artifact sd12=oFactory.newArtifact("sd12",
					   black,
					   "SD12");

        Artifact alg11=oFactory.newArtifact("alg11",
					   black,
					   "ALG11");
        Artifact alg12=oFactory.newArtifact("alg12",
					   black,
					   "ALG12");
        Artifact res1=oFactory.newArtifact("res1",
					   black,
					   "Res 1");


        Used u1=oFactory.newUsed(p1,oFactory.newRole("query"),q,summary);
        Used u2=oFactory.newUsed(p1,oFactory.newRole("db"),docdb,summary);

        Used u3=oFactory.newUsed(r1,oFactory.newRole("in"),ov1,black);
        Used u4=oFactory.newUsed(s1,oFactory.newRole("in"),rv1,black);

        Used u5=oFactory.newUsed(ed1,oFactory.newRole("in"),ot1,black);

        Used u6=oFactory.newUsed(g11,oFactory.newRole("vp"),vp11,black);
        Used u7=oFactory.newUsed(g12,oFactory.newRole("vp"),vp12,black);
        Used u8=oFactory.newUsed(g11,oFactory.newRole("vp"),sd11,black);
        Used u9=oFactory.newUsed(g12,oFactory.newRole("vp"),sd12,black);
        Used u10=oFactory.newUsed(g11,oFactory.newRole("vp"),alg11,black);
        Used u11=oFactory.newUsed(g12,oFactory.newRole("vp"),alg12,black);
        Used u12=oFactory.newUsed(rt1,oFactory.newRole("res"),res1,black);

        Used u13=oFactory.newUsed(sum1,oFactory.newRole("in"),d1,summary);

        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(sd1,oFactory.newRole("out"),p1,summary);
        WasGeneratedBy wg2=oFactory.newWasGeneratedBy(rv1,oFactory.newRole("out"),r1,black);
        WasGeneratedBy wg3=oFactory.newWasGeneratedBy(v1,oFactory.newRole("out"),s1,black);

        WasGeneratedBy wg4=oFactory.newWasGeneratedBy(t1,oFactory.newRole("out"),ed1,black);
        WasGeneratedBy wg5=oFactory.newWasGeneratedBy(td11,oFactory.newRole("out"),g11,black);
        WasGeneratedBy wg6=oFactory.newWasGeneratedBy(td12,oFactory.newRole("out"),g12,black);

        WasGeneratedBy wg7=oFactory.newWasGeneratedBy(m1,oFactory.newRole("out"),rt1,black);
        WasGeneratedBy wg8=oFactory.newWasGeneratedBy(sd1,oFactory.newRole("out"),sum1,summary);

        // Document doc=oFactory.builder.newDocument();
        // Element el=doc.createElementNS(APP_NS,"app:ignore");
        // Element el2=doc.createElementNS(APP_NS,"app:image");
        // el.appendChild(el2);
        // el2.appendChild(doc.createTextNode("http://www.ipaw.info/challenge/anatomy1.img"));
        // doc.appendChild(el);


        
        // oFactory.addValue(a1,el,"http://www.ipaw.info/imagePointer");


      
        // Document doc_=oFactory.builder.newDocument();
        // Element el_=doc_.createElementNS(APP_NS,"app:ignore");
        // el_.appendChild(doc_.createCDATASection("   {\"menu\": { \"id\": \"file\", \"value\": \"File\"}}"));
        // doc_.appendChild(el_);


        // oFactory.addValue(a1,"   {\"menu\": { \"id\": \"file\", \"value\": \"File\"}}","mime:application/json");


        // oFactory.addValue(a1,10,"http://www.ipaw.info/imagePointer3");

        // oFactory.addValue(a1,"foo","http://www.ipaw.info/imagePointer4");

        // oFactory.addValue(a1,1.0,"http://www.ipaw.info/imagePointer5");




        // Used u1=oFactory.newUsed(p1,oFactory.newRole("img1"),a1,black);
        // Used u2=oFactory.newUsed(p1,oFactory.newRole("hdr1"),a2,black);
        // Used u3=oFactory.newUsed(p1,oFactory.newRole("imgRef"),a3,black);
        // Used u4=oFactory.newUsed(p1,oFactory.newRole("hdrRef"),a4,black);


	//        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a5,oFactory.newRole("x"),p1,black);


        WasDerivedFrom cdt1=oFactory.newWasDerivedFrom(d1,t1,collection1);
        WasDerivedFrom cdv1=oFactory.newWasDerivedFrom(d1,v1,collection1);
        WasDerivedFrom cdtds1=oFactory.newWasDerivedFrom(d1,tds1,collection1);
        WasDerivedFrom cdm1=oFactory.newWasDerivedFrom(d1,m1,collection1);
        WasDerivedFrom cdmd1=oFactory.newWasDerivedFrom(d1,md1,collection1);
        WasDerivedFrom ct3dst3d11=oFactory.newWasDerivedFrom(tds1,td11,collection1);
        WasDerivedFrom ct3dst3d12=oFactory.newWasDerivedFrom(tds1,td12,collection1);

        WasDerivedFrom csdst1=oFactory.newWasDerivedFrom(sd1,st1,collection2);
        WasDerivedFrom csdsv1=oFactory.newWasDerivedFrom(sd1,sv1,collection2);
        WasDerivedFrom csdstds1=oFactory.newWasDerivedFrom(sd1,stds1,collection2);
        WasDerivedFrom csdsm1=oFactory.newWasDerivedFrom(sd1,sm1,collection2);
        WasDerivedFrom csdsmd1=oFactory.newWasDerivedFrom(sd1,smd1,collection2);
        WasDerivedFrom cst3dsst3d11=oFactory.newWasDerivedFrom(stds1,std11,collection2);
        WasDerivedFrom cst3dsst3d12=oFactory.newWasDerivedFrom(stds1,std12,collection2);


	//        WasControlledBy wc1=oFactory.newWasControlledBy(p1,oFactory.newRole("user"),ag1,black);
        WasControlledBy wc2=oFactory.newWasControlledBy(ed1,oFactory.newRole("user"),hist1,black);

        WasDerivedFrom wd0=oFactory.newWasDerivedFrom(docdb,d1,black);

        WasDerivedFrom wd1=oFactory.newWasDerivedFrom(sd1,d1,summary);
        WasDerivedFrom wd2=oFactory.newWasDerivedFrom(st1,t1,summary);
        WasDerivedFrom wd3=oFactory.newWasDerivedFrom(sv1,v1,summary);
        WasDerivedFrom wd4=oFactory.newWasDerivedFrom(stds1,tds1,summary);
        WasDerivedFrom wd5=oFactory.newWasDerivedFrom(sm1,m1,summary);
        WasDerivedFrom wd6=oFactory.newWasDerivedFrom(std11,td11,summary);
        WasDerivedFrom wd7=oFactory.newWasDerivedFrom(std12,td12,summary);
        WasDerivedFrom wd8=oFactory.newWasDerivedFrom(smd1,md1,summary);

        WasDerivedFrom wd9=oFactory.newWasDerivedFrom(rv1,ov1,black);
        WasDerivedFrom wd10=oFactory.newWasDerivedFrom(v1,rv1,black);
        WasDerivedFrom wd11=oFactory.newWasDerivedFrom(t1,ot1,black);

        WasDerivedFrom wd12=oFactory.newWasDerivedFrom(m1,res1,black);

        OPMGraph graph=oFactory.newOPMGraph(allAccounts,
                                            new Overlaps[] { },
                                            new Process[] { p1, r1, s1, ed1, g11, g12, rt1, sum1},
                                            new Artifact[] {d1,t1,v1,tds1,td11,td12,m1, sd1,st1,sv1,stds1,std11,std12,sm1, docdb, rv1, ov1, ot1, vp11, vp12, sd11, sd12, alg11, alg12, res1, md1},
                                            new Agent[] { hist1 },
                                            new Object[] {cdt1, cdv1, cdtds1, cdm1, cdmd1, ct3dst3d11, ct3dst3d12,
					                  csdst1, csdsv1, csdstds1, csdsm1, csdsmd1, cst3dsst3d11, cst3dsst3d12,
					                  wd0, wd1, wd2, wd3, wd4, wd5, wd6, wd7, wd8, wd9, wd10, wd11,
					                  u1, u2, u3, u4, u5, u6, u7, u8, u9, u10, u11, u12, u13,
					                  wg1, wg2, wg3, wg4, wg5, wg6, wg7, wg8,
					                  wc2 } );



        return graph;

   }
    

    /** Produces a dot representation
     * of created graph. */
    public void testDL1Conversion() throws java.io.FileNotFoundException,  java.io.IOException   {
        //OPMToDot toDot=new OPMToDot(true); // with roles
        OPMToDot toDot=new OPMToDot("src/main/resources/dl1Config.xml"); // with multisteps
        
        toDot.convert(graph1,"target/dl1.dot", "target/dl1.pdf");
    }

    /** Produces a dot representation
     * of created graph. */
    public void testDL1Conversion2() throws java.io.FileNotFoundException,  java.io.IOException   {
        //OPMToDot toDot=new OPMToDot(true); // with roles
        OPMToDot toDot=new OPMToDot("src/main/resources/dl1Config2.xml"); // with multisteps
        
        toDot.convert(graph1,"target/dl12.dot", "target/dl12.pdf");
    }




    public void testDL1Copy() throws java.io.FileNotFoundException,  java.io.IOException   {
        OPMFactory oFactory=new OPMFactory();

        OPMGraph graph2=oFactory.newOPMGraph(graph1);

        assertTrue( "self graph1 differ", graph1.equals(graph1) );        

        assertTrue( "self graph2 differ", graph2.equals(graph2) );        

        assertTrue( "graph1 graph2 differ", graph1.equals(graph2) );        
        
    }



}
