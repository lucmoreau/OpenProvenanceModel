package org.openprovenance.model.extension;

import  org.openprovenance.model.OPMToDot;
import  org.openprovenance.model.OPMDeserialiser;
import  org.openprovenance.model.Edge;

import java.util.HashMap;

import org.openprovenance.model.extension.NamedWasDerivedFrom;
import org.openprovenance.model.extension.NamedWasControlledBy;



public class ExtOPMToDot extends OPMToDot {

    public void addEdgeName(Edge e, HashMap<String,String> properties) {
        super.addEdgeName(e,properties);
        if (e instanceof NamedWasDerivedFrom) {
            NamedWasDerivedFrom edge=(NamedWasDerivedFrom) e;
            properties.put("label",convertEdgeLabel(edge.getType()));
            properties.put("labelfontsize","8");
        }
        if (e instanceof NamedWasControlledBy) {
            NamedWasControlledBy edge=(NamedWasControlledBy) e;
            properties.put("label",convertEdgeLabel(edge.getType()));
            properties.put("labelfontsize","8");
        }
    }

    public String convertEdgeLabel(String label) {
        return label.substring(label.indexOf("#")+1, label.length());
    }

    


}