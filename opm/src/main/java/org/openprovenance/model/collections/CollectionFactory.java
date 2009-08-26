package org.openprovenance.model.collections;

import java.util.Collection;
import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;


import org.openprovenance.model.extension.OPMExtendedFactory;
import org.openprovenance.model.ArtifactId;
import org.openprovenance.model.Artifact;
import org.openprovenance.model.extension.ArtifactExt;
import org.openprovenance.model.AccountId;
import org.openprovenance.model.WasDerivedFrom;
import org.openprovenance.model.Account;
import org.openprovenance.model.extension.NamedWasDerivedFrom;



/** An extended Factory of OPM objects related to Collections.
    Needs to be moved in a separate module probably, since it is an extension*/

public class CollectionFactory  implements CollectionURIs {

    final private OPMExtendedFactory eFactory;

    public CollectionFactory(OPMExtendedFactory eFactory) {
        this.eFactory=eFactory;
    }
   

    public NamedWasDerivedFrom newContained(ArtifactId aid1,
                                            ArtifactId aid2,
                                            Collection<AccountId> accounts) {
        return eFactory.newNamedWasDerivedFrom(aid1,aid2,CONTAINED,accounts);
    }

    public NamedWasDerivedFrom newContained(Artifact a1,
                                            Artifact a2,
                                            Collection<Account> accounts) {
        return eFactory.newNamedWasDerivedFrom(a1,a2,CONTAINED,accounts);
    }


    public NamedWasDerivedFrom newWasIdenticalTo(ArtifactId aid1,
                                                 ArtifactId aid2,
                                                 Collection<AccountId> accounts) {
        return eFactory.newNamedWasDerivedFrom(aid1,aid2,WASIDENTICALTO,accounts);
    }

    public NamedWasDerivedFrom newWasIdenticalTo(Artifact a1,
                                                 Artifact a2,
                                                 Collection<Account> accounts) {
        return eFactory.newNamedWasDerivedFrom(a1,a2,WASIDENTICALTO,accounts);
    }





}

