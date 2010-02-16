package org.openprovenance.model.security;


import org.openprovenance.model.OPMGraph;
import org.openprovenance.model.OPMFactory;

import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;


public class StoreSignerFactory {
    private KeyStore ks;
    public StoreSignerFactory(String keyStoreType,
                              String keyStoreName,
                              String keyStorePassword)
        throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        this.ks=com.uprovenance.util.security.Signature.getKeyStore(keyStoreType,
                                                                    keyStoreName,
                                                                    keyStorePassword);
    }
    public StoreSignerFactory()
        throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
    }

    public SignerFunctionality newInstance(OPMFactory oFactory,
                                           String privateKeyAlias,
                                           String privateKeyPassword,
                                           String simpleName) 
        throws ClassNotFoundException, InstantiationException, IllegalAccessException, KeyStoreException {
        return new Signer(oFactory,
                          ks,
                          privateKeyAlias,
                          privateKeyPassword,
                          simpleName);
    }


    public SignerFunctionality newEmptyInstance(String privateKeyAlias,
                                                String privateKeyPassword) 
        throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        final String dName="CN="+privateKeyAlias;
        return new SignerFunctionality() {
            public boolean sign(OPMGraph pac) throws javax.xml.bind.JAXBException, Exception {
                return true;
            }
            public boolean validate(OPMGraph pac, boolean checkDN) throws javax.xml.bind.JAXBException, Exception {
                return true;
            }
            public String getDistinguishedName() throws java.security.KeyStoreException {
                return dName;
            }
        };
    }
}

