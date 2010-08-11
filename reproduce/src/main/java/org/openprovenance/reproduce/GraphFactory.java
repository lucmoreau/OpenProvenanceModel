package org.openprovenance.reproduce;
import org.openprovenance.model.OPMGraph;
import org.openprovenance.model.Used;
import org.openprovenance.model.WasGeneratedBy;

public interface GraphFactory {
    OPMGraph getNewGraph();
    Used addUsed(Used used);
    WasGeneratedBy addWasGeneratedBy(WasGeneratedBy wasGeneratedBy);
}