package org.openprovenance.model.extension;

import  org.openprovenance.model.OPMToDot;
import  org.openprovenance.model.OPMDeserialiser;
import  org.openprovenance.model.Edge;

import java.util.HashMap;

import org.openprovenance.model.NamedWasDerivedFrom;



public class ExtOPMToDot extends OPMToDot {

    public void addName(Edge e, HashMap<String,String> properties) {
        if (e instanceof NamedWasDerivedFrom) {
            NamedWasDerivedFrom edge=(NamedWasDerivedFrom) e;
            properties.put("label",convertEdgeLabel(edge.getType()));
        }
    }

    public String convertEdgeLabel(String label) {
        return label;
    }

    public OPMDeserialiser getDeserialiser() {
        return OPMDeserialiser.getThreadOPMDeserialiser();
    }
}