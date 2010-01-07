package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;
import org.openprovenance.rdf.Role;

import javax.xml.namespace.QName;

import org.openrdf.elmo.ElmoManager;

public class RdfWasControlledBy extends org.openprovenance.model.WasControlledBy implements org.openprovenance.rdf.WasControlledBy, HasFacade {
    String prefix;
    ElmoManager manager;
    QName qname;

    public RdfWasControlledBy(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
    }

    public void setId(String value) {
        super.setId(value);
        qname = new QName(prefix, value);
        manager.designate(qname, org.openprovenance.rdf.WasControlledBy.class);
    }

    public QName getQName() {
        return qname;
    }

    public void setEffect(org.openprovenance.model.ProcessRef value) {
        super.setEffect(value);
        QName q=((RdfProcess)(value.getRef())).getQName();
        org.openprovenance.rdf.Process p=(org.openprovenance.rdf.Process)manager.find(q);
        org.openprovenance.rdf.WasControlledBy c=findMyFacade();
        c.getEffects().add(p);
    }

    public void setCause(org.openprovenance.model.AgentRef value) {
        super.setCause(value);
        QName q=((RdfAgent)(value.getRef())).getQName();
        org.openprovenance.rdf.Agent ag=(org.openprovenance.rdf.Agent)manager.find(q);
        org.openprovenance.rdf.WasControlledBy c=findMyFacade();
        c.getCauses().add(ag);
    }

    public org.openprovenance.rdf.WasControlledBy findMyFacade() {
        org.openprovenance.rdf.WasControlledBy c=(org.openprovenance.rdf.WasControlledBy)manager.find(getQName());
        return c;
    }


    public void setRole(org.openprovenance.model.Role value) {
        super.setRole(value);
        if (value!=null) {
            QName q=((RdfRole)value).getQName();
            org.openprovenance.rdf.Role r=(org.openprovenance.rdf.Role)manager.find(q);
            org.openprovenance.rdf.WasControlledBy c=findMyFacade();
            c.getHasRole().add(r);
        }
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
        

    public Set<Role> getHasRole() {
        throw new UnsupportedOperationException();
    }

    public void setHasRole(Set<? extends Role> accs) {
        for (Role acc: accs) {
            //getRole().add(acc.getRef());
            throw new UnsupportedOperationException();
        }
    }


}
