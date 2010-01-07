package org.openprovenance.elmo;

import javax.xml.namespace.QName;


public interface HasFacade<TYPE> {

    QName getQName();
    TYPE findMyFacade();
}

