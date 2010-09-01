package org.openprovenance.reproduce;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.net.URL;

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

    final JavaPrimitive jp= new JavaPrimitive();

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
            throw new UnsupportedOperationException("prepareInvocationArguments: procedure " + procedure);
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
            args.put(role,getValue(a2));            
        }


        return new Object[] {args,arguments};
    }

    String makeVariable(Artifact a) {
        return "var_" + a.getId();
    }

    static String PATH_PROPERTY="http://openprovenance.org/primitives#path";
    static String URL_PROPERTY="http://openprovenance.org/primitives#url";


    public Object getValue(Artifact a) {
        Object value=oFactory.getValue(a);
        if (value!=null) {
            return value;
        }

        List pvalue=oFactory.getPropertyValues(a,PATH_PROPERTY);
        if ((pvalue==null) || (pvalue.size()==0)) {
        } else {
            return new File(makeFilename((String)pvalue.get(0)));
        }


        List uvalue=oFactory.getPropertyValues(a,URL_PROPERTY);
        if ((uvalue==null) || (uvalue.size()==0)) {
        } else {
            try {
                return new URL((String)uvalue.get(0));
            } catch (java.net.MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        }

        return null;

    }

    public String makeFilename(String file) {
        return file;
    }




    public Object invoke(Object o, String name, String primitive, Utilities u) throws IOException, org.jaxen.JaxenException {
        HashMap<String,Object> arguments=(HashMap<String,Object>) ((Object[])o)[0];
        HashMap<String,Artifact> allIOs=(HashMap<String,Artifact>) ((Object[])o)[1];

        System.out.println("JavaExecute: primitive " + primitive);
        System.out.println("JavaExecute: name " + name);
        System.out.println("JavaExecute: args " + arguments);

        HashMap<String,Object> results= new HashMap();

        jp.invoke(primitive,arguments,results);

        System.out.println("invoke, now create output artifacts for " + primitive);
        try {
            // not ideal, would be nicer to have cached results.
            u.loadLibrary("java.xml");
        } catch (Exception e) {}

        List<?> procs=u.getDefinitionForUri(primitive);
        if ((procs==null)
            || (procs.size()==0)) {
            throw new UnsupportedOperationException("invoke for " + primitive);
        }

        Node proc=(Node)procs.get(0);

        List<?> outs=u.getOutputs(proc);
        for (Object out: outs) {
            String role=u.getRole((Node)out);
            //String name=u.getName((Node)out);
            //String type=u.getType((Node)out);

            Artifact a=allIOs.get(role);
            Artifact a2=artifactFactory.newArtifact(a);

            Object thisResult=results.get(role);


            if (thisResult instanceof URL) {
                oFactory.setPropertyValue(a2,URL_PROPERTY,thisResult.toString());
            } else if (thisResult instanceof File) {
                oFactory.setPropertyValue(a2,PATH_PROPERTY,thisResult.toString());
            } else {
                oFactory.setValue(a2,thisResult);
            }
         }


        return results;
    }


}