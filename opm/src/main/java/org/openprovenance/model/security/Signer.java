package org.openprovenance.model.security;

import org.openprovenance.model.OPMGraph;
import org.openprovenance.model.Signature;
import org.openprovenance.model.OPMSerialiser;
import org.openprovenance.model.OPMFactory;



import javax.xml.crypto.dsig.XMLSignature;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.security.cert.CertificateException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.transform.dom.DOMResult;
import java.security.KeyStore;




public class Signer extends com.uprovenance.util.security.Signature implements SignerFunctionality {

    static OPMFactory oFactory=new OPMFactory();

    public Signer() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    }

    public Signer(String keyStoreType,
                  String keyStoreName,
                  String keyStorePassword,
                  String privateKeyAlias,
                  String privateKeyPassword)
        throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException,
               ClassNotFoundException, InstantiationException, IllegalAccessException {
        super(keyStoreType,
              keyStoreName,
              keyStorePassword,
              privateKeyAlias,
              privateKeyPassword);
    }

    public Signer(KeyStore ks,
                  String privateKeyAlias,
                  String privateKeyPassword)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        super(ks,
              privateKeyAlias,
              privateKeyPassword);
    }


    public boolean sign(OPMGraph oGraph) throws javax.xml.bind.JAXBException, Exception {
        if (oFactory.getSignature(oGraph)!=null) return false;

        OPMSerialiser pserial=OPMSerialiser.getThreadOPMSerialiser();
        Document doc=pserial.serialiseOPMGraph(oGraph);
        DOMResult result=new DOMResult(); // may be better to use a saxresult!
        generateEnveloped(doc,result);
        Node theNode=result.getNode();
        Document doc2=(Document)theNode;
        NodeList nl = 
            doc2.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
        Node theSignature=nl.item(0);
        // orphaning the signature
        Node removedSignature=theSignature.getParentNode().removeChild(theSignature);
        oFactory.addAnnotation(oGraph,oFactory.newSignature(removedSignature));
        return true;
    }

    public boolean validate(OPMGraph oGraph) throws javax.xml.bind.JAXBException, Exception {
        return validate(oGraph,true);
    }


    public boolean validate(OPMGraph oGraph, boolean checkDN) throws javax.xml.bind.JAXBException, Exception {
        OPMSerialiser pserial=OPMSerialiser.getThreadOPMSerialiser();
        Document doc=pserial.serialiseOPMGraph(oGraph);
        // Find Signature element
        NodeList nl = 
            doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
        Node theSignature=nl.item(0);
        boolean sameDN=true;
        if (checkDN) {
            // would be better with an xpath!
            NodeList kil = 
                ((Element)theSignature).getElementsByTagNameNS(XMLSignature.XMLNS, "KeyInfo");
            Element theKeyInfo=(Element)kil.item(0);
            NodeList x509dl = 
                theKeyInfo.getElementsByTagNameNS(XMLSignature.XMLNS, "X509Data");
            Element theX509Data=(Element)x509dl.item(0);
            NodeList snl = 
                theX509Data.getElementsByTagNameNS(XMLSignature.XMLNS, "X509SubjectName");
            Element x509SubjectName=(Element)snl.item(0);
            String dn=x509SubjectName.getTextContent();
            String dn2=((Signature)oFactory.getSignature(oGraph)).getDn();
            System.out.println("** signature found DN " + dn);
            System.out.println("** signature found DN " + dn2);
            sameDN=dn.equals(dn2);
        }
        return validate(theSignature) && sameDN;
    }


}
