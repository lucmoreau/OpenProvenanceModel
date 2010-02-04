package org.openprovenance.elmo;
import java.util.Set;
import java.net.URI;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.namespace.QName;
import org.openrdf.elmo.ElmoManager;


public class RdfValue extends org.openprovenance.model.Value implements HasFacade {

    ElmoManager manager;
    String prefix;
    QName qname;

    static int count=0;
    
    public RdfValue(ElmoManager manager, QName qname) {
        this.manager=manager;
        this.qname=qname;
        this.prefix=qname.getNamespaceURI();
        setId(qname.getLocalPart());
    }
    

    public RdfValue(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
        setId("av_" + (count++));
    }

    public void setId(String value) {
        qname = new QName(prefix, value);
        manager.designate(qname, org.openprovenance.rdf.AValue.class);
    }



    public void setContent(Object value) {
        super.setContent(value);
        org.openprovenance.rdf.AValue r=findMyFacade();
        if (value instanceof Element) {
            r.setContent(new XMLLiteral(((Element)value).getOwnerDocument()));
        } else {
            r.setContent(value);
        }
    }


    public void setEncoding(String encoding) {
        super.setEncoding(encoding);
        org.openprovenance.rdf.AValue r=findMyFacade();
        r.setEncoding(URI.create(encoding));
    }

    public QName getQName() {
        return qname;
    }

    public org.openprovenance.rdf.AValue findMyFacade() {
        org.openprovenance.rdf.AValue r=(org.openprovenance.rdf.AValue)manager.find(getQName());
        return r;
    }

    public void setFields(Object content, String encoding) {
        super.setContent(content);
        super.setEncoding(encoding);
    }

}



