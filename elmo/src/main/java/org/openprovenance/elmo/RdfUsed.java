package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;
import org.openprovenance.rdf.Role;

import javax.xml.namespace.QName;

import org.openrdf.elmo.ElmoManager;

public class RdfUsed extends org.openprovenance.model.Used implements org.openprovenance.rdf.Used {
    String prefix;
    ElmoManager manager;
    QName qname;

    public RdfUsed(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
    }

    public void setId(String value) {
        super.setId(value);
        qname = new QName(prefix, value);
        manager.designate(qname, org.openprovenance.rdf.Used.class);
    }

    public QName getQName() {
        return qname;
    }

    public void setEffect(org.openprovenance.model.ProcessRef value) {
        super.setEffect(value);
        QName q=((RdfProcess)(value.getRef())).getQName();
        org.openprovenance.rdf.Process p=(org.openprovenance.rdf.Process)manager.find(q);
        org.openprovenance.rdf.Used u=(org.openprovenance.rdf.Used)manager.find(getQName());
        u.getEffects().add(p);
    }


    public void setEdgeAccount(Set<? extends Account> accs) {
        for (Account acc: accs) {
            //getAccount().add(acc.getRef());
            throw new UnsupportedOperationException();
        }
    }

    public Set<Account> getEdgeAccount() {
        throw new UnsupportedOperationException();
    }


    public void setCauses(Set<? extends Node> accs) {
        throw new UnsupportedOperationException();
    }

    public Set<Node> getCauses() {
        throw new UnsupportedOperationException();
    }

	public Set<Node> getEffects() {
        throw new UnsupportedOperationException();
    }

	public void setEffects(Set<? extends Node> effects) {
        throw new UnsupportedOperationException();
    }
        
    public void setUsedRole(Set<? extends Role> accs) {
        for (Role acc: accs) {
            //getRole().add(acc.getRef());
            throw new UnsupportedOperationException();
        }
    }

    public Set<Role> getUsedRole() {
        throw new UnsupportedOperationException();
    }

    public void setEdgeRole(Set<? extends Role> accs) {
        for (Role acc: accs) {
            //getRole().add(acc.getRef());
            throw new UnsupportedOperationException();
        }
    }

    public Set<Role> getEdgeRole() {
        throw new UnsupportedOperationException();
    }
        


}
