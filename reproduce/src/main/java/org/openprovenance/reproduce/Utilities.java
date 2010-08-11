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
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Attr;
import org.xml.sax.SAXException;

import java.io.StringWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.apache.xml.serialize.XMLSerializer;
import org.apache.xml.serialize.OutputFormat;

import org.openprovenance.model.OPMSerialiser;
import org.openprovenance.model.OPMGraph;

import java.io.StringWriter;
import org.jaxen.JaxenException;

public class Utilities {
    static String swift_URI_PREFIX="http://openprovenance.org/reproducibility/swift#";
    static String air_URI_PREFIX="http://openprovenance.org/reproducibility/air#";
    static String swift_XML_NS="http://ci.uchicago.edu/swift/2009/02/swiftscript";
    static String opr_XML_NS="http://openprovenance.org/reproducibility";

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

    static String predefinedProcedures="air.xml";

    Document library;

    public Document loadLibrary() throws SAXException, IOException {
        library=parserXML(Utilities.class.getClassLoader().getResourceAsStream(predefinedProcedures));
        return library;
    }

    public List<?> getLibraryDefinition(String name) throws JaxenException {
        String xp="/swift:program/swift:procedure[@name='"+ name + "']";
        org.jaxen.dom.DOMXPath xpath=new org.jaxen.dom.DOMXPath(xp);
        org.jaxen.SimpleNamespaceContext context = new org.jaxen.SimpleNamespaceContext();
        context.addNamespace("swift",swift_XML_NS);
        xpath.setNamespaceContext(context);
        List<?> results = xpath.selectNodes(library.getDocumentElement());
        return results;
    }

    public List<?> getLibraryTypeDefinition(String name) throws JaxenException {
        String xp="/swift:program/swift:types/swift:type[swift:typename/text()='"+ name + "']";
        org.jaxen.dom.DOMXPath xpath=new org.jaxen.dom.DOMXPath(xp);
        org.jaxen.SimpleNamespaceContext context = new org.jaxen.SimpleNamespaceContext();
        context.addNamespace("swift",swift_XML_NS);
        xpath.setNamespaceContext(context);
        List<?> results = xpath.selectNodes(library.getDocumentElement());
        return results;
    }



    public String getNameFromUri(String name) {
        if (name.startsWith(swift_URI_PREFIX)) {
            String s=name.substring(swift_URI_PREFIX.length());
            //System.out.println("Found " + s);
            return s;
        } else if (name.startsWith(air_URI_PREFIX)) {
            String s=name.substring(air_URI_PREFIX.length());
            //System.out.println("Found " + s);
            return s;
        } else {
            return null;
        }
    }

    public List<?> getDefinitionForUri(String name) throws JaxenException {
        return getLibraryDefinition(getNameFromUri(name));
    }


    public List<?> getInputs(Node node) throws JaxenException {
        String xp="./swift:input";
        org.jaxen.dom.DOMXPath xpath=new org.jaxen.dom.DOMXPath(xp);
        org.jaxen.SimpleNamespaceContext context = new org.jaxen.SimpleNamespaceContext();
        context.addNamespace("swift",swift_XML_NS);
        xpath.setNamespaceContext(context);
        List<?> results = xpath.selectNodes(node);
        return results;
    }

    public List<?> getOutputs(Node node) throws JaxenException {
        String xp="./swift:output";
        org.jaxen.dom.DOMXPath xpath=new org.jaxen.dom.DOMXPath(xp);
        org.jaxen.SimpleNamespaceContext context = new org.jaxen.SimpleNamespaceContext();
        context.addNamespace("swift",swift_XML_NS);
        xpath.setNamespaceContext(context);
        List<?> results = xpath.selectNodes(node);
        return results;
    }


    public String getRole(Node node) throws JaxenException {
        String xp="./@opr:role";
        org.jaxen.dom.DOMXPath xpath=new org.jaxen.dom.DOMXPath(xp);
        org.jaxen.SimpleNamespaceContext context = new org.jaxen.SimpleNamespaceContext();
        context.addNamespace("opr",opr_XML_NS);
        xpath.setNamespaceContext(context);
        List<?> results = xpath.selectNodes(node);
        if (results==null) return null;
        Attr attr=(Attr) results.get(0);
        return attr.getValue();
    }


    public void removeRoles(Node node) throws JaxenException {
        String xp=".//@opr:role";
        org.jaxen.dom.DOMXPath xpath=new org.jaxen.dom.DOMXPath(xp);
        org.jaxen.SimpleNamespaceContext context = new org.jaxen.SimpleNamespaceContext();
        context.addNamespace("opr",opr_XML_NS);
        xpath.setNamespaceContext(context);
        List<?> results = xpath.selectNodes(node);
        if (results==null) return;
        for (Object result: results) {
            Attr attr=(Attr) result;
            Element owner=attr.getOwnerElement();
            owner.removeAttributeNode(attr);
        }
    }


    public String getName(Node node) throws JaxenException {
        String xp="./@name";
        org.jaxen.dom.DOMXPath xpath=new org.jaxen.dom.DOMXPath(xp);
        org.jaxen.SimpleNamespaceContext context = new org.jaxen.SimpleNamespaceContext();
        context.addNamespace("swift",swift_XML_NS);
        xpath.setNamespaceContext(context);
        List<?> results = xpath.selectNodes(node);
        if (results==null) return null;
        if (results.size()==0) return null;
        Attr attr=(Attr) results.get(0);
        return attr.getValue();
    }

    public String getType(Node node) throws JaxenException {
        String xp="./@type";
        org.jaxen.dom.DOMXPath xpath=new org.jaxen.dom.DOMXPath(xp);
        org.jaxen.SimpleNamespaceContext context = new org.jaxen.SimpleNamespaceContext();
        context.addNamespace("swift",swift_XML_NS);
        xpath.setNamespaceContext(context);
        List<?> results = xpath.selectNodes(node);
        if (results==null) return null;
        if (results.size()==0) return null;
        Attr attr=(Attr) results.get(0);
        return attr.getValue();
    }

    public void serializeToStandardOut(Element el, Document doc) throws IOException {
        serialize(el,doc,System.out);
    }

    public void serialize(Element el, Document doc, OutputStream out) throws IOException {
        OutputFormat of=new OutputFormat(doc,null,true);
        XMLSerializer serial = new XMLSerializer(out, of);
        serial.serialize(el);
    }

    public void debugPrintOPMGraph(OPMGraph g) {
        try {
            OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
            StringWriter sw=new StringWriter();
            serial.serialiseOPMGraph(sw,g,true);
            System.out.println(sw.getBuffer());
        } catch (Exception e) {
                 e.printStackTrace();
        }
    }


}