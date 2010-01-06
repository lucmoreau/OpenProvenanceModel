package org.openprovenance.elmo;
import org.openrdf.elmo.annotations.rdf;
import org.openrdf.elmo.annotations.factory;

import org.openprovenance.model.Process;
import org.openprovenance.model.Artifact;
import org.openprovenance.model.Used;

import org.openrdf.elmo.ElmoManager;


@rdf("http://www.ipaw.info/2007/opm#Artifact")
public class RdfObjectFactory extends org.openprovenance.model.ObjectFactory {

    final ElmoManager manager;
    final String prefix;
    

    public RdfObjectFactory(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
    }

    public Artifact createArtifact() {
        return new RdfArtifact(manager,prefix);
    }

    public Process createProcess() {
        return new RdfProcess(manager,prefix);
    }

    public Used createUsed() {
        return new RdfUsed(manager,prefix);
    }

    /** Don't understand how this is used! */

    @factory
    public org.openprovenance.rdf.Artifact createArtifact2(org.openprovenance.rdf.Artifact a) {
        System.out.println("*************************** " + a);
        return a;
    }

}
