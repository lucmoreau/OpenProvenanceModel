package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;
import org.openprovenance.rdf.Annotable;

import javax.xml.namespace.QName;
import org.openrdf.elmo.ElmoManager;

public class RdfEmbeddedAnnotation extends org.openprovenance.model.EmbeddedAnnotation implements HasFacade {

    ElmoManager manager;
    String prefix;
    QName qname;

    public RdfEmbeddedAnnotation(ElmoManager manager, QName qname) {
        this.manager=manager;
        this.qname=qname;
        this.prefix=qname.getNamespaceURI();
        super.setId(qname.getLocalPart());
    }
    


    public RdfEmbeddedAnnotation(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
    }

    public void setId(String value) {
        super.setId(value);
        qname = new QName(prefix, value);
        manager.designate(qname, org.openprovenance.rdf.Annotation.class);
    }

    public QName getQName() {
        return qname;
    }

    public org.openprovenance.rdf.Annotation findMyFacade() {
        org.openprovenance.rdf.Annotation r=(org.openprovenance.rdf.Annotation)manager.find(getQName());
        return r;
    }


        

}
