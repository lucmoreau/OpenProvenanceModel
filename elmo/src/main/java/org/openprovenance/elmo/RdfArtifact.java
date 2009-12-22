package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;

public class RdfArtifact extends org.openprovenance.model.Artifact implements org.openprovenance.rdf.Artifact {

    public RdfArtifact() {
    }

    public void setNodeAccount(Set<? extends Account> accs) {
        for (Account acc: accs) {
            //getAccount().add(acc.getRef());
            throw new UnsupportedOperationException();
        }
    }

    public Set<Account> getNodeAccount() {
        throw new UnsupportedOperationException();
    }
        

	public Set<String> getNames() {
        throw new UnsupportedOperationException();
    }

	public void setNames(Set<? extends String> names) {
        throw new UnsupportedOperationException();
    }

}
