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

import java.io.FileOutputStream;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;

public class Signer extends com.uprovenance.util.security.Signature implements SignerFunctionality {

    static OPMFactory oFactory=new OPMFactory();

    public Signer() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    }

    String signer;
    
    public Signer(String keyStoreType,
                  String keyStoreName,
                  String keyStorePassword,
                  String privateKeyAlias,
                  String privateKeyPassword,
                  String simpleName)
        throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException,
               ClassNotFoundException, InstantiationException, IllegalAccessException {
        super(keyStoreType,
              keyStoreName,
              keyStorePassword,
              privateKeyAlias,
              privateKeyPassword);
        if (simpleName!=null) {
            signer=simpleName;
        } else {
            signer=getDistinguishedName();
        }
                                  
    }

    public Signer(KeyStore ks,
                  String privateKeyAlias,
                  String privateKeyPassword,
                  String simpleName)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException, KeyStoreException {
        super(ks,
              privateKeyAlias,
              privateKeyPassword);
        if (simpleName!=null) {
            signer=simpleName;
        } else {
            signer=getDistinguishedName();
        }
    }


    public boolean sign(OPMGraph oGraph) throws javax.xml.bind.JAXBException, Exception {
        if (oFactory.getSignature(oGraph)!=null) return false;

        // create the opmSig
        Signature opmSig=oFactory.newSignature();
        opmSig.setSigner(signer);
        oFactory.addAnnotation(oGraph,opmSig);

        OPMSerialiser pserial=OPMSerialiser.getThreadOPMSerialiser();
        Document doc=pserial.serialiseOPMGraph(oGraph);

        NodeList anchor = 
            doc.getElementsByTagNameNS("http://openprovenance.org/model/v1.1.a", "signature");
        // System.out.println("Found anchor " + anchor);

        DOMResult result=new DOMResult(); // may be better to use a saxresult!
        generateEnveloped(doc,anchor.item(0),result);
        //generateEnveloped(doc,result);
        Node theNode=result.getNode();
        Document doc2=(Document)theNode;
        //debug
        serializeToXML(doc2,"target/signature0.xml");
        NodeList nl = 
            doc2.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
        Node theSignature=nl.item(0);
        // orphaning the signature
        Node removedSignature=theSignature.getParentNode().removeChild(theSignature);

        // insert the XML signature inside the opmSig
        opmSig.setAny(removedSignature);
        //oGraph.setAny(removedSignature);
        return true;
    }

    void serializeToXML(Document node, String filename) throws java.io.IOException, java.io.FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(filename);
        // XERCES 1 or 2 additionnal classes.
        OutputFormat of = new OutputFormat("XML","UTF-8",true);
        of.setIndent(1);
        of.setIndenting(true);
        //of.setDoctype(null,"users.dtd");
        XMLSerializer serializer = new XMLSerializer(fos,null);
        // As a DOM Serializer
        serializer.asDOMSerializer().serialize( node );
        fos.close();
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
            String dn2=((Signature)oFactory.getSignature(oGraph)).getSigner();
            System.out.println("** signature found DN " + dn);
            System.out.println("** signature found DN " + dn2);
            sameDN=dn.equals(dn2);
        }
        return validate(theSignature) && sameDN;
    }


}
