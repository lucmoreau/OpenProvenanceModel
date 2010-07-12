package org.openprovenance.reproduce;
import org.openprovenance.model.Artifact;
import org.openprovenance.model.Process;
import org.openprovenance.model.Agent;
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
import org.xml.sax.SAXException;
import java.io.IOException;



/**
 * Unit test for Reproducibility .
 */

public class Reproduce1Test extends org.openprovenance.model.Reproduce1Test
{

    Utilities u=new Utilities(oFactory);


    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public Reproduce1Test( String testName ) throws SAXException, IOException
        
    {
        super( testName );
        u.loadLibrary();
    }

    public void testLibrary() throws Exception {

        assertTrue(u.library!=null);
        
        List<?> ll=u.getLibraryDefinition("greeting");
        System.out.println("HOUOUOUOUO" + ll);

        assertTrue(ll!=null);
        assertTrue(ll.size()==1);


        ll=u.getDefinitionForUri("http://openprovenance.org/reproducibility/swift#greeting");
        System.out.println("Found " + ll);
        assertTrue(ll!=null);
        assertTrue(ll.size()==1);

        ll=u.getDefinitionForUri("http://openprovenance.org/reproducibility/swift#countwords");
        System.out.println("Found " + ll);
        assertTrue(ll!=null);
        assertTrue(ll.size()==1);

        ll=u.getDefinitionForUri("http://openprovenance.org/reproducibility/swift#null");
        System.out.println("Found " + ll);
        assertTrue(ll!=null);
        assertTrue(ll.size()==0);

        ll=u.getDefinitionForUri("http://other/reproducibility/swift#null");
        System.out.println("Found " + ll);
        assertTrue(ll==null);

    }



    public void decorate(Artifact a1,
                         Artifact a2,
                         Artifact a3,
                         Process p1,
                         Process p2,
                         Agent ag1,
                         Agent ag2) {

        oFactory.addAnnotation(ag1,
                               oFactory.newPName(Utilities.swift_NS+ "greeting"));
        oFactory.addAnnotation(ag2,
                               oFactory.newPName(Utilities.swift_NS+ "countwords"));

        oFactory.addAnnotation(ag1,
                               oFactory.newType("http://openprovenance.org/primitives#primitive"));
        oFactory.addAnnotation(ag2,
                               oFactory.newType("http://openprovenance.org/primitives#primitive"));

        oFactory.addAnnotation(a1,
                               oFactory.newType("http://openprovenance.org/primitives#String"));
        oFactory.addAnnotation(a2,
                               oFactory.newType("http://openprovenance.org/primitives#File"));
        oFactory.addAnnotation(a3,
                               oFactory.newType("http://openprovenance.org/primitives#File"));


        oFactory.addAnnotation(p1,
                               oFactory.newType("http://openprovenance.org/primitives#greeting"));
        oFactory.addAnnotation(p2,
                               oFactory.newType("http://openprovenance.org/primitives#countwords"));


    };



}
