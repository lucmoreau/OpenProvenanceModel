package org.openprovenance.model;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import java.io.StringWriter;
import java.io.File;

/** Serialiser of OPM Graphs. */

public class OPMSerialiser {
    private ObjectFactory of=new ObjectFactory();
	static DocumentBuilder docBuilder;


    /** Note DocumentBuilderFactory is documented to be non thread safe. 
        TODO: code analysis, of potential concurrency issues. */
	static void initBuilder() {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException ex) {
			throw new RuntimeException(ex);
		}
	}

    private static ThreadLocal<OPMSerialiser> threadSerialiser =
        new ThreadLocal<OPMSerialiser> () {
        protected synchronized OPMSerialiser initialValue () {
                try {
                    return new OPMSerialiser();
                } catch (JAXBException jxb) {
                    throw new RuntimeException("OPMDeserialiser: serialiser init failure()");
                }
            }
    };

    public static OPMSerialiser getThreadOPMSerialiser() {
        return threadSerialiser.get();
    }


    
    static {
        initBuilder();
    }
    protected JAXBContext jc;

    public static String defaultNamespace="http://example.com/";

    protected final boolean usePrefixMapper;
    public OPMSerialiser () throws JAXBException {
        jc = JAXBContext.newInstance( OPMFactory.packageList );
        usePrefixMapper=true;
    }
    public OPMSerialiser (boolean usePrefixMapper) throws JAXBException {
        jc = JAXBContext.newInstance( OPMFactory.packageList );
        this.usePrefixMapper=usePrefixMapper;
    }

    public OPMSerialiser (String packageList) throws JAXBException {
        jc = JAXBContext.newInstance( packageList );
        usePrefixMapper=true;
    }
    public OPMSerialiser (boolean usePrefixMapper, String packageList) throws JAXBException {
        jc = JAXBContext.newInstance( packageList );
        this.usePrefixMapper=usePrefixMapper;
    }

    public void configurePrefixes(Marshaller m) throws PropertyException {
        if (usePrefixMapper) {
            m.setProperty("com.sun.xml.bind.namespacePrefixMapper",
                          new NamespacePrefixMapper(defaultNamespace));
        }
    }

    public Document serialiseOPMGraph (OPMGraph request) throws JAXBException {
        return (Document) serialiseOPMGraph (defaultEmptyDocument(), request);
    }
    
    public Node serialiseOPMGraph (Node addTo, OPMGraph graph)
        throws JAXBException {
        Marshaller m=jc.createMarshaller();
        m.setProperty("jaxb.formatted.output",true);
        configurePrefixes(m);
        m.marshal(of.createOpmGraph(graph),addTo);
        return addTo;
    }
    public String serialiseOPMGraph (StringWriter sw, OPMGraph graph)
        throws JAXBException {
        Marshaller m=jc.createMarshaller();
        m.marshal(of.createOpmGraph(graph),sw);
        configurePrefixes(m);
        return sw.toString();
    }

    public String serialiseOPMGraph (StringWriter sw, OPMGraph graph, boolean format)
        throws JAXBException {
        Marshaller m=jc.createMarshaller();
        m.setProperty("jaxb.formatted.output",format);
        configurePrefixes(m);
        m.marshal(of.createOpmGraph(graph),sw);
        return sw.toString();
    }

    public void serialiseOPMGraph (File file, OPMGraph graph, boolean format)
        throws JAXBException {
        Marshaller m=jc.createMarshaller();
        m.setProperty("jaxb.formatted.output",format);
        configurePrefixes(m);
        m.marshal(of.createOpmGraph(graph),file);
    }

    /** By default we use a document provided by the DocumentBuilder
        factory. If this functionality is required,
        PStructureSerialiser needs to be subclassed and the
        defaultEmptyDocument method overriden. */

    public Document defaultEmptyDocument () {
        return docBuilder.newDocument();
    }


    

}

