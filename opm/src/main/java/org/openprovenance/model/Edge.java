package org.openprovenance.model;

import java.util.List;

public interface Edge extends Annotable {
    Ref getCause();
    Ref getEffect();
    List<AccountRef> getAccount();
} 