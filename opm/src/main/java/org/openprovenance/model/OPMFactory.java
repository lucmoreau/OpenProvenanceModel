package org.openprovenance.model;
import java.util.Collection;
import java.util.Arrays;
import java.util.LinkedList;

/** Factory of OPM objects. */

public class OPMFactory {

    public static final String packageList=
        "org.openprovenance.model";

    private final static OPMFactory oFactory=new OPMFactory();



    public static OPMFactory getFactory() {
        return oFactory;
    }

    private ObjectFactory of;

    public OPMFactory() {
        of=new ObjectFactory();
    }

    public ProcessId newProcessId(Process p) {
        ProcessId res=of.createProcessId();
        res.setId(p);
        return res;
    }

    public ArtifactId newArtifactId(Artifact a) {
        ArtifactId res=of.createArtifactId();
        res.setId(a);
        return res;
    }
    public AgentId newAgentId(Agent a) {
        AgentId res=of.createAgentId();
        res.setId(a);
        return res;
    }
    public ProcessId newProcessId(String s) {
        ProcessId res=of.createProcessId();
        res.setId(s);
        return res;
    }
    public ArtifactId newArtifactId(String s) {
        ArtifactId res=of.createArtifactId();
        res.setId(s);
        return res;
    }
    public AgentId newAgentId(String s) {
        AgentId res=of.createAgentId();
        res.setId(s);
        return res;
    }
    public AccountId newAccountId(Account acc) {
        AccountId res=of.createAccountId();
        res.setId(acc);
        return res;
    }


    public Process newProcess_(String pr,
                              Collection<AccountId> accounts,
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
                ll.add(newAccountId(acc));
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
                ll.add(newAccountId(acc));
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
            ll.add(newAccountId(acc));
            i++;
        }
        res.getAccount().addAll(ll);
        return res;
    }
        
    public Overlaps newOverlaps(AccountId aid1,AccountId aid2) {
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

    public Artifact newArtifact_(String id,
                                Collection<AccountId> accounts,
                                Object value) {
        Artifact res=of.createArtifact();
        res.setId(id);
        if ((accounts !=null) && (accounts.size()!=0)) {
            res.getAccount().addAll(accounts);
        }
        res.setValue(value);
        return res;
    }
    public Artifact newArtifact(String id,
                                Collection<Account> accounts,
                                Object value) {
        Artifact res=of.createArtifact();
        res.setId(id);
        if ((accounts !=null) && (accounts.size()!=0)) {
            LinkedList ll=new LinkedList();
            for (Account acc: accounts) {
                ll.add(newAccountId(acc));
            }
            res.getAccount().addAll(ll);
        }
        res.setValue(value);
        return res;
    }

    public Used newUsed(ProcessId pid,
                        Role role,
                        ArtifactId aid,
                        Collection<AccountId> accounts) {
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
        ProcessId pid=newProcessId(p);
        ArtifactId aid=newArtifactId(a);
        LinkedList ll=new LinkedList();
        for (Account acc: accounts) {
            ll.add(newAccountId(acc));
        }
        return newUsed(pid,role,aid,ll);
    }


    public WasGeneratedBy newWasGeneratedBy(ArtifactId aid,
                                            Role role,
                                            ProcessId pid,
                                            Collection<AccountId> accounts) {
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
        ArtifactId aid=newArtifactId(a);
        ProcessId pid=newProcessId(p);
        LinkedList ll=new LinkedList();
        for (Account acc: accounts) {
            ll.add(newAccountId(acc));
        }
        return  newWasGeneratedBy(aid,role,pid,ll);
    }



    public WasControlledBy newWasControlledBy(ProcessId pid,
                                              Role role,
                                              AgentId agid,
                                              Collection<AccountId> accounts) {
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
        AgentId agid=newAgentId(ag);
        ProcessId pid=newProcessId(p);
        LinkedList ll=new LinkedList();
        for (Account acc: accounts) {
            ll.add(newAccountId(acc));
        }
        return  newWasControlledBy(pid,role,agid,ll);
    }

    public WasDerivedFrom newWasDerivedFrom(ArtifactId aid1,
                                            ArtifactId aid2,
                                            Collection<AccountId> accounts) {
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
        ArtifactId aid1=newArtifactId(a1);
        ArtifactId aid2=newArtifactId(a2);
        LinkedList ll=new LinkedList();
        for (Account acc: accounts) {
            ll.add(newAccountId(acc));
        }
        return  newWasDerivedFrom(aid1,aid2,ll);
    }



    public WasTriggeredBy newWasTriggeredBy(ProcessId pid1,
                                            ProcessId pid2,
                                            Collection<AccountId> accounts) {
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
        ProcessId pid1=newProcessId(p1);
        ProcessId pid2=newProcessId(p2);
        LinkedList ll=new LinkedList();
        for (Account acc: accounts) {
            ll.add(newAccountId(acc));
        }
        return  newWasTriggeredBy(pid1,pid2,ll);
    }



    public OPMGraph newOPMGraph(Collection<Account> accs,
                                Collection<Overlaps> ops,
                                Collection<Process> ps,
                                Collection<Artifact> as,
                                Collection<Agent> ags,
                                Collection<Object> lks)
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

