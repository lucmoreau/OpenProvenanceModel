package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;

import javax.xml.namespace.QName;
import org.openrdf.elmo.ElmoManager;

public class RdfAgent extends org.openprovenance.model.Agent implements HasFacade {

    ElmoManager manager;
    String prefix;
    QName qname;

    public RdfAgent(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
    }

    public RdfAgent(ElmoManager manager, QName qname) {
        this.manager=manager;
        this.qname=qname;
        this.prefix=qname.getNamespaceURI();
        super.setId(qname.getLocalPart());
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


    public void setAccounts(Set<? extends Account> accs) {
        for (Account acc: accs) {
            //getAccount().add(acc.getRef());
            throw new UnsupportedOperationException();
        }
    }

    public Set<Account> getAccounts() {
        throw new UnsupportedOperationException();
    }
        

    public void setAnnotations(java.util.Set<? extends org.openprovenance.rdf.Annotation> ann) {
        throw new UnsupportedOperationException();
    }



    public java.util.Set<org.openprovenance.rdf.Annotation> getAnnotations() {
        throw new UnsupportedOperationException();
    }

}
