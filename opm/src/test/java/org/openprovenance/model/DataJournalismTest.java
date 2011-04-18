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
    static String PRIMITIVE_ANALYZE1="http://openprovenance.org/primitives#analyze1";
    static String PRIMITIVE_ANALYZE2="http://openprovenance.org/primitives#analyze2";
    static String PRIMITIVE_WRITE1="http://openprovenance.org/primitives#write1";
    static String PRIMITIVE_WRITE2="http://openprovenance.org/primitives#write2";
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


        Process trpl1=oFactory.newProcess("trpl1",
                                       account0,
                                       "trpl1");
        oFactory.addAnnotation(trpl1,
                               oFactory.newEmbeddedAnnotation("an1_trpl1",
                                                              PRIMITIVE_PROPERTY,
                                                              PRIMITIVE_TRIPLEIZE,
                                                              null));

        Process trpl2=oFactory.newProcess("trpl2",
                                       account0,
                                       "trpl2");
        oFactory.addAnnotation(trpl2,
                               oFactory.newEmbeddedAnnotation("an1_trpl2",
                                                              PRIMITIVE_PROPERTY,
                                                              PRIMITIVE_TRIPLEIZE,
                                                              null));

        Process dwnld1=oFactory.newProcess("dwnld1",
                                       account1,
                                       "dwnld1");
        oFactory.addAnnotation(dwnld1,
                               oFactory.newEmbeddedAnnotation("an1_dwnld1",
                                                              PRIMITIVE_PROPERTY,
                                                              PRIMITIVE_DOWNLOAD,
                                                              null));

        Process dwnld3=oFactory.newProcess("dwnld3",
                                       account2,
                                       "dwnld3");
        oFactory.addAnnotation(dwnld3,
                               oFactory.newEmbeddedAnnotation("an1_dwnld3",
                                                              PRIMITIVE_PROPERTY,
                                                              PRIMITIVE_DOWNLOAD,
                                                              null));

        Process pub1=oFactory.newProcess("pub1",
                                       account0,
                                         "pub1");
        oFactory.addAnnotation(pub1,
                               oFactory.newEmbeddedAnnotation("an1_pub1",
                                                              PRIMITIVE_PROPERTY,
                                                              PRIMITIVE_PUBLISH,
                                                              null));


        Process pub2=oFactory.newProcess("pub2",
                                       account0,
                                         "pub2");
        oFactory.addAnnotation(pub2,
                               oFactory.newEmbeddedAnnotation("an1_pub2",
                                                              PRIMITIVE_PROPERTY,
                                                              PRIMITIVE_PUBLISH,
                                                              null));

        Process anl1=oFactory.newProcess("anl1",
                                       account1,
                                       "anl1");
        oFactory.addAnnotation(anl1,
                               oFactory.newEmbeddedAnnotation("an1_anl1",
                                                              PRIMITIVE_PROPERTY,
                                                              PRIMITIVE_ANALYZE1,
                                                              null));

        Process wr1=oFactory.newProcess("wr1",
                                       account1,
                                       "wr1");
        oFactory.addAnnotation(wr1,
                               oFactory.newEmbeddedAnnotation("an1_wr1",
                                                              PRIMITIVE_PROPERTY,
                                                              PRIMITIVE_WRITE1,
                                                              null));

        Process anl2=oFactory.newProcess("anl2",
                                       account2,
                                       "anl2");
        oFactory.addAnnotation(anl2,
                               oFactory.newEmbeddedAnnotation("an1_anl2",
                                                              PRIMITIVE_PROPERTY,
                                                              PRIMITIVE_ANALYZE2,
                                                              null));
        Process pub3=oFactory.newProcess("pub3",
                                       account1,
                                       "pub3");
        oFactory.addAnnotation(pub3,
                               oFactory.newEmbeddedAnnotation("an1_pub3",
                                                              PRIMITIVE_PROPERTY,
                                                              PRIMITIVE_PUBLISH,
                                                              null));


        Process pub4=oFactory.newProcess("pub4",
                                       account2,
                                       "pub4");
        oFactory.addAnnotation(pub4,
                               oFactory.newEmbeddedAnnotation("an1_pub4",
                                                              PRIMITIVE_PROPERTY,
                                                              PRIMITIVE_PUBLISH,
                                                              null));

        Process dwnld2=oFactory.newProcess("dwnld2",
                                       account2,
                                       "dwnld2");
        oFactory.addAnnotation(dwnld2,
                               oFactory.newEmbeddedAnnotation("an1_dwnld2",
                                                              PRIMITIVE_PROPERTY,
                                                              PRIMITIVE_DOWNLOAD,
                                                              null));



        Artifact d1=newResource(oFactory,
                            "d1",
                            account0,
                            "d1",
                            "statistics1.csv",
                            "http://gov/data/");

        Artifact f1=newRdfGraphResource(oFactory,
                                "f1",
                                account0,
                                "f1",
                                "statistics1.rdf",
                                "file://repo/data/");


        Artifact r1=newRdfGraphResource(oFactory,
                                        "r1",
                                        account0,
                                        "r1",
                                        "statistics1.rdf",
                                        "http://gov/data/");

        Artifact r2=newRdfGraphResource(oFactory,
                                        "r2",
                                        account0,
                                        "r2",
                                        "statistics2.rdf",
                                        "http://gov/data/");
        

        Artifact d2=newResource(oFactory,
                            "d2",
                            account0,
                            "d2",
                            "statistics2.csv",
                            "http://gov/data/");

        Artifact f2=newRdfGraphResource(oFactory,
                                          "f2",
                                          account0,
                                          "f2",
                                          "statistics2.rdf",
                                          "http://gov/data/");
        

        Artifact lcp1=newRdfGraphFile(oFactory,
                            "lcp1",
                            account1,
                            "lcp1",
                            "statistics1.rdf",
                            "file:/home/work/");


        Artifact lcp3=newRdfGraphFile(oFactory,
                            "lcp3",
                            account2,
                            "lcp3",
                            "statistics2.rdf",
                            "file:/home/work/");

        Artifact li1=newResource(oFactory,
                                 "li1",
                                 account0,
                                 "li1",
                                 "license1.txt",
                                 "http://lic.org/");

        Artifact li2=newResource(oFactory,
                                 "li2",
                                 account1,
                                 "li2",
                                 "license2.txt",
                                 "http://lic.org/");
        Artifact li3=newResource(oFactory,
                                 "li3",
                                 account2,
                                 "li3",
                                 "license3.txt",
                                 "http://lic.org/");


        Artifact c1=newFile(oFactory,
                            "c1",
                            account1,
                            "c1",
                            "chart1.jpg",
                            "file:/home/work/");
        Artifact art1=newFile(oFactory,
                            "art1",
                            account1,
                            "art1",
                            "art1.html",
                            "file:/home/work/");

        Artifact c2=newFile(oFactory,
                            "c2",
                            account2,
                            "c2",
                            "chart2.jpg",
                            "file:/home/work/");
        Artifact r3=newResource(oFactory,
                                "r3",
                                account1,
                                "r3",
                                "chart1.jpg",
                                "http://journal.com/");

        Artifact r4=newResource(oFactory,
                                "r4",
                                account1,
                                "r4",
                                "art1.html",
                                "http://journal.com/");

        Artifact r5=newResource(oFactory,
                                "r5",
                                account2,
                                "r5",
                                "chart2.jpg",
                                "http://blog.com/");

        Artifact lcp2=newFile(oFactory,
                            "lcp2",
                            account2,
                            "lcp2",
                            "chart1.jpg",
                            "file:/home/work/");

        Agent blogger=oFactory.newAgent("blogger",
                                        account2,
                                        "blogger");
        Agent journal=oFactory.newAgent("journal",
                                        account1,
                                        "journal");
        Agent author1=oFactory.newAgent("author1",
                                        account1,
                                        "author1");
        Agent author2=oFactory.newAgent("author2",
                                        account1,
                                        "author2");
        Agent gov=oFactory.newAgent("gov",
                                        account0,
                                        "gov");


        Used u1=oFactory.newUsed(trpl1,oFactory.newRole("in"),d1,account0);
        Used u1v2=oFactory.newUsed(trpl2,oFactory.newRole("in"),d2,account0);
        Used u2=oFactory.newUsed(pub1,oFactory.newRole("in"),f1,account0);
        Used u2e=oFactory.newUsed(pub1,oFactory.newRole("lic"),li1,account0);

        Used u2b=oFactory.newUsed(dwnld1,oFactory.newRole("in"),r1,account1);
        Used u2f=oFactory.newUsed(dwnld3,oFactory.newRole("in"),r2,account2);
        Used u3=oFactory.newUsed(anl1,oFactory.newRole("in"),lcp1,account1);
        Used u3b=oFactory.newUsed(anl2,oFactory.newRole("in2"),lcp3,account2);
        Used u3a=oFactory.newUsed(anl2,oFactory.newRole("in1"),lcp2,account2);
        Used u4=oFactory.newUsed(pub3,oFactory.newRole("in1"),c1,account1);
        Used u4c=oFactory.newUsed(pub3,oFactory.newRole("lic"),li2,account1);
        Used u4b=oFactory.newUsed(pub4,oFactory.newRole("in"),c2,account2);
        Used u4d=oFactory.newUsed(pub4,oFactory.newRole("lic"),li3,account2);
        Used u5=oFactory.newUsed(dwnld2,oFactory.newRole("in"),r3,account2);

        Used u2c=oFactory.newUsed(pub2,oFactory.newRole("in"),f2,account0);
        Used u2d=oFactory.newUsed(pub2,oFactory.newRole("lic"),li1,account0);

        Used u6=oFactory.newUsed(pub3,oFactory.newRole("in2"),art1,account1);

        Used u7=oFactory.newUsed(wr1,oFactory.newRole("in2"),c1,account1);



        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(f1,oFactory.newRole("out"),trpl1,account0);
        WasGeneratedBy wg1v2=oFactory.newWasGeneratedBy(f2,oFactory.newRole("out"),trpl2,account0);
        WasGeneratedBy wg2b=oFactory.newWasGeneratedBy(r1,oFactory.newRole("out"),pub1,account0);
        WasGeneratedBy wg2c=oFactory.newWasGeneratedBy(r2,oFactory.newRole("out"),pub2,account0);
        WasGeneratedBy wg2=oFactory.newWasGeneratedBy(lcp1,oFactory.newRole("out"),dwnld1,account1);
        WasGeneratedBy wg2d=oFactory.newWasGeneratedBy(lcp3,oFactory.newRole("out"),dwnld3,account2);
        WasGeneratedBy wg3=oFactory.newWasGeneratedBy(c1,oFactory.newRole("analysis"),anl1,account1);
        WasGeneratedBy wg3b=oFactory.newWasGeneratedBy(c2,oFactory.newRole("analysis"),anl2,account2);
        WasGeneratedBy wg4=oFactory.newWasGeneratedBy(r3,oFactory.newRole("fig"),pub3,account1);
        WasGeneratedBy wg7=oFactory.newWasGeneratedBy(r4,oFactory.newRole("art"),pub3,account1);
        WasGeneratedBy wg5=oFactory.newWasGeneratedBy(lcp2,oFactory.newRole("out"),dwnld2,account2);
        WasGeneratedBy wg6=oFactory.newWasGeneratedBy(r5,oFactory.newRole("out"),pub4,account2);

        WasGeneratedBy wg8=oFactory.newWasGeneratedBy(art1,oFactory.newRole("out"),wr1,account1);


        WasDerivedFrom wd1=oFactory.newWasDerivedFrom(f1,d1,account0);
        WasDerivedFrom wd2=oFactory.newWasDerivedFrom(r1,f1,account0);
        WasDerivedFrom wd2b=oFactory.newWasDerivedFrom(lcp1,r1,account1);
        WasDerivedFrom wd2c=oFactory.newWasDerivedFrom(r2,f2,account0);

        WasDerivedFrom wd2d=oFactory.newWasDerivedFrom(lcp3,r2,account2);

        WasDerivedFrom wd4=oFactory.newWasDerivedFrom(c1,lcp1,account1);
        WasDerivedFrom wd4b=oFactory.newWasDerivedFrom(c2,lcp3,account2);
        WasDerivedFrom wd5=oFactory.newWasDerivedFrom(r3,c1,account1);
        WasDerivedFrom wd6=oFactory.newWasDerivedFrom(lcp2,r3,account2);

        WasDerivedFrom wd7=oFactory.newWasDerivedFrom(r5,c2,account2);

        WasDerivedFrom wd1v2=oFactory.newWasDerivedFrom(f2,d2,account0);
        WasDerivedFrom wd0=oFactory.newWasDerivedFrom("wd0",d2,d1,"isVersionOf",account0);

        WasDerivedFrom wd10=oFactory.newWasDerivedFrom("wd10",art1,c1,"contains",account1);
        WasDerivedFrom wd10b=oFactory.newWasDerivedFrom("wd10b",r4,r3,"contains",account1);
        WasDerivedFrom wd11=oFactory.newWasDerivedFrom(r4,art1,account1);

        WasTriggeredBy wt1=oFactory.newWasTriggeredBy(dwnld3,anl2,account2);

        WasControlledBy wc1=oFactory.newWasControlledBy(pub1,oFactory.newRole("pub"),gov,account0);
        WasControlledBy wc2=oFactory.newWasControlledBy(pub2,oFactory.newRole("pub"),gov,account0);
        WasControlledBy wc3=oFactory.newWasControlledBy(pub3,oFactory.newRole("pub"),journal,account1);
        WasControlledBy wc3b=oFactory.newWasControlledBy(anl1,oFactory.newRole("pub"),author1,account1);
        WasControlledBy wc3c=oFactory.newWasControlledBy(wr1,oFactory.newRole("pub"),author2,account1);
        WasControlledBy wc4=oFactory.newWasControlledBy(pub4,oFactory.newRole("pub"),blogger,account2);
        WasControlledBy wc5=oFactory.newWasControlledBy(anl2,oFactory.newRole("pub"),blogger,account2);

        OPMGraph graph=oFactory.newOPMGraph(account012,
                                            new Overlaps[] { },
                                            new Process[] {trpl1, trpl2, dwnld1,dwnld3,dwnld2, anl1,anl2, pub3,pub4, pub1,pub2, dwnld2,wr1},
                                            new Artifact[] {d1,f1,lcp1,lcp3,lcp2,c1,c2,r3,art1, r4, r5, d2, f2,li1,li2,li3,r1,r2},
                                            new Agent[] { blogger, journal, gov, author1, author2
                                                        },
                                            new Object[] {u1,u2,u2b,u2c,u2d,u2e,u2f,u3a,u3,u3b,u4,u4b,u4c,u4d,u5,u6,u7, u1v2,
                                                          wg1,wg2,wg2b,wg2c,wg2d,wg3,wg3b,wg4,wg5, wg1v2,wg6,wg7,wg8,
                                                          wd1,wd2,wd2b,wd2c,wd2d,wd4,wd4b,wd5,wd6, wd1v2, wd0,wd10,wd10b,wd11, wd7,
                                                          wt1,
                                                          wc1,wc2,wc3,wc3b,wc3c,wc4,wc5
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
