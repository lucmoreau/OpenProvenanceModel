package org.openprovenance.elmo;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;



import javax.xml.namespace.QName;
import org.openprovenance.model.OPMDeserialiser;
import org.openprovenance.model.OPMGraph;
import org.openprovenance.model.OPMSerialiser;
import org.openrdf.elmo.ElmoManager;
import org.openrdf.elmo.ElmoManagerFactory;
import org.openrdf.elmo.ElmoModule;
import org.openrdf.elmo.sesame.SesameManager;
import org.openrdf.elmo.sesame.SesameManagerFactory;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.n3.N3Writer;
import org.openrdf.rio.ntriples.NTriplesWriter;
import org.openrdf.rio.rdfxml.RDFXMLWriter;

public class RepositoryHelper {


    public void registerConcepts(ElmoModule module) {
        module.addConcept(org.openprovenance.rdf.Edge.class);
        module.addConcept(org.openprovenance.rdf.Node.class);
        module.addConcept(org.openprovenance.rdf.OPMGraph.class);
        module.addConcept(org.openprovenance.rdf.Account.class);
        module.addConcept(org.openprovenance.rdf.Artifact.class);
        module.addConcept(org.openprovenance.rdf.Process.class);
        module.addConcept(org.openprovenance.rdf.Agent.class);
        module.addConcept(org.openprovenance.rdf.Role.class);
        module.addConcept(org.openprovenance.rdf.Used.class);
        module.addConcept(org.openprovenance.rdf.WasGeneratedBy.class);
        module.addConcept(org.openprovenance.rdf.WasDerivedFrom.class);
        module.addConcept(org.openprovenance.rdf.WasTriggeredBy.class);
        module.addConcept(org.openprovenance.rdf.WasControlledBy.class);
        module.addConcept(org.openprovenance.rdf.Annotation.class);
        module.addConcept(org.openprovenance.rdf.Property.class);
        module.addConcept(org.openprovenance.rdf.OTime.class);
    }

    public void setPrefixes(RDFHandler serialiser,
                            Collection<String[]> prefixes) throws org.openrdf.rio.RDFHandlerException {
            serialiser.handleNamespace("opm","http://openprovenance.org/ontology#");
            for (String[] prefix: prefixes) {
                serialiser.handleNamespace(prefix[0],prefix[1]);
            }
    }


    public void dumpToRDF(File file,
                          SesameManager manager,
                          RDFFormat format,
                          Collection<String[]> prefixes) throws Exception {
        Writer writer = new FileWriter(file);
        RDFHandler serialiser=null;
        if (format.equals(RDFFormat.N3)) {
            serialiser=new N3Writer(writer);
        } else if (format.equals(RDFFormat.RDFXML)) {
            serialiser=new RDFXMLWriter(writer);
        } else if  (format.equals(RDFFormat.NTRIPLES)) {
            serialiser=new NTriplesWriter (writer);
        }
        setPrefixes(serialiser,prefixes);
        manager.getConnection().export(serialiser);
        writer.close();
    }

    public void readFromRDF(File file, String uri, SesameManager manager, RDFFormat format) throws Exception {
        manager.getConnection().add(file,uri,format);
    }

    static String TEST_NS="http://example.com/";

    public static void main(String [] args) throws Exception {
        if ((args==null) || (args.length!=3)) {
            System.out.println("Usage: opmconvert [-rdf2xml | -xml2rdf] fileIn fileOut NS");
            return;
        }
        if (args[0].equals("-rdf2xml")) {
            String fileIn=args[1];
            String fileOut=args[2];

            RepositoryHelper rHelper=new RepositoryHelper();
            rHelper.rdfToXml(fileIn,fileOut,TEST_NS,"gr1");
            return;
        }
        //TODO: other options here

        if (args[0].equals("-xml2rdf")) {
            String fileIn=args[1];
            String fileOut=args[2];

            RepositoryHelper rHelper=new RepositoryHelper();
            rHelper.xmlToRdf(fileIn,fileOut,TEST_NS);
            return;
        }
    }

    public void rdfToXml(String fileIn, String fileOut, String NS, String graphId) throws Exception {
        ElmoModule module = new ElmoModule();
        registerConcepts(module);
        ElmoManagerFactory factory=new SesameManagerFactory(module);
        ElmoManager manager = factory.createElmoManager();

        RdfOPMFactory oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,NS),
                                                 manager);
        File file = new File(fileIn);
        readFromRDF(file,null,(SesameManager)manager,RDFFormat.RDFXML);

        QName qname = new QName(NS, graphId);
        Object o=manager.find(qname);
        org.openprovenance.rdf.OPMGraph gr=(org.openprovenance.rdf.OPMGraph)o;
        OPMGraph oGraph=oFactory.newOPMGraph(gr);
        OPMSerialiser.getThreadOPMSerialiser().serialiseOPMGraph(new File(fileOut),oGraph,true);
    }

    public void xmlToRdf(String fileIn, String fileOut, String NS) throws Exception {
        ElmoModule module = new ElmoModule();
        registerConcepts(module);
        ElmoManagerFactory factory=new SesameManagerFactory(module);
        ElmoManager manager = factory.createElmoManager();

        RdfOPMFactory oFactory=new RdfOPMFactory(new RdfObjectFactory(manager,NS));

        OPMGraph oGraph=OPMDeserialiser.getThreadOPMDeserialiser().deserialiseOPMGraph(new File(fileIn));

        String graphId=oGraph.getId();

        //TODO:
        System.out.println("CREATE RDFOPMGraph VALUES");

        Collection<String[]> prefixes=Collections.singleton(new String[]{"ex",TEST_NS});
        
            
        dumpToRDF(new File(fileOut),(SesameManager)manager,RDFFormat.RDFXML,prefixes);
    }


}