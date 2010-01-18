package org.openprovenance.elmo;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMSource;
import org.xml.sax.InputSource;
import javax.xml.transform.stream.StreamResult;



import java.io.CharArrayWriter;
import java.io.CharArrayReader;
import org.openrdf.elmo.exceptions.ElmoConversionException;


/**
XMLLiteral support was dropped because they where being mapped to org.w3c.dom.Document and did not support XML fragments (multiple roots elements).

It is now recommended that users create their own XMLLiteral class that can handle XML fragments to what ever extent is desirable.

Below is a sample class, which maps rdf:XMLLiteral to the class XmlLiteral, which exposes a read-only w3c document as a property. It can be registered in the ElmoModule or in a configuration file.

		module.addLiteral(XmlLiteral.class,
				"http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral");


See http://www.openrdf.org/forum/mvnforum/viewthread?thread=1738

*/



public class XMLLiteral {
    private static DocumentBuilder builder;

    private static Transformer transformer;

    private String xml;

    public XMLLiteral(String xml) {
        this.xml = xml;
    }

    public XMLLiteral(Document document) {
        this.xml = serialize(document);
    }

    public Document getDocument() {
        return deserialize(xml);
    }

    @Override
		public String toString() {
        return xml;
    }

    private String serialize(Document object) {
        Source source = new DOMSource(object);
        CharArrayWriter writer = new CharArrayWriter();
        Result result = new StreamResult(writer);
        try {
            if (transformer == null) {
                transformer = TransformerFactory.newInstance()
                    .newTransformer();
            }
            transformer.transform(source, result);
        } catch (Exception e) {
            throw new ElmoConversionException(e);
        }
        return writer.toString();
    }

    private Document deserialize(String xml) {
        try {
            char[] charArray = xml.toCharArray();
            CharArrayReader reader = new CharArrayReader(charArray);
            try {
                if (builder == null) {
                    builder = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder();
                }
                return builder.parse(new InputSource(reader));
            } finally {
                reader.close();
            }
        } catch (Exception e) {
            throw new ElmoConversionException(e);
        }
    }

}