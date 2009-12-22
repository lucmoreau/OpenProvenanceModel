package org.openprovenance.elmo;
import org.openrdf.elmo.annotations.rdf;
import org.openrdf.elmo.annotations.factory;

import org.openprovenance.model.Process;
import org.openprovenance.model.Artifact;
import org.openprovenance.model.Used;


@rdf("http://www.ipaw.info/2007/opm#Artifact")
public class RdfObjectFactory extends org.openprovenance.model.ObjectFactory {

    public RdfObjectFactory() {
    }

    public Artifact createArtifact() {
        return new RdfArtifact();
    }

    public Process createProcess() {
        return new RdfProcess();
    }

    public Used createUsed() {
        return new RdfUsed();
    }

    /** Don't understand how this is used! */

    @factory
    public org.openprovenance.rdf.Artifact createArtifact2(org.openprovenance.rdf.Artifact a) {
        System.out.println("*************************** " + a);
        return a;
    }

}
