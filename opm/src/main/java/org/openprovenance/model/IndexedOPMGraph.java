package org.openprovenance.model;
import java.util.Collection;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.HashMap;

public class IndexedOPMGraph extends OPMGraph {

    HashMap<String,Account> accountMap=new HashMap();
    HashMap<String,Artifact> artifactMap=new HashMap();
    HashMap<String,Process> processMap=new HashMap();
    HashMap<String,Collection<Used>> processUsedMap=new HashMap();
    HashMap<String,Collection<Used>> artifactUsedMap=new HashMap();

    protected ObjectFactory of=new ObjectFactory();

    Account addAccount(String name, Account account) {
        Account existing=accountMap.get(name);
        if (existing!=null) {
            return existing;
        } else {
            accountMap.put(name,account);
            Accounts accounts=getAccounts();
            if (accounts==null) {
                accounts=of.createAccounts();
                setAccounts(accounts);
            }
            accounts.getAccount().add(account);
            return account;
        }
    }
    Account addAccount(Account account) {
        return addAccount(account.getId(),account);
    }

    Artifact addArtifact(Artifact artifact) {
        return addArtifact(artifact.getId(),artifact);
    }
    Artifact addArtifact(String name, Artifact artifact) {
        Artifact existing=artifactMap.get(name);
        if (existing!=null) {
            return existing;
        } else {
            artifactMap.put(name,artifact);
            Artifacts artifacts=getArtifacts();
            if (artifacts==null) {
                artifacts=of.createArtifacts();
                setArtifacts(artifacts);
            }
            artifacts.getArtifact().add(artifact);
            return artifact;
        }
    }

    Process addProcess(Process process) {
        return addProcess(process.getId(),process);
    }

    Process addProcess(String name, Process process) {
        Process existing=processMap.get(name);
        if (existing!=null) {
            return existing;
        } else {
            processMap.put(name,process);
            Processes processes=getProcesses();
            if (processes==null) {
                processes=of.createProcesses();
                setProcesses(processes);
            }
            processes.getProcess().add(process);
            return process;
        }
    }
            
    public IndexedOPMGraph(OPMGraph graph) {
        if (graph.getAccounts()!=null) {
            for (Account acc: graph.getAccounts().getAccount()) {
                addAccount(acc);
            }
        }

        if (graph.getArtifacts()!=null) {
            for (Artifact acc: graph.getArtifacts().getArtifact()) {
                addArtifact(acc);
            }
        }

        if (graph.getProcesses()!=null) {
            for (Process acc: graph.getProcesses().getProcess()) {
                addProcess(acc);
            }
        }
    }

    public Used addUsed(Used used) {
        ProcessId pid=used.getEffect();
        Process p=(Process)(pid.getId());
        ArtifactId aid=used.getCause();
        Artifact a=(Artifact)(aid.getId());
        Role r=used.getRole();
        Collection<AccountId> accs=used.getAccount();

        Used result=used;

        Collection<Used> ucoll=processUsedMap.get(p.getId());
        if (ucoll==null) {
            ucoll=new LinkedList();
            ucoll.add(used);
            processUsedMap.put(p.getId(),ucoll);
        } else {
            boolean found=false;
            for (Used u: ucoll) {
                
                if (aid.equals(u.getCause())
                    &&
                    r.equals(u.getRole())) {

                    // curently adds all allcounts,
                    // but should only add in not present
                    u.getAccount().addAll(accs);
                    result=u;
                    found=true;
                }
            }
            if (!found) {
                ucoll.add(used);
            }
        }

        ucoll=artifactUsedMap.get(a.getId());
        if (ucoll==null) {
            ucoll=new LinkedList();
            ucoll.add(used);
            artifactUsedMap.put(p.getId(),ucoll);
        } else {
            boolean found=false;
            for (Used u: ucoll) {
                
                if (pid.equals(u.getEffect())
                    &&
                    r.equals(u.getRole())) {

                    // curently adds all allcounts,
                    // but should only add in not present
                    u.getAccount().addAll(accs);
                    found=true;

                    
                }
            }
            if (!found) {
                ucoll.add(used);
            }
        }



        return result;
   }
}