package org.openprovenance.model.printer;
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
public class OPMPrinterConfigDeserialiser {



    // it is recommended by the Jaxb documentation that one JAXB
    // context is created for one application. This object is thread
    // safe (in the sun impelmenation, but not
    // marshallers/unmarshallers.

    static protected JAXBContext jc;

    public OPMPrinterConfigDeserialiser () throws JAXBException {
        if (jc==null) 
            jc = JAXBContext.newInstance( "org.openprovenance.model.printer" );
    }

    public OPMPrinterConfigDeserialiser (String packageList) throws JAXBException {
        if (jc==null) 
            jc = JAXBContext.newInstance(packageList);
    }


    private static ThreadLocal<OPMPrinterConfigDeserialiser> threadDeserialiser=
        new ThreadLocal<OPMPrinterConfigDeserialiser> () {
        protected synchronized OPMPrinterConfigDeserialiser initialValue () {
                try {
                    return new OPMPrinterConfigDeserialiser();
                } catch (JAXBException jxb) {
                    throw new RuntimeException("OPMPrinterConfigDeserialiser: deserialiser init failure()");
                }
            }
    };

    public static OPMPrinterConfigDeserialiser getThreadOPMPrinterConfigDeserialiser() {
        return threadDeserialiser.get();
    }


    public OPMPrinterConfiguration deserialiseOPMPrinterConfiguration (File serialised)
        throws JAXBException {
        Unmarshaller u=jc.createUnmarshaller();
        Object root= u.unmarshal(serialised);
        OPMPrinterConfiguration res=(OPMPrinterConfiguration)((JAXBElement<OPMPrinterConfiguration>) root).getValue();
        return res;
    }

    public OPMPrinterConfiguration deserialiseOPMPrinterConfiguration (InputStream serialised)
        throws JAXBException {
        Unmarshaller u=jc.createUnmarshaller();
        Object root= u.unmarshal(serialised);
        OPMPrinterConfiguration res=(OPMPrinterConfiguration)((JAXBElement<OPMPrinterConfiguration>) root).getValue();
        return res;
    }

}
