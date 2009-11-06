package org.openprovenance.model;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBElement;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.Source;

import org.openprovenance.model.printer.OPMPrinterConfiguration;

/** Deserialiser of OPM Graphs. */
public class OPMDeserialiser {



    // it is recommended by the Jaxb documentation that one JAXB
    // context is created for one application. This object is thread
    // safe (in the sun impelmenation, but not
    // marshallers/unmarshallers.

    static protected JAXBContext jc;

    public OPMDeserialiser () throws JAXBException {
        if (jc==null) 
            jc = JAXBContext.newInstance( OPMFactory.packageList );
        // note, it is sometimes recommended to pass the current classloader
        
    }

    public OPMDeserialiser (String packageList) throws JAXBException {
        if (jc==null) 
            jc = JAXBContext.newInstance(packageList);
    }


    private static ThreadLocal<OPMDeserialiser> threadDeserialiser=
        new ThreadLocal<OPMDeserialiser> () {
        protected synchronized OPMDeserialiser initialValue () {
                try {
                    return new OPMDeserialiser();
                } catch (JAXBException jxb) {
                    throw new RuntimeException("OPMDeserialiser: deserialiser init failure()");
                }
            }
    };

    public static OPMDeserialiser getThreadOPMDeserialiser() {
        return threadDeserialiser.get();
    }

    public OPMGraph deserialiseOPMGraph (Element serialised)
        throws JAXBException {
        Unmarshaller u=jc.createUnmarshaller();
        JAXBElement<OPMGraph> root= u.unmarshal(serialised,OPMGraph.class);
        OPMGraph res=root.getValue();
        return res;
    }

    public OPMGraph deserialiseOPMGraph (File serialised)
        throws JAXBException {
        Unmarshaller u=jc.createUnmarshaller();
        Object root= u.unmarshal(serialised);
        OPMGraph res=(OPMGraph)((JAXBElement<OPMGraph>) root).getValue();
        return res;
    }


    public OPMGraph validateOPMGraph (String[] schemaFiles, File serialised)
        throws JAXBException,SAXException, IOException {
        SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source [] sources=new Source[2+schemaFiles.length];
        sources[0]=new StreamSource(this.getClass().getResourceAsStream("/"+"opm.1_1.xsd"));
        int i=0;
        for (String schemaFile: schemaFiles) {
            sources[2+i]=new StreamSource(new File(schemaFile));
            i++;
        }
        Schema schema = sf.newSchema(sources);  
        Unmarshaller u=jc.createUnmarshaller();
        //u.setValidating(true); was jaxb1.0
        u.setSchema(schema);
        Object root= u.unmarshal(serialised);
        OPMGraph res=(OPMGraph)((JAXBElement<OPMGraph>) root).getValue();
        return res;
    }

    public static void main(String [] args) {
        OPMDeserialiser deserial=OPMDeserialiser.getThreadOPMDeserialiser();
        if ((args==null) ||  (args.length==0)) {
            System.out.println("Usage: opmxml-validate <filename> {schemaFiles}*");
            return;
        }
        File f=new File(args[0]);
        String [] schemas=new String[args.length-1];
        for (int i=1; i< args.length; i++) {
            schemas[i-1]=args[i];
        }
        try {
            deserial.validateOPMGraph(schemas,f);
            System.out.println(args[0] + " IS a valid OPM graph");
            return ;
        } catch (JAXBException je) {
            je.printStackTrace();
            System.out.println(args[0] + " IS NOT a valid OPM graph");
        } catch (SAXException je) {
            je.printStackTrace();
            System.out.println(args[0] + " IS NOT a valid OPM graph");
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println(args[0] + " IS NOT a valid OPM graph");
        }
    }
}
