package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;
import org.openprovenance.rdf.Annotable;

import javax.xml.namespace.QName;
import org.openrdf.elmo.ElmoManager;

public class RdfAnnotation extends org.openprovenance.model.Annotation implements HasFacade {

    ElmoManager manager;
    String prefix;
    QName qname;

    public RdfAnnotation(ElmoManager manager, QName qname) {
        this.manager=manager;
        this.qname=qname;
        this.prefix=qname.getNamespaceURI();
        super.setId(qname.getLocalPart());
    }
    


    public RdfAnnotation(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
    }

    public void setId(String value) {
        super.setId(value);
        qname = new QName(prefix, value);
        manager.designate(qname, org.openprovenance.rdf.Annotation.class);
    }

    public void setLocalSubject(Object value) {
        super.setLocalSubject(value);
        Annotable ann=(Annotable) ((HasFacade)value).findMyFacade();
        ann.getAnnotations().add(findMyFacade());
    }

    public QName getQName() {
        return qname;
    }

    public org.openprovenance.rdf.Annotation findMyFacade() {
        org.openprovenance.rdf.Annotation r=(org.openprovenance.rdf.Annotation)manager.find(getQName());
        return r;
    }


        
    public void setAnnotations(java.util.Set<? extends org.openprovenance.rdf.Annotation> ann) {
        throw new UnsupportedOperationException();
    }

    public java.util.Set<org.openprovenance.rdf.Annotation> getAnnotations() {
        throw new UnsupportedOperationException();
    }


}
