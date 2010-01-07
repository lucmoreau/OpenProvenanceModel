package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;

import javax.xml.namespace.QName;
import org.openrdf.elmo.ElmoManager;

public class RdfRole extends org.openprovenance.model.Role implements org.openprovenance.rdf.Role {

    ElmoManager manager;
    String prefix;
    QName qname;

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
        r.getValues().add(value);
    }

    public QName getQName() {
        return qname;
    }

    public org.openprovenance.rdf.Role findMyFacade() {
        org.openprovenance.rdf.Role r=(org.openprovenance.rdf.Role)manager.find(getQName());
        return r;
    }


    public void setValues(Set<? extends java.lang.String> values) {
        throw new UnsupportedOperationException();
    }

    public Set<String> getValues() {
        throw new UnsupportedOperationException();
    }

	public Set<String> getNames() {
        throw new UnsupportedOperationException();
    }

	public void setNames(Set<? extends String> names) {
        throw new UnsupportedOperationException();
    }

}
