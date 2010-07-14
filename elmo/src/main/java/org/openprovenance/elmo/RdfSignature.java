package org.openprovenance.elmo;
import java.util.Set;
import java.net.URI;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.namespace.QName;
import org.openrdf.elmo.ElmoManager;


public class RdfSignature extends org.openprovenance.model.Signature implements HasFacade {

    ElmoManager manager;
    String prefix;
    QName qname;

    static int count=0;
    
    public RdfSignature(ElmoManager manager, QName qname) {
        this.manager=manager;
        this.qname=qname;
        this.prefix=qname.getNamespaceURI();
        setId(qname.getLocalPart());
    }
    

    public RdfSignature(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
        setId("sig_" + (count++));
    }

    public void setId(String value) {
        qname = new QName(prefix, value);
        manager.designate(qname, org.openprovenance.rdf.Signature.class);
    }



    public void setAny(Object value) {
        super.setAny(value);
        org.openprovenance.rdf.Signature r=findMyFacade();
        Element element=(Element) value;
        //TODO make signature orphan, and elmeent in a dummy
        Document doc=RdfOPMFactory.builder.newDocument();
        String APP_NS="http://openprovenance.org/ignore";
        //Element el=doc.createElementNS(APP_NS,"app:ignore");
        Element el=(Element)doc.importNode(element,true);
        doc.appendChild(el);
        System.out.println("---> setAny  " + el);
        r.setSig(new XMLLiteral(doc));
    }


    public void setSigner(String signer) {
        super.setSigner(signer);
        org.openprovenance.rdf.Signature r=findMyFacade();
        r.setSigner(signer);
    }

    public QName getQName() {
        return qname;
    }

    public org.openprovenance.rdf.Signature findMyFacade() {
        org.openprovenance.rdf.Signature r=(org.openprovenance.rdf.Signature)manager.find(getQName());
        return r;
    }

    public void setFields(Object sig, String signer) {
        super.setAny(sig);
        super.setSigner(signer);
    }

}



