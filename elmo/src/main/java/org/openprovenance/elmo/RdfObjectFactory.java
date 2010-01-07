package org.openprovenance.elmo;
import org.openrdf.elmo.annotations.rdf;
import org.openrdf.elmo.annotations.factory;

import org.openprovenance.model.Process;
import org.openprovenance.model.Artifact;
import org.openprovenance.model.Agent;
import org.openprovenance.model.Used;
import org.openprovenance.model.Role;
import org.openprovenance.model.Account;
import org.openprovenance.model.OPMGraph;
import org.openprovenance.model.WasGeneratedBy;
import org.openprovenance.model.WasDerivedFrom;
import org.openprovenance.model.WasTriggeredBy;
import org.openprovenance.model.WasControlledBy;

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


    public Agent createAgent() {
        return new RdfAgent(manager,prefix);
    }

    public Role createRole() {
        return new RdfRole(manager,prefix);
    }

    public Account createAccount() {
        return new RdfAccount(manager,prefix);
    }

    public Used createUsed() {
        return new RdfUsed(manager,prefix);
    }

    public WasGeneratedBy createWasGeneratedBy() {
        return new RdfWasGeneratedBy(manager,prefix);
    }

    public WasDerivedFrom createWasDerivedFrom() {
        return new RdfWasDerivedFrom(manager,prefix);
    }

    public WasTriggeredBy createWasTriggeredBy() {
        return new RdfWasTriggeredBy(manager,prefix);
    }

    public WasControlledBy createWasControlledBy() {
        return new RdfWasControlledBy(manager,prefix);
    }

    /** Don't understand how this is used! */

    @factory
    public org.openprovenance.rdf.Artifact createArtifact2(org.openprovenance.rdf.Artifact a) {
        System.out.println("*************************** " + a);
        return a;
    }

}
