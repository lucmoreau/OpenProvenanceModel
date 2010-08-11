package org.openprovenance.reproduce;

import org.openprovenance.model.Process;

public interface ProcessFactory {
    Process newProcess(Process o);
}