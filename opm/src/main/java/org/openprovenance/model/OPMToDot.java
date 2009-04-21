package org.openprovenance.model;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.File;
import java.io.InputStream;
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
    public final static String DEFAULT_CONFIGURATION_FILE="defaultConfig.xml";
    public final static String USAGE="opm2dot opmFile.xml out.dot out.pdf [configuration.xml]";

    OPMUtilities u=new OPMUtilities();
    OPMFactory of=new OPMFactory();



    public static void main(String [] args) throws Exception {
        if ((args==null) || (args.length==0) || (args.length>4)) {
            System.out.println(USAGE);
            return;
        }

        String opmFile=args[0];
        String outDot=args[1];
        String outPdf=args[2];
        String configFile=((args.length==4) ? args[3] : null);

        OPMToDot converter=((configFile==null) ? new OPMToDot() : new OPMToDot(configFile));

        converter.convert(opmFile,outDot,outPdf);
    }


    public OPMToDot() {
        InputStream is=this.getClass().getClassLoader().getResourceAsStream(DEFAULT_CONFIGURATION_FILE);
        init(is);
    }

    public OPMToDot(String configurationFile) {
        this();
        init(configurationFile);
    }

    public void init(String configurationFile) {
        OPMDeserialiser deserial=OPMDeserialiser.getThreadOPMDeserialiser();
        try {
            OPMPrinterConfiguration opc=deserial.deserialiseOPMPrinterConfiguration(new File(configurationFile));
            init(opc);
        } catch (JAXBException je) {
            je.printStackTrace();
        }
    }

    public void init(InputStream is) {
        OPMDeserialiser deserial=OPMDeserialiser.getThreadOPMDeserialiser();
        try {
            OPMPrinterConfiguration opc=deserial.deserialiseOPMPrinterConfiguration(is);
            init(opc);
        } catch (JAXBException je) {
            je.printStackTrace();
        }
    }

    public void init(OPMPrinterConfiguration configuration) {
        if (configuration==null) return;

        if (configuration.getEdges()!=null) {
            if (configuration.getEdges().getDefault()!=null) {
                defaultEdgeStyle=configuration.getEdges().getDefault();
            }
            
            for (EdgeStyleMapEntry edge: configuration.getEdges().getEdge()) {
                edgeStyleMap.put(edge.getType(),edge.getStyle());
            }
        }

        if (configuration.getProcesses()!=null) {
            if (configuration.getProcesses().isDisplayValue()!=null) {
                this.displayProcessValue=configuration.getProcesses().isDisplayValue();
            }
            if (configuration.getProcesses().isColoredAsAccount()!=null) {
                this.displayProcessColor=configuration.getProcesses().isColoredAsAccount();
            }
            for (ProcessMapEntry process: configuration.getProcesses().getProcess()) {
                processNameMap.put(process.getValue(),process.getDisplay());
            }
        }

        if (configuration.getArtifacts()!=null) {
            if (configuration.getArtifacts().isDisplayValue()!=null) {
                this.displayArtifactValue=configuration.getArtifacts().isDisplayValue();
            }
            if (configuration.getArtifacts().isColoredAsAccount()!=null) {
                this.displayArtifactColor=configuration.getArtifacts().isColoredAsAccount();
            }
            for (ArtifactMapEntry artifact: configuration.getArtifacts().getArtifact()) {
                artifactNameMap.put(artifact.getValue(),artifact.getDisplay());
            }
        }

        if (configuration.getAgents()!=null) {
            if (configuration.getAgents().isDisplayValue()!=null) {
                this.displayAgentValue=configuration.getAgents().isDisplayValue();
            }
            if (configuration.getAgents().isColoredAsAccount()!=null) {
                this.displayAgentColor=configuration.getAgents().isColoredAsAccount();
            }
            for (AgentMapEntry agent: configuration.getAgents().getAgent()) {
                agentNameMap.put(agent.getValue(),agent.getDisplay());
            }
        }

        if (configuration.getAccounts()!=null) {
            if (configuration.getAccounts().getDefaultAccount()!=null) {
                this.defaultAccountLabel=configuration.getAccounts().getDefaultAccount();
            }
            if (configuration.getAccounts().getDefaultColor()!=null) {
                this.defaultAccountColor=configuration.getAccounts().getDefaultColor();
            }
            for (AccountColorMapEntry account: configuration.getAccounts().getAccount()) {
                accountColourMap.put(account.getName(),account.getColor());
            }
        }

        if (configuration.getGraphName()!=null) {
            this.name=configuration.getGraphName();
        }

    }

    public void convert(String opmFile, String dotFile, String pdfFile)
        throws java.io.FileNotFoundException, java.io.IOException, JAXBException {
        convert (OPMDeserialiser.getThreadOPMDeserialiser().deserialiseOPMGraph(new File(opmFile)),dotFile,pdfFile);
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
                 addProcessShape(p,addProcessLabel(p, addProcessColor(p,properties))),
                 out);
    }

    public void emitArtifact(Artifact a, PrintStream out) {
        HashMap<String,String> properties=new HashMap();

        emitNode(a.getId(),
                 addArtifactShape(a,addArtifactLabel(a, addArtifactColor(a,properties))),
                 out);
    }

    public void emitAgent(Agent ag, PrintStream out) {
        HashMap<String,String> properties=new HashMap();

        emitNode(ag.getId(),
                 addAgentShape(ag,addAgentLabel(ag, addAgentColor(ag,properties))),
                 out);
    }

    

    public HashMap<String,String> addProcessShape(Process p, HashMap<String,String> properties) {
        properties.put("shape","polygon");
        properties.put("sides","4");
        return properties;
    }

    public HashMap<String,String> addProcessLabel(Process p, HashMap<String,String> properties) {
        properties.put("label",processLabel(p));
        return properties;
    }

    public HashMap<String,String> addProcessColor(Process p, HashMap<String,String> properties) {
        if (displayProcessColor) {
            properties.put("color",processColor(p));
            properties.put("fontcolor",processColor(p));
        }
        return properties;
    }

    public HashMap<String,String> addArtifactShape(Artifact p, HashMap<String,String> properties) {
        // default is good for artifact
        return properties;
    }

    public HashMap<String,String> addArtifactColor(Artifact a, HashMap<String,String> properties) {
        if (displayArtifactColor) {
            properties.put("color",artifactColor(a));
            properties.put("fontcolor",artifactColor(a));
        }
        return properties;
    }

    public HashMap<String,String> addArtifactLabel(Artifact p, HashMap<String,String> properties) {
        properties.put("label",artifactLabel(p));
        return properties;
    }

    public HashMap<String,String> addAgentShape(Agent p, HashMap<String,String> properties) {
        properties.put("shape","polygon");
        properties.put("sides","5");
        return properties;
    }

    public HashMap<String,String> addAgentLabel(Agent p, HashMap<String,String> properties) {
        properties.put("label",agentLabel(p));
        return properties;
    }

    public HashMap<String,String> addAgentColor(Agent a, HashMap<String,String> properties) {
        if (displayAgentColor) {
            properties.put("color",agentColor(a));
            properties.put("fontcolor",agentColor(a));
        }
        return properties;
    }


    boolean displayProcessValue=false;
    boolean displayProcessColor=false;
    boolean displayArtifactValue=false;
    boolean displayArtifactColor=false;
    boolean displayAgentColor=false;
    boolean displayAgentValue=false;

    public String processLabel(Process p) {
        if (displayProcessValue) {
            return convertProcessName(""+p.getValue());
        } else {
            return p.getId();
        }
    }
    public String processColor(Process p) {
        // Note, I should compute effective account membership
        List<String> colors=new LinkedList();
        for (AccountId acc: p.getAccount()) {
            String accountLabel=((Account)acc.getId()).getId();
            String colour=convertAccount(accountLabel);
            colors.add(colour);
        }

        return selectColor(colors);
    }

    // returns the first non transparent color
    public String selectColor(List<String> colors) {
        String tr="transparent";
        for (String c: colors) {
            if (!(c.equals(tr))) return c;
        }
        return tr;
    }
        
    public String artifactLabel(Artifact p) {
        if (displayArtifactValue) {
            return convertArtifactName(""+p.getValue());
        } else {
            return p.getId();
        }
    }
    public String artifactColor(Artifact p) {
        // Note, I should compute effective account membership
        List<String> colors=new LinkedList();
        for (AccountId acc: p.getAccount()) {
            String accountLabel=((Account)acc.getId()).getId();
            String colour=convertAccount(accountLabel);
            colors.add(colour);
        }
        return selectColor(colors);
    }
    public String agentColor(Agent p) {
        // Note, I should compute effective account membership
        List<String> colors=new LinkedList();
        for (AccountId acc: p.getAccount()) {
            String accountLabel=((Account)acc.getId()).getId();
            String colour=convertAccount(accountLabel);
            colors.add(colour);
        }
        return selectColor(colors);
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
        return defaultAccountColor;
    }

    String defaultEdgeStyle;
    HashMap<String,String> edgeStyleMap=new HashMap<String,String>();
    public String getEdgeStyle(Edge edge) {
        String name=edge.getClass().getName();
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
    String defaultAccountColor;


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
        boolean first=true;
        for (String key: properties.keySet()) {
            if (first) {
                first=false;
            } else {
                sb.append(",");
            }
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


