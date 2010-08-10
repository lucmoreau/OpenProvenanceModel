package org.openprovenance.reproduce;

public class OpenProvenanceEnvironment implements PrimitiveEnvironment {
    Hashtable<String,String> table=new Hashtable();

    public OpenProvenanceEnvironment() {
        table.put("http://openprovenance.org/primitives#align_warp",
                  "http://openprovenance.org/reproducibility/air#align_warp");
        
    }
    public String get(String o) {
        return table.get(o);
    }
}