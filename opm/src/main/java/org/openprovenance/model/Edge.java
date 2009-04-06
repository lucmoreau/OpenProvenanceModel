package org.openprovenance.model;

import java.util.List;

public interface Edge {
    Id getCause();
    Id getEffect();
    List<AccountId> getAccount();
} 