package org.openprovenance.reproduce;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.vocabulary.RDFS;


import java.util.LinkedList;
import java.util.List;

public class Queries {
    final Model model;

    public Queries(Model model) {
        this.model=model;
        
    }

    String queryString1(String p) {
        return "PREFIX opm: <http://openprovenance.org/ontology#> " +
            getPrefixes() +
            "SELECT ?a " +
            "WHERE {" +
            "      " + p  + " opm:_used ?a " +
            "      }";
    }


    String queryString2(String p) {
        return "PREFIX opm: <http://openprovenance.org/ontology#> " +
            getPrefixes() +
            "SELECT ?a " +
            "WHERE {" +
            "      ?a  opm:_wasGeneratedBy " + p +
            "      }";
    }


    String queryString3() {
        return "PREFIX opm: <http://openprovenance.org/ontology#> " +
            getPrefixes() +
            "SELECT ?p " +
            "WHERE {" +
            "      ?p a opm:Process " +
            "      }" +
            "ORDER BY ?p ";
    }


    String queryString4() {
        return "PREFIX opm: <http://openprovenance.org/ontology#> " +
            getPrefixes() +
            "SELECT ?a " +
            "WHERE {" +
            "      ?a a opm:Artifact " +
            "      OPTIONAL {?a opm:_wasGeneratedBy ?p }" +
            "      FILTER (!bound(?p)) } ";
    }

    String queryString5(String uri, String value) {
        return "PREFIX opm: <http://openprovenance.org/ontology#> " +
            getPrefixes() +
            "SELECT ?p " +
            "WHERE {" +
            "      ?p a opm:Process ." +
            "      ?ann a opm:Annotation ." +
            "      ?p opm:annotation ?ann." +
            "      ?ann opm:property ?prop ." +
            "      ?prop a opm:Property ." +
            "      ?prop opm:uri \"" + uri + "\" ;" +
            "            opm:value \"" + value + "\" . " +
            " } ";
    }

    String queryString6(String n, String uri) {
        return "PREFIX opm: <http://openprovenance.org/ontology#> " +
            getPrefixes() +
            "SELECT ?val " +
            "WHERE {" +
            "      <" + n + "> opm:annotation ?ann." +
            "      ?ann a opm:Annotation ." +
            "      ?ann opm:property ?prop ." +
            "      ?prop a opm:Property ." +
            "      ?prop opm:uri \"" + uri + "\" ;" +
            "            opm:value ?val . " +
            " } ";
    }

    String queryString7(String p) {
        return "PREFIX opm: <http://openprovenance.org/ontology#> " +
            getPrefixes() +
            "SELECT ?a ?r " +
            "WHERE {" +
            "      " + p  + " opm:_used ?a ." +
            "      ?u a opm:Used ; "  +
            "         opm:effect " + p + " ; "  +
            "         opm:cause ?a; " +
            "         opm:role ?role. " +
            "      ?role a opm:Role; " +
            "            opm:value ?r. " +
            "      }";
    }


    String queryString8(String p) {
        return "PREFIX opm: <http://openprovenance.org/ontology#> " +
            getPrefixes() +
            "SELECT ?a ?r " +
            "WHERE {" +
            "      ?a opm:_wasGeneratedBy " + p + "." +
            "      ?u a opm:WasGeneratedBy ; "  +
            "         opm:cause " + p + " ; "  +
            "         opm:effect ?a; " +
            "         opm:role ?role. " +
            "      ?role a opm:Role; " +
            "            opm:value ?r. " +
            "      }";
    }


    String prefixes="";

    public void addPrefixes(String prefix, String uri) {
        prefixes=prefixes + "PREFIX " + prefix + ": <" + uri + "> ";
    }
    
    String getPrefixes() {
        return prefixes;
    }


    QueryExecution qe;

    public ResultSet getUsedArtifacts(String process) {
        String query=queryString1(process);
        qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        return results;
    }

    public List<Resource> getAsResources(ResultSet results, String var) {
        List<Resource> ll=new LinkedList();
        while (results.hasNext()) {
            QuerySolution sol=results.next();
            Resource r=sol.getResource(var);
            ll.add(r);
        }
        return ll;
    }

    public List<Literal> getAsLiterals(ResultSet results, String var) {
        List<Literal> ll=new LinkedList();
        while (results.hasNext()) {
            QuerySolution sol=results.next();
            Literal r=sol.getLiteral(var);
            ll.add(r);
        }
        return ll;
    }


    public ResultSet getGeneratedArtifacts(String process) {
        String query=queryString2(process);
        qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        return results;
    }


    public ResultSet getProcesses() {
        String query=queryString3();
        qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        return results;
    }


    public ResultSet getInputArtifacts() {
        String query=queryString4();
        qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        return results;
    }

    public ResultSet getProcessesWithProp(String uri, String value) {
        String query=queryString5(uri, value);
        //System.out.println(query);
        qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        return results;
    }

    public ResultSet getResourceProperty(String resource, String prop) {
        String query=queryString6(resource, prop);
        //System.out.println(query);
        qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        return results;
    }

    public ResultSet getUsedArtifactsAndRoles(String process) {
        String query=queryString7(process);
        //System.out.println(query);
        qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        return results;
    }

    public ResultSet getGeneratedArtifactsAndRoles(String process) {
        String query=queryString8(process);
        //System.out.println(query);
        qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        return results;
    }

    public List<Literal> getResourcePropertyAsLiterals(String resource, String prop) {
        ResultSet results = getResourceProperty(resource,prop);
        return getAsLiterals(results,"?val");
    }

    public List<Resource> getUsedArtifactsAsResources(String process) {
        ResultSet results = getUsedArtifacts(process);
        return getAsResources(results,"?a");
    }

    public List<Resource> getGeneratedArtifactsAsResources(String process) {
        ResultSet results = getGeneratedArtifacts(process);
        return getAsResources(results,"?a");
    }

    public List<Resource> getProcessesAsResources() {
        ResultSet results = getProcesses();
        return getAsResources(results,"?p");
    }


    public List<Resource> getProcessesWithPropAsResources(String uri, String value) {
        ResultSet results = getProcessesWithProp(uri,value);
        return getAsResources(results,"?p");
    }

    public List<Resource> getInputArtifactsAsResources(String NS) {
        ResultSet results = getInputArtifacts();
        List<Resource> ll=getAsResources(results,"?a");
        List<Resource> ll2=filterResourcesByNamespace(ll,NS);
        return ll2;
    }

    public List<Resource> getProcessesAsResources(String NS) {
        ResultSet results = getProcesses();
        List<Resource> ll=getAsResources(results,"?p");
        List<Resource> ll2=filterResourcesByNamespace(ll,NS);
        return ll2;
    }

    public List<Resource> filterResourcesByNamespace(List<Resource> ll, String NS) {
        if (NS==null) return ll;
        List<Resource> ll2=new LinkedList();
        for (Resource r: ll) {
            if ((r.getNameSpace().equals(NS))) {
                ll2.add(r);
            }
        }
        return ll2;
    }

    public void close() {
        qe.close();
    }



}