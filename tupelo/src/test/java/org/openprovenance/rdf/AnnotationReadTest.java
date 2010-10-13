package org.openprovenance.rdf;
import java.io.File;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Comparator;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBElement;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.openprovenance.model.OPMGraph; 
import org.openprovenance.model.Identifiable; 
import org.openprovenance.model.Annotation; 
import org.openprovenance.model.Edge; 
import org.openprovenance.model.Account; 
import org.openprovenance.model.AccountRef; 
import org.openprovenance.model.Processes; 
import org.openprovenance.model.EmbeddedAnnotation; 
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
import org.openprovenance.model.IndexedOPMGraph; 
import org.openprovenance.model.Normalise; 


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



    OPMFactory oFactory=new OPMFactory();

    
    public void testRDFReadAnnotation() throws Exception {
        OPMRdf2Xml fromRdf=new OPMRdf2Xml();
        System.out.println("testRDFReadAnnotation (AnnotationReadTest.java)");
        graph2=fromRdf.convert("target/annotation1-rdf.xml");


        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/annotation-example1-from-rdf.xml"),graph2,true);


        
        System.out.println("testRDFReadAnnotation (AnnotationReadTest.java): asserting True");
        assertTrue( true );

        // dont call todot, since exands annotations
        //OPMToDot toDot=new OPMToDot();
        //toDot.convert(graph2,"target/annotation1-rdf.dot", "target/annotation1-rdf.pdf");


        OPMDeserialiser deserial=OPMDeserialiser.getThreadOPMDeserialiser();
        OPMGraph graph=deserial.deserialiseOPMGraph(new File("target/annotation-example1.xml"));
        
        assertTrue( graph!=null );

        Normalise normaliser=new Normalise(oFactory);

        normaliser.updateOriginalGraph(graph);
        normaliser.updateFromRdfGraph(graph2);

        normaliser.sortGraph(graph);
        normaliser.sortGraph(graph2);


        serial.serialiseOPMGraph(new File("target/annotation-example1-from-rdf-updated.xml"),graph2,true);

        serial.serialiseOPMGraph(new File("target/annotation-example1-updated.xml"),graph,true);

        assertTrue( "self graph differ", graph.equals(graph) );

        assertTrue( "self graph2  differ", graph2.equals(graph2) );

        assertTrue( "accounts differ", graph.getAccounts().getAccount().equals(graph2.getAccounts().getAccount()) );

        assertTrue( "account overalps differ", graph.getAccounts().getOverlaps().equals(graph2.getAccounts().getOverlaps()) );

        assertTrue( "accounts elements differ", graph.getAccounts().equals(graph2.getAccounts()) );

        assertTrue( "processes elements differ", graph.getProcesses().equals(graph2.getProcesses()) );

        assertTrue( "artifacts elements differ", graph.getArtifacts().equals(graph2.getArtifacts()) );

        assertTrue( "edges elements differ", graph.getDependencies().equals(graph2.getDependencies()) );

        if (graph.getAgents()!=null && graph2.getAgents()!=null) {
            assertTrue( "agents elements differ", graph.getAgents().equals(graph2.getAgents()) );
        } else {
            if (graph.getAgents()!=null) {
                assertTrue( "agents elements differ, graph not null",  graph.getAgents().getAgent().isEmpty());
                graph.setAgents(null);
            } else if (graph2.getAgents()!=null) {
                assertTrue( "agents elements differ, graph not null",  graph2.getAgents().getAgent().isEmpty());
                graph2.setAgents(null);
            }
        }

        assertTrue( "graph differ", graph.equals(graph2) );

    }





}
