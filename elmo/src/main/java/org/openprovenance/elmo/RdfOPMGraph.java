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

public class RdfOPMGraph extends org.openprovenance.model.OPMGraph implements HasFacade {

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

}
