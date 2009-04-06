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

        for (Edge e: edges) {
            emitEdge(e,out);
        }

        postlude(out);
       
    }

    public void emitProcess(Process p, PrintStream out) {
        out.println(p.getId() + "[shape=polygon,sides=4]");
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
        out.println("[color=\"" + colour + "\", fontcolor=\"" + colour + "\"]");
    }

    HashMap<String,String> accountColourMap=new HashMap<String,String>();
    public String convertAccount(String account) {
        String colour=accountColourMap.get(account);
        if (colour!=null) return colour;
        return account;
    }

    String name="OPMGraph";
    String defaultAccountLabel="black";

    void prelude(PrintStream out) {
        out.println("digraph " + name + " { rankdir=\"BT\"; ");
    }

    void postlude(PrintStream out) {
        out.println("}");
        out.close();
    }


    


    
}


