package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;
import org.openprovenance.rdf.Role;

import javax.xml.namespace.QName;

import org.openrdf.elmo.ElmoManager;

public class RdfWasGeneratedBy extends org.openprovenance.model.WasGeneratedBy implements org.openprovenance.rdf.WasGeneratedBy, HasFacade {
    String prefix;
    ElmoManager manager;
    QName qname;

    public RdfWasGeneratedBy(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
    }

    public void setId(String value) {
        super.setId(value);
        qname = new QName(prefix, value);
        manager.designate(qname, org.openprovenance.rdf.WasGeneratedBy.class);
    }

    public QName getQName() {
        return qname;
    }

    public void setEffect(org.openprovenance.model.ArtifactRef value) {
        super.setEffect(value);
        QName q=((RdfArtifact)(value.getRef())).getQName();
        org.openprovenance.rdf.Artifact a=(org.openprovenance.rdf.Artifact)manager.find(q);
        org.openprovenance.rdf.WasGeneratedBy g=findMyFacade();
        g.getEffects().add(a);
    }

    public org.openprovenance.rdf.WasGeneratedBy findMyFacade() {
        org.openprovenance.rdf.WasGeneratedBy g=(org.openprovenance.rdf.WasGeneratedBy)manager.find(getQName());
        return g;
    }

    public void setCause(org.openprovenance.model.ProcessRef value) {
        super.setCause(value);
        QName q=((RdfProcess)(value.getRef())).getQName();
        org.openprovenance.rdf.Process p=(org.openprovenance.rdf.Process)manager.find(q);
        System.out.println("=============> " + getQName());
        System.out.println("=============>p " + q);
        org.openprovenance.rdf.WasGeneratedBy g=findMyFacade();
        g.getCauses().add(p);
    }

    public void setRole(org.openprovenance.model.Role value) {
        super.setRole(value);
        if (value!=null) {
            QName q=((RdfRole)value).getQName();
            org.openprovenance.rdf.Role r=(org.openprovenance.rdf.Role)manager.find(q);
            org.openprovenance.rdf.WasGeneratedBy g=findMyFacade();
            g.getRoles().add(r);
        }
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
        
    public void setRoles(Set<? extends Role> accs) {
        for (Role acc: accs) {
            throw new UnsupportedOperationException();
        }
    }

    public Set<Role> getRoles() {
        throw new UnsupportedOperationException();
    }


}
