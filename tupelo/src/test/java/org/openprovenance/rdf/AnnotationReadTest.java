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

        OPMToDot toDot=new OPMToDot();
        toDot.convert(graph2,"target/annotation1-rdf.dot", "target/annotation1-rdf.pdf");


        OPMDeserialiser deserial=OPMDeserialiser.getThreadOPMDeserialiser();
        OPMGraph graph=deserial.deserialiseOPMGraph(new File("target/annotation-example1.xml"));
        
        assertTrue( graph!=null );

        updateGraph2(graph2);
        updateGraph(graph);

        serial.serialiseOPMGraph(new File("target/annotation-example1-from-rdf-updated.xml"),graph2,true);

        serial.serialiseOPMGraph(new File("target/annotation-example1-updated.xml"),graph,true);

        assertTrue( "self graph differ", graph.equals(graph) );

        assertTrue( "self graph2  differ", graph2.equals(graph2) );

        System.out.println("overlaps " + graph2.getAccounts().getOverlaps());
        System.out.println("overlaps " + graph.getAccounts().getOverlaps());

        assertTrue( "accounts differ", graph.getAccounts().getAccount().equals(graph2.getAccounts().getAccount()) );

        assertTrue( "account overalps differ", graph.getAccounts().getOverlaps().equals(graph2.getAccounts().getOverlaps()) );

        assertTrue( "accounts elements differ", graph.getAccounts().equals(graph2.getAccounts()) );

        assertTrue( "processes elements differ", graph.getProcesses().equals(graph2.getProcesses()) );

        assertTrue( "artifacts elements differ", graph.getArtifacts().equals(graph2.getArtifacts()) );

        assertTrue( "edges elements differ", graph.getCausalDependencies().equals(graph2.getCausalDependencies()) );

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


    void updateGraph2(OPMGraph graph) {



        IndexedOPMGraph igraph=new IndexedOPMGraph(oFactory,graph);
        List<Account> accs=graph.getAccounts().getAccount();
        for (Account acc: accs) {
            if (acc.getId().equals("_null")) {
                accs.remove(acc);
                break;
            }
        }



        Collection<Account> green=Collections.singleton(igraph.getAccount("green"));
        Collection<Account> orange=Collections.singleton(igraph.getAccount("orange"));

        List<Account> green_orange=new LinkedList();
        green_orange.addAll(green);
        green_orange.addAll(orange);


        Overlaps ov1=oFactory.newOverlaps(green_orange);
        graph.getAccounts().getOverlaps().add(ov1);





        if (graph.getProcesses()!=null && graph.getProcesses().getProcess()!=null) {
            sortById((List)graph.getProcesses().getProcess());
            for (Process p: graph.getProcesses().getProcess()) {
                sortAnnotations(p.getAnnotation());
            }
        }

        if (graph.getArtifacts()!=null && graph.getArtifacts().getArtifact()!=null) {
            sortById((List)graph.getArtifacts().getArtifact());
            for (Artifact a: graph.getArtifacts().getArtifact()) {
                sortAnnotations(a.getAnnotation());
            }
        }

        if (graph.getAgents()!=null && graph.getAgents().getAgent()!=null) {
            sortById((List)graph.getAgents().getAgent());
            for (Agent a: graph.getAgents().getAgent()) {
                sortAnnotations(a.getAnnotation());
            }
        }

        for (Object e: graph.getCausalDependencies().getUsedOrWasGeneratedByOrWasTriggeredBy()) {
            sortAnnotations(((Edge)e).getAnnotation());
        }

        sortEdges(graph.getCausalDependencies().getUsedOrWasGeneratedByOrWasTriggeredBy());


    }

    void destroy(List ll) {
        for (int i=ll.size()-1; i>=0; i--) {
            ll.remove(ll.get(i));
        }
    }


    void updateGraph(OPMGraph graph) {

        IndexedOPMGraph igraph=new IndexedOPMGraph(oFactory,graph);


        
        if (graph.getAnnotations()!=null) {
//             List<Annotation> anns=graph.getAnnotations().getAnnotation();
//             for (int i=anns.size()-1; i>=0; i--) {
//                 anns.remove(anns.get(i));
//             }
            graph.setAnnotations(null);
        }


        if (graph.getProcesses()!=null && graph.getProcesses().getProcess()!=null) {
            sortById((List)graph.getProcesses().getProcess());
            for (Process p: graph.getProcesses().getProcess()) {
                List<AccountRef> accRefs=p.getAccount();
                destroy(accRefs);  //rdf does not have accounts in nodes
                sortAnnotations(p.getAnnotation());
            }
        }

        if (graph.getArtifacts()!=null && graph.getArtifacts().getArtifact()!=null) {
            sortById((List)graph.getArtifacts().getArtifact());
            for (Artifact a: graph.getArtifacts().getArtifact()) {
                List<AccountRef> accRefs=a.getAccount();
                destroy(accRefs); //rdf does not have accounts in nodes
                sortAnnotations(a.getAnnotation());
            }
        }

        if (graph.getAgents()!=null && graph.getAgents().getAgent()!=null) {
            sortById((List)graph.getAgents().getAgent());
            for (Agent a: graph.getAgents().getAgent()) {
                List<AccountRef> accRefs=a.getAccount();
                destroy(accRefs); //rdf does not have accounts in nodes
                sortAnnotations(a.getAnnotation());
            }
        }

        for (Object e: graph.getCausalDependencies().getUsedOrWasGeneratedByOrWasTriggeredBy()) {
            sortAnnotations(((Edge)e).getAnnotation());
            ((Identifiable)e).setId(null);  // tupelo rdf does not allow ids in edges
        }

        for (Object e: graph.getCausalDependencies().getUsedOrWasGeneratedByOrWasTriggeredBy()) {
            ((Identifiable)e).setId(null);  // tupelo rdf does not allow ids in edges


            // my translation does not support annotations on roles
            if (e instanceof WasGeneratedBy) {
                WasGeneratedBy wgb=(WasGeneratedBy) e;
                wgb.getRole().setId(null);
            }
            if (e instanceof Used) {
                Used u=(Used) e;
                u.getRole().setId(null);
            }
            if (e instanceof WasControlledBy) {
                WasControlledBy wcb=(WasControlledBy) e;
                wcb.getRole().setId(null);
            }
            sortAnnotations(((Edge)e).getAnnotation());
        }
        sortEdges(graph.getCausalDependencies().getUsedOrWasGeneratedByOrWasTriggeredBy());
    }

    public void sortById(List ll) {
        Collections.sort(ll,
                         new Comparator() {
                             public int compare(Object o1, Object o2) {
                                 Identifiable i1=(Identifiable) o1;
                                 Identifiable i2=(Identifiable) o2;
                                 return i1.getId().compareTo(i2.getId());
                             }
                             public boolean equals_IGNORE(Object o1, Object o2) {
                                 Identifiable i1=(Identifiable) o1;
                                 Identifiable i2=(Identifiable) o2;
                                 return (i1.getId().equals(i2.getId()));
                             }
                         });
    }

    public void sortAnnotationsIGNORE(List<EmbeddedAnnotation> ll) {
        Collections.sort(ll,
                         new Comparator() {
                             public int compare(Object o1, Object o2) {
                                 EmbeddedAnnotation a1=(EmbeddedAnnotation) o1;
                                 EmbeddedAnnotation a2=(EmbeddedAnnotation) o2;

                                 if (a1.getId()==null) {
                                     if (a2.getId()==null) {
                                         return a1.getClass().getName().compareTo(a2.getClass().getName());
                                     } else {
                                         return -1;
                                     }
                                 } else {
                                     if (a2.getId()==null) {
                                         return +1;
                                     } else {
                                         return a1.getId().compareTo(a2.getId());                                         
                                     }
                                 }


                             }});
    }

    public void sortAnnotations(List<JAXBElement<? extends EmbeddedAnnotation>> ll) {
        Collections.sort(ll,
                         new Comparator() {
                             public int compare(Object o1, Object o2) {
                                 JAXBElement j1=(JAXBElement) o1;
                                 JAXBElement j2=(JAXBElement) o2;
                                 EmbeddedAnnotation a1=(EmbeddedAnnotation) j1.getValue();
                                 EmbeddedAnnotation a2=(EmbeddedAnnotation) j2.getValue();

                                 if (a1.getId()==null) {
                                     if (a2.getId()==null) {
                                         return a1.getClass().getName().compareTo(a2.getClass().getName());
                                     } else {
                                         return -1;
                                     }
                                 } else {
                                     if (a2.getId()==null) {
                                         return +1;
                                     } else {
                                         return a1.getId().compareTo(a2.getId());                                         
                                     }
                                 }


                             }});
    }

    public void sortEdges(List ll) {
        Collections.sort(ll,
                         new Comparator() {
                             public int compare(Object o1, Object o2) {
                                 Edge e1=(Edge) o1;
                                 Edge e2=(Edge) o2;
                                 String id1_1=((Identifiable)(e1.getCause().getRef())).getId();
                                 String id1_2=((Identifiable)(e1.getEffect().getRef())).getId();
                                 String s1=id1_1+id1_2;
                                 String id2_1=((Identifiable)(e2.getCause().getRef())).getId();
                                 String id2_2=((Identifiable)(e2.getEffect().getRef())).getId();
                                 String s2=id2_1+id2_2;
                                 
                                 return s1.compareTo(s2);
                             }

                         });
    }


}
