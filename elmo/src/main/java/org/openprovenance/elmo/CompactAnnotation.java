package org.openprovenance.elmo;
import org.openprovenance.model.Annotable;

public interface CompactAnnotation {
    void toRdf(Annotable entity) throws org.openrdf.repository.RepositoryException ;
}