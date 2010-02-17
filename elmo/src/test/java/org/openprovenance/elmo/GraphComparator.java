package org.openprovenance.elmo;

import java.io.File;

import javax.xml.namespace.QName;
import org.openprovenance.model.OPMSerialiser;
import org.openprovenance.model.OPMDeserialiser;
import org.openprovenance.model.OPMGraph;
import org.openprovenance.model.OPMFactory;
import org.openprovenance.model.Normalise;
import junit.framework.Assert;
import org.openrdf.rio.RDFFormat;
import org.openrdf.elmo.ElmoManager;
import org.openrdf.elmo.sesame.SesameManager;



public class GraphComparator {

    public void testCompareGraphs(String graphFile1,
                                  String graphFile2,
                                  String NS,
                                  RDFFormat format,
                                  RepositoryHelper rHelper,
                                  ElmoManager manager,
                                  String normalisedFile1,
                                  String normalisedFile2) throws Exception { 

        OPMDeserialiser deserial=OPMDeserialiser.getThreadOPMDeserialiser();
        OPMGraph graph1=deserial.deserialiseOPMGraph(new File(graphFile1));

        File file = new File(graphFile2);
        rHelper.readFromRDF(file,null,(SesameManager)manager,format);

        QName qname = new QName(NS, graph1.getId());


        org.openprovenance.rdf.OPMGraph gr=(org.openprovenance.rdf.OPMGraph)manager.find(qname);

        RdfOPMFactory oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,NS),
                                                 manager);


        OPMGraph graph2=oFactory.newOPMGraph(gr);
        testCompareGraphs(graph1,graph2,normalisedFile1,normalisedFile2);
    }


        


    public void testCompareGraphs(OPMGraph graph1,
                                  OPMGraph graph2,
                                  String normalisedFile1,
                                  String normalisedFile2) throws Exception { 
        OPMFactory oFactory=new OPMFactory();  // use a regular factory, not an rdf one
        
        Normalise normaliser=new Normalise(oFactory);

        // graph1: xml graph
        // graph2, from rdf graph
        normaliser.embedExternalAnnotations(graph1);


        //no need, since by nature, they are embedded in rdf
        //normaliser.embedExternalAnnotations(graph2);

        normaliser.sortGraph(graph1);
        normaliser.sortGraph(graph2);


        OPMSerialiser.getThreadOPMSerialiser().serialiseOPMGraph(new File(normalisedFile1),graph1,true);
        OPMSerialiser.getThreadOPMSerialiser().serialiseOPMGraph(new File(normalisedFile2),graph2,true);

        compareAssert(graph1,graph2);

    }


    /** Reads xml file graphFile1,
        creates an rdf graph (saved in graphFile2)
        compares the two graphs. */

    
    public void testCompareGraphCopies(RdfOPMFactory oFactory,
                                       String graphFile1,
                                       String graphFile2,
                                       String normalisedGraphFile1,
                                       String normalisedGraphFile2) throws Exception {


        OPMDeserialiser deserial=OPMDeserialiser.getThreadOPMDeserialiser();
        OPMGraph graph1=deserial.deserialiseOPMGraph(new File(graphFile1));

        RdfOPMGraph graph2=oFactory.newOPMGraph(graph1);

        OPMSerialiser.getThreadOPMSerialiser().serialiseOPMGraph(new File(graphFile2),graph2,true);

        Normalise normaliser=new Normalise(oFactory);

        normaliser.sortGraph(graph1);
        normaliser.sortGraph(graph2);

        //normaliser.noAnnotation(graph1);
        //normaliser.noAnnotation(graph2);


        OPMSerialiser.getThreadOPMSerialiser().serialiseOPMGraph(new File(normalisedGraphFile1),graph1,true);
        OPMSerialiser.getThreadOPMSerialiser().serialiseOPMGraph(new File(normalisedGraphFile1),graph2,true);


        System.out.println("Now comparing");

        compareAssert(graph1,graph2);
    }


    public void compareAssert(OPMGraph graph1, OPMGraph graph2) {
        Assert.assertTrue( "self graph differ", graph1.equals(graph1) );

        Assert.assertTrue( "self graph2  differ", graph2.equals(graph2) );

        Assert.assertTrue( "accounts differ", graph1.getAccounts().getAccount().equals(graph2.getAccounts().getAccount()) );

        Assert.assertTrue( "account overalps differ", graph1.getAccounts().getOverlaps().equals(graph2.getAccounts().getOverlaps()) );

        Assert.assertTrue( "accounts elements differ", graph1.getAccounts().equals(graph2.getAccounts()) );

        Assert.assertTrue( "processes elements differ", graph1.getProcesses().equals(graph2.getProcesses()) );

        Assert.assertTrue( "artifacts elements differ", graph1.getArtifacts().equals(graph2.getArtifacts()) );

        Assert.assertTrue( "edges elements differ", graph1.getCausalDependencies().equals(graph2.getCausalDependencies()) );

        if (graph1.getAgents()!=null && graph2.getAgents()!=null) {
            Assert.assertTrue( "agents elements differ", graph1.getAgents().equals(graph2.getAgents()) );
        } else {
            if (graph1.getAgents()!=null) {
                Assert.assertTrue( "agents elements differ, graph not null",  graph1.getAgents().getAgent().isEmpty());
                graph1.setAgents(null);
            } else if (graph2.getAgents()!=null) {
                Assert.assertTrue( "agents elements differ, graph not null",  graph2.getAgents().getAgent().isEmpty());
                graph2.setAgents(null);
            }
        }

        Assert.assertTrue( "copy of graph differs from original", graph1.equals(graph2) );
    }
}