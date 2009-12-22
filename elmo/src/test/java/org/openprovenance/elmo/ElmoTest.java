package org.openprovenance.elmo;
import java.io.File;
import java.io.StringWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
//import javax.xml.bind.JAXBException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.xml.namespace.QName;

import org.openprovenance.rdf.Node;
import org.openprovenance.rdf.Edge;


import org.openrdf.elmo.ElmoModule;
import org.openrdf.elmo.ElmoManagerFactory;
import org.openrdf.elmo.ElmoManager;
import org.openrdf.elmo.sesame.SesameManagerFactory;
import org.openrdf.elmo.sesame.SesameManager;
import org.openrdf.rio.rdfxml.RDFXMLWriter;

import org.openprovenance.model.OPMFactory;
import org.openprovenance.model.Artifact;
import org.openprovenance.model.Process;
import org.openprovenance.model.Used;


/**
 * Unit test for Elmo.
 */
public class ElmoTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ElmoTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */

    static ElmoManager manager;

    public void testElmo1() throws Exception {
        assert Edge.class.isInterface();
        assert Node.class.isInterface();

        ElmoModule module = new ElmoModule();
        module.addConcept(Edge.class);
        module.addConcept(Node.class);
        module.addConcept(org.openprovenance.rdf.Artifact.class);
        module.addConcept(org.openprovenance.rdf.Process.class);
        module.addConcept(org.openprovenance.rdf.Used.class);

        //module.addBehaviour(RdfArtifact.class);

        module.addFactory(RdfObjectFactory.class);

        ElmoManagerFactory factory = new SesameManagerFactory(module);
        manager = factory.createElmoManager();


        OPMFactory oFactory=new OPMFactory(new RdfObjectFactory());

        
        Artifact a1=oFactory.newArtifact("a1",null, "a1");
        assert (a1 instanceof RdfArtifact);
        assert (a1 instanceof Node);

        Process p1=oFactory.newProcess("p1",null, "p1");
        assert (p1 instanceof RdfProcess);
        assert (p1 instanceof Node);

        Used u1=oFactory.newUsed(p1,null,a1,new LinkedList());
        assert (u1 instanceof RdfUsed);
        assert (u1 instanceof Edge);


        QName id0 = new QName("http://example.com/", "a0");
        Object a0_ = manager.designate(id0, org.openprovenance.rdf.Artifact.class);
        System.out.println("===> a0_ " + a0_);

        QName id1 = new QName("http://example.com/", "p0");
        Object p0_ = manager.designate(id1, org.openprovenance.rdf.Process.class);
        System.out.println("===> p0_ " + p0_);


        QName id2 = new QName("http://example.com/", "u0");
        Object u0_ = manager.designate(id2, org.openprovenance.rdf.Used.class);
        System.out.println("===> u0_ " + u0_);

        //Question: how can i serialise my beans into rdf?

    }

    public void testElmo2() throws Exception {
        File file = new File("target/repository.rdf");
        Writer writer = new FileWriter(file);
        assert manager!=null;
        ((SesameManager)manager).getConnection().export(new RDFXMLWriter(writer));
        writer.close();
    }



    
}


