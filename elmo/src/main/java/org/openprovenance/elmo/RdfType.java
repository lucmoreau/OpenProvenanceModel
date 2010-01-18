package org.openprovenance.elmo;
import java.util.Set;
import java.net.URI;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;

import javax.xml.namespace.QName;
import org.openrdf.elmo.ElmoManager;
import org.openprovenance.model.Annotable;
import org.openrdf.model.Statement;
import org.openrdf.elmo.sesame.SesameManager;

import org.openprovenance.model.CommonURIs;

public class RdfType extends org.openprovenance.model.Type implements CompactAnnotation, CommonURIs {

    ElmoManager manager;
    String prefix;
    QName qname;

    static int count=0;

    public RdfType(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
    }


    public void toRdf(Annotable entity) throws org.openrdf.repository.RepositoryException {
        org.openprovenance.rdf.Annotable subject=(org.openprovenance.rdf.Annotable)((HasFacade)entity).findMyFacade();
        subject.getTypes().add(URI.create(getValue()));
    }


}
