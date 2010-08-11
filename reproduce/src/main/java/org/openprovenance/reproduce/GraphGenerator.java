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

        init("//home/lavm/papers/papers/opmowl/OpenProvenanceModel/reproduce/src/test/resources/pc1/");
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
        String path=table.get(id);
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


    Hashtable<String,String> table=new Hashtable();

    public void init(String where) {
        table.put("a1",  where + "reference.img");
        table.put("a2",  where + "reference.hdr");
        table.put("a3",  where + "anatomy1.img");
        table.put("a4",  where + "anatomy1.hdr");
        table.put("a5",  where + "anatomy2.img");
        table.put("a6",  where + "anatomy2.hdr");
        table.put("a7",  where + "anatomy3.img");
        table.put("a8",  where + "anatomy3.hdr");
        table.put("a9",  where + "anatomy4.img");
        table.put("a10", where + "anatomy4.hdr");

    }

}

    
