package org.openprovenance.model.collections;

import java.util.Collection;
import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;



import org.openprovenance.model.ArtifactRef;
import org.openprovenance.model.Artifact;
import org.openprovenance.model.AccountRef;
import org.openprovenance.model.WasDerivedFrom;
import org.openprovenance.model.Account;
import org.openprovenance.model.OPMFactory;




/** An extended Factory of OPM objects related to Collections.
    Needs to be moved in a separate module probably, since it is an extension*/

public class CollectionFactory  implements CollectionURIs {

    final private OPMFactory oFactory;

    public CollectionFactory(OPMFactory oFactory) {
        this.oFactory=oFactory;
    }
   

//     public WasDerivedFrom newContained(String id,
//                                        ArtifactRef aid1,
//                                        ArtifactRef aid2,
//                                        Collection<AccountRef> accounts) {
//         return oFactory.newWasDerivedFrom(id,aid1,aid2,CONTAINED,accounts);
//     }

    public WasDerivedFrom newContained(String id,
                                       Artifact a1,
                                       Artifact a2,
                                       Collection<Account> accounts) {
        return oFactory.newWasDerivedFrom(id,a1,a2,CONTAINED,accounts);
    }


//     public WasDerivedFrom newWasIdenticalTo(String id,
//                                             ArtifactRef aid1,
//                                             ArtifactRef aid2,
//                                             Collection<AccountRef> accounts) {
//         return oFactory.newWasDerivedFrom(id,aid1,aid2,WASIDENTICALTO,accounts);
//     }

    public WasDerivedFrom newWasIdenticalTo(String id,
                                            Artifact a1,
                                            Artifact a2,
                                            Collection<Account> accounts) {
        return oFactory.newWasDerivedFrom(id,a1,a2,WASIDENTICALTO,accounts);
    }





}

