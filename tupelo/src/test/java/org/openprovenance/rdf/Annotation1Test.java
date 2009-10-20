package org.openprovenance.rdf;
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

import org.openprovenance.model.OPMGraph; 
import org.openprovenance.model.OPMFactory; 
import org.openprovenance.model.Edge; 
import org.openprovenance.model.Account; 
import org.openprovenance.model.Annotation; 
import org.openprovenance.model.Overlaps; 
import org.openprovenance.model.AccountRef; 
import org.openprovenance.model.Id; 
import org.openprovenance.model.Processes; 
import org.openprovenance.model.Node; 
import org.openprovenance.model.Agent; 
import org.openprovenance.model.Process; 
import org.openprovenance.model.Artifact; 
import org.openprovenance.model.Used; 
import org.openprovenance.model.Role; 
import org.openprovenance.model.EmbeddedAnnotation; 
import org.openprovenance.model.WasGeneratedBy; 
import org.openprovenance.model.WasTriggeredBy; 
import org.openprovenance.model.WasDerivedFrom; 
import org.openprovenance.model.WasControlledBy; 
import org.openprovenance.model.OPMUtilities; 
import org.openprovenance.model.OPMDeserialiser; 
import org.openprovenance.model.OPMSerialiser; 



/**
 * Unit test for simple App.
 */
public class Annotation1Test 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public Annotation1Test( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( Annotation1Test.class );
    }

    static OPMGraph graph1;
    static OPMGraph graph2;

    /** Creates and serialises an OPM graph. */

    public void testAnnotation1() throws JAXBException
    {
        OPMFactory oFactory=new OPMFactory();

        Account account1=oFactory.newAccount("green");
        Collection<Account> green=Collections.singleton(account1);
        Collection<Account> orange=Collections.singleton(oFactory.newAccount("orange"));
        
        Process p1=oFactory.newProcess("p1",
                                       green,
                                       "http://process.org/add1ToAll");
        Process p2=oFactory.newProcess("p2",
                                       orange,
                                       "http://process.org/split");
        Process p3=oFactory.newProcess("p3",
                                       orange,
                                       "http://process.org/plus1");
        Process p4=oFactory.newProcess("p4",
                                       orange,
                                       "http://process.org/plus1");
        Process p5=oFactory.newProcess("p5",
                                       orange,
                                       "http://process.org/cons");


        List<Account> green_orange=new LinkedList();
        green_orange.addAll(green);
        green_orange.addAll(orange);

        Artifact a1=oFactory.newArtifact("a1",
                                         green_orange,
                                         "(2,6)");
        Artifact a2=oFactory.newArtifact("a2",
                                         green_orange,
                                         "(3,7)");
        Artifact a3=oFactory.newArtifact("a3",
                                         orange,
                                         "2");
        Artifact a4=oFactory.newArtifact("a4",
                                         orange,
                                         "6");
        Artifact a5=oFactory.newArtifact("a5",
                                         orange,
                                         "3");
        Artifact a6=oFactory.newArtifact("a6",
                                         orange,
                                         "7");

        Used u1=oFactory.newUsed(p1,oFactory.newRole("in"),a1,green);
        Used u2=oFactory.newUsed(p2,oFactory.newRole("pair"),a1,orange);
        Used u3=oFactory.newUsed(p3,oFactory.newRole("in"),a3,orange);
        Used u4=oFactory.newUsed(p4,oFactory.newRole("in"),a4,orange);
        Used u5=oFactory.newUsed(p5,oFactory.newRole("left"),a5,orange);
        Used u6=oFactory.newUsed("u6",p5,oFactory.newRole("right"),a6,orange);




        WasGeneratedBy wg1=oFactory.newWasGeneratedBy(a2,oFactory.newRole("out"),p1,green);
        WasGeneratedBy wg2=oFactory.newWasGeneratedBy(a3,oFactory.newRole("left"),p2,orange);
        WasGeneratedBy wg3=oFactory.newWasGeneratedBy(a4,oFactory.newRole("right"),p2,orange);
        WasGeneratedBy wg4=oFactory.newWasGeneratedBy(a5,oFactory.newRole("out"),p3,orange);
        WasGeneratedBy wg5=oFactory.newWasGeneratedBy(a6,oFactory.newRole("out"),p4,orange);
        Role r1=oFactory.newRole("r1","pair");
        WasGeneratedBy wg6=oFactory.newWasGeneratedBy("wg6",a2,r1,p5,orange);

        Overlaps ov1=oFactory.newOverlaps(green_orange);


        Annotation an1=oFactory.newAnnotation("an1",a1,"http://property.org/hasQuality", "good", null);
        Annotation an2=oFactory.newAnnotation("an2",a1,"http://property.org/hasQuality", new Integer(1), null);
        Annotation an3=oFactory.newAnnotation("an3",a1,"http://property.org/hasQuality", new Float(1.01), null);
        Annotation an4=oFactory.newAnnotation("an4",a2,"http://property.org/hasQuality", new Float(1.01), green_orange);
        Annotation an5=oFactory.newAnnotation("an5",a3,"http://property.org/hasQuality", 1, green);


        Annotation an6=oFactory.newAnnotation("an6",p1,"http://property.org/hasQuality", 1, green);
        Annotation an7=oFactory.newAnnotation("an7",an6,"http://property.org/hasQuality", "bad", orange);

        Annotation an8=oFactory.newAnnotation("an8",wg6,"http://property.org/hasQuality", "average", orange);
        Annotation an9=oFactory.newAnnotation("an9",u6,"http://property.org/hasQuality", "bad", orange);

        Annotation an10=oFactory.newAnnotation("an10",r1,"http://property.org/hasQuality", "bad", orange);

        a4.getAnnotation().add(oFactory.newEmbeddedAnnotation("an11","http://property.org/hasQuality", "bad", orange));

        EmbeddedAnnotation ann12=oFactory.newEmbeddedAnnotation("an12","http://property.org/hasQuality", "average", green);
        ann12.getProperty().add(oFactory.newProperty("http://property.org/numericQuality", 0.4));

        p5.getAnnotation().add(ann12);

        p5.getAnnotation().add(oFactory.newEmbeddedAnnotation("an13","http://property.org/hasProperty", "beau", green));

        account1.getAnnotation().add(oFactory.newEmbeddedAnnotation("an14","http://property.org/hasAlternativeName", "vert", null));

        OPMGraph graph=oFactory.newOPMGraph(green_orange,
                                            new Overlaps[] { ov1 },
                                            new Process[] {p1,p2,p3,p4,p5},
                                            new Artifact[] {a1,a2,a3,a4,a5,a6},
                                            null,
                                            new Object[] {u1,u2,u3,u4,u5,u6,
                                                          wg1,wg2,wg3,wg4,wg5,wg6},
                                            new Annotation[] {an1, an2, an3, an4, an5, an6, an7, an8, an9, an10} );





        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        StringWriter sw=new StringWriter();
        serial.serialiseOPMGraph(sw,graph,true);
        serial.serialiseOPMGraph(new File("target/annotation-example1.xml"),graph,true);
        //System.out.println(sw);

        graph1=graph;

        assertTrue( true );
    }


    public void testAnnotationRDF1() throws Exception {
        OPMXml2Rdf toRdf=new OPMXml2Rdf();
        //System.out.println("graph is " + graph1);
        toRdf.convert(graph1,"target/annotation1-rdf.xml");
    }
    

}
