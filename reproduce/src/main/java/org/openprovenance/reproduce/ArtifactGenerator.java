package org.openprovenance.reproduce;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.HashMap;
import org.openprovenance.model.Artifact;
import org.openprovenance.model.Process;
import org.openprovenance.model.OPMFactory;
import org.openprovenance.model.OPMGraph;
import org.openprovenance.model.OPMSerialiser;

public class ArtifactGenerator implements ArtifactFactory, ProcessFactory {
    public String artifactPrefix="_a_";
    public String processPrefix="_p_";

    private HashMap<String,String> aMap=new HashMap();
    private HashMap<String,String> pMap=new HashMap();

    public HashMap<String,String> getArtifactMap() {
        return aMap;
    }
    public HashMap<String,String> getProcessMap() {
        return pMap;
    }

    final OPMFactory oFactory;

    static int artifactCount=0;
    static int processCount=0;
    

    public ArtifactGenerator (OPMFactory oFactory) {
        this.oFactory=oFactory;
    }

    public String newArtifactId() {
        return artifactPrefix + (artifactCount++);
    }
    public String newProcessId() {
        return processPrefix + (processCount++);
    }

    public Artifact newArtifact(Artifact a) {
        Artifact n=oFactory.newArtifact(a);
        String nid=newArtifactId();
        n.setId(nid);
        aMap.put(a.getId(),nid);

        // System.out.println(" ==> " + a);
        // System.out.println(" ==> " + n);
        // try {
        //     OPMGraph g=oFactory.newOPMGraph();
        //     g.getArtifacts().getArtifact().add(a);
        //     g.getArtifacts().getArtifact().add(n);
        //     OPMSerialiser serial=OPMSerialiser.getThreadOPMSerialiser();
        //     StringWriter sw=new StringWriter();
        //     serial.serialiseOPMGraph(sw,g,true);
        //     System.out.println("$$$$$$$$$$$$$$$ " + sw.getBuffer());
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        return n;
    }

    public Process newProcess(Process a) {
        Process n=oFactory.newProcess(a);
        n.setId(newProcessId());
        return n;
    }
}

    
