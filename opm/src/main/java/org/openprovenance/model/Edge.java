package org.openprovenance.model;

import java.util.List;

public interface Edge extends Annotable, HasAccounts {
    Ref getCause();
    Ref getEffect();
} 