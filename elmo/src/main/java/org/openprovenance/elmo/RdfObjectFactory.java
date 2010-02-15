package org.openprovenance.elmo;
import org.openrdf.elmo.annotations.rdf;
import org.openrdf.elmo.annotations.factory;

import org.openprovenance.model.Process;
import org.openprovenance.model.Artifact;
import org.openprovenance.model.Agent;
import org.openprovenance.model.Used;
import org.openprovenance.model.Role;
import org.openprovenance.model.Annotation;
import org.openprovenance.model.EmbeddedAnnotation;
import org.openprovenance.model.Account;
import org.openprovenance.model.Property;
import org.openprovenance.model.Reference;
import org.openprovenance.model.OPMGraph;
import org.openprovenance.model.WasGeneratedBy;
import org.openprovenance.model.WasDerivedFrom;
import org.openprovenance.model.WasTriggeredBy;
import org.openprovenance.model.WasControlledBy;
import org.openprovenance.model.Label;
import org.openprovenance.model.Type;
import org.openprovenance.model.PName;
import org.openprovenance.model.Profile;
import org.openprovenance.model.Value;
import org.openprovenance.model.OTime;

import org.openrdf.elmo.ElmoManager;



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

    public Property createProperty() {
        return new RdfProperty(manager,prefix);
    }

    public Role createRole() {
        return new RdfRole(manager,prefix);
    }

    public Account createAccount() {
        return new RdfAccount(manager,prefix);
    }

    public Annotation createAnnotation() {
        return new RdfAnnotation(manager,prefix);
    }


    public EmbeddedAnnotation createEmbeddedAnnotation() {
        return new RdfEmbeddedAnnotation(manager,prefix);
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

    public OPMGraph createOPMGraph() {
        return new RdfOPMGraph(manager,prefix);
    }

    public Label createLabel() {
        return new RdfLabel(manager,prefix);
    }


    public Type createType() {
        return new RdfType(manager,prefix);
    }

    public PName createPName() {
        return new RdfPName(manager,prefix);
    }

    public Profile createProfile() {
        return new RdfProfile(manager,prefix);
    }

    public Value createValue() {
        return new RdfValue(manager,prefix);
    }

    public Reference createReference() {
        return new RdfReference(manager,prefix);
    }

    public OTime createOTime() {
        return new RdfOTime(manager,prefix);
    }


}
