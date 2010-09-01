package org.openprovenance.reproduce;
import java.util.Collection;
import org.openprovenance.model.OPMGraph;
import org.openprovenance.model.Used;
import org.openprovenance.model.WasGeneratedBy;
import org.openprovenance.model.Account;
import org.openprovenance.model.Process;
import org.openprovenance.model.Role;
import org.openprovenance.model.Artifact;

public interface GraphFactory {
    OPMGraph getNewGraph();
    Used addUsed(Process p,
                 Role role,
                 Artifact a,
                 Collection<Account> accounts);
    WasGeneratedBy addWasGeneratedBy(WasGeneratedBy wasGeneratedBy);
}