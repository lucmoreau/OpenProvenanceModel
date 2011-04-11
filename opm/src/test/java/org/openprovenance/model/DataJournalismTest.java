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
public class DataJournalismTest 
    extends TestCase
{
    public static OPMFactory oFactory=new OPMFactory();

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DataJournalismTest( String testName )
    {
        super( testName );
    }


    /**
     * @return the suite of tests being tested
     */


    static OPMGraph graph1;


    public void testDataJournalism() throws JAXBException
    {
        OPMGraph graph=makeDataJournalismGraph(oFactory);

        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/refinement1.xml"),graph,true);

        
        //System.out.println(sw);

        graph1=graph;
        System.out.println("DataJournalism Test asserting True");
        assertTrue( true );


    }

    static String PATH_PROPERTY="http://openprovenance.org/primitives#path";
    static String URL_PROPERTY="http://openprovenance.org/primitives#url";
    static String PRIMITIVE_PROPERTY="http://openprovenance.org/primitives#primitive";
    static String FILE_LOCATION="/shomewhere/pc1/";
    static String URL_LOCATION="http://www.ipaw.info/challenge/";

    static String PRIMITIVE_TRIPLEIZE="http://openprovenance.org/primitives#tripleize";
    static String PRIMITIVE_DOWNLOAD="http://openprovenance.org/primitives#download";
    static String PRIMITIVE_PLOT="http://openprovenance.org/primitives#plot";
    static String PRIMITIVE_PUBLISH="http://openprovenance.org/primitives#publish";

    static String PRIMITIVE_DIV="http://openprovenance.org/primitives#div";
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
                                                              PATH_PROPERTY,
                                                              location + file,
                                                              null));
        return a;
    }

    public Artifact newResource(OPMFactory oFactory,
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
                                                              URL_PROPERTY,
                                                              location + file,
                                                              null));
        return a;
    }

    public Artifact newRdfGraphResource(OPMFactory oFactory,
                            String id,
                            Collection<Account> accounts,
                            String label,
                            String RdfGraph,
                            String location) {
                            
        Artifact a=oFactory.newArtifact(id,
                                          accounts,
                                          label);
        oFactory.addAnnotation(a,
                               oFactory.newType("http://openprovenance.org/primitives#RdfGraph"));
        oFactory.addAnnotation(a,
                               oFactory.newEmbeddedAnnotation("an1_" + id,
                                                              URL_PROPERTY,
                                                              location + RdfGraph,
                                                              null));
        return a;
    }

    public Artifact newRdfGraphFile(OPMFactory oFactory,
                            String id,
                            Collection<Account> accounts,
                            String label,
                            String RdfGraph,
                            String location) {
                            
        Artifact a=oFactory.newArtifact(id,
                                          accounts,
                                          label);
        oFactory.addAnnotation(a,
                               oFactory.newType("http://openprovenance.org/primitives#RdfGraph"));
        oFactory.addAnnotation(a,
                               oFactory.newEmbeddedAnnotation("an1_" + id,
                                                              PATH_PROPERTY,
                                                              location + RdfGraph,
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




    public OPMGraph makeDataJournalismGraph(OPMFactory oFactory)
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
                                                              PRIMITIVE_TRIPLEIZE,
                                                              null));

        Process p1v2=oFactory.newProcess("p1v2",
                                       account0,
                                       "p1v2");
        oFactory.addAnnotation(p1v2,
                               oFactory.newEmbeddedAnnotation("an1_p1v2",
                                                              PRIMITIVE_PROPERTY,
                                                              PRIMITIVE_TRIPLEIZE,
                                                              null));

        Process p2=oFactory.newProcess("p2",
                                       account0,
                                       "p2");
        oFactory.addAnnotation(p2,
                               oFactory.newEmbeddedAnnotation("an1_p2",
                                                              PRIMITIVE_PROPERTY,
                                                              PRIMITIVE_DOWNLOAD,
                                                              null));

        Process p3=oFactory.newProcess("p3",
                                       account0,
                                       "p3");
        oFactory.addAnnotation(p3,
                               oFactory.newEmbeddedAnnotation("an1_p3",
                                                              PRIMITIVE_PROPERTY,
                                                              PRIMITIVE_PLOT,
                                                              null));
        Process p4=oFactory.newProcess("p4",
                                       account0,
                                       "p4");
        oFactory.addAnnotation(p4,
                               oFactory.newEmbeddedAnnotation("an1_p4",
                                                              PRIMITIVE_PROPERTY,
                                                              PRIMITIVE_PUBLISH,
                                                              null));

        Process p5=oFactory.newProcess("p5",
                                       account0,
                                       "p5");
        oFactory.addAnnotation(p5,
                               oFactory.newEmbeddedAnnotation("an1_p5",
                                                              PRIMITIVE_PROPERTY,
                                                              PRIMITIVE_DOWNLOAD,
                                                              null));



        Artifact a1=newResource(oFactory,
                            "a1",
                            account0,
                            "a1",
                            "statistics.csv",
                            "http://gov/data/");

        Artifact a2=newRdfGraphResource(oFactory,
                                "a2",
                                account0,
                                "a2",
                                "statistics.rdf",
                                "http://gov/data/");
        

        Artifact a1v2=newResource(oFactory,
                            "a1v2",
                            account0,
                            "a1v2",
                            "statistics.v2.csv",
                            "http://gov/data/");

        Artifact a2v2=newRdfGraphResource(oFactory,
                                          "a2v2",
                                          account0,
                                          "a2v2",
                                          "statistics.v2.rdf",
                                          "http://gov/data/");
        

        Artifact a3=newRdfGraphFile(oFactory,
                            "a3",
                            account0,
                            "a3",
                            "statistics.rdf",
                            "file:/home/work/");

        Artifact a4=newFile(oFactory,
                            "a4",
                            account0,
                            "a4",
                            "chart.jpg",
                            "file:/home/work/");
        Artifact a5=newResource(oFactory,
                                "a5",
                                account0,
                                "a5",
                                "chart.jpg",
                                "http://journal.com/");
        Artifact a6=newFile(oFactory,
                            "a6",
                            account0,
                            "a6",
                            "chart.jpg",
                            "file:/home/work/");


        Used u1=oFactory.newUsed(p1,oFactory.newRole("in"),a1,account0);
        Used u1v2=oFactory.newUsed(p1v2,oFactory.newRole("in"),a1v2,account0);
        Used u2=oFactory.newUsed(p2,oFactory.newRole("in"),a2,account0);
        Used u3=oFactory.newUsed(p3,oFactory.newRole("in"),a3,account0);
        Used u4=oFactory.newUsed(p4,oFactory.newRole("in"),a4,account0);
        Used u5=oFactory.newUsed(p5,oFactory.newRole("in"),a5,account0);






        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a2,oFactory.newRole("out"),p1,account0);
        WasGeneratedBy wg1v2=oFactory.newWasGeneratedBy(a2v2,oFactory.newRole("out"),p1v2,account0);
        WasGeneratedBy wg2=oFactory.newWasGeneratedBy(a3,oFactory.newRole("out"),p2,account0);
        WasGeneratedBy wg3=oFactory.newWasGeneratedBy(a4,oFactory.newRole("plot"),p3,account0);
        WasGeneratedBy wg4=oFactory.newWasGeneratedBy(a5,oFactory.newRole("out"),p4,account0);
        WasGeneratedBy wg5=oFactory.newWasGeneratedBy(a6,oFactory.newRole("out"),p5,account0);



        WasDerivedFrom wd1=oFactory.newWasDerivedFrom(a2,a1,account0);
        WasDerivedFrom wd2=oFactory.newWasDerivedFrom(a3,a2,account0);


        WasDerivedFrom wd4=oFactory.newWasDerivedFrom(a4,a3,account0);
        WasDerivedFrom wd5=oFactory.newWasDerivedFrom(a5,a4,account0);
        WasDerivedFrom wd6=oFactory.newWasDerivedFrom(a6,a5,account0);


        WasDerivedFrom wd1v2=oFactory.newWasDerivedFrom(a2v2,a1v2,account0);
        WasDerivedFrom wd0=oFactory.newWasDerivedFrom(a1v2,a1,account0);

        OPMGraph graph=oFactory.newOPMGraph(account0,
                                            new Overlaps[] { },
                                            new Process[] {p1, p1v2, p2, p3, p4 },
                                            new Artifact[] {a1,a2,a3,a4,a5,a6, a1v2, a2v2},
                                            new Agent[] { //ag1
                                                        },
                                            new Object[] {u1,u2,u3,u4,u5, u1v2,
                                                          wg1,wg2,wg3,wg4,wg5, wg1v2,
                                                          wd1,wd2, wd4,wd5,wd6, wd1v2, wd0
                                                          //wc1,
                                            } );



        return graph;
    }
    

    /** Produces a dot representation
     * of created graph. */
    public void testDataJournalismConversion() throws java.io.FileNotFoundException,  java.io.IOException   {
        //OPMToDot toDot=new OPMToDot(true); // with roles
        OPMToDot toDot=new OPMToDot("src/test/resources/refinement1.xml");

        toDot.convert(graph1,"target/journalism1.dot", "target/journalism1.pdf");

        OPMGraph graph2=makeDataJournalismGraph(oFactory);
        toDot=new OPMToDot("src/test/resources/refinement2.xml");

        toDot.convert(graph2,"target/journalism2.dot", "target/journalism2.pdf");
    }




    public void testCopyDataJournalism() throws java.io.FileNotFoundException,  java.io.IOException   {
        OPMFactory oFactory=new OPMFactory();

        OPMGraph graph2=oFactory.newOPMGraph(graph1);

        assertTrue( "self graph1 differ", graph1.equals(graph1) );        

        assertTrue( "self graph2 differ", graph2.equals(graph2) );        

        assertTrue( "graph1 graph2 differ", graph1.equals(graph2) );        
        
    }

}
