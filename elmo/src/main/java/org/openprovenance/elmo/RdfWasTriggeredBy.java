package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;
import org.openprovenance.rdf.Role;

import javax.xml.namespace.QName;

import org.openrdf.elmo.ElmoManager;

public class RdfWasTriggeredBy extends org.openprovenance.model.WasTriggeredBy implements org.openprovenance.rdf.WasTriggeredBy, HasFacade {
    String prefix;
    ElmoManager manager;
    QName qname;

    public RdfWasTriggeredBy(ElmoManager manager, QName qname) {
        this.manager=manager;
        this.qname=qname;
        this.prefix=qname.getNamespaceURI();
        super.setId(qname.getLocalPart());
    }

    public RdfWasTriggeredBy(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
    }

    public void setId(String value) {
        super.setId(value);
        qname = new QName(prefix, value);
        manager.designate(qname, org.openprovenance.rdf.WasTriggeredBy.class);
    }

    public QName getQName() {
        return qname;
    }

    public org.openprovenance.rdf.WasTriggeredBy findMyFacade() {
        org.openprovenance.rdf.WasTriggeredBy c=(org.openprovenance.rdf.WasTriggeredBy)manager.find(getQName());
        return c;
    }


    public void setEffect(org.openprovenance.model.ProcessRef value) {
        super.setEffect(value);
        QName q=((RdfProcess)(value.getRef())).getQName();
        org.openprovenance.rdf.Process p=(org.openprovenance.rdf.Process)manager.find(q);
        org.openprovenance.rdf.WasTriggeredBy t=findMyFacade();
        t.getEffects().add(p);
    }

    public void setCause(org.openprovenance.model.ProcessRef value) {
        super.setCause(value);
        QName q=((RdfProcess)(value.getRef())).getQName();
        org.openprovenance.rdf.Process p=(org.openprovenance.rdf.Process)manager.find(q);
        org.openprovenance.rdf.WasTriggeredBy t=findMyFacade();
        t.getCauses().add(p);
    }


    public void setNodes(org.openprovenance.model.ProcessRef cause,
                         org.openprovenance.model.ProcessRef effect) {
        super.setCause(cause);
        super.setEffect(effect);
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
        
    public void setGeneratedRole(Set<? extends Role> accs) {
        for (Role acc: accs) {
            throw new UnsupportedOperationException();
        }
    }

    public Set<Role> getGeneratedRole() {
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

    public void setAnnotations(java.util.Set<? extends org.openprovenance.rdf.Annotation> ann) {
        throw new UnsupportedOperationException();
    }



    public java.util.Set<org.openprovenance.rdf.Annotation> getAnnotations() {
        throw new UnsupportedOperationException();
    }

        


}
