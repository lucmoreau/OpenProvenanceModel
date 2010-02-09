package org.openprovenance.model.security;


import org.openprovenance.model.OPMGraph;

public interface SignerFunctionality {
    public boolean sign(OPMGraph pac) throws javax.xml.bind.JAXBException, Exception;
    public boolean validate(OPMGraph pac, boolean checkDN) throws javax.xml.bind.JAXBException, Exception;
    public String getDistinguishedName() throws java.security.KeyStoreException;
}