package org.openprovenance.model;
import java.util.List;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Collections;
import javax.xml.bind.JAXBElement;
import java.util.Comparator;

public class Normalise  {
    OPMFactory oFactory;

    public Normalise(OPMFactory oFactory) {
        this.oFactory=oFactory;
    }

    public void sortGraph(OPMGraph graph) {

        graph.setAnnotations(oFactory.getObjectFactory().createAnnotations());

        sortById((List)graph.getAccounts().getAccount());

        if (graph.getProcesses()!=null && graph.getProcesses().getProcess()!=null) {
            sortById((List)graph.getProcesses().getProcess());
            for (Process p: graph.getProcesses().getProcess()) {
                sortAnnotations(p.getAnnotation());
                sortByRef(p.getAccount());
            }
        }

        if (graph.getArtifacts()!=null && graph.getArtifacts().getArtifact()!=null) {
            sortById((List)graph.getArtifacts().getArtifact());
            for (Artifact a: graph.getArtifacts().getArtifact()) {
                sortAnnotations(a.getAnnotation());
                sortByRef(a.getAccount());
            }
        }

        if (graph.getAgents()!=null && graph.getAgents().getAgent()!=null) {
            sortById((List)graph.getAgents().getAgent());
            for (Agent a: graph.getAgents().getAgent()) {
                sortAnnotations(a.getAnnotation());
                sortByRef(a.getAccount());
            }
        }

        for (Object e: graph.getDependencies().getUsedOrWasGeneratedByOrWasTriggeredBy()) {
            sortAnnotations(((Edge)e).getAnnotation());
            sortByRef(((Edge)e).getAccount());
        }

        sortEdges(graph.getDependencies().getUsedOrWasGeneratedByOrWasTriggeredBy());
    }


    public void updateFromRdfGraph(OPMGraph graph) {

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


    }

    public void noAnnotation(OPMGraph graph) {
        graph.setAnnotations(null);

        destroy(graph.getAnnotation());

        if (graph.getProcesses()!=null && graph.getProcesses().getProcess()!=null) {
            for (Process p: graph.getProcesses().getProcess()) {
                destroy(p.getAnnotation());
            }
        }

        if (graph.getArtifacts()!=null && graph.getArtifacts().getArtifact()!=null) {
            for (Artifact a: graph.getArtifacts().getArtifact()) {
                destroy(a.getAnnotation());
            }
        }

        if (graph.getAgents()!=null && graph.getAgents().getAgent()!=null) {
            for (Agent a: graph.getAgents().getAgent()) {
                destroy(a.getAnnotation());
            }
        }

        for (Object e: graph.getDependencies().getUsedOrWasGeneratedByOrWasTriggeredBy()) {

            destroy(((Edge)e).getAnnotation());
        }
        
    }



    public void embedExternalAnnotations(OPMGraph graph) {

        IndexedOPMGraph igraph=new IndexedOPMGraph(oFactory,graph);

        // embed external annotations
        
        if (graph.getAnnotations()!=null) {
            List<Annotation> anns=graph.getAnnotations().getAnnotation();
            for (Annotation ann: anns) {
                String id=(((Identifiable)ann.getLocalSubject()).getId());
                EmbeddedAnnotation embedded=oFactory.newEmbeddedAnnotation(ann.getId(),
                                                                           ann.getProperty(),
                                                                           ann.getAccount(),
                                                                           null);
                Process p=igraph.getProcess(id);
                if (p!=null) {
                    p.getAnnotation().add(oFactory.compactAnnotation(embedded));
                } else {
                    Artifact a=igraph.getArtifact(id);
                    if (a!=null) {
                        a.getAnnotation().add(oFactory.compactAnnotation(embedded));
                    } else {
                        Agent ag=igraph.getAgent(id);
                        if (ag!=null) {
                            ag.getAnnotation().add(oFactory.compactAnnotation(embedded));
                        }
                    }
                }
            }
            graph.setAnnotations(null);
        }
    }

    public void updateOriginalGraph(OPMGraph graph) {

        embedExternalAnnotations(graph);

        if (graph.getProcesses()!=null && graph.getProcesses().getProcess()!=null) {
            for (Process p: graph.getProcesses().getProcess()) {
                List<AccountRef> accRefs=p.getAccount();
                destroy(accRefs);  //rdf does not have accounts in nodes
            }
        }

        if (graph.getArtifacts()!=null && graph.getArtifacts().getArtifact()!=null) {
            for (Artifact a: graph.getArtifacts().getArtifact()) {
                List<AccountRef> accRefs=a.getAccount();
                destroy(accRefs); //rdf does not have accounts in nodes
            }
        }

        if (graph.getAgents()!=null && graph.getAgents().getAgent()!=null) {
            for (Agent a: graph.getAgents().getAgent()) {
                List<AccountRef> accRefs=a.getAccount();
                destroy(accRefs); //rdf does not have accounts in nodes
            }
        }

        for (Object e: graph.getDependencies().getUsedOrWasGeneratedByOrWasTriggeredBy()) {
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
        }
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

    public void sortByRef(List ll) {
        Collections.sort(ll,
                         new Comparator() {
                             public int compare(Object o1, Object o2) {
                                 Identifiable i1=(Identifiable) ((Ref) o1).getRef();
                                 Identifiable i2=(Identifiable) ((Ref) o2).getRef();
                                 return i1.getId().compareTo(i2.getId());
                             }
                         });
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
        for (JAXBElement je: ll) {
            EmbeddedAnnotation a=(EmbeddedAnnotation) je.getValue();
            sortProperties(a.getProperty());
            sortByRef(a.getAccount());
        }
    }


    public void sortProperties(List<Property> ll) {
        // TODO: when keys are identical, i should sort by values too
        Collections.sort(ll,
                         new Comparator() {
                             public int compare(Object o1, Object o2) {
                                 Property p1=(Property) o1;
                                 Property p2=(Property) o2;

                                 return p1.getKey().compareTo(p2.getKey());
                             }});
    }

    public void sortAccounts(List<Account> ll) {
        // TODO: when keys are identical, i should sort by values too
        Collections.sort(ll,
                         new Comparator() {
                             public int compare(Object o1, Object o2) {
                                 Account acc1=(Account) o1;
                                 Account acc2=(Account) o2;

                                 return acc1.getId().compareTo(acc2.getId());
                             }});
    }



    public void destroy(List ll) {
        for (int i=ll.size()-1; i>=0; i--) {
            ll.remove(ll.get(i));
        }
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


} 