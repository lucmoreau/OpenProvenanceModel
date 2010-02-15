package org.openprovenance.elmo;
import java.util.Set;
import java.net.URI;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.namespace.QName;
import org.openrdf.elmo.ElmoManager;


public class RdfReference extends org.openprovenance.model.Reference implements HasFacade {

    ElmoManager manager;
    String prefix;
    QName qname;

    static int count=0;
    
    public RdfReference(ElmoManager manager, QName qname) {
        this.manager=manager;
        this.qname=qname;
        this.prefix=qname.getNamespaceURI();
        setId(qname.getLocalPart());
    }
    

    public RdfReference(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
        setId("rf_" + (count++));
    }

    public void setId(String value) {
        qname = new QName(prefix, value);
        manager.designate(qname, org.openprovenance.rdf.Reference.class);
    }


    public void setLocation(String location) {
        super.setLocation(location);
        org.openprovenance.rdf.Reference r=findMyFacade();
        r.setLocation(URI.create(location));
    }


    public void setEncoding(String encoding) {
        super.setEncoding(encoding);
        org.openprovenance.rdf.Reference r=findMyFacade();
        r.setEncoding2(URI.create(encoding));
    }

    public void setDigest(String digest) {
        super.setDigest(digest);
        org.openprovenance.rdf.Reference r=findMyFacade();
        r.setDigest(digest);
    }

    public QName getQName() {
        return qname;
    }

    public org.openprovenance.rdf.Reference findMyFacade() {
        org.openprovenance.rdf.Reference r=(org.openprovenance.rdf.Reference)manager.find(getQName());
        return r;
    }

    public void setFields(String location, String encoding, String digest) {
        super.setLocation(location);
        super.setEncoding(encoding);
        super.setDigest(digest);
    }

}



