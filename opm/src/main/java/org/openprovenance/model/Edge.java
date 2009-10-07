package org.openprovenance.model;

import java.util.List;

public interface Edge extends Id {
    IdRef getCause();
    IdRef getEffect();
    List<AccountId> getAccount();
} 