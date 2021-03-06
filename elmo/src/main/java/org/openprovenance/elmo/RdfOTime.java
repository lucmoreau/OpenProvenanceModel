package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import org.openrdf.elmo.ElmoManager;

public class RdfOTime extends org.openprovenance.model.OTime implements HasFacade {

    ElmoManager manager;
    String prefix;
    QName qname;

    static int count=0;
    
    public RdfOTime(ElmoManager manager, QName qname) {
        this.manager=manager;
        this.qname=qname;
        this.prefix=qname.getNamespaceURI();
        setId(qname.getLocalPart());
    }
    

    public RdfOTime(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
        setId("ti_" + (count++));
    }

    //    org.openprovenance.rdf.OTime facade;

    public void setId(String value) {
        qname = new QName(prefix, value);
        manager.designate(qname, org.openprovenance.rdf.OTime.class);

        // would be nice to make this an anonymous node!
        //facade=manager.create(org.openprovenance.rdf.OTime.class);
    }



    public void setNoEarlierThan(XMLGregorianCalendar value) {
        super.setNoEarlierThan(value);
        org.openprovenance.rdf.OTime r=findMyFacade();
        r.setNoEarlierThan(value);
    }


    public void setNoLaterThan(XMLGregorianCalendar value) {
        super.setNoLaterThan(value);
        org.openprovenance.rdf.OTime r=findMyFacade();
        r.setNoLaterThan(value);
    }

    public void setExactlyAt(XMLGregorianCalendar value) {
        super.setExactlyAt(value);
        org.openprovenance.rdf.OTime r=findMyFacade();
        r.setExactlyAt(value);
    }

    public QName getQName() {
        return qname;
    }

    public org.openprovenance.rdf.OTime findMyFacade() {
        org.openprovenance.rdf.OTime r=(org.openprovenance.rdf.OTime)manager.find(getQName());
        return r; //facade
    }

    public void setFields(XMLGregorianCalendar begin, XMLGregorianCalendar end, XMLGregorianCalendar at) {
        super.setNoEarlierThan(begin);
        super.setNoLaterThan(end);
        super.setExactlyAt(at);
    }

}



