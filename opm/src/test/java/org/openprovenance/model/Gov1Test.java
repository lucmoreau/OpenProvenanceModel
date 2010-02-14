package org.openprovenance.model;
import java.io.File;
import java.io.StringWriter;
import java.util.Collection;
import java.net.URI;
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
public class Gov1Test 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public Gov1Test( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */


    static OPMGraph graph1;

    static public OPMFactory oFactory=new OPMFactory();



    public void testGov1() throws Exception
    {
        OPMGraph graph=makeGov1Graph(oFactory);

        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/gov1.xml"),graph,true);

        
        //System.out.println(sw);

        graph1=graph;
        System.out.println("testOPM1 asserting True");
        assertTrue( true );




        OPMToDot toDot=new OPMToDot();        
        toDot.convert(graph1,"target/gov1.dot", "target/gov1.pdf");


    }

    public OPMGraph makeGov1Graph(OPMFactory oFactory) throws Exception {

        CollectionFactory cFactory=new CollectionFactory(oFactory);

        Collection<Account> black=Collections.singleton(oFactory.newAccount("black"));
        Collection<Account> orange=Collections.singleton(oFactory.newAccount("orange"));

        Agent ag1=oFactory.newAgent("ag1",
                                    black,
                                    "Jeni");
	OTime otime1=oFactory.newInstantaneousTimeNow();
        
        Process p1=oFactory.newProcess("p1",
                                       black,
                                       "download");

	OTime otime2=oFactory.newInstantaneousTimeNow();
        Process p2=oFactory.newProcess("p2",
                                       black,
                                       "unzip");

	OTime otime3=oFactory.newInstantaneousTimeNow();
        Process p3=oFactory.newProcess("p3",
                                       black,
                                       "transform");
	OTime otime4=oFactory.newInstantaneousTimeNow();
        Process p4=oFactory.newProcess("p4",
                                       black,
                                       "select");
	OTime otime5=oFactory.newInstantaneousTimeNow();
        Process p5=oFactory.newProcess("p5",
                                       black,
                                       "access");



        List<Account> black_orange=new LinkedList();
        black_orange.addAll(orange);
        black_orange.addAll(black);


        Artifact a1=oFactory.newArtifact("a1",
                                         black,
                                         "zip file");
        Artifact a2=oFactory.newArtifact("a2",
                                         black,
                                         "local zip file");
        Artifact a3=oFactory.newArtifact("a3",
                                         black,
                                         "GOR csv file");
        Artifact a4=oFactory.newArtifact("a4",
                                         black,
                                         "region xslt");
        Artifact a5=oFactory.newArtifact("a5",
                                         black,
                                         "GOR rdf file");
        Artifact a6=oFactory.newArtifact("a6",
                                         black,
                                         "Government Office Regions");
        Artifact a7=oFactory.newArtifact("a7",
                                         black,
                                         "Region H");

        oFactory.addAnnotation(a6,
                               oFactory.newPName("http://statistics.data.gov.uk/id/region"));
        oFactory.addAnnotation(a6,
                               oFactory.newType("http://rdfs.org/ns/void#DataSet"));
        oFactory.addAnnotation(a6,
                               oFactory.newEmbeddedAnnotation("ea1",
							      "foaf:homepage",
							      "http://statistics.data.gov.uk/doc/region",
							      null));

        oFactory.addAnnotation(a5,
                               oFactory.newType("http://rdfs.org/ns/void#DataSet"));
        oFactory.addReference(a5,
			      "http://www.jenitennison.com/log/2009-10-24/cache/GOR_DEC_2008_EN_NC.rdf",
			      "http://www.iana.org/assignments/media-types/application/xsl+rdf",
			      "CEDDDDEA");


        oFactory.addAnnotation(a3,
                               oFactory.newType("http://www.iana.org/assignments/media-types/application/excel"));
        oFactory.addReference(a3,
			      "http://www.jenitennison.com/log/2009-10-24/cache/GOR_DEC_2008_EN_NC.csv",
			      "http://www.iana.org/assignments/media-types/text/csv",
			      "HCEAAAC");


        oFactory.addAnnotation(a4,
                               oFactory.newType("http://www.w3.org/1999/XSL/Transform"));
        oFactory.addReference(a4,
			      "http://www.jenitennison.com/log/2009-10-24/cache/region.xsl",
			      "http://www.iana.org/assignments/media-types/application/xslt+xml",
			      "CDEBGFH");


        oFactory.addAnnotation(a2,
                               oFactory.newType("http://www.iana.org/assignments/media-types/application/zip"));
        oFactory.addReference(a2,
			      "http://www.jenitennison.com/log/2009-10-24/cache/government-office-regions.zip",
			      "http://www.iana.org/assignments/media-types/application/zip",
			      "EDABGFH");


        oFactory.addAnnotation(a1,
                               oFactory.newType("http://www.iana.org/assignments/media-types/application/zip"));
        oFactory.addReference(a1,
			      "http://www.ons.gov.uk/about-statistics/geography/products/geog-products-area/names-codes/administrative/government-office-regions.zip",
			      "http://www.iana.org/assignments/media-types/application/zip",
			      "EDABGFH");

        Artifact a3-A2=oFactory.newArtifact("a3_A2",
					    black,
					    "A");
        Artifact a3_B2=oFactory.newArtifact("a3_B2",
					    black,
					    "North East");
	
        Artifact a3_A8=oFactory.newArtifact("a3_A8",
					    black,
					    "H");
        Artifact a3_B8=oFactory.newArtifact("a3_B2",
					    black,
					    "London");
	
	
	
        
        Used u1=oFactory.newUsed(p1,oFactory.newRole("src"),a1,black);

        Used u2=oFactory.newUsed(p2,oFactory.newRole("in"),a2,black);

        Used u3=oFactory.newUsed(p3,oFactory.newRole("in"),a3,black);

        Used u4=oFactory.newUsed(p3,oFactory.newRole("in"),a4,black);

        Used u5=oFactory.newUsed(p4,oFactory.newRole("in"),a5,black);

        Used u6=oFactory.newUsed(p5,oFactory.newRole("in"),a6,black);




        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a2,oFactory.newRole("dst"),p1,black);

        WasGeneratedBy wg2=oFactory.newWasGeneratedBy(a3,oFactory.newRole("out"),p2,black);

        WasGeneratedBy wg3=oFactory.newWasGeneratedBy(a5,oFactory.newRole("out"),p3,black);

        WasGeneratedBy wg4=oFactory.newWasGeneratedBy(a6,oFactory.newRole("out"),p4,black);

        WasGeneratedBy wg5=oFactory.newWasGeneratedBy(a7,oFactory.newRole("out"),p5,black);


        WasDerivedFrom wd1=cFactory.newWasIdenticalTo("wd1",a2,a1,black);
	WasDerivedFrom wd2=cFactory.newContained("wd2",a3,a2,black);
	WasDerivedFrom wd3=oFactory.newWasDerivedFrom("wd3",a5,a3,"wasTransformationOf",black);
	WasDerivedFrom wd3b=oFactory.newWasDerivedFrom("wd3",a5,a4,"wasStyledBy",black);
	WasDerivedFrom wd4=oFactory.newWasDerivedFrom("wd4",a6,a5,"wasSubsetOf",black);
	WasDerivedFrom wd5=cFactory.newContained("wd5",a7,a6,black);


        WasControlledBy wc1=oFactory.newWasControlledBy(p1,oFactory.newRole("user"),ag1,black);
        WasControlledBy wc2=oFactory.newWasControlledBy(p2,oFactory.newRole("user"),ag1,black);
        WasControlledBy wc3=oFactory.newWasControlledBy(p3,oFactory.newRole("user"),ag1,black);
        WasControlledBy wc4=oFactory.newWasControlledBy(p4,oFactory.newRole("user"),ag1,black);
        WasControlledBy wc5=oFactory.newWasControlledBy(p5,oFactory.newRole("user"),ag1,black);

	wc1.setStartTime(otime1);
	wc2.setStartTime(otime2);
	wc3.setStartTime(otime3);
	wc4.setStartTime(otime4);
	wc5.setStartTime(otime5);


	WasDerivedFrom wd3_A2=cFactory.newContained("wd3_A2",a3_A2,a2,black);
	WasDerivedFrom wd3_B2=cFactory.newContained("wd3_B2",a3_B2,a2,black);
	WasDerivedFrom wd3_A8=cFactory.newContained("wd3_A8",a3_A8,a2,black);
	WasDerivedFrom wd3_B8=cFactory.newContained("wd3_B8",a3_B8,a2,black);

        

        OPMGraph graph=oFactory.newOPMGraph(black_orange,
                                            new Overlaps[] { },
                                            new Process[] {p1, p2, p3, p4, p5},
                                            new Artifact[] {a1,a2, a3, a4, a5, a6, a7},
                                            new Agent[] { ag1 },
                                            new Object[] {u1,u2, u3, u4, u5, u6, 
                                                          wg1, wg2, wg3, wg4, wg5,
							  wc1, wc2, wc3, wc4, wc5,
                                                          wd1, wd2, wd3, wd3b, wd4, wd5} );




	return graph;
    }
    
}
