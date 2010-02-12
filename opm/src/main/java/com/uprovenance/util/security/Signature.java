package com.uprovenance.util.security;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignatureProperties;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLObject;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.crypto.dsig.spec.XPathFilterParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
   Code inspired from the examples accompanying the xmlsec library.
   This code attempts to abstract the different signature methods
   GenDetached, GenEnveloped and GenEnveloping.

   Code initially developed in the context of
   Universal Provenance Infrastructure
   
*/

public class Signature {
    public static final String XMLSIG_PREFIX="ds";
    public static final String XMLSIG_NAMESPACE=XMLSignature.XMLNS;
    public static final String XMLSIG_PROP_PREFIX="dsp";
    public static final String XMLSIG_PROP_NAMESPACE="http://www.w3.org/2009/xmldsig-properties";
    public static final String SIG_PROPERTIES_ID_SUFFIX=".sig.properties";
    public static final String SIG_OBJECT_ID_SUFFIX=".object";

    public Signature() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        createSignatureFactory();
    }

    public Signature(KeyPair kp) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.kp = kp;
        createSignatureFactory();
    }


    private KeyPairGenerator kpg;
    private KeyPair kp;
    private XMLSignatureFactory fac;

    public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        if (kpg==null) {
            kpg = KeyPairGenerator.getInstance("DSA");
            kpg.initialize(512);
        }
        kp = kpg.generateKeyPair();
        return kp;
    }

    private KeyStore ks;
    private String privateKeyAlias;
    private String privateKeyPassword;

    public Signature(String keyStoreType,
                     String keyStoreName,
                     String keyStorePassword,
                     String privateKeyAlias,
                     String privateKeyPassword)
        throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException,
               ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.kp=null;
        this.ks=getKeyStore(keyStoreType,keyStoreName,keyStorePassword);
        this.privateKeyAlias=privateKeyAlias;
        this.privateKeyPassword=privateKeyPassword;
        createSignatureFactory();
    }

    public Signature(KeyStore ks,
                     String privateKeyAlias,
                     String privateKeyPassword)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.kp=null;
        this.ks=ks;
        this.privateKeyAlias=privateKeyAlias;
        this.privateKeyPassword=privateKeyPassword;
        createSignatureFactory();
    }

    public Signature(KeyStore ks,
                     String privateKeyAlias,
                     String privateKeyPassword,
                     boolean create)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.kp=null;
        this.ks=ks;
        this.privateKeyAlias=privateKeyAlias;
        this.privateKeyPassword=privateKeyPassword;
        createSignatureFactory();
    }

    static public KeyStore getKeyStore(String keyStoreType,
                                       String keyStoreName,
                                       String keyStorePassword)
        throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException{
        KeyStore ks = KeyStore.getInstance(keyStoreType);
        FileInputStream fis = new FileInputStream(keyStoreName);
        ks.load(fis, keyStorePassword.toCharArray());
        return ks;
    }

    public PrivateKey getPrivateKey(KeyStore ks,
                                    String privateKeyAlias,
                                    String privateKeyPassword)
        throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        PrivateKey privateKey =  (PrivateKey) ks.getKey(privateKeyAlias,
                                                        privateKeyPassword.toCharArray());

        return privateKey;
    }


    public PrivateKey getPrivateKey()
        throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        if (kp!=null) return kp.getPrivate();
        if (ks!=null) return getPrivateKey(ks, privateKeyAlias, privateKeyPassword);
        return null;
    }


    public PublicKey getPublicKey() throws java.security.KeyStoreException {
        if (kp!=null) return kp.getPublic();
        if (ks!=null) return ks.getCertificate(privateKeyAlias).getPublicKey();
        return null;
    }

    public String getDistinguishedName() throws java.security.KeyStoreException {
        if (ks!=null) {
            X509Certificate cert=(X509Certificate) ks.getCertificate(privateKeyAlias);
            return cert.getSubjectDN().getName();
        }
        return null;
    }



    public void createSignatureFactory() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // First, create a DOM XMLSignatureFactory that will be used to 
        // generate the XMLSignature and marshal it to DOM.
        String providerName = System.getProperty("jsr105Provider",
                                                 "org.jcp.xml.dsig.internal.dom.XMLDSigRI");
        fac = XMLSignatureFactory.getInstance("DOM",
                                              (Provider) Class.forName(providerName).newInstance());
    }

    public void setPrefixes(XMLCryptoContext context) {
        context.putNamespacePrefix(XMLSIG_NAMESPACE, XMLSIG_PREFIX);
	context.putNamespacePrefix(XMLSIG_PROP_NAMESPACE,XMLSIG_PROP_PREFIX);
        //context.putNamespacePrefix("opm","http://openprovenance.org/model/v1.1.a");
    }

    /**
     Synopsis: java GenDetached [output]
    
     where output is the name of the file that will contain the detached
     signature. 
    */

    public void generateDetached(String file)  throws KeyException, UnrecoverableKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, java.security.KeyStoreException, TransformerException, ParserConfigurationException, MarshalException, XMLSignatureException, FileNotFoundException {
        generateDetached(new FileOutputStream(file));
    }

    // Create the SignedInfo
    public SignedInfo createSignedInfo(Reference ref) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
	return createSignedInfo(Collections.singletonList(ref));
    }
    // Create the SignedInfo
    public SignedInfo createSignedInfo(List<Reference> refs) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        SignedInfo si = fac.newSignedInfo(
                                          fac.newCanonicalizationMethod
                                          (CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS, 
                                           (C14NMethodParameterSpec) null),
                                          fac.newSignatureMethod(SignatureMethod.DSA_SHA1, null),
                                          refs);
        return si;
    }





    public void generateDetached(OutputStream os) throws KeyException, UnrecoverableKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, java.security.KeyStoreException, TransformerException, ParserConfigurationException, MarshalException, XMLSignatureException {

        // Create a Reference to an external URI that will be digested
        // using the SHA1 digest algorithm
        Reference ref = fac.newReference("http://www.w3.org/TR/xml-stylesheet",
                                         fac.newDigestMethod(DigestMethod.SHA1, null));

        // Create the SignedInfo
        SignedInfo si = createSignedInfo(ref);

        KeyInfo ki = makeKeyInfo();


        // Create the XMLSignature (but don't sign it yet)
        XMLSignature signature = fac.newXMLSignature(si, ki);

        // Create the Document that will hold the resulting XMLSignature
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true); // must be set
        Document doc = dbf.newDocumentBuilder().newDocument();

        // Create a DOMSignContext and set the signing Key to the DSA 
        // PrivateKey and specify where the XMLSignature should be inserted 
        // in the target document (in this case, the document root)
        DOMSignContext dsc = new DOMSignContext(getPrivateKey(), doc);
        setPrefixes(dsc);

        // Marshal, generate (and sign) the detached XMLSignature. The DOM 
        // Document will contain the XML Signature if this method returns 
        // successfully.
        signature.sign(dsc);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer trans = tf.newTransformer();
        trans.transform(new DOMSource(doc), new StreamResult(os));
    }

    //
    // Synopsis: java GenEnveloped [document] [output]
    //
    //    where "document" is the name of a file containing the XML document
    //    to be signed, and "output" is the name of the file to store the
    //    signed document. The 2nd argument is optional - if not specified,
    //    standard output will be used.
    //

    public  void generateEnveloped(String input, String output)  throws KeyException, UnrecoverableKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, java.security.KeyStoreException, TransformerException, ParserConfigurationException, MarshalException, XMLSignatureException, FileNotFoundException, SAXException, IOException {
        generateEnveloped(new FileInputStream(input),
                          new FileOutputStream(output));
    }

    public  void generateEnveloped(InputStream input, OutputStream output)  throws KeyException, UnrecoverableKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, java.security.KeyStoreException, TransformerException, ParserConfigurationException, MarshalException, XMLSignatureException, SAXException, IOException {
        // Instantiate the document to be signed
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        Document doc=dbf.newDocumentBuilder().parse(input);
        generateEnveloped(doc,new StreamResult(output));
    }

    boolean modifiedEnveloped=false;
    public Transform newTransformEnveloped() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        if (modifiedEnveloped) {
            String xp=    
                "count(ancestor-or-self::opm:signature | "
                + " here()/ancestor::opm:signature[1]) > "
                + " count(ancestor-or-self::opm:signature) ";

            xp="not(ancestor-or-self::ds:Signature)";
            HashMap map=new HashMap();
            map.put("opm","http://openprovenance.org/model/v1.1.a");
            map.put("ds","http://www.w3.org/2000/09/xmldsig#");
            XPathFilterParameterSpec xpath=new XPathFilterParameterSpec(xp,map);
            return fac.newTransform(Transform.XPATH,
                                    xpath);
        } else {
            return fac.newTransform(Transform.ENVELOPED,
                                    (TransformParameterSpec) null);
        }
    }

    Random rand=new Random();
    
    public List<XMLObject> signatureProperties(String sigId,
					       Document doc)  {

        
        List<XMLObject> res=new LinkedList();
        Date now=new Date();
        List contentCreated=new LinkedList();
        Element createdEl = doc.createElementNS(XMLSIG_PROP_NAMESPACE,XMLSIG_PROP_PREFIX+":Created");
        Text date=doc.createTextNode(""+now);
        createdEl.appendChild(date);
        //createdEl.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:ignore");
        contentCreated.add(new DOMStructure(createdEl));

        List contentReplayProtect=new LinkedList();
        Element replayProtectEl = doc.createElementNS(XMLSIG_PROP_NAMESPACE,XMLSIG_PROP_PREFIX+":ReplayProtect");
        Element timestampEl =     doc.createElementNS(XMLSIG_PROP_NAMESPACE,XMLSIG_PROP_PREFIX+":timestamp");
        Element nonceEl =         doc.createElementNS(XMLSIG_PROP_NAMESPACE,XMLSIG_PROP_PREFIX+":nonce");
        date=doc.createTextNode(""+now);
        replayProtectEl.appendChild(timestampEl);
        replayProtectEl.appendChild(nonceEl);
        Text nonce=doc.createTextNode(""+rand.nextInt());
        timestampEl.appendChild(date);
        nonceEl.appendChild(nonce);
        //replayProtectEl.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:ignore");
        contentReplayProtect.add(new DOMStructure(replayProtectEl));

        // NEED SIGNATURE PROPERTIES
        // NEED ID: Id="AMadeUpTimeStamp"
        // NEED TO add refernce <Reference URI="#AMadeUpTimeStamp" Type="http://www.w3.org/2000/09/xmldsig#SignatureProperties">

	
        List content2 = new LinkedList();
        content2.add(fac.newSignatureProperty(contentCreated,"#" + sigId, sigId + ".created"));
        content2.add(fac.newSignatureProperty(contentReplayProtect,"#" + sigId, sigId + ".contentreplay"));

        List content3 = Collections.singletonList(fac.newSignatureProperties(content2, sigId + SIG_PROPERTIES_ID_SUFFIX));
	
        XMLObject obj=fac.newXMLObject(content3,sigId + SIG_OBJECT_ID_SUFFIX,null,null);
        res.add(obj);
        return res;
    }

    static int sigCount=0;
    public String newSignatureId() {
	return "sigId"+ sigCount++;
    }

   /** Create a Reference to a signature properties and
	also specify the SHA1 digest algorithm.
	@param id: signature  id used as a prefix to create the signature properties id
	@return a Reference
    */
    public Reference newReferenceToSigProperties(String sigId) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
	return fac.newReference("#" + sigId + SIG_OBJECT_ID_SUFFIX, //".created"
                            fac.newDigestMethod(DigestMethod.SHA1, null),
                            null, 
                            SignatureProperties.TYPE,
                            null);
    }

    /** Create a Reference to the enveloped document (in this case we are
	signing the whole document, so a URI of "" signifies that) and
	also specify the SHA1 digest algorithm and the ENVELOPED Transform.
	@param id: document id, use "" to denote root
	@return a Reference
    */
    public Reference newReferenceToDocument(String id) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
	return fac.newReference(id,
				fac.newDigestMethod(DigestMethod.SHA1, null),
				Collections.singletonList(newTransformEnveloped()), 
				null,
				null);
    }
    
    public  void generateEnveloped(Document doc, Result result) throws KeyException, UnrecoverableKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, java.security.KeyStoreException, TransformerException, ParserConfigurationException, MarshalException, XMLSignatureException {
        generateEnveloped(doc,doc.getDocumentElement(),result);
    }
    public  void generateEnveloped(Document doc, Node node, Result result) throws KeyException, UnrecoverableKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, java.security.KeyStoreException, TransformerException, ParserConfigurationException, MarshalException, XMLSignatureException {

	// create an identifier for signature (used as prefix for other substructure ids)
	String sigId=newSignatureId();

	List<Reference> refs=new LinkedList();

        refs.add(newReferenceToDocument(""));

	//TODO, validation fails for second reference here.
	if (true) 
	    refs.add(newReferenceToSigProperties(sigId));


        // Create the SignedInfo
        SignedInfo si = createSignedInfo(refs);

        KeyInfo ki = makeKeyInfo();

        // Create the XMLSignature (but don't sign it yet)
        XMLSignature signature = fac.newXMLSignature(si,
						     ki,
                                                     signatureProperties(sigId,doc),
                                                     sigId,
						     null);

    
        // Create a DOMSignContext and specify the DSA PrivateKey and
        // location of the resulting XMLSignature's parent element
        DOMSignContext dsc = new DOMSignContext(getPrivateKey(), node);
        setPrefixes(dsc);


        // Marshal, generate (and sign) the enveloped signature
        signature.sign(dsc);


        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer trans = tf.newTransformer();
        trans.transform(new DOMSource(doc), result);
    }

    public void generateEnveloping(String input, String file) throws KeyException, UnrecoverableKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, java.security.KeyStoreException, TransformerException, ParserConfigurationException, MarshalException, XMLSignatureException, SAXException, FileNotFoundException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        Document doc = dbf.newDocumentBuilder().parse(input);

        generateEnveloping(doc, doc.getDocumentElement(), new FileOutputStream(file));
    }


    public void generateEnveloping(Document doc, String file) throws KeyException, UnrecoverableKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, java.security.KeyStoreException, TransformerException, ParserConfigurationException, MarshalException, XMLSignatureException, SAXException, FileNotFoundException, IOException {
        generateEnveloping(doc, doc.getDocumentElement(), new FileOutputStream(file));
    }

    //
    // Synopis: java GenEnveloping [output]
    //
    //   where "output" is the name of a file that will contain the
    //   generated signature. If not specified, standard ouput will be used.
    //

    public KeyInfo makeKeyInfo() throws java.security.KeyStoreException, java.security.KeyException {
        if (ks!=null) return makeKeyInfoWithCertificate();
        if (kp!=null) return makeKeyInfoWithKeyValue(); 
        return null;
    }

    /** Create a KeyInfo containing a KeyValue containing the publisc key. */
    public KeyInfo makeKeyInfoWithKeyValue() throws java.security.KeyStoreException, java.security.KeyException {
        KeyInfoFactory kif = fac.getKeyInfoFactory();
        KeyValue kv = kif.newKeyValue(getPublicKey());

        // Create a KeyInfo and add the KeyValue to it
        KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kv));
        return ki;
    }

    /** Create a KeyInfo containing an X509Data consisting of the distinguished name and certificate. */
    public KeyInfo makeKeyInfoWithCertificate() throws java.security.KeyStoreException, java.security.KeyException {
        KeyInfoFactory kif = fac.getKeyInfoFactory();
        List<Object> ll=new LinkedList<Object>();
        X509Certificate cert=(X509Certificate) ks.getCertificate(privateKeyAlias);
        ll.add(cert.getSubjectDN().getName());
        ll.add(cert);
        X509Data kv = kif.newX509Data(ll);

        // Create a KeyInfo and add the KeyValue to it
        KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kv));
        return ki;
    }

    public void generateEnveloping(Document doc, Node node, OutputStream output) throws KeyException, UnrecoverableKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, java.security.KeyStoreException, TransformerException, ParserConfigurationException, MarshalException, XMLSignatureException {

        // Next, create a Reference to a same-document URI that is an Object
        // element and specify the SHA1 digest algorithm
        Reference ref = fac.newReference("#object",
                                         fac.newDigestMethod(DigestMethod.SHA1, null));

        // Next, create the referenced Object

        XMLStructure content = new DOMStructure(node);
        XMLObject obj = fac.newXMLObject
            (Collections.singletonList(content), "object", null, null);

        // Create the SignedInfo
        SignedInfo si = createSignedInfo(ref);

        // Create a KeyInfo and add the KeyValue to it
        KeyInfo ki = makeKeyInfo();

        // Create the XMLSignature (but don't sign it yet)
        XMLSignature signature = fac.newXMLSignature(si, ki,
                                                     Collections.singletonList(obj), null, null); 

        // Create a DOMSignContext and specify the DSA PrivateKey for signing
        // and the document location of the XMLSignature
        DOMSignContext dsc = new DOMSignContext(getPrivateKey(), doc);
        setPrefixes(dsc);

        // Lastly, generate the enveloping signature using the PrivateKey
        signature.sign(dsc);


        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer trans = tf.newTransformer();
        trans.transform(new DOMSource(doc), new StreamResult(output));
    }

    public void testGenerateEnveloping(OutputStream output) throws KeyException, UnrecoverableKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, java.security.KeyStoreException, TransformerException, ParserConfigurationException, MarshalException, XMLSignatureException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        Document doc = dbf.newDocumentBuilder().newDocument();
        generateEnveloping(doc,doc.createTextNode("some text"),output);
    }


    public boolean validate(Node signatureToCheck) throws MarshalException, XMLSignatureException {
        // Create a DOMValidateContext and specify a KeyValue KeySelector
        // and document context

        System.out.println("Validating " + signatureToCheck);
        DOMValidateContext valContext = new DOMValidateContext(new MultiKeySelector(),
                                                               signatureToCheck);
        setPrefixes(valContext);
	
        // unmarshal the XMLSignature
        XMLSignature signature = fac.unmarshalXMLSignature(valContext);

        // Validate the XMLSignature (generated above)
        boolean coreValidity = signature.validate(valContext); 

        // Check core validation status
        if (coreValidity == false) {
    	    System.out.println("- Signature failed core validation"); 
            boolean sv = signature.getSignatureValue().validate(valContext);
            System.out.println(" - signature validation status: " + sv);
            // check the validation status of each Reference
            int j=0;
            System.out.println(" - references: " + signature.getSignedInfo().getReferences().size());
            for (Object o: signature.getSignedInfo().getReferences()) {
                Reference ref=(Reference) o;
                boolean refValid = ref.validate(valContext);
                System.out.println("  - ref["+j+"] validity status: " + refValid + " " + new String(ref.getDigestValue()) + " " + new String(ref.getCalculatedDigestValue()));
                j++;
            }
            return false;
        } else {
    	    System.out.println("- Signature passed core validation");
            return true;
        }
    }

}
