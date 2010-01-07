package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;
import org.openprovenance.rdf.AnnotationOrEdgeOrNode;

import javax.xml.namespace.QName;
import org.openrdf.elmo.ElmoManager;

public class RdfAnnotation extends org.openprovenance.model.Annotation implements org.openprovenance.rdf.Annotation, HasFacade {

    ElmoManager manager;
    String prefix;
    QName qname;

    public RdfAnnotation(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
    }

    public void setId(String value) {
        super.setId(value);
        qname = new QName(prefix, value);
        manager.designate(qname, org.openprovenance.rdf.Annotation.class);
    }

//     public void setValue(String value) {
//         super.setValue(value);
//         org.openprovenance.rdf.Annotation r=findMyFacade();
//         r.getValues().add(value);
//     }

    public void setLocalSubject(Object value) {
        super.setLocalSubject(value);
        AnnotationOrEdgeOrNode ann=(AnnotationOrEdgeOrNode) ((HasFacade)value).findMyFacade();
        ann.getAnnotations().add(this);
    }

    public QName getQName() {
        return qname;
    }

    public org.openprovenance.rdf.Annotation findMyFacade() {
        org.openprovenance.rdf.Annotation r=(org.openprovenance.rdf.Annotation)manager.find(getQName());
        return r;
    }


    public void setProperties(Set<? extends org.openprovenance.rdf.Property> values) {
         throw new UnsupportedOperationException();
     }

     public Set<org.openprovenance.rdf.Property> getProperties() {
         return null;
     }

// 	public Set<String> getNames() {
//         throw new UnsupportedOperationException();
//     }

// 	public void setNames(Set<? extends String> names) {
//         throw new UnsupportedOperationException();
//     }

    public void setAccounts(Set<? extends Account> accs) {
        for (Account acc: accs) {
            //getAccount().add(acc.getRef());
            throw new UnsupportedOperationException();
        }
    }

    public Set<Account> getAccounts() {
        return null;
    }
        
    public void setAnnotations(java.util.Set<? extends org.openprovenance.rdf.Annotation> ann) {
        throw new UnsupportedOperationException();
    }



    public java.util.Set<org.openprovenance.rdf.Annotation> getAnnotations() {
        return null;
    }


}
