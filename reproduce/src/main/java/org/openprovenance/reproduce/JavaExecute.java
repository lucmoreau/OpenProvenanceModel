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

        HashMap<String,Object> args=new HashMap();

        for (Object in: ins) {
            String role=u.getRole((Node)in);
            String name=u.getName((Node)in);
            String type=u.getType((Node)in);

            Artifact a=arguments.get(role);
            Artifact a2=artifactFactory.newArtifact(a);
            args.put(role,oFactory.getValue(a2));            
        }


        // for (Object out: outs) {
        //     String role=u.getRole((Node)out);
        //     String name=u.getName((Node)out);
        //     String type=u.getType((Node)out);

        //     Artifact a=arguments.get(role);
        //     Artifact a2=artifactFactory.newArtifact(a);
        // }

        return new Object[] {args,arguments};
    }

    String makeVariable(Artifact a) {
        return "var_" + a.getId();
    }




    public Object invoke(Object o, String name, String primitive, Utilities u) throws IOException, org.jaxen.JaxenException {
        HashMap<String,Object> arguments=(HashMap<String,Object>) ((Object[])o)[0];
        HashMap<String,Artifact> allIOs=(HashMap<String,Artifact>) ((Object[])o)[1];

        System.out.println("JavaExecute: primitive " + primitive);
        System.out.println("JavaExecute: name " + name);
        System.out.println("JavaExecute: args " + arguments);

        HashMap<String,Object> results= new HashMap();
        Object thisResult=null;
        if (primitive.equals("http://openprovenance.org/reproducibility/java#sum")) {
            Integer arg1=(Integer) arguments.get("summand1");
            Integer arg2=(Integer) arguments.get("summand2");
            thisResult=arg1+arg2;
            results.put("out", thisResult);
        } else if (primitive.equals("http://openprovenance.org/reproducibility/java#multiplication")) {
            Integer arg1=(Integer) arguments.get("factor1");
            Integer arg2=(Integer) arguments.get("factor2");
            thisResult=arg1*arg2;
            results.put("product", thisResult);
        } else if (primitive.equals("http://openprovenance.org/reproducibility/java#division")) {
            Integer arg1=(Integer) arguments.get("dividend");
            Integer arg2=(Integer) arguments.get("divisor");
            thisResult=arg1/arg2;
            results.put("quotient", thisResult);
        } else new UnsupportedOperationException(primitive);


        System.out.println("JavaExecute: results " + results);

        System.out.println("invoke, now create output artifacts for " + primitive);
        try {
            // not ideal, would be nicer to have cached results.
            u.loadLibrary("java.xml");
        } catch (Exception e) {}

        List<?> procs=u.getDefinitionForUri(primitive);
        if ((procs==null)
            || (procs.size()==0)) {
            throw new NullPointerException();
        }

        Node proc=(Node)procs.get(0);

        List<?> outs=u.getOutputs(proc);
        for (Object out: outs) {
            String role=u.getRole((Node)out);
            //String name=u.getName((Node)out);
            //String type=u.getType((Node)out);

            Artifact a=allIOs.get(role);
            Artifact a2=artifactFactory.newArtifact(a);
            oFactory.setValue(a2,thisResult);
         }




        return results;
    }


}