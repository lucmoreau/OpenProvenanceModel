package org.openprovenance.reproduce;

import java.util.HashMap;
import java.util.List;

import org.openprovenance.model.Artifact;
import org.openprovenance.model.Process;
import org.openprovenance.model.OPMFactory;


import org.xml.sax.SAXException;
import java.io.IOException;


public class ExecuteFactory {

    public Execute newInstance(String procedure,
                               OPMFactory oFactory,
                               ArtifactFactory artifactFactory) throws SAXException, IOException {
        if (procedure.startsWith("http://openprovenance.org/reproducibility/java")) {
            return new JavaExecute(oFactory,artifactFactory);
        }
        if (procedure.startsWith("http://openprovenance.org/reproducibility/swift")) {
            return new SwiftExecute(oFactory,artifactFactory);
        }
        throw new UnsupportedOperationException(procedure);
    }

            
}