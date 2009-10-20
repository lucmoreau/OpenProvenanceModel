package org.openprovenance.model;
import java.util.Collection;
import java.util.Arrays;
import java.util.LinkedList;

/** Factory of OPM objects. */

public class OPMFactory {

    public static final String packageList=
        "org.openprovenance.model:org.openprovenance.model.extension";

    public String getPackageList() {
        return packageList;
    }

    private final static OPMFactory oFactory=new OPMFactory();

    public static OPMFactory getFactory() {
        return oFactory;
    }

    protected ObjectFactory of;

    public OPMFactory() {
        of=new ObjectFactory();
    }

    public ProcessRef newProcessRef(Process p) {
        ProcessRef res=of.createProcessRef();
        res.setRef(p);
        return res;
    }

    public RoleRef newRoleRef(Role p) {
        RoleRef res=of.createRoleRef();
        res.setRef(p);
        return res;
    }

    public AnnotationRef newAnnotationRef(Annotation a) {
        AnnotationRef res=of.createAnnotationRef();
        res.setRef(a);
        return res;
    }

    public ArtifactRef newArtifactRef(Artifact a) {
        ArtifactRef res=of.createArtifactRef();
        res.setRef(a);
        return res;
    }
    public AgentRef newAgentRef(Agent a) {
        AgentRef res=of.createAgentRef();
        res.setRef(a);
        return res;
    }

    public AccountRef newAccountRef(Account acc) {
        AccountRef res=of.createAccountRef();
        res.setRef(acc);
        return res;
    }





    public CausalDependencyRef newCausalDependencyRef(WasGeneratedBy edge) {
        CausalDependencyRef res=of.createCausalDependencyRef();
        res.setRef(edge);
        return res;
    }

    public CausalDependencyRef newCausalDependencyRef(Used edge) {
        CausalDependencyRef res=of.createCausalDependencyRef();
        res.setRef(edge);
        return res;
    }
    public CausalDependencyRef newCausalDependencyRef(WasDerivedFrom edge) {
        CausalDependencyRef res=of.createCausalDependencyRef();
        res.setRef(edge);
        return res;
    }

    public CausalDependencyRef newCausalDependencyRef(WasControlledBy edge) {
        CausalDependencyRef res=of.createCausalDependencyRef();
        res.setRef(edge);
        return res;
    }
    public CausalDependencyRef newCausalDependencyRef(WasTriggeredBy edge) {
        CausalDependencyRef res=of.createCausalDependencyRef();
        res.setRef(edge);
        return res;
    }


    public Process newProcess_(String pr,
                              Collection<AccountRef> accounts,
                              Object value) {
        Process res=of.createProcess();
        res.setId(pr);
        if ((accounts !=null) && (accounts.size()!=0)) {
            res.getAccount().addAll(accounts);
        }
        res.setValue(value);
        return res;
    }

    public Process newProcess(String pr,
                              Collection<Account> accounts,
                              Object value) {
        Process res=of.createProcess();
        res.setId(pr);
        if ((accounts !=null) && (accounts.size()!=0)) {
            LinkedList ll=new LinkedList();
            for (Account acc: accounts) {
                ll.add(newAccountRef(acc));
            }
            res.getAccount().addAll(ll);
        }
        res.setValue(value);
        return res;
    }

    public Agent newAgent(String ag,
                          Collection<Account> accounts,
                          Object value) {
        Agent res=of.createAgent();
        res.setId(ag);
        if ((accounts !=null) && (accounts.size()!=0)) {
            LinkedList ll=new LinkedList();
            for (Account acc: accounts) {
                ll.add(newAccountRef(acc));
            }
            res.getAccount().addAll(ll);
        }
        res.setValue(value);
        return res;
    }

    public Account newAccount(String acc) {
        Account res=of.createAccount();
        res.setId(acc);
        return res;
    }

    public Overlaps newOverlaps(Collection<Account> accounts) {
        Overlaps res=of.createOverlaps();
        LinkedList ll=new LinkedList();
        int i=0;
        for (Account acc: accounts) {
            if (i==2) break;
            ll.add(newAccountRef(acc));
            i++;
        }
        res.getAccount().addAll(ll);
        return res;
    }
        
    public Overlaps newOverlaps(AccountRef aid1,AccountRef aid2) {
        Overlaps res=of.createOverlaps();
        res.getAccount().add(aid1);
        res.getAccount().add(aid2);
        return res;
    }
        

    public Role newRole(String value) {
        Role res=of.createRole();
        res.setValue(value);
        return res;
    }

    public Role newRole(String id, String value) {
        Role res=of.createRole();
        res.setId(id);
        res.setValue(value);
        return res;
    }

//     public Artifact newArtifact_(String id,
//                                 Collection<AccountRef> accounts,
//                                 Object value) {
//         Artifact res=of.createArtifact();
//         res.setId(id);
//         if ((accounts !=null) && (accounts.size()!=0)) {
//             res.getAccount().addAll(accounts);
//         }
//         res.setValue(value);
//         return res;
//     }
    public Artifact newArtifact(String id,
                                Collection<Account> accounts,
                                Object value) {
        Artifact res=of.createArtifact();
        res.setId(id);
        if ((accounts !=null) && (accounts.size()!=0)) {
            LinkedList ll=new LinkedList();
            for (Account acc: accounts) {
                ll.add(newAccountRef(acc));
            }
            res.getAccount().addAll(ll);
        }
        res.setValue(value);
        return res;
    }

    public Used newUsed(ProcessRef pid,
                        Role role,
                        ArtifactRef aid,
                        Collection<AccountRef> accounts) {
        Used res=of.createUsed();
        res.setEffect(pid);
        res.setRole(role);
        res.setCause(aid);
        if ((accounts !=null) && (accounts.size()!=0)) {
            res.getAccount().addAll(accounts);
        }
        return res;
    }

    public Used newUsed(Process p,
                        Role role,
                        Artifact a,
                        Collection<Account> accounts) {
        ProcessRef pid=newProcessRef(p);
        ArtifactRef aid=newArtifactRef(a);
        LinkedList ll=new LinkedList();
        for (Account acc: accounts) {
            ll.add(newAccountRef(acc));
        }
        return newUsed(pid,role,aid,ll);
    }

    public Used newUsed(String id,
                        Process p,
                        Role role,
                        Artifact a,
                        Collection<Account> accounts) {
        Used res=newUsed(p,role,a,accounts);
        res.setId(id);
        return res;
    }

    public Used newUsed(Used u) {
        return newUsed(u.getEffect(),
                       u.getRole(),
                       u.getCause(),
                       u.getAccount());
    }

    public WasControlledBy newWasControlledBy(WasControlledBy c) {
        return newWasControlledBy(c.getEffect(),
                                  c.getRole(),
                                  c.getCause(),
                                  c.getAccount());
    }

    public WasGeneratedBy newWasGeneratedBy(WasGeneratedBy g) {
        return newWasGeneratedBy(g.getEffect(),
                                 g.getRole(),
                                 g.getCause(),
                                 g.getAccount());
    }

    public WasDerivedFrom newWasDerivedFrom(WasDerivedFrom d) {
        return newWasDerivedFrom(d.getEffect(),
                                 d.getCause(),
                                 d.getAccount());
    }

    public WasTriggeredBy newWasTriggeredBy(WasTriggeredBy d) {
        return newWasTriggeredBy(d.getEffect(),
                                 d.getCause(),
                                 d.getAccount());
    }



    public WasGeneratedBy newWasGeneratedBy(ArtifactRef aid,
                                            Role role,
                                            ProcessRef pid,
                                            Collection<AccountRef> accounts) {
        WasGeneratedBy res=of.createWasGeneratedBy();
        res.setCause(pid);
        res.setRole(role);
        res.setEffect(aid);
        if ((accounts !=null) && (accounts.size()!=0)) {
            res.getAccount().addAll(accounts);
        }
        return res;
    }
    public WasGeneratedBy newWasGeneratedBy(Artifact a,
                                            Role role,
                                            Process p,
                                            Collection<Account> accounts) {
        ArtifactRef aid=newArtifactRef(a);
        ProcessRef pid=newProcessRef(p);
        LinkedList ll=new LinkedList();
        for (Account acc: accounts) {
            ll.add(newAccountRef(acc));
        }
        return  newWasGeneratedBy(aid,role,pid,ll);
    }


    public WasGeneratedBy newWasGeneratedBy(String id,
                                            Artifact a,
                                            Role role,
                                            Process p,
                                            Collection<Account> accounts) {
        WasGeneratedBy res= newWasGeneratedBy(a,role,p,accounts);
        res.setId(id);
        return res;
    }



    public WasControlledBy newWasControlledBy(ProcessRef pid,
                                              Role role,
                                              AgentRef agid,
                                              Collection<AccountRef> accounts) {
        WasControlledBy res=of.createWasControlledBy();
        res.setEffect(pid);
        res.setRole(role);
        res.setCause(agid);
        if ((accounts !=null) && (accounts.size()!=0)) {
            res.getAccount().addAll(accounts);
        }
        return res;
    }


    public WasControlledBy newWasControlledBy(Process p,
                                              Role role,
                                              Agent ag,
                                              Collection<Account> accounts) {
        AgentRef agid=newAgentRef(ag);
        ProcessRef pid=newProcessRef(p);
        LinkedList ll=new LinkedList();
        for (Account acc: accounts) {
            ll.add(newAccountRef(acc));
        }
        return  newWasControlledBy(pid,role,agid,ll);
    }

    public WasDerivedFrom newWasDerivedFrom(ArtifactRef aid1,
                                            ArtifactRef aid2,
                                            Collection<AccountRef> accounts) {
        WasDerivedFrom res=of.createWasDerivedFrom();
        res.setCause(aid2);
        res.setEffect(aid1);
        if ((accounts !=null) && (accounts.size()!=0)) {
            res.getAccount().addAll(accounts);
        }

        return res;
    }

    public WasDerivedFrom newWasDerivedFrom(Artifact a1,
                                            Artifact a2,
                                            Collection<Account> accounts) {
        ArtifactRef aid1=newArtifactRef(a1);
        ArtifactRef aid2=newArtifactRef(a2);
        LinkedList ll=new LinkedList();
        for (Account acc: accounts) {
            ll.add(newAccountRef(acc));
        }
        return  newWasDerivedFrom(aid1,aid2,ll);
    }



    public WasTriggeredBy newWasTriggeredBy(ProcessRef pid1,
                                            ProcessRef pid2,
                                            Collection<AccountRef> accounts) {
        WasTriggeredBy res=of.createWasTriggeredBy();
        res.setEffect(pid1);
        res.setCause(pid2);
        if ((accounts !=null) && (accounts.size()!=0)) {
            res.getAccount().addAll(accounts);
        }

        return res;
    }

    public WasTriggeredBy newWasTriggeredBy(Process p1,
                                            Process p2,
                                            Collection<Account> accounts) {
        ProcessRef pid1=newProcessRef(p1);
        ProcessRef pid2=newProcessRef(p2);
        LinkedList<AccountRef> ll=new LinkedList();
        for (Account acc: accounts) {
            ll.add(newAccountRef(acc));
        }
        return  newWasTriggeredBy(pid1,pid2,ll);
    }

    public EmbeddedAnnotation newEmbeddedAnnotation(String id,
                                                    String property,
                                                    Object value,
                                                    Collection<Account> accs) {
        LinkedList<AccountRef> ll=new LinkedList();
        if (accs!=null) {
            for (Account acc: accs) {
                ll.add(newAccountRef(acc));
            }
        }
        return newEmbeddedAnnotation(id,property,value,ll,null);
    }

    public Annotation newAnnotation(String id,
                                    Artifact a,
                                    String property,
                                    Object value,
                                    Collection<Account> accs) {
        ArtifactRef aid=newArtifactRef(a);
        LinkedList<AccountRef> ll=new LinkedList();
        if (accs!=null) {
            for (Account acc: accs) {
                ll.add(newAccountRef(acc));
            }
        }
        return newAnnotation(id,aid,property,value,ll);
    }
    public Annotation newAnnotation(String id,
                                    Process p,
                                    String property,
                                    Object value,
                                    Collection<Account> accs) {
        ProcessRef pid=newProcessRef(p);
        LinkedList<AccountRef> ll=new LinkedList();
        if (accs!=null) {
            for (Account acc: accs) {
                ll.add(newAccountRef(acc));
            }
        }
        return newAnnotation(id,pid,property,value,ll);
    }

    public Annotation newAnnotation(String id,
                                    Annotation a,
                                    String property,
                                    Object value,
                                    Collection<Account> accs) {
        AnnotationRef aid=newAnnotationRef(a);
        LinkedList<AccountRef> ll=new LinkedList();
        if (accs!=null) {
            for (Account acc: accs) {
                ll.add(newAccountRef(acc));
            }
        }
        return newAnnotation(id,aid,property,value,ll);
    }

    public Annotation newAnnotation(String id,
                                    WasDerivedFrom edge,
                                    String property,
                                    Object value,
                                    Collection<Account> accs) {
        CausalDependencyRef cid=newCausalDependencyRef(edge);
        LinkedList<AccountRef> ll=new LinkedList();
        if (accs!=null) {
            for (Account acc: accs) {
                ll.add(newAccountRef(acc));
            }
        }
        return newAnnotation(id,cid,property,value,ll);
    }
    public Annotation newAnnotation(String id,
                                    Used edge,
                                    String property,
                                    Object value,
                                    Collection<Account> accs) {
        CausalDependencyRef cid=newCausalDependencyRef(edge);
        LinkedList<AccountRef> ll=new LinkedList();
        if (accs!=null) {
            for (Account acc: accs) {
                ll.add(newAccountRef(acc));
            }
        }
        return newAnnotation(id,cid,property,value,ll);
    }
    public Annotation newAnnotation(String id,
                                    WasGeneratedBy edge,
                                    String property,
                                    Object value,
                                    Collection<Account> accs) {
        CausalDependencyRef cid=newCausalDependencyRef(edge);
        LinkedList<AccountRef> ll=new LinkedList();
        if (accs!=null) {
            for (Account acc: accs) {
                ll.add(newAccountRef(acc));
            }
        }
        return newAnnotation(id,cid,property,value,ll);
    }
    public Annotation newAnnotation(String id,
                                    WasControlledBy edge,
                                    String property,
                                    Object value,
                                    Collection<Account> accs) {
        CausalDependencyRef cid=newCausalDependencyRef(edge);
        LinkedList<AccountRef> ll=new LinkedList();
        if (accs!=null) {
            for (Account acc: accs) {
                ll.add(newAccountRef(acc));
            }
        }
        return newAnnotation(id,cid,property,value,ll);
    }
    public Annotation newAnnotation(String id,
                                    WasTriggeredBy edge,
                                    String property,
                                    Object value,
                                    Collection<Account> accs) {
        CausalDependencyRef cid=newCausalDependencyRef(edge);
        LinkedList<AccountRef> ll=new LinkedList();
        if (accs!=null) {
            for (Account acc: accs) {
                ll.add(newAccountRef(acc));
            }
        }
        return newAnnotation(id,cid,property,value,ll);
    }

    public Annotation newAnnotation(String id,
                                    Role role,
                                    String property,
                                    Object value,
                                    Collection<Account> accs) {
        RoleRef rid=newRoleRef(role);
        LinkedList<AccountRef> ll=new LinkedList();
        if (accs!=null) {
            for (Account acc: accs) {
                ll.add(newAccountRef(acc));
            }
        }
        return newAnnotation(id,rid,property,value,ll);
    }

    public Property newProperty(String property,
                                Object value) {
        Property res=of.createProperty();
        res.setUri(property);
        res.setValue(value);
        return res;
    }


    public Annotation newAnnotation(String id,
                                    Ref ref,
                                    String property,
                                    Object value,
                                    Collection<AccountRef> accs) {
        Annotation res=of.createAnnotation();
        res.setId(id);
        res.setLocalSubject(ref.getRef());
        res.getProperty().add(newProperty(property,value));
        if (accs!=null) {
            res.getAccount().addAll(accs);
        }
        return res;
    }

    public EmbeddedAnnotation newEmbeddedAnnotation(String id,
                                                    String property,
                                                    Object value,
                                                    Collection<AccountRef> accs,
                                                    Object dummyParameterForAvoidingSameErasure) {
        EmbeddedAnnotation res=of.createEmbeddedAnnotation();
        res.setId(id);
//         res.getPropertyAndValue().add(property);
//         res.getPropertyAndValue().add(1);
//         res.getPropertyAndValue().add(property);
//         res.getPropertyAndValue().add(2);
//        res.getPropertyAndValue().add(of.createProperty(property));
        //        res.getPropertyAndValue().add(of.createValue(value));
        res.getProperty().add(newProperty(property,value));
        if (accs!=null) {
            res.getAccount().addAll(accs);
        }
        return res;
    }

//     void setRef(Annotation ann, Ref ref) {
//         if (ref instanceof ArtifactRef) {
//             ann.setArtifact((ArtifactRef) ref);
//         }
//         if (ref instanceof AnnotationRef) {
//             ann.setAnnotation((AnnotationRef) ref);
//         }
//         if (ref instanceof ProcessRef) {
//             ann.setProcess((ProcessRef) ref);
//         }
//     }


    public OPMGraph newOPMGraph(Collection<Account> accs,
                                Collection<Overlaps> ops,
                                Collection<Process> ps,
                                Collection<Artifact> as,
                                Collection<Agent> ags,
                                Collection<Object> lks) {
        return newOPMGraph(accs,ops,ps,as,ags,lks,null);
    }

    public OPMGraph newOPMGraph(Collection<Account> accs,
                                Collection<Overlaps> ops,
                                Collection<Process> ps,
                                Collection<Artifact> as,
                                Collection<Agent> ags,
                                Collection<Object> lks,
                                Collection<Annotation> anns)
    {
        OPMGraph res=of.createOPMGraph();
        if (accs!=null) {
            Accounts aaccs=of.createAccounts();
            aaccs.getAccount().addAll(accs);
            if (ops!=null) 
                aaccs.getOverlaps().addAll(ops);
            res.setAccounts(aaccs);
            
        }
        if (ps!=null) {
            Processes pps=of.createProcesses();
            pps.getProcess().addAll(ps);
            res.setProcesses(pps);
        }
        if (as!=null) {
            Artifacts aas=of.createArtifacts();
            aas.getArtifact().addAll(as);
            res.setArtifacts(aas);
        }
        if (ags!=null) {
            Agents aags=of.createAgents();
            aags.getAgent().addAll(ags);
            res.setAgents(aags);
        }
        if (lks!=null) {
            CausalDependencies ccls=of.createCausalDependencies();
            ccls.getUsedOrWasGeneratedByOrWasTriggeredBy().addAll(lks);
            res.setCausalDependencies(ccls);
        }

        if (anns!=null) {
            Annotations l=of.createAnnotations();
            l.getAnnotation().addAll(anns);
            res.setAnnotations(l);
        }
        return res;
    }

    public OPMGraph newOPMGraph(Collection<Account> accs,
                                Overlaps [] ovs,
                                Process [] ps,
                                Artifact [] as,
                                Agent [] ags,
                                Object [] lks) 
    {

        return newOPMGraph(accs,
                           ((ovs==null) ? null : Arrays.asList(ovs)),
                           ((ps==null) ? null : Arrays.asList(ps)),
                           ((as==null) ? null : Arrays.asList(as)),
                           ((ags==null) ? null : Arrays.asList(ags)),
                           ((lks==null) ? null : Arrays.asList(lks)));
    }
    public OPMGraph newOPMGraph(Collection<Account> accs,
                                Overlaps [] ovs,
                                Process [] ps,
                                Artifact [] as,
                                Agent [] ags,
                                Object [] lks,
                                Annotation [] anns) 
    {

        return newOPMGraph(accs,
                           ((ovs==null) ? null : Arrays.asList(ovs)),
                           ((ps==null) ? null : Arrays.asList(ps)),
                           ((as==null) ? null : Arrays.asList(as)),
                           ((ags==null) ? null : Arrays.asList(ags)),
                           ((lks==null) ? null : Arrays.asList(lks)),
                           ((anns==null) ? null : Arrays.asList(anns)));
    }

    public OPMGraph newOPMGraph(Accounts accs,
                                Processes ps,
                                Artifacts as,
                                Agents ags,
                                CausalDependencies lks)
    {
        OPMGraph res=of.createOPMGraph();
        res.setAccounts(accs);
        res.setProcesses(ps);
        res.setArtifacts(as);
        res.setAgents(ags);
        res.setCausalDependencies(lks);
        return res;
    }

    public OPMGraph newOPMGraph(Accounts accs,
                                Processes ps,
                                Artifacts as,
                                Agents ags,
                                CausalDependencies lks,
                                Annotations anns)
    {
        OPMGraph res=of.createOPMGraph();
        res.setAccounts(accs);
        res.setProcesses(ps);
        res.setArtifacts(as);
        res.setAgents(ags);
        res.setCausalDependencies(lks);
        res.setAnnotations(anns);
        return res;
    }

    public Accounts newAccounts(Collection<Account> accs,
                                Collection<Overlaps> ovlps) {
        Accounts res=of.createAccounts();
        if (accs!=null) {
            res.getAccount().addAll(accs);
        }
        if (ovlps!=null) {
            res.getOverlaps().addAll(ovlps);
        }
        return res;
    }

            
}

