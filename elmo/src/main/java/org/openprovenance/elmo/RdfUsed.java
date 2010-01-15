package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;
import org.openprovenance.rdf.Role;

import javax.xml.namespace.QName;

import org.openrdf.elmo.ElmoManager;

public class RdfUsed extends org.openprovenance.model.Used implements HasFacade {
    String prefix;
    ElmoManager manager;
    QName qname;

    public RdfUsed(ElmoManager manager, QName qname) {
        this.manager=manager;
        this.qname=qname;
        this.prefix=qname.getNamespaceURI();
        super.setId(qname.getLocalPart());
    }


    public RdfUsed(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
    }

    public void setId(String value) {
        super.setId(value);
        qname = new QName(prefix, value);
        manager.designate(qname, org.openprovenance.rdf.Used.class);
    }

    public QName getQName() {
        return qname;
    }

    public void setEffect(org.openprovenance.model.ProcessRef value) {
        super.setEffect(value);
        QName q=((RdfProcess)(value.getRef())).getQName();
        org.openprovenance.rdf.Process p=(org.openprovenance.rdf.Process)manager.find(q);
        org.openprovenance.rdf.Used u=(org.openprovenance.rdf.Used)manager.find(getQName());
        u.setEffect(p);
    }

    public org.openprovenance.rdf.Used findMyFacade() {
        org.openprovenance.rdf.Used u=(org.openprovenance.rdf.Used)manager.find(getQName());
        return u;
    }

    public void setCause(org.openprovenance.model.ArtifactRef value) {
        super.setCause(value);
        QName q=((RdfArtifact)(value.getRef())).getQName();
        org.openprovenance.rdf.Artifact a=(org.openprovenance.rdf.Artifact)manager.find(q);
        org.openprovenance.rdf.Used u=findMyFacade();
        u.setCause(a);
    }

    public void setRole(org.openprovenance.model.Role value) {
        super.setRole(value);
        if (value!=null) {
            QName q=((RdfRole)value).getQName();
            org.openprovenance.rdf.Role r=(org.openprovenance.rdf.Role)manager.find(q);
            org.openprovenance.rdf.Used u=findMyFacade();
            u.setRole(r);
        }
    }

    public void setFields(org.openprovenance.model.ArtifactRef cause,
                          org.openprovenance.model.ProcessRef effect,
                          org.openprovenance.model.Role role) {
        super.setCause(cause);
        super.setEffect(effect);
        super.setRole(role);
    }

    public void setTime(org.openprovenance.model.OTime otime) {
        super.setTime(otime);
        org.openprovenance.rdf.EventEdge u=(org.openprovenance.rdf.Used)manager.find(getQName());



        //u.setTime(otime.toRdf())
    }



}
