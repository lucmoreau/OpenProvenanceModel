package org.openprovenance.model.extension;
import java.util.Collection;
import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;

import org.openprovenance.model.OPMFactory;
import org.openprovenance.model.ArtifactId;
import org.openprovenance.model.Artifact;
import org.openprovenance.model.ProcessId;
import org.openprovenance.model.Process;
import org.openprovenance.model.AgentId;
import org.openprovenance.model.Agent;
import org.openprovenance.model.ArtifactExt;
import org.openprovenance.model.AccountId;
import org.openprovenance.model.Role;
import org.openprovenance.model.WasDerivedFrom;
import org.openprovenance.model.Account;
import org.openprovenance.model.NamedWasDerivedFrom;
import org.openprovenance.model.NamedWasControlledBy;

/** An extended Factory of OPM objects with NamedWasDerivedFrom edges.
    Needs to be moved in a separate module probably, since it is an extension*/

public class OPMExtendedFactory extends OPMFactory {

    private final static OPMFactory oFactory=new OPMExtendedFactory();



    public static OPMFactory getFactory() {
        return oFactory;
    }


    public NamedWasDerivedFrom newNamedWasDerivedFrom(ArtifactId aid1,
                                                      ArtifactId aid2,
                                                      String type,
                                                      Collection<AccountId> accounts) {
        NamedWasDerivedFrom res=of.createNamedWasDerivedFrom();
        res.setCause(aid2);
        res.setEffect(aid1);
        res.setType(type);
        if ((accounts !=null) && (accounts.size()!=0)) {
            res.getAccount().addAll(accounts);
        }

        return res;
    }




    public NamedWasDerivedFrom newNamedWasDerivedFrom(Artifact a1,
                                                      Artifact a2,
                                                      String type,
                                                      Collection<Account> accounts) {
        ArtifactId aid1=newArtifactId(a1);
        ArtifactId aid2=newArtifactId(a2);
        LinkedList ll=new LinkedList();
        for (Account acc: accounts) {
            ll.add(newAccountId(acc));
        }
        return  newNamedWasDerivedFrom(aid1,aid2,type,ll);
    }



    public NamedWasControlledBy newNamedWasControlledBy(ProcessId pid,
                                                        Role role,
                                                        AgentId agid,
                                                        String type,
                                                        Collection<AccountId> accounts) {
        NamedWasControlledBy res=of.createNamedWasControlledBy();
        res.setCause(agid);
        res.setEffect(pid);
        res.setRole(role);
        res.setType(type);
        if ((accounts !=null) && (accounts.size()!=0)) {
            res.getAccount().addAll(accounts);
        }
        return res;
    }




    public NamedWasControlledBy newNamedWasControlledBy(Process p,
                                                        Role role,
                                                        Agent ag,
                                                        String type,
                                                        Collection<Account> accounts) {
        ProcessId pid=newProcessId(p);
        AgentId agid=newAgentId(ag);
        LinkedList ll=new LinkedList();
        for (Account acc: accounts) {
            ll.add(newAccountId(acc));
        }
        return  newNamedWasControlledBy(pid,role, agid,type,ll);
    }


    public NamedWasDerivedFrom newNamedWasDerivedFrom(NamedWasDerivedFrom g) {
        return newNamedWasDerivedFrom(g.getEffect(),
                                      g.getCause(),
                                      g.getType(),
                                      g.getAccount());
    }

    public WasDerivedFrom newWasDerivedFrom(WasDerivedFrom g) {
        if (g instanceof NamedWasDerivedFrom) {
            return newNamedWasDerivedFrom((NamedWasDerivedFrom) g);
        } else {
            return newWasDerivedFrom(g.getEffect(),
                                     g.getCause(),
                                     g.getAccount());
        }
    }

    public ArtifactExt newArtifactExt(String id,
                                      Collection<Account> accounts,
                                      Object value,
                                      List<Object> any) {
        ArtifactExt res=of.createArtifactExt();
        res.setId(id);
        if ((accounts !=null) && (accounts.size()!=0)) {
            LinkedList ll=new LinkedList();
            for (Account acc: accounts) {
                ll.add(newAccountId(acc));
            }
            res.getAccount().addAll(ll);
        }
        res.setValue(value);
        if (any!=null) {
            res.getAny().addAll(any);
        }
        return res;
    }


            
}

