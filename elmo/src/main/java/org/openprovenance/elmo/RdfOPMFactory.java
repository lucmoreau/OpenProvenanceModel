package org.openprovenance.elmo;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;

import org.openprovenance.model.HasAccounts;
import org.openprovenance.model.AccountRef;

import org.openprovenance.rdf.EdgeOrNode;

public class RdfOPMFactory extends org.openprovenance.model.OPMFactory {


    public RdfOPMFactory(org.openprovenance.model.ObjectFactory o) {
        super(o);
    }

    public void addAccounts(HasAccounts element, Collection<AccountRef> accounts) {
        System.out.println("add accounts to ");
        if (element instanceof EdgeOrNode) {
            HasFacade facade=(HasFacade) element;
            Object o=facade.findMyFacade();
            EdgeOrNode el=(EdgeOrNode) o;

            Set set=new HashSet();
            for (AccountRef accr: accounts) {
                set.add((org.openprovenance.rdf.Account)accr.getRef());
            }
            //el.getHasAccount().addAll(set);
            el.setHasAccount(set);
        }
    }

}
