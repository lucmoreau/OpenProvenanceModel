package org.openprovenance.elmo;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Collection;
import org.openrdf.elmo.ElmoModule;
import org.openrdf.elmo.sesame.SesameManager;
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


}