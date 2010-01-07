package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;
import org.openprovenance.rdf.Edge;
import org.openprovenance.rdf.Process;
import org.openprovenance.rdf.Agent;
import org.openprovenance.rdf.Artifact;

import javax.xml.namespace.QName;
import org.openrdf.elmo.ElmoManager;

public class RdfOPMGraph extends org.openprovenance.model.OPMGraph implements org.openprovenance.rdf.OPMGraph, HasFacade {

    ElmoManager manager;
    String prefix;
    QName qname;

    public RdfOPMGraph(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
    }

    public void setId(String value) {
        super.setId(value);
        qname = new QName(prefix, value);
        manager.designate(qname, org.openprovenance.rdf.OPMGraph.class);
    }

    public QName getQName() {
        return qname;
    }

    public org.openprovenance.rdf.OPMGraph findMyFacade() {
        org.openprovenance.rdf.OPMGraph gr=(org.openprovenance.rdf.OPMGraph)manager.find(getQName());
        return gr;
    }

    public void setHasProcess(Set<? extends Process> processes)  {
        throw new UnsupportedOperationException();
    }

    public Set<Process> getHasProcess()  {
         throw new UnsupportedOperationException();
    }


    public void setHasAgent(Set<? extends Agent> agents)  {
        throw new UnsupportedOperationException();
    }

    public Set<Agent> getHasAgent()  {
        throw new UnsupportedOperationException();
    }


    public void setHasArtifact(Set<? extends Artifact> artifacts)  {
        throw new UnsupportedOperationException();
    }

    public Set<Artifact> getHasArtifact()  {
        throw new UnsupportedOperationException();
    }


    public void setHasDependency(Set<? extends Edge> edges)  {
        throw new UnsupportedOperationException();
    }

    public Set<Edge> getHasDependency()  {
        throw new UnsupportedOperationException();
    }

    public void setHasAccount(Set<? extends Account> accounts)  {
        throw new UnsupportedOperationException();
    }

    public Set<Account> getHasAccount()  {
        throw new UnsupportedOperationException();
    }


}
