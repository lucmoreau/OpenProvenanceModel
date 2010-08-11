package org.openprovenance.reproduce;

import java.util.HashMap;
import org.openprovenance.model.Process;

public interface ProcessFactory {
    Process newProcess(Process o);
    HashMap<String,String> getProcessMap();
}