package org.openprovenance.elmo;
import java.util.Set;
import org.openprovenance.rdf.Account;
import org.openprovenance.rdf.Node;
import org.openprovenance.rdf.AnnotationOrEdgeOrNode;

import javax.xml.namespace.QName;
import org.openrdf.elmo.ElmoManager;

public class RdfEmbeddedAnnotation extends RdfAnnotation {

    public RdfEmbeddedAnnotation(ElmoManager manager, String prefix) {
        super(manager,prefix);
    }


}
