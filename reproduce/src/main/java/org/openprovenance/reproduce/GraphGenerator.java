package org.openprovenance.reproduce;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Hashtable;
import org.openprovenance.model.Artifact;
import org.openprovenance.model.Process;
import org.openprovenance.model.OPMFactory;
import org.openprovenance.model.IndexedOPMGraph;
import org.openprovenance.model.EmbeddedAnnotation;
import org.openprovenance.model.OPMGraph;
import org.openprovenance.model.Used;
import org.openprovenance.model.Property;
import org.openprovenance.model.WasGeneratedBy;
import javax.xml.bind.JAXBElement;



public class GraphGenerator implements ArtifactFactory, ProcessFactory, GraphFactory {

    static String PATH_PROPERTY="http://openprovenance.org/primitives#path";
    static String VALUE_PROPERTY="http://openprovenance.org/primitives#value";

    public String artifactPrefix="_a_";
    public String processPrefix="_p_";
    public String usedPrefix="_u_";
    public String wasGeneratedByPrefix="_g_";

    private HashMap<String,String> aMap=new HashMap();
    private HashMap<String,String> pMap=new HashMap();

    final private IndexedOPMGraph nGraph;

    public OPMGraph getNewGraph() {
        return nGraph;
    }

    public HashMap<String,String> getArtifactMap() {
        return aMap;
    }
    public HashMap<String,String> getProcessMap() {
        return pMap;
    }

    final OPMFactory oFactory;

    static int artifactCount=0;
    static int processCount=0;
    static int usedCount=0;
    static int wasGeneratedByCount=0;
    

    public GraphGenerator (OPMFactory oFactory) {
        this.oFactory=oFactory;
        this.nGraph=new IndexedOPMGraph(oFactory,oFactory.newOPMGraph());

    }

    public String newArtifactId() {
        return artifactPrefix + (artifactCount++);
    }
    public String newProcessId() {
        return processPrefix + (processCount++);
    }
    public String newUsedId() {
        return usedPrefix + (usedCount++);
    }
    public String newWasGeneratedById() {
        return wasGeneratedByPrefix + (wasGeneratedByCount++);
    }

    public Artifact newArtifact(Artifact a) {
        String oid=aMap.get(a.getId());
        Artifact n;
        if (oid==null) {
            n=oFactory.newArtifact(a);
            updatePath(a.getId(),n);
            updateValue(a.getId(),n);
            String nid=newArtifactId();
            n.setId(nid);
            aMap.put(a.getId(),nid);
            nGraph.addArtifact(n);
        } else {
            n=nGraph.getArtifact(oid);
        }
        return n;
    }

    public Process newProcess(Process p) {
        Process n=oFactory.newProcess(p);
        String nid=newProcessId();
        n.setId(nid);
        pMap.put(p.getId(),nid);
        nGraph.addProcess(n);
        return n;
    }

    public Used addUsed(Used used) {
        String nid=newUsedId();
        used.setId(nid);
        return nGraph.addUsed(used);
    }
    public WasGeneratedBy addWasGeneratedBy(WasGeneratedBy wasGeneratedBy) {
        String nid=newWasGeneratedById();
        wasGeneratedBy.setId(nid);
        return nGraph.addWasGeneratedBy(wasGeneratedBy);
    }

    
    public void updatePath(String id, Artifact a) {
        String path=pathTable.get(id);
        if (path!=null) {
            for (JAXBElement<? extends EmbeddedAnnotation> jann: a.getAnnotation()) {
                EmbeddedAnnotation ann=jann.getValue();
                for (Property prop:ann.getProperty()) {
                    if (prop.getUri().equals(PATH_PROPERTY)) {
                        prop.setValue(path);
                    }
                }
            }
        }
    }


    public void updateValue(String id, Artifact a) {
        Object value=valueTable.get(id);
        if (value!=null) {
            for (JAXBElement<? extends EmbeddedAnnotation> jann: a.getAnnotation()) {
                EmbeddedAnnotation ann=jann.getValue();
                if (ann instanceof org.openprovenance.model.Value) {
                    org.openprovenance.model.Value val=(org.openprovenance.model.Value) ann;
                    val.setContent(value);
                } else {
                    for (Property prop:ann.getProperty()) {
                        //does not work if value is not explicit
                        if (prop.getUri().equals(VALUE_PROPERTY)) {
                            prop.setValue(value);
                        }
                    }
                }
            }
        }
    }


    Hashtable<String,String> pathTable=new Hashtable();
    Hashtable<String,Object> valueTable=new Hashtable();


    public void setPathTable(Hashtable<String,String> pathTable) {
        this.pathTable=pathTable;
    }
    public void setValueTable(Hashtable<String,Object> valueTable) {
        this.valueTable=valueTable;
    }

}

    
