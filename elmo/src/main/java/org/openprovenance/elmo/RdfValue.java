package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;

import javax.xml.namespace.QName;
import org.openrdf.elmo.ElmoManager;
import org.openprovenance.model.Annotable;
import org.openrdf.model.Statement;
import org.openrdf.elmo.sesame.SesameManager;

import org.openprovenance.model.CommonURIs;

public class RdfValue extends org.openprovenance.model.Value implements CompactAnnotation, CommonURIs {

    ElmoManager manager;
    String prefix;
    QName qname;

    static int count=0;

    public RdfValue(ElmoManager manager, String prefix) {
        this.manager=manager;
        this.prefix=prefix;
    }



    public void toRdf(Annotable entity) throws org.openrdf.repository.RepositoryException {
        QName subject=((HasFacade)entity).getQName();
        //TODO: currently only supporting string values

        Statement stmnt1=new org.openrdf.model.impl.StatementImpl(new org.openrdf.model.impl.URIImpl(subject.getNamespaceURI()+subject.getLocalPart()),
                                                                 new org.openrdf.model.impl.URIImpl(NEW_VALUE_PROPERTY),
                                                                  new org.openrdf.model.impl.LiteralImpl((String)getContent()));
        Statement stmnt2=new org.openrdf.model.impl.StatementImpl(new org.openrdf.model.impl.URIImpl(subject.getNamespaceURI()+subject.getLocalPart()),
                                                                 new org.openrdf.model.impl.URIImpl(NEW_ENCODING_PROPERTY),
                                                                 new org.openrdf.model.impl.LiteralImpl(getEncoding()));

        ((SesameManager)manager).getConnection().add(stmnt1);
        ((SesameManager)manager).getConnection().add(stmnt2);

    }


}
