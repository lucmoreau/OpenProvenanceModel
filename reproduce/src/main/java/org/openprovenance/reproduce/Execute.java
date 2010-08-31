package org.openprovenance.reproduce;

import java.util.HashMap;
import java.util.List;

import org.openprovenance.model.Artifact;
import org.openprovenance.model.Process;
import org.openprovenance.model.OPMFactory;

import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.xml.sax.SAXException;
import java.io.IOException;


public interface Execute {

    
    public Object prepareInvocationArguments(String procedure,
                                             HashMap<String,Artifact> arguments)
        throws org.jaxen.JaxenException;


    public Object invoke(Object o, String name, Utilities u) throws IOException;

}