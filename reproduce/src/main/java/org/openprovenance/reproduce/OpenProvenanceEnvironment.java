package org.openprovenance.reproduce;
import java.util.Hashtable;

public class OpenProvenanceEnvironment implements PrimitiveEnvironment {
    Hashtable<String,String> table=new Hashtable();

    public OpenProvenanceEnvironment() {
        table.put("http://openprovenance.org/primitives#align_warp",
                  "http://openprovenance.org/reproducibility/air#align_warp");
        table.put("http://openprovenance.org/primitives#reslice",
                  "http://openprovenance.org/reproducibility/air#reslice");
        table.put("http://openprovenance.org/primitives#softmean",
                  "http://openprovenance.org/reproducibility/air#softmean");
        table.put("http://openprovenance.org/primitives#slicer",
                  "http://openprovenance.org/reproducibility/air#slicer");
        table.put("http://openprovenance.org/primitives#convert",
                  "http://openprovenance.org/reproducibility/air#convert");
        
    }
    public String get(String o) {
        return table.get(o);
    }
}