package org.openprovenance.model;

import java.util.List;

public interface Edge {
    IdRef getCause();
    IdRef getEffect();
    List<AccountId> getAccount();
} 