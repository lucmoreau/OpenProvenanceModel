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
public class NumericTest 
    extends TestCase
{
    public static OPMFactory oFactory=new OPMFactory();

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public NumericTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */


    static OPMGraph graph1;


    public void testNumeric() throws JAXBException
    {
        OPMGraph graph=makeNumericGraph(oFactory);

        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/numeric.xml"),graph,true);

        
        //System.out.println(sw);

        graph1=graph;
        System.out.println("Numeric Test asserting True");
        assertTrue( true );


    }

    static String PATH_PROPERTY="http://openprovenance.org/primitives#path";
    static String PRIMITIVE_PROPERTY="http://openprovenance.org/primitives#primitive";
    static String FILE_LOCATION="/shomewhere/pc1/";

    static String PRIMITIVE_SUM="http://openprovenance.org/primitives#sum";
    static String PRIMITIVE_MULTIPLICATION="http://openprovenance.org/primitives#multiplication";
    static String PRIMITIVE_DIVISION="http://openprovenance.org/primitives#division";
    static String PRIMITIVE_ALIGN_WARP="http://openprovenance.org/primitives#align_warp";
    static String PRIMITIVE_RESLICE="http://openprovenance.org/primitives#reslice";
    static String PRIMITIVE_SOFTMEAN="http://openprovenance.org/primitives#softmean";
    static String PRIMITIVE_CONVERT="http://openprovenance.org/primitives#convert";
    static String PRIMITIVE_SLICER="http://openprovenance.org/primitives#slicer";


    public Artifact newFile(OPMFactory oFactory,
                            String id,
                            Collection<Account> accounts,
                            String label,
                            String file,
                            String location) {
                            
        Artifact a=oFactory.newArtifact(id,
                                        accounts,
                                        label);
        oFactory.addAnnotation(a,
                               oFactory.newType("http://openprovenance.org/primitives#File"));
        oFactory.addAnnotation(a,
                               oFactory.newEmbeddedAnnotation("an1_" + id,
                                                              PATH_PROPERTY,
                                                              location + file,
                                                              null));
        return a;
    }


    public Artifact newParameter(OPMFactory oFactory,
                                 String id,
                                 Collection<Account> accounts,
                                 String label,
                                 String value) {
                            
        Artifact a=oFactory.newArtifact(id,
                                        accounts,
                                        label);
        oFactory.addAnnotation(a,
                               oFactory.newType("http://openprovenance.org/primitives#String"));
        oFactory.addValue(a,value,"mime:application/text");

        return a;
    }


    public Artifact newNumber(OPMFactory oFactory,
                              String id,
                              Collection<Account> accounts,
                              String label,
                              int value) {
                            
        Artifact a=oFactory.newArtifact(id,
                                        accounts,
                                        label);
        oFactory.addAnnotation(a,
                               oFactory.newType("http://openprovenance.org/primitives#Integer"));
        oFactory.addValue(a,value,"mime:application/int");

        return a;
    }

    public Process newProcess(OPMFactory oFactory,
                              String id,
                              Collection<Account> accounts,
                              String label,
                              String type) {
        Process p=oFactory.newProcess(id,
                                       accounts,
                                       label);
        oFactory.addAnnotation(p,
                               oFactory.newEmbeddedAnnotation("an1_" + id,
                                                              PRIMITIVE_PROPERTY,
                                                              type,
                                                              null));
        return p;
    }




    public OPMGraph makeNumericGraph(OPMFactory oFactory) {
        return makeNumericGraph(oFactory,FILE_LOCATION,"./");
    }

    public OPMGraph makeNumericGraph(OPMFactory oFactory, String inputLocation, String outputLocation)
    {

        Collection<Account> black=Collections.singleton(oFactory.newAccount("black"));
        

        Process p0=newProcess(oFactory,
                              "p0",
                              black,
                              "+",
                              PRIMITIVE_SUM);

        Process p1=newProcess(oFactory,
                              "p1",
                              black,
                              "*",
                              PRIMITIVE_MULTIPLICATION);

        Process p2=newProcess(oFactory,
                              "p2",
                              black,
                              "/",
                              PRIMITIVE_DIVISION);


        Artifact a1=newNumber(oFactory,
                              "a1",
                              black,
                              "n1",
                              10);

        Artifact a2=newNumber(oFactory,
                              "a2",
                              black,
                              "n2",
                              20);

        Artifact a3=newNumber(oFactory,
                              "a3",
                              black,
                              "n3",
                              30);

        Artifact a4=newNumber(oFactory,
                              "a4",
                              black,
                              "n4",
                              9);

        Artifact a5=newNumber(oFactory,
                              "a5",
                              black,
                              "n5",
                              30);

        Artifact a6=newNumber(oFactory,
                              "a6",
                              black,
                              "n6",
                              900);

        Artifact a7=newNumber(oFactory,
                              "a7",
                              black,
                              "n7",
                              100);

        Used u1=oFactory.newUsed(p0,oFactory.newRole("summand1"),a1,black);
        Used u2=oFactory.newUsed(p0,oFactory.newRole("summand2"),a2,black);
        Used u3=oFactory.newUsed(p1,oFactory.newRole("factor1"),a3,black);
        Used u4=oFactory.newUsed(p1,oFactory.newRole("factor2"),a5,black);
        Used u5=oFactory.newUsed(p2,oFactory.newRole("dividend"),a6,black);
        Used u6=oFactory.newUsed(p2,oFactory.newRole("divisor"),a4,black);





        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a5,oFactory.newRole("out"),p0,black);
        WasGeneratedBy wg2=oFactory.newWasGeneratedBy(a6,oFactory.newRole("product"),p1,black);
        WasGeneratedBy wg3=oFactory.newWasGeneratedBy(a7,oFactory.newRole("quotient"),p2,black);



        WasDerivedFrom wd1=oFactory.newWasDerivedFrom(a5,a1,black);
        WasDerivedFrom wd2=oFactory.newWasDerivedFrom(a5,a2,black);
        WasDerivedFrom wd3=oFactory.newWasDerivedFrom(a6,a3,black);
        WasDerivedFrom wd4=oFactory.newWasDerivedFrom(a6,a5,black);
        WasDerivedFrom wd5=oFactory.newWasDerivedFrom(a7,a6,black);
        WasDerivedFrom wd6=oFactory.newWasDerivedFrom(a7,a4,black);


        OPMGraph graph=oFactory.newOPMGraph(black,
                                            new Overlaps[] { },
                                            new Process[] {p0, p1, p2},
                                            new Artifact[] {a1,a2,a5,a6,a3,a4,a7},
                                            new Agent[] { //ag1
                                            },
                                            new Object[] {u1,u2,u3,u4,u5,u6,
                                                          wg1,wg2,wg3,
                                                          wd1,wd2,wd3,wd4,wd5,wd6
                                                          //wc1,
                                            } );



        return graph;
    }
    

    /** Produces a dot representation
     * of created graph. */
    public void testNumericConversion() throws java.io.FileNotFoundException,  java.io.IOException   {
        OPMToDot toDot=new OPMToDot(true); // with roles
        
        toDot.convert(graph1,"target/numeric.dot", "target/numeric.pdf");
    }




    public void testCopyNumeric() throws java.io.FileNotFoundException,  java.io.IOException   {
        OPMFactory oFactory=new OPMFactory();

        OPMGraph graph2=oFactory.newOPMGraph(graph1);

        assertTrue( "self graph1 differ", graph1.equals(graph1) );        

        assertTrue( "self graph2 differ", graph2.equals(graph2) );        

        assertTrue( "graph1 graph2 differ", graph1.equals(graph2) );        
        
    }

}
