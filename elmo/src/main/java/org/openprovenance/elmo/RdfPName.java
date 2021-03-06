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

public class RdfPName extends org.openprovenance.model.PName implements CompactAnnotation, CommonURIs {

    ElmoManager manager;
    String prefix;
    QName qname;

    static int count=0;

    public RdfPName(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
    }


    public void toRdf(Annotable entity) throws org.openrdf.repository.RepositoryException {
        org.openprovenance.rdf.Annotable subject=(org.openprovenance.rdf.Annotable)((HasFacade)entity).findMyFacade();
        subject.getPnames().add(URI.create(getValue()));
    }

    public void toRdf_DELETE(Annotable entity) throws org.openrdf.repository.RepositoryException {
        QName subject=((HasFacade)entity).getQName();
        Statement stmnt=new org.openrdf.model.impl.StatementImpl(new org.openrdf.model.impl.URIImpl(subject.getNamespaceURI()+subject.getLocalPart()),
                                                                 new org.openrdf.model.impl.URIImpl(NEW_PNAME_PROPERTY),
                                                                 new org.openrdf.model.impl.LiteralImpl(getValue()));

        ((SesameManager)manager).getConnection().add(stmnt);

    }


}
