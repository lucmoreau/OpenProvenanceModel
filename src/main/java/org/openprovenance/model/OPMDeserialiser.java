package org.openprovenance.model;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBElement;
import org.w3c.dom.Element;


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


    
}


