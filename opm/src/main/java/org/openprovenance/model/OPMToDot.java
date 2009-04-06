package org.openprovenance.model;
import java.util.List;
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


    public void convert(OPMGraph graph, File file) throws java.io.FileNotFoundException{
        OutputStream os=new FileOutputStream(file);
        convert(graph, new PrintStream(os));
    }

    public void convert(OPMGraph graph, PrintStream out) {
        List<Edge> edges=u.getEdges(graph);

        prelude(out);

        for (Process p: graph.getProcesses().getProcess()) {
            declareProcess(p,out);
        }

        for (Edge e: edges) {
            declareEdge(e,out);
        }

        postlude(out);
       
    }

    public void declareProcess(Process p, PrintStream out) {
        out.println(p.getId() + "[shape=polygon,sides=4]");  //,fontcolor="red",color="red"]
    }

    public void declareEdge(Edge e, PrintStream out) {
        out.println( ((Node)e.getEffect().getId()).getId() + " -> " + ((Node)e.getCause().getId()).getId());
    }
    

    String name="OPMGraph";

    void prelude(PrintStream out) {
        out.println("digraph " + name + " { rankdir=\"BT\"; ");
    }

    void postlude(PrintStream out) {
        out.println("}");
    }


    


    
}


