package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;

import javax.xml.namespace.QName;
import org.openrdf.elmo.ElmoManager;

public class RdfAgent extends org.openprovenance.model.Agent implements org.openprovenance.rdf.Agent, HasFacade {

    ElmoManager manager;
    String prefix;
    QName qname;

    public RdfAgent(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
    }

    public void setId(String value) {
        super.setId(value);
        qname = new QName(prefix, value);
        manager.designate(qname, org.openprovenance.rdf.Agent.class);
    }

    public QName getQName() {
        return qname;
    }

    public org.openprovenance.rdf.Agent findMyFacade() {
        org.openprovenance.rdf.Agent ag=(org.openprovenance.rdf.Agent)manager.find(getQName());
        return ag;
    }


    public void setHasAccount(Set<? extends Account> accs) {
        for (Account acc: accs) {
            //getAccount().add(acc.getRef());
            throw new UnsupportedOperationException();
        }
    }

    public Set<Account> getHasAccount() {
        throw new UnsupportedOperationException();
    }
        

	public Set<String> getNames() {
        throw new UnsupportedOperationException();
    }

	public void setNames(Set<? extends String> names) {
        throw new UnsupportedOperationException();
    }

}
