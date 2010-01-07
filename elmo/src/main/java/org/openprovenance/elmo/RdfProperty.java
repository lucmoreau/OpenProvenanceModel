package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;

import javax.xml.namespace.QName;
import org.openrdf.elmo.ElmoManager;

public class RdfProperty extends org.openprovenance.model.Property implements org.openprovenance.rdf.Property, HasFacade {

    ElmoManager manager;
    String prefix;
    QName qname;

    static int count=0;
    
    public RdfProperty(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
        setId("p" + (count++));
    }

    public void setId(String value) {
        qname = new QName(prefix, value);
        manager.designate(qname, org.openprovenance.rdf.Property.class);
    }



    public void setUri(String value) {
        super.setUri(value);
        org.openprovenance.rdf.Property r=findMyFacade();
        r.getUris().add(value);
    }


    public void setValue(Object value) {
        super.setValue(value);
        org.openprovenance.rdf.Property r=findMyFacade();
        //TODO: currently, only strings supported
        r.getValues().add((String)value);
    }

    public QName getQName() {
        return qname;
    }

    public org.openprovenance.rdf.Property findMyFacade() {
        org.openprovenance.rdf.Property r=(org.openprovenance.rdf.Property)manager.find(getQName());
        return r;
    }


    public void setValues(Set<? extends String> values) {
        throw new UnsupportedOperationException();
    }

    public Set<String> getValues() {
        throw new UnsupportedOperationException();
    }

 	public Set<String> getUris() {
        throw new UnsupportedOperationException();
    }

 	public void setUris(Set<? extends String> uris) {
        throw new UnsupportedOperationException();
    }

}
