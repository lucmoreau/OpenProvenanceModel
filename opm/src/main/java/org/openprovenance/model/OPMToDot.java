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


/** Serialisation of  OPM Graphs to DOT format. */
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

        if (graph.getProcesses()!=null) {
            for (Process p: graph.getProcesses().getProcess()) {
                emitProcess(p,out);
            }
        }

        if (graph.getArtifacts()!=null) {
            for (Artifact p: graph.getArtifacts().getArtifact()) {
                emitArtifact(p,out);
            }
        }

        if (graph.getAgents()!=null) {
            for (Agent p: graph.getAgents().getAgent()) {
                emitAgent(p,out);
            }
        }

        for (Edge e: edges) {
            emitDependency(e,out);
        }
        

        postlude(out);
       
    }

    //////////////////////////////////////////////////////////////////////
    ///
    ///                              NODES
    ///
    //////////////////////////////////////////////////////////////////////

    public void emitProcess(Process p, PrintStream out) {
        HashMap<String,String> properties=new HashMap();

        emitNode(p.getId(),
                 addProcessShape(p,addProcessLabel(p, properties)),
                 out);
    }

    public void emitArtifact(Artifact a, PrintStream out) {
        HashMap<String,String> properties=new HashMap();

        emitNode(a.getId(),
                 addArtifactShape(a,addArtifactLabel(a, properties)),
                 out);
    }

    public void emitAgent(Agent ag, PrintStream out) {
        HashMap<String,String> properties=new HashMap();

        emitNode(ag.getId(),
                 addAgentShape(ag,addAgentLabel(ag, properties)),
                 out);
    }

    

    public HashMap<String,String> addProcessShape(Process p, HashMap<String,String> properties) {
        properties.put("shape","poligon");
        properties.put("sides","4");
        return properties;
    }

    public HashMap<String,String> addProcessLabel(Process p, HashMap<String,String> properties) {
        properties.put("label",processLabel(p));
        return properties;
    }

    public HashMap<String,String> addArtifactShape(Artifact p, HashMap<String,String> properties) {
        // default is good for artifact
        return properties;
    }

    public HashMap<String,String> addArtifactLabel(Artifact p, HashMap<String,String> properties) {
        properties.put("label",artifactLabel(p));
        return properties;
    }

    public HashMap<String,String> addAgentShape(Agent p, HashMap<String,String> properties) {
        properties.put("shape","poligon");
        properties.put("sides","5");
        return properties;
    }

    public HashMap<String,String> addAgentLabel(Agent p, HashMap<String,String> properties) {
        properties.put("label",agentLabel(p));
        return properties;
    }



    boolean displayProcessValue;
    boolean displayArtifactValue;
    boolean displayAgentValue;

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
    public String agentLabel(Agent p) {
        if (displayAgentValue) {
            return convertAgentName(""+p.getValue());
        } else {
            return p.getId();
        }
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
    HashMap<String,String> agentNameMap=new HashMap<String,String>();
    public String convertAgentName(String agent) {
        String name=agentNameMap.get(agent);
        if (name!=null) return name;
        return agent;
    }


    //////////////////////////////////////////////////////////////////////
    ///
    ///                              EDGES
    ///
    //////////////////////////////////////////////////////////////////////

    public void emitDependency(Edge e, PrintStream out) {
        HashMap<String,String> properties=new HashMap();

        List<AccountId> accounts=e.getAccount();
        if (accounts.isEmpty()) {
            accounts=new LinkedList();
            accounts.add(of.newAccountId(of.newAccount(defaultAccountLabel)));
        }
            
        for (AccountId acc: accounts) {
            String accountLabel=((Account)acc.getId()).getId();
            addEdgeAttributes(accountLabel,e,properties);
            emitEdge( ((Node)e.getEffect().getId()).getId(),
                      ((Node)e.getCause().getId()).getId(),
                      properties,
                      out);
        }
    }

    public HashMap<String,String> addEdgeAttributes(String accountLabel,
                                                    Edge e,
                                                    HashMap<String,String> properties) {
        String colour=convertAccount(accountLabel);
        properties.put("color",colour);
        properties.put("fontcolor",colour);
        properties.put("style",getEdgeStyle(e));
        return properties;
    }


    HashMap<String,String> accountColourMap=new HashMap<String,String>();
    public String convertAccount(String account) {
        String colour=accountColourMap.get(account);
        if (colour!=null) return colour;
        return account;
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

    
    //////////////////////////////////////////////////////////////////////
    ///
    ///                              DOT FORMAT GENERATION
    ///
    //////////////////////////////////////////////////////////////////////
    
    
    String name;
    String defaultAccountLabel;


    public void emitNode(String name, HashMap<String,String> properties, PrintStream out) {
        StringBuffer sb=new StringBuffer();
        sb.append(name);
        emitProperties(sb,properties);
        out.println(sb.toString());
    }


    public void emitEdge(String src, String dest, HashMap<String,String> properties, PrintStream out) {
        StringBuffer sb=new StringBuffer();
        sb.append(src);
        sb.append(" -> ");
        sb.append(dest);
        emitProperties(sb,properties);
        out.println(sb.toString());
    }

    public void emitProperties(StringBuffer sb,HashMap<String,String> properties) {
        sb.append(" [");
        for (String key: properties.keySet()) {
            String value=properties.get(key);
            sb.append(key);
            sb.append("=\"");
            sb.append(value);
            sb.append("\"");
        }
        sb.append("]");
    }

    void prelude(PrintStream out) {
        out.println("digraph " + name + " { rankdir=\"BT\"; ");
    }

    void postlude(PrintStream out) {
        out.println("}");
        out.close();
    }


    


    
}


