package org.openprovenance.rdf;
import java.io.File;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
//import javax.xml.bind.JAXBException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit test for simple App.
 */
public class TupeloTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TupeloTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TupeloTest.class );
    }

//     public void testOPM() throws JAXBException    {
//         OPMDeserialiser deserial=OPMDeserialiser.getThreadOPMDeserialiser();
//         OPMGraph graph=deserial.deserialiseOPMGraph(new File("src/test/resources/example2.xml"));

//         graph1=graph;

//     }


    public void testTupelo() throws Exception {
        Tutorial t= new Tutorial();
        t.example(null);
    }

}
