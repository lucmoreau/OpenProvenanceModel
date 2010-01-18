
package org.openprovenance.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.util.Iterator;
import java.util.List;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;

/**
 * A convenience class for building a DOM document or adding to a document
 * fragment iteratively.  It maintains
 * a position in the document to which to add nodes.
 * Adapated from Simon Miles
 */

public class DOMSerialiser {
    // The root node to which is being added
    private Node   _root;
    // The current namespace
    private String _namespace;
    // The current node to which to add new nodes
    private Node   _current;

    /**
     * Starts serialisation for adding nodes to a given root node.
     */
    public DOMSerialiser (Node root, String namespace) {
        _root      = root;
        _namespace = namespace;
        _current   = root;
    }
    
    /**
     * Starts serialisation for adding nodes to a given root node,
     * using the same namespace as that node.
     */
    public DOMSerialiser (Node root) {
        this (root, root.getNamespaceURI ());
    }

    public DOMSerialiser (String namespace) {
        this (null, namespace);
        _root=newDocument();
        _current=_root;
    }
    
    /**
     * Starts serialisation with a new document.
     */
    public DOMSerialiser () {
        this (null,null);
        _root=newDocument();
        _current=_root;
    }

    /**
     * Appends a new child to the current one.  If the new child is a Document,
     * then instead append the Document's root Element.
     */
    public DOMSerialiser append (Node child) {
        if (child == null) {
            return this;
        }
        if (child instanceof Document) {
            append (((Document) child).getDocumentElement ());
        } else {
            _current.appendChild (child);
        }
        return this;
    }
    
    public DOMSerialiser appendAll (List<Node> childNodes) {
        Iterator<Node> eachChild = childNodes.iterator ();
        
        while (eachChild.hasNext ()) {
            append (eachChild.next ());
        }
        return this;
    }
    
    public void attribute (String key, String value) {
        ((Element) _current).setAttribute (key, value);
    }
    
    public void attribute (String namespace, String localName, String value) {
        ((Element) _current).setAttributeNS (namespace, localName, value);
    }
    
    /**
     * Include a copy of the document fragment given by the element.
     */
    public DOMSerialiser copy (Element original) {
        if (original == null) {
            throw new NullPointerException ("Attempting to copy a null DOM element");
        } else {
            return append (newElement (_root.getOwnerDocument (), original));
        }
    }
    
    /**
     * Finish adding children to the current node and set the current node to
     * its parent.
     */
    public DOMSerialiser end () {
        _current = _current.getParentNode ();
        return this;
    }

    public Node getCurrent () {
        return _current;
    }
    
    /**
     * Return the root node being added to.
     */
    public Node getRoot () {
        return _root;
    }

    
    /**
     * Set the current namespace used for new nodes.
     */
    public DOMSerialiser namespace (String newNamespace) {
        _namespace = newNamespace;
        return this;
    }

    /**
     * Adds a new element with the current namespace and given local name to
     * the current node, and continue editing the current node.
     */
    public DOMSerialiser single (String elementName) {
        start (elementName);
        return end ();
    }

    /**
     * Adds a new element with the current namespace, given local name and
     * given single child to the current node, and continue editing the
     * current node.
     */
    public DOMSerialiser single (String elementName, Node child) {
        if (child != null) {
            start (elementName);
            append (child);
            end ();
        }
        return this;
    }
    
    /**
     * Adds a new element with the current namespace, given local name and
     * given text content to the current node, and continue editing the
     * current node.
     */
    public DOMSerialiser single (String elementName, String content) {
        if (content != null) {
            single (elementName,
                    newText ((Document)_root, content));
        }
        return this;
    }

    /**
     * Adds a new element with the current namespace, given local name and
     * given serialised integer content to the current node, and continue editing the
     * current node.
     */
    public DOMSerialiser single (String elementName, int content) {
        return single (elementName, Integer.toString (content));
    }
    
    /**
     * Adds a new element with the given namespace and given local name 
     * to the current node, and make this new child the current node.
     */
    public DOMSerialiser start (String namespace, String elementName) {
        Element newElem = newElement ((Document)_root, namespace, elementName);
        _current.appendChild (newElem);
        _current = newElem;
        return this;
    }
    
    /**
     * Adds a new element with the current namespace and given local name 
     * to the current node, and make this new child the current node.
     */
    public DOMSerialiser start (String elementName) {
        //System.out.println("root " + _root);
        //System.out.println("current " + _current);

        Element newElem = newElement ((Document)_root, _namespace, elementName);
        _current.appendChild (newElem);
        _current = newElem;
        return this;
    }
    
    public DOMSerialiser text (String text) {
        Text newNode = newText ((Document)_root, text);
        _current.appendChild (newNode);
        return this;
    }

    static DocumentBuilder docBuilder;

	static void initBuilder() {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException ex) {
			throw new RuntimeException(ex);
		}
	}
    static {
        initBuilder();
    }



    public Document newDocument () {
        return docBuilder.newDocument();
    }

    public Element newElement(Document owner, Element convertFrom) {

        Element result = newElement (owner,
                                     getNamespaceURIInConversion (convertFrom),
                                     getLocalNameInConversion (convertFrom));
        
        NodeList     eachNode = convertFrom.getChildNodes ();
        NamedNodeMap eachAttr = convertFrom.getAttributes ();
        Attr         space;
        
        /*        for (int index = 0; index < eachAttr.getLength (); index += 1) {
                  space = (Attr) eachAttr.item (index);
                  if (space.getNodeName ().startsWith ("xmlns:")) {
                  processNamespace (space);
                  }
                  }
                  for (int index = 0; index < eachNode.getLength (); index += 1) {
                  if (eachNode.item (index).getNodeType () == Node.ATTRIBUTE_NODE) {
                  space = (Attr) eachNode.item (index);
                  if (space.getNodeName ().startsWith ("xmlns:")) {
                  processNamespace (space);
                  }
                  }
                  }*/
        
        eachNode = convertFrom.getChildNodes ();
        for (int index = 0; index < eachNode.getLength (); index += 1) {
            if (eachNode.item (index).getNodeType () == Node.ATTRIBUTE_NODE) {
                space = (Attr) eachNode.item (index);
                if (!isNamespaceDeclaration (space)) { //Luc, it's the same code in both branches, why?
                    result.appendChild (newAttr (owner, (Attr) eachNode.item (index)));
                } else {
                    result.appendChild (newAttr (owner, (Attr) eachNode.item (index)));
                }
            }
            if (eachNode.item (index).getNodeType () == Node.ELEMENT_NODE) {
                result.appendChild (newElement (owner, (Element) eachNode.item (index)));
            }
            if (eachNode.item (index).getNodeType () == Node.TEXT_NODE) {
                result.appendChild (newText (owner, (Text) eachNode.item (index)));
            }
        }
        
        eachAttr = convertFrom.getAttributes ();
        for (int index = 0; index < eachAttr.getLength (); index += 1) {
            space = (Attr) eachAttr.item (index);
            if (!isNamespaceDeclaration (space)) {
                result.appendChild (newAttr (owner, (Attr) eachAttr.item (index)));
            }
        }

        return result;
    }

    private boolean isNamespaceDeclaration (Attr space) {
        return (space.getNodeName () != null && space.getNodeName ().startsWith ("xmlns:"))
            || (space.getPrefix () != null && space.getPrefix ().equals ("xmlns"));
    }

    

    /**
     * Returns the local name of an element that this element is to be converted
     * from or throws a more meaningful error message if the element is null.
     */
    private static String getLocalNameInConversion (Element convertFrom) {
        if (convertFrom == null) {
            throw new NullPointerException ("Trying to convert from a null DOM element");
        } else {
            return convertFrom.getLocalName ();
        }
    }
    
    /**
     * Returns the namespace URI of an element that this element is to be converted
     * from or throws a more meaningful error message if the element is null.
     */
    private static String getNamespaceURIInConversion (Element convertFrom) {
        if (convertFrom == null) {
            throw new NullPointerException ("Trying to convert from a null DOM element");
        } else {
            return convertFrom.getNamespaceURI ();
        }
    }

    public Element newElement(Document doc, String namespace, String elementName) {
        return doc.createElementNS(namespace,elementName);        
    }

    public Text newText(Document doc, Text text) {
        return doc.createTextNode(text.getNodeValue());
    }

    public Text newText(Document doc, String text) {
        return doc.createTextNode(text);
    }


    public Attr newAttr(Document doc, Attr attr) {
        return doc.createAttributeNS(attr.getNamespaceURI(), attr.getName());
    }
    
}
