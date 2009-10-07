package org.openprovenance.model;

import java.util.List;

public interface Edge extends Id {
    Ref getCause();
    Ref getEffect();
    List<AccountRef> getAccount();
} 