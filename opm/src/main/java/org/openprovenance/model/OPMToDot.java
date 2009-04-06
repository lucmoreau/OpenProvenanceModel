package org.openprovenance.model;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.File;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBElement;
import org.w3c.dom.Element;

/** Deserialiser of OPM Graphs. */
public class OPMToDot {

    OPMUtilities u=new OPMUtilities();
    OPMFactory of=new OPMFactory();

    public OPMToDot() {
        init();
    }

    public void init() {
        processNameMap.put("http://process.org/add1ToAll","add1ToAll");
        processNameMap.put("http://process.org/split","split");
        processNameMap.put("http://process.org/plus1","+1");
        processNameMap.put("http://process.org/cons","cons");
        processNameMap.put("http://process.org/fry","fry");
        processNameMap.put("http://process.org/bake","bake");
        processNameMap.put("http://process.org/badBake","badBake");
        edgeStyleMap.put("org.openprovenance.model.Used","dotted");
        edgeStyleMap.put("org.openprovenance.model.WasGeneratedBy","dotted");
        edgeStyleMap.put("org.openprovenance.model.WasDerivedFrom","bold");
        accountColourMap.put("orange","red");
        defaultEdgeStyle="filled";
        this.name="OPMGraph";
        this.defaultAccountLabel="black";
        this.displayProcessValue=true;
        this.displayArtifactValue=true;
    }

    public void convert(OPMGraph graph, String dotFile, String pdfFile)
        throws java.io.FileNotFoundException, java.io.IOException {
        convert(graph,new File(dotFile));
        Runtime runtime = Runtime.getRuntime();
        java.lang.Process proc = runtime.exec("dot -o " + pdfFile + " -Tpdf " + dotFile);
    }

    public void convert(OPMGraph graph, File file) throws java.io.FileNotFoundException{
        OutputStream os=new FileOutputStream(file);
        convert(graph, new PrintStream(os));
    }

    public void convert(OPMGraph graph, PrintStream out) {
        List<Edge> edges=u.getEdges(graph);

        prelude(out);

        for (Process p: graph.getProcesses().getProcess()) {
            emitProcess(p,out);
        }
        for (Artifact p: graph.getArtifacts().getArtifact()) {
            emitArtifact(p,out);
        }

        for (Edge e: edges) {
            emitEdge(e,out);
        }

        postlude(out);
       
    }

    public void emitProcess(Process p, PrintStream out) {
        out.println(p.getId() + "[shape=polygon,sides=4,label=\"" + processLabel(p) + "\"]");
    }

    public void emitArtifact(Artifact a, PrintStream out) {
        out.println(a.getId() + "[label=\"" + artifactLabel(a) + "\"]");
    }

    boolean displayProcessValue;
    boolean displayArtifactValue;

    public String processLabel(Process p) {
        if (displayProcessValue) {
            return convertProcessName(""+p.getValue());
        } else {
            return p.getId();
        }
    }
    public String artifactLabel(Artifact p) {
        if (displayArtifactValue) {
            return convertArtifactName(""+p.getValue());
        } else {
            return p.getId();
        }
    }

    public void emitEdge(Edge e, PrintStream out) {
        List<AccountId> accounts=e.getAccount();
        if (accounts.isEmpty()) {
            accounts=new LinkedList();
            accounts.add(of.newAccountId(of.newAccount(defaultAccountLabel)));
        }
            
        for (AccountId acc: accounts) {
            String accountLabel=((Account)acc.getId()).getId();
            out.print( ((Node)e.getEffect().getId()).getId() + " -> " + ((Node)e.getCause().getId()).getId() + " ");
            emitEdgeAttributes(accountLabel,e,out);
        }

    }

    public void emitEdgeAttributes(String accountLabel, Edge e, PrintStream out) {
        String colour=convertAccount(accountLabel);
        out.println("[color=\"" + colour + "\", fontcolor=\"" + colour + "\",style=\"" + getEdgeStyle(e) + "\"]");
    }

    HashMap<String,String> accountColourMap=new HashMap<String,String>();
    public String convertAccount(String account) {
        String colour=accountColourMap.get(account);
        if (colour!=null) return colour;
        return account;
    }

    HashMap<String,String> processNameMap=new HashMap<String,String>();
    public String convertProcessName(String process) {
        String name=processNameMap.get(process);
        if (name!=null) return name;
        return process;
    }
    HashMap<String,String> artifactNameMap=new HashMap<String,String>();
    public String convertArtifactName(String artifact) {
        String name=artifactNameMap.get(artifact);
        if (name!=null) return name;
        return artifact;
    }

    String defaultEdgeStyle;
    HashMap<String,String> edgeStyleMap=new HashMap<String,String>();
    public String getEdgeStyle(Edge edge) {
        String name=edge.getClass().getName();
        System.out.println("name " + name);
        String style=edgeStyleMap.get(name);
        if (style!=null) return style;
        return defaultEdgeStyle;
    }

    

    String name;
    String defaultAccountLabel;

    void prelude(PrintStream out) {
        out.println("digraph " + name + " { rankdir=\"BT\"; ");
    }

    void postlude(PrintStream out) {
        out.println("}");
        out.close();
    }


    


    
}


