package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;
import org.openprovenance.rdf.Role;

import javax.xml.namespace.QName;

import org.openrdf.elmo.ElmoManager;

public class RdfWasGeneratedBy extends org.openprovenance.model.WasGeneratedBy implements HasFacade {
    String prefix;
    ElmoManager manager;
    QName qname;


    public RdfWasGeneratedBy(ElmoManager manager, QName qname) {
        this.manager=manager;
        this.qname=qname;
        this.prefix=qname.getNamespaceURI();
        super.setId(qname.getLocalPart());
    }


    public RdfWasGeneratedBy(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
    }

    public void setId(String value) {
        super.setId(value);
        qname = new QName(prefix, value);
        manager.designate(qname, org.openprovenance.rdf.WasGeneratedBy.class);
    }

    public QName getQName() {
        return qname;
    }

    public void setEffect(org.openprovenance.model.ArtifactRef value) {
        super.setEffect(value);
        QName q=((RdfArtifact)(value.getRef())).getQName();
        org.openprovenance.rdf.Artifact a=(org.openprovenance.rdf.Artifact)manager.find(q);
        org.openprovenance.rdf.WasGeneratedBy g=findMyFacade();
        g.setEffect(a);
        g.setEffectWasGeneratedBy(a); //to allow OWL chain property inferences
    }

    public org.openprovenance.rdf.WasGeneratedBy findMyFacade() {
        org.openprovenance.rdf.WasGeneratedBy g=(org.openprovenance.rdf.WasGeneratedBy)manager.find(getQName());
        return g;
    }

    public void setCause(org.openprovenance.model.ProcessRef value) {
        super.setCause(value);
        QName q=((RdfProcess)(value.getRef())).getQName();
        org.openprovenance.rdf.Process p=(org.openprovenance.rdf.Process)manager.find(q);
        org.openprovenance.rdf.WasGeneratedBy g=findMyFacade();
        g.setCause(p);
        g.setCauseWasGeneratedBy(p); //to allow OWL chain property inferences
    }

    public void setRole(org.openprovenance.model.Role value) {
        super.setRole(value);
        if (value!=null) {
            QName q=((RdfRole)value).getQName();
            org.openprovenance.rdf.Role r=(org.openprovenance.rdf.Role)manager.find(q);
            org.openprovenance.rdf.WasGeneratedBy g=findMyFacade();
            g.setRole(r);
        }
    }

    public void setFields(org.openprovenance.model.ProcessRef cause,
                          org.openprovenance.model.ArtifactRef effect,
                          org.openprovenance.model.Role role) {
        super.setCause(cause);
        super.setEffect(effect);
        super.setRole(role);
    }

    public void setTime(org.openprovenance.model.OTime otime) {
        super.setTime(otime);
        org.openprovenance.rdf.OTime time=(org.openprovenance.rdf.OTime)((HasFacade)(otime)).findMyFacade();
        org.openprovenance.rdf.EventEdge u=(org.openprovenance.rdf.WasGeneratedBy)manager.find(getQName());
        u.setTime(time);
    }



}
