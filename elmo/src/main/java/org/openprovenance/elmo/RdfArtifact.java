package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;

import javax.xml.namespace.QName;
import org.openrdf.elmo.ElmoManager;

public class RdfArtifact extends org.openprovenance.model.Artifact implements  HasFacade {

    ElmoManager manager;
    String prefix;
    QName qname;

    public RdfArtifact(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
    }

    public RdfArtifact(ElmoManager manager, QName qname) {
        this.manager=manager;
        this.qname=qname;
        this.prefix=qname.getNamespaceURI();
        super.setId(qname.getLocalPart());
    }
    
    public void setId(String value) {
        super.setId(value);
        qname = new QName(prefix, value);
        org.openprovenance.rdf.Artifact a0_ = (org.openprovenance.rdf.Artifact) manager.designate(qname, org.openprovenance.rdf.Artifact.class);
    }

    public QName getQName() {
        return qname;
    }

    public org.openprovenance.rdf.Artifact findMyFacade() {
        org.openprovenance.rdf.Artifact a=(org.openprovenance.rdf.Artifact)manager.find(getQName());
        return a;
    }


}
