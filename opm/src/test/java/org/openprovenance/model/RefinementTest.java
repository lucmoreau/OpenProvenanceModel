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
public class RefinementTest 
    extends TestCase
{
    public static OPMFactory oFactory=new OPMFactory();

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RefinementTest( String testName )
    {
        super( testName );
    }

    public boolean urlFlag=true;

    /**
     * @return the suite of tests being tested
     */


    static OPMGraph graph1;


    public void testRefinement() throws JAXBException
    {
        OPMGraph graph=makeRefinementGraph(oFactory);

        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/refinement1.xml"),graph,true);

        
        //System.out.println(sw);

        graph1=graph;
        System.out.println("Refinement Test asserting True");
        assertTrue( true );


    }

    static String PATH_PROPERTY="http://openprovenance.org/primitives#path";
    static String URL_PROPERTY="http://openprovenance.org/primitives#url";
    static String PRIMITIVE_PROPERTY="http://openprovenance.org/primitives#primitive";
    static String FILE_LOCATION="/shomewhere/pc1/";
    static String URL_LOCATION="http://www.ipaw.info/challenge/";

    static String PRIMITIVE_DIV="http://openprovenance.org/primitives#div";
    static String PRIMITIVE_MINUS10="http://openprovenance.org/primitives#minus10";
    static String PRIMITIVE_MINUS2="http://openprovenance.org/primitives#minus2";



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
                                                              (urlFlag) ? URL_PROPERTY : PATH_PROPERTY,
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


    public Artifact newInteger(OPMFactory oFactory,
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



    public OPMGraph makeRefinementGraph(OPMFactory oFactory) {
        if (urlFlag) {
            return makeRefinementGraph(oFactory,URL_LOCATION,URL_LOCATION);
        } else {
            return makeRefinementGraph(oFactory,FILE_LOCATION,"./");
        }
    }

    public OPMGraph makeRefinementGraph(OPMFactory oFactory, String inputLocation, String outputLocation)
    {

        Collection<Account> account0=Collections.singleton(oFactory.newAccount("account0"));
        Collection<Account> account1=Collections.singleton(oFactory.newAccount("account1"));
        Collection<Account> account2=Collections.singleton(oFactory.newAccount("account2"));
        
        List<Account> account01=new LinkedList();
        account01.addAll(account0);
        account01.addAll(account1);


        List<Account> account02=new LinkedList();
        account02.addAll(account0);
        account02.addAll(account2);

        List<Account> account12=new LinkedList();
        account12.addAll(account1);
        account12.addAll(account2);


        List<Account> account012=new LinkedList();
        account012.addAll(account0);
        account012.addAll(account1);
        account012.addAll(account2);


        Process p1=oFactory.newProcess("p1",
                                       account0,
                                       "p1");
        oFactory.addAnnotation(p1,
                               oFactory.newEmbeddedAnnotation("an1_p1",
                                                              PRIMITIVE_PROPERTY,
                                                              PRIMITIVE_MINUS10,
                                                              null));

        Process p2=oFactory.newProcess("p2",
                                       account0,
                                       "p2");
        oFactory.addAnnotation(p2,
                               oFactory.newEmbeddedAnnotation("an1_p2",
                                                              PRIMITIVE_PROPERTY,
                                                              PRIMITIVE_MINUS2,
                                                              null));

        Process p3=oFactory.newProcess("p3",
                                       account1,
                                       "p3");
        oFactory.addAnnotation(p3,
                               oFactory.newEmbeddedAnnotation("an1_p3",
                                                              PRIMITIVE_PROPERTY,
                                                              PRIMITIVE_DIV,
                                                              null));
        Process p4=oFactory.newProcess("p4",
                                       account2,
                                       "p4");
        oFactory.addAnnotation(p4,
                               oFactory.newEmbeddedAnnotation("an1_p4",
                                                              PRIMITIVE_PROPERTY,
                                                              PRIMITIVE_DIV,
                                                              null));



        Artifact a1=newInteger(oFactory,
                               "a1",
                               account01,
                               "a1",
                               14);
        Artifact a2=newInteger(oFactory,
                               "a2",
                               account012,
                               "a2",
                               4);

        Artifact a3=newInteger(oFactory,
                               "a3",
                               account02,
                               "a3",
                               2);

        Artifact a4=newInteger(oFactory,
                               "a4",
                               account1,
                               "a4",
                               3);
        Artifact a5=newInteger(oFactory,
                               "a5",
                               account12,
                               "a5",
                               2);
        Artifact a6=newInteger(oFactory,
                               "a6",
                               account2,
                               "a6",
                               0);


        Used u1=oFactory.newUsed(p1,oFactory.newRole("arg"),a1,account0);
        Used u2=oFactory.newUsed(p2,oFactory.newRole("arg"),a2,account0);
        Used u3=oFactory.newUsed(p3,oFactory.newRole("dividend"),a1,account1);
        Used u4=oFactory.newUsed(p3,oFactory.newRole("divisor"),a4,account1);
        Used u5=oFactory.newUsed(p4,oFactory.newRole("dividend"),a2,account2);
        Used u6=oFactory.newUsed(p4,oFactory.newRole("divisor"),a5,account2);






        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a2,oFactory.newRole("out"),p1,account0);
        WasGeneratedBy wg2=oFactory.newWasGeneratedBy(a3,oFactory.newRole("out"),p2,account0);
        WasGeneratedBy wg3=oFactory.newWasGeneratedBy(a2,oFactory.newRole("quotient"),p3,account1);
        WasGeneratedBy wg4=oFactory.newWasGeneratedBy(a5,oFactory.newRole("remainder"),p3,account1);
        WasGeneratedBy wg5=oFactory.newWasGeneratedBy(a3,oFactory.newRole("quotient"),p4,account2);
        WasGeneratedBy wg6=oFactory.newWasGeneratedBy(a6,oFactory.newRole("remainder"),p4,account2);



        WasDerivedFrom wd1=oFactory.newWasDerivedFrom(a2,a1,account0);
        WasDerivedFrom wd2=oFactory.newWasDerivedFrom(a3,a2,account0);

        WasDerivedFrom wd3=oFactory.newWasDerivedFrom(a2,a1,account1);
        WasDerivedFrom wd4=oFactory.newWasDerivedFrom(a2,a4,account1);
        WasDerivedFrom wd5=oFactory.newWasDerivedFrom(a5,a1,account1);
        WasDerivedFrom wd6=oFactory.newWasDerivedFrom(a5,a4,account1);

        WasDerivedFrom wd7=oFactory.newWasDerivedFrom(a3,a2,account2);
        WasDerivedFrom wd8=oFactory.newWasDerivedFrom(a3,a5,account2);
        WasDerivedFrom wd9=oFactory.newWasDerivedFrom(a6,a2,account2);
        WasDerivedFrom wd10=oFactory.newWasDerivedFrom(a6,a5,account2);


        OPMGraph graph=oFactory.newOPMGraph(account012,
                                            new Overlaps[] { },
                                            new Process[] {p1, p2, p3, p4 },
                                            new Artifact[] {a1,a2,a3,a4,a5,a6},
                                            new Agent[] { //ag1
                                                        },
                                            new Object[] {u1,u2,u3,u4,u5,u6,
                                                          wg1,wg2,wg3,wg4,wg5,wg6,
                                                          wd1,wd2,wd3,wd4,wd5,wd6,wd7,wd8,wd9,wd10
                                                          //wc1,
                                            } );



        return graph;
    }
    

    /** Produces a dot representation
     * of created graph. */
    public void testRefinementConversion() throws java.io.FileNotFoundException,  java.io.IOException   {
        //OPMToDot toDot=new OPMToDot(true); // with roles
        OPMToDot toDot=new OPMToDot("src/test/resources/refinement1.xml");

        toDot.convert(graph1,"target/refinement1.dot", "target/refinement1.pdf");

        OPMGraph graph2=makeRefinementGraph(oFactory);
        toDot=new OPMToDot("src/test/resources/refinement2.xml");

        toDot.convert(graph2,"target/refinement2.dot", "target/refinement2.pdf");
    }




    public void testCopyRefinement() throws java.io.FileNotFoundException,  java.io.IOException   {
        OPMFactory oFactory=new OPMFactory();

        OPMGraph graph2=oFactory.newOPMGraph(graph1);

        assertTrue( "self graph1 differ", graph1.equals(graph1) );        

        assertTrue( "self graph2 differ", graph2.equals(graph2) );        

        assertTrue( "graph1 graph2 differ", graph1.equals(graph2) );        
        
    }

}
