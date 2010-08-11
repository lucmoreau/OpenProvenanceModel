package org.openprovenance.reproduce;
import java.util.LinkedList;
import java.util.HashMap;
import org.openprovenance.model.Artifact;
import org.openprovenance.model.Process;
import org.openprovenance.model.OPMFactory;
import org.openprovenance.model.IndexedOPMGraph;
import org.openprovenance.model.OPMGraph;
import org.openprovenance.model.Used;
import org.openprovenance.model.WasGeneratedBy;


public class GraphGenerator implements ArtifactFactory, ProcessFactory, GraphFactory {
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
}

    
