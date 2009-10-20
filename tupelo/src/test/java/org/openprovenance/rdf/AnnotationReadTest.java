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
import org.openprovenance.model.Edge; 
import org.openprovenance.model.Account; 
import org.openprovenance.model.Processes; 
import org.openprovenance.model.Artifact; 
import org.openprovenance.model.Agent; 
import org.openprovenance.model.Used; 
import org.openprovenance.model.OPMFactory; 
import org.openprovenance.model.WasGeneratedBy; 
import org.openprovenance.model.WasDerivedFrom; 
import org.openprovenance.model.WasTriggeredBy; 
import org.openprovenance.model.WasControlledBy; 
import org.openprovenance.model.OPMDeserialiser; 
import org.openprovenance.model.OPMSerialiser; 
import org.openprovenance.model.Overlaps; 
import org.openprovenance.model.Process; 
import org.openprovenance.model.OPMToDot; 
import org.openprovenance.model.OPMUtilities; 


/**
 * Unit test for simple App.
 */
public class AnnotationReadTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AnnotationReadTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */


    static OPMGraph graph2;





    
    public void testRDFReadAnnotation() throws Exception {
        OPMRdf2Xml fromRdf=new OPMRdf2Xml();
        System.out.println("testRDFReadAnnotation (AnnotationReadTest.java)");
        graph2=fromRdf.convert("target/annotation1-rdf.xml");


        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/annotation-example1-from-rdf.xml"),graph2,true);


        
        System.out.println("testRDFReadAnnotation (AnnotationReadTest.java): asserting True");
        assertTrue( true );

        OPMToDot toDot=new OPMToDot();
        toDot.convert(graph2,"target/annotation1-rdf.dot", "target/annotation1-rdf.pdf");

    }



}
