package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;

import javax.xml.namespace.QName;
import org.openrdf.elmo.ElmoManager;

public class RdfRole extends org.openprovenance.model.Role implements HasFacade {

    ElmoManager manager;
    String prefix;
    QName qname;

    public RdfRole(ElmoManager manager, QName qname) {
        this.manager=manager;
        this.qname=qname;
        this.prefix=qname.getNamespaceURI();
        super.setId(qname.getLocalPart());
        //super.setValue((manager.find(qname));
    }

    public RdfRole(ElmoManager manager, QName qname, String value) {
        this.manager=manager;
        this.qname=qname;
        this.prefix=qname.getNamespaceURI();
        super.setId(qname.getLocalPart());
        super.setValue(value);
    }

    public RdfRole(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
    }

    public void setId(String value) {
        super.setId(value);
        qname = new QName(prefix, value);
        manager.designate(qname, org.openprovenance.rdf.Role.class);
    }

    public void setValue(String value) {
        super.setValue(value);
        org.openprovenance.rdf.Role r=findMyFacade();
        r.setValue(value);
    }

    public QName getQName() {
        return qname;
    }

    public org.openprovenance.rdf.Role findMyFacade() {
        org.openprovenance.rdf.Role r=(org.openprovenance.rdf.Role)manager.find(getQName());
        return r;
    }



    public void setAnnotations(java.util.Set<? extends org.openprovenance.rdf.Annotation> ann) {
        throw new UnsupportedOperationException();
    }

    public java.util.Set<org.openprovenance.rdf.Annotation> getAnnotations() {
        throw new UnsupportedOperationException();
    }


}
