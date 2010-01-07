package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;
import org.openprovenance.rdf.Role;

import javax.xml.namespace.QName;

import org.openrdf.elmo.ElmoManager;

public class RdfWasDerivedFrom extends org.openprovenance.model.WasDerivedFrom implements org.openprovenance.rdf.WasDerivedFrom, HasFacade {
    String prefix;
    ElmoManager manager;
    QName qname;

    public RdfWasDerivedFrom(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
    }

    public void setId(String value) {
        super.setId(value);
        qname = new QName(prefix, value);
        manager.designate(qname, org.openprovenance.rdf.WasDerivedFrom.class);
    }

    public QName getQName() {
        return qname;
    }

    public org.openprovenance.rdf.WasDerivedFrom findMyFacade() {
        org.openprovenance.rdf.WasDerivedFrom c=(org.openprovenance.rdf.WasDerivedFrom)manager.find(getQName());
        return c;
    }


    public void setEffect(org.openprovenance.model.ArtifactRef value) {
        super.setEffect(value);
        QName q=((RdfArtifact)(value.getRef())).getQName();
        org.openprovenance.rdf.Artifact a=(org.openprovenance.rdf.Artifact)manager.find(q);
        org.openprovenance.rdf.WasDerivedFrom d=findMyFacade();
        d.getEffects().add(a);
    }

    public void setCause(org.openprovenance.model.ArtifactRef value) {
        super.setCause(value);
        QName q=((RdfArtifact)(value.getRef())).getQName();
        org.openprovenance.rdf.Artifact a=(org.openprovenance.rdf.Artifact)manager.find(q);
        org.openprovenance.rdf.WasDerivedFrom d=findMyFacade();
        d.getCauses().add(a);
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
        
    public void setGeneratedRole(Set<? extends Role> accs) {
        for (Role acc: accs) {
            throw new UnsupportedOperationException();
        }
    }

    public Set<Role> getGenerateRole() {
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
