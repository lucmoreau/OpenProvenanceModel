package org.openprovenance.model.extension;
import java.util.Collection;
import java.util.Arrays;
import java.util.LinkedList;

import org.openprovenance.model.OPMFactory;
import org.openprovenance.model.ArtifactId;
import org.openprovenance.model.Artifact;
import org.openprovenance.model.AccountId;
import org.openprovenance.model.WasDerivedFrom;
import org.openprovenance.model.Account;
import org.openprovenance.model.NamedWasDerivedFrom;

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

            
}

