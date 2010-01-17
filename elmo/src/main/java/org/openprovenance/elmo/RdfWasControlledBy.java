package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;
import org.openprovenance.rdf.Role;

import javax.xml.namespace.QName;

import org.openrdf.elmo.ElmoManager;

public class RdfWasControlledBy extends org.openprovenance.model.WasControlledBy implements HasFacade {
    String prefix;
    ElmoManager manager;
    QName qname;

    public RdfWasControlledBy(ElmoManager manager, QName qname) {
        this.manager=manager;
        this.qname=qname;
        this.prefix=qname.getNamespaceURI();
        super.setId(qname.getLocalPart());
    }


    public RdfWasControlledBy(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
    }

    public void setId(String value) {
        super.setId(value);
        qname = new QName(prefix, value);
        manager.designate(qname, org.openprovenance.rdf.WasControlledBy.class);
    }

    public QName getQName() {
        return qname;
    }

    public void setEffect(org.openprovenance.model.ProcessRef value) {
        super.setEffect(value);
        QName q=((RdfProcess)(value.getRef())).getQName();
        org.openprovenance.rdf.Process p=(org.openprovenance.rdf.Process)manager.find(q);
        org.openprovenance.rdf.WasControlledBy c=findMyFacade();
        c.setEffect(p);
    }

    public void setCause(org.openprovenance.model.AgentRef value) {
        super.setCause(value);
        QName q=((RdfAgent)(value.getRef())).getQName();
        org.openprovenance.rdf.Agent ag=(org.openprovenance.rdf.Agent)manager.find(q);
        org.openprovenance.rdf.WasControlledBy c=findMyFacade();
        c.setCause(ag);
    }

    public org.openprovenance.rdf.WasControlledBy findMyFacade() {
        org.openprovenance.rdf.WasControlledBy c=(org.openprovenance.rdf.WasControlledBy)manager.find(getQName());
        return c;
    }


    public void setRole(org.openprovenance.model.Role value) {
        super.setRole(value);
        if (value!=null) {
            QName q=((RdfRole)value).getQName();
            org.openprovenance.rdf.Role r=(org.openprovenance.rdf.Role)manager.find(q);
            org.openprovenance.rdf.WasControlledBy c=findMyFacade();
            c.setRole(r);
        }
    }


    public void setFields(org.openprovenance.model.AgentRef cause,
                          org.openprovenance.model.ProcessRef effect,
                          org.openprovenance.model.Role role) {
        super.setCause(cause);
        super.setEffect(effect);
        super.setRole(role);
    }


    public void setStartTime(org.openprovenance.model.OTime otime) {
        super.setStartTime(otime);
        org.openprovenance.rdf.OTime time=(org.openprovenance.rdf.OTime)((HasFacade)(otime)).findMyFacade();
        org.openprovenance.rdf.WasControlledBy wcb=(org.openprovenance.rdf.WasControlledBy)manager.find(getQName());
        wcb.setStartTime(time);
    }



    public void setEndTime(org.openprovenance.model.OTime otime) {
        super.setEndTime(otime);
        org.openprovenance.rdf.OTime time=(org.openprovenance.rdf.OTime)((HasFacade)(otime)).findMyFacade();
        org.openprovenance.rdf.WasControlledBy wcb=(org.openprovenance.rdf.WasControlledBy)manager.find(getQName());
        wcb.setEndTime(time);
    }




}
