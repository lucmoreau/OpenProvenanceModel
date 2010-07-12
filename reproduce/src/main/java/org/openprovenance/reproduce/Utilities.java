package org.openprovenance.reproduce;

import org.openprovenance.model.OPMFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.jaxen.JaxenException;

public class Utilities {

    final OPMFactory oFactory;

    public Utilities (OPMFactory oFactory) {
        this.oFactory=oFactory;
    }
    
    public Document parserXML(File file) throws SAXException, IOException {
        return parserXML(oFactory.builder,file);
    }

    public Document parserXML(InputStream stream) throws SAXException, IOException {
        return parserXML(oFactory.builder,stream);
    }

	public Document parserXML(DocumentBuilder builder, File file) throws SAXException, IOException
	{
		return builder.parse(file);
	}

	public Document parserXML(DocumentBuilder builder, InputStream stream) throws SAXException, IOException
	{
		return builder.parse(stream);
	}

    static String predefinedProcedures="lib.xml";

    Document library;

    public Document loadLibrary() throws SAXException, IOException {
        library=parserXML(Utilities.class.getClassLoader().getResourceAsStream(predefinedProcedures));
        return library;
    }

    public List<?> getLibraryDefinition(String name) throws JaxenException {
        String xp="/swift:program/swift:procedure[@name='"+ name + "']";
        org.jaxen.dom.DOMXPath xpath=new org.jaxen.dom.DOMXPath(xp);
        org.jaxen.SimpleNamespaceContext context = new org.jaxen.SimpleNamespaceContext();
        context.addNamespace("swift","http://ci.uchicago.edu/swift/2009/02/swiftscript");
        xpath.setNamespaceContext(context);
        List<?> results = xpath.selectNodes(library.getDocumentElement());
        return results;
    }

    static String swift_NS="http://openprovenance.org/reproducibility/swift#";
    
    public List<?> getDefinitionForUri(String name) throws JaxenException {
        if (name.startsWith(swift_NS)) {
            String s=name.substring(swift_NS.length());
            System.out.println("Found " + s);
            return getLibraryDefinition(s);
        } else {
            return null;
        }
    }



}