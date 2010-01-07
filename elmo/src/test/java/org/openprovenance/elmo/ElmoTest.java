package org.openprovenance.elmo;
import java.io.File;
import java.io.StringWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
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
import org.openrdf.rio.n3.N3Writer;

import org.openprovenance.model.OPMSerialiser;
import org.openprovenance.model.OPMFactory;
import org.openprovenance.model.Artifact;
import org.openprovenance.model.Agent;
import org.openprovenance.model.Process;
import org.openprovenance.model.Used;
import org.openprovenance.model.Overlaps;
import org.openprovenance.model.Account;
import org.openprovenance.model.OPMGraph;
import org.openprovenance.model.WasGeneratedBy;
import org.openprovenance.model.WasDerivedFrom;
import org.openprovenance.model.WasTriggeredBy;
import org.openprovenance.model.WasControlledBy;

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
        module.addConcept(org.openprovenance.rdf.OPMGraph.class);
        module.addConcept(org.openprovenance.rdf.Account.class);
        module.addConcept(org.openprovenance.rdf.Artifact.class);
        module.addConcept(org.openprovenance.elmo.RdfArtifact.class);
        module.addConcept(org.openprovenance.rdf.Process.class);
        module.addConcept(org.openprovenance.rdf.Agent.class);
        module.addConcept(org.openprovenance.rdf.Role.class);
        module.addConcept(org.openprovenance.rdf.Used.class);
        module.addConcept(org.openprovenance.rdf.WasGeneratedBy.class);
        module.addConcept(org.openprovenance.rdf.WasDerivedFrom.class);
        module.addConcept(org.openprovenance.rdf.WasTriggeredBy.class);
        module.addConcept(org.openprovenance.rdf.WasControlledBy.class);

        //module.addBehaviour(RdfArtifact.class);

        // what is this?
        //module.addFactory(RdfObjectFactory.class);

        ElmoManagerFactory factory = new SesameManagerFactory(module);
        manager = factory.createElmoManager();


        OPMFactory oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,"http://newexample.com/"));

        Account acc1=oFactory.newAccount("acc1");
        assert (acc1 instanceof RdfAccount);

        Account acc2=oFactory.newAccount("acc2");
        assert (acc2 instanceof RdfAccount);

        Collection<Account> accl1=Collections.singleton(acc1);
        Collection<Account> accl2=Collections.singleton(acc2);
        Collection<Account> accl12=new LinkedList();
        accl12.add(acc1);
        accl12.add(acc2);
        
        Artifact a1=oFactory.newArtifact("a1",accl1, "a1");
        assert (a1 instanceof RdfArtifact);
        assert (a1 instanceof Node);

        Artifact a2=oFactory.newArtifact("a2",accl1, "a2");
        assert (a2 instanceof RdfArtifact);
        assert (a2 instanceof Node);


        Agent ag1=oFactory.newAgent("ag1",accl2, "ag1");
        assert (ag1 instanceof RdfAgent);
        assert (ag1 instanceof Node);

        Process p1=oFactory.newProcess("p1",accl2, "p1");
        assert (p1 instanceof RdfProcess);
        assert (p1 instanceof Node);

        Process p2=oFactory.newProcess("p2",accl2, "p2");
        assert (p2 instanceof RdfProcess);
        assert (p2 instanceof Node);

        Used u1=oFactory.newUsed("u1",p1,oFactory.newRole("r1","r1"),a1,accl2);
        assert (u1 instanceof RdfUsed);
        assert (u1 instanceof Edge);


        WasGeneratedBy wg1=oFactory.newWasGeneratedBy("g1",a1,oFactory.newRole("r2","r2"),p1,accl12);
        assert (wg1 instanceof RdfWasGeneratedBy);
        assert (wg1 instanceof Edge);


        WasDerivedFrom wd1=oFactory.newWasDerivedFrom("d1",a2,a1,accl2);
        assert (wd1 instanceof RdfWasDerivedFrom);
        assert (wd1 instanceof Edge);


        WasTriggeredBy wt1=oFactory.newWasTriggeredBy("t1",p2,p1,accl12);
        assert (wt1 instanceof RdfWasTriggeredBy);
        assert (wt1 instanceof Edge);

        WasControlledBy wc1=oFactory.newWasControlledBy("c1",p1,oFactory.newRole("r3","r3"),ag1,accl2);
        assert (wc1 instanceof RdfWasControlledBy);
        assert (wc1 instanceof Edge);


        OPMGraph graph=oFactory.newOPMGraph("gr1",
                                            accl12,
                                            new Overlaps[] { },
                                            new Process[] {p1,p2},
                                            new Artifact[] {a1,a2},
                                            new Agent[] { ag1 },
                                            new Object[] {u1,
                                                          wg1,
                                                          wd1,
                                                          wc1,
                                                          wt1},
                                            null);

        assert (graph instanceof RdfOPMGraph);



        OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        serial.serialiseOPMGraph(new File("target/repository.xml"),graph,true);

    }

    public void testDumptoRDFXML() throws Exception {
        File file = new File("target/repository.rdf");
        Writer writer = new FileWriter(file);
        assert manager!=null;
        RDFXMLWriter serialiser=new RDFXMLWriter(writer);
        serialiser.handleNamespace("opm","http://www.ipaw.info/2007/opm#");
        ((SesameManager)manager).getConnection().export(serialiser);
        
    }


    public void testDumpToN3() throws Exception {
        File file = new File("target/repository.n3");
        Writer writer = new FileWriter(file);
        assert manager!=null;
        N3Writer serialiser=new N3Writer(writer);
        serialiser.handleNamespace("opm","http://www.ipaw.info/2007/opm#");
        ((SesameManager)manager).getConnection().export(serialiser);
        writer.close();
    }



    
}


