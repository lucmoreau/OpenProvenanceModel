package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;
import javax.xml.namespace.QName;

import org.openrdf.elmo.ElmoManager;

public class RdfProcess extends org.openprovenance.model.Process implements org.openprovenance.rdf.Process, HasFacade {

    ElmoManager manager;
    String prefix;
    QName qname;

    public RdfProcess(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
    }

    public void setId(String value) {
        super.setId(value);
        qname = new QName(prefix, value);
        manager.designate(qname, org.openprovenance.rdf.Process.class);
    }

    public QName getQName() {
        return qname;
    }

    public org.openprovenance.rdf.Process findMyFacade() {
        org.openprovenance.rdf.Process p=(org.openprovenance.rdf.Process)manager.find(getQName());
        return p;
    }

    public void setAccounts(Set<? extends Account> accs) {
        for (Account acc: accs) {
            //getAccount().add(acc.getRef());
            throw new UnsupportedOperationException();
        }
    }

    public Set<Account> getAccounts() {
        throw new UnsupportedOperationException();
    }
        

	public Set<String> getNames() {
        throw new UnsupportedOperationException();
    }

	public void setNames(Set<? extends String> names) {
        throw new UnsupportedOperationException();
    }

}
