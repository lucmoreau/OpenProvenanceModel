package org.openprovenance.reproduce;

import java.util.HashMap;
import java.util.List;

import org.openprovenance.model.Artifact;
import org.openprovenance.model.Process;
import org.openprovenance.model.OPMFactory;

import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.xml.sax.SAXException;
import java.io.IOException;


public class JavaExecute implements Execute {

    final OPMFactory oFactory;
    final ArtifactFactory artifactFactory;

    final Utilities u;

    public JavaExecute(OPMFactory oFactory, ArtifactFactory artifactFactory) throws SAXException, IOException {
        this.oFactory=oFactory;
        this.u=new Utilities(oFactory);
        this.artifactFactory=artifactFactory;
        u.loadLibrary("java.xml");
    }

    static int count=0;
    String prefix="_r_";

    public String newProcessName() {
        return prefix + "p" + (count++);
    }

    
    public Object prepareInvocationArguments(String procedure,
                                             HashMap<String,Artifact> arguments)
        throws org.jaxen.JaxenException {

        System.out.println("prepareInvocationArguments for " + procedure);

        List<?> procs=u.getDefinitionForUri(procedure);
        if ((procs==null)
            || (procs.size()==0)) {
            throw new NullPointerException();
        }

        Node proc=(Node)procs.get(0);


        List<?> ins=u.getInputs(proc);
        List<?> outs=u.getOutputs(proc);

        for (Object in: ins) {
            String role=u.getRole((Node)in);
            String name=u.getName((Node)in);
            String type=u.getType((Node)in);

            Artifact a=arguments.get(role);
            Artifact a2=artifactFactory.newArtifact(a);
            
        }

        

        return arguments;
    }

    String makeVariable(Artifact a) {
        return "var_" + a.getId();
    }


    void generateOutputForArtifact(Artifact a, String varType, Element call, Element program, Element types, Document doc) throws org.jaxen.JaxenException {
        Element output=doc.createElementNS(Utilities.swift_XML_NS,"swift:output");

        String type=oFactory.getType(a);
        if (type==null) throw new NullPointerException("Unknown type");
        if (type.equals("http://openprovenance.org/primitives#File")) {
            Element ref=doc.createElementNS(Utilities.swift_XML_NS,"swift:variableReference");
            ref.appendChild(doc.createTextNode(makeVariable(a)));
            output.appendChild(ref);

            Element var=doc.createElementNS(Utilities.swift_XML_NS,"swift:variable");
            Element file=doc.createElementNS(Utilities.swift_XML_NS,"swift:file");
            var.appendChild(file);
            var.setAttribute("name",makeVariable(a));
            var.setAttribute("type",varType);
            var.setAttribute("isGlobal","false");
            List value=oFactory.getPropertyValues(a,"http://openprovenance.org/primitives#path");
            file.setAttribute("name",makeFilename((String)value.get(0)));
            program.appendChild(var);

            addTypeDeclaration(types,varType,doc);

        }
        
        call.appendChild(output);
    }

    HashMap<String,Boolean> seen=new HashMap();
    public void  addTypeDeclaration(Element types,String varType, Document doc) throws org.jaxen.JaxenException {
        if (seen.get(varType)==null)  {
            seen.put(varType,true);
            Node n=(Node)u.getLibraryTypeDefinition(varType).get(0);
            Node nn=doc.importNode(n,true);
            types.appendChild(nn);
        }
    }

    public String makeFilename(String path) {
        return path;
    }

    public Object invoke(Object o, String name, Utilities u) throws IOException {
        HashMap<String,Artifact> arguments=(HashMap<String,Artifact>) o;
        System.out.println("JavaExecute: name " + name);
        System.out.println("JavaExecute: args " + arguments);
        return arguments;
    }


}