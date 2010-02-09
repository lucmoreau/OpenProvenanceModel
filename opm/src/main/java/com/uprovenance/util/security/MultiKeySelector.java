package com.uprovenance.util.security;
import java.util.List;

import java.security.Key;
import java.security.KeyException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.keyinfo.X509Data;

/**
 * KeySelector which retrieves the public key out of the
 * KeyValue element and returns it.
 * NOTE: If the key algorithm doesn't match signature algorithm,
 * then the public key will be ignored.

     Code initially developed in the context of
     Universal Provenance Infrastructure

 */
public class MultiKeySelector extends KeySelector {

		/**
		 * Returns public key of first certificate, if there is, or public key of KeyInfo.
		 * 
		 * @param keyInfo key info.
		 * @param purpose purpose for key.
		 * @param method algorithm method.
		 * @param context context.
		 * @return KeySelectorResult - result that contains public key used for
		 *         verification.
		 * @throws KeySelectorException
		 *             - when there is no certificate or key info.
		 */


    public KeySelectorResult select(KeyInfo keyInfo,
                                    KeySelector.Purpose purpose,
                                    AlgorithmMethod method,
                                    XMLCryptoContext context)
        throws KeySelectorException {
        if (keyInfo == null) {
            throw new KeySelectorException("Null KeyInfo object!");
        }

        SignatureMethod sm = (SignatureMethod) method;

        List<?> list = keyInfo.getContent();

        for (int i = 0; i < list.size(); i++) {
            XMLStructure xmlStructure = (XMLStructure) list.get(i);
            if (xmlStructure instanceof  X509Data) {
                List<?> x509DataContents = ((X509Data) xmlStructure).getContent();
                for (Object x509DataElement: x509DataContents) {
                    if (x509DataElement instanceof X509Certificate) {
                        X509Certificate cert=(X509Certificate) x509DataElement;
                        PublicKey pk=cert.getPublicKey();
                        // make sure algorithm is compatible with method
                        if (algEquals(sm.getAlgorithm(), pk.getAlgorithm())) {
                            return new SimpleKeySelectorResult(pk);
                            
                        }
                    }
                }
            } else if (xmlStructure instanceof KeyValue) {
                PublicKey pk = null;
                try {
                    pk = ((KeyValue)xmlStructure).getPublicKey();
                } catch (KeyException ke) {
                    throw new KeySelectorException(ke);
                }
                // make sure algorithm is compatible with method
                if (algEquals(sm.getAlgorithm(), pk.getAlgorithm())) {
                    return new SimpleKeySelectorResult(pk);
                }
            }
        }
        throw new KeySelectorException("No Certifacate or KeyValue element found!");
    }

    //@@@FIXME: this should also work for key types other than DSA/RSA
    static boolean algEquals(String algURI, String algName) {
        if (algName.equalsIgnoreCase("DSA") &&
            algURI.equalsIgnoreCase(SignatureMethod.DSA_SHA1)) {
            return true;
        } else if (algName.equalsIgnoreCase("RSA") &&
                   algURI.equalsIgnoreCase(SignatureMethod.RSA_SHA1)) {
            return true;
        } else {
            return false;
        }
    }


    private static class SimpleKeySelectorResult implements KeySelectorResult {
        private PublicKey pk;
        SimpleKeySelectorResult(PublicKey pk) {
            this.pk = pk;
        }
        
        public Key getKey() { return pk; }
    }

}

