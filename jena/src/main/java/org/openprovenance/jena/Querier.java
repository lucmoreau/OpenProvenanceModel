package org.openprovenance.jena;

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

import static org.openprovenance.jena.TripleStore.OPMO_NS;
import static org.openprovenance.jena.TripleStore.OPMV_NS;

import java.util.LinkedList;
import java.util.List;

public class Querier {
    final Model model;

    public Querier(Model model) {
        this.model=model;
        
    }

    String queryString_used(String p) {
        return "PREFIX opmo: <" + OPMO_NS + "> " +
            "PREFIX opmv: <" + OPMV_NS + "> " +
            getPrefixes() +
            "SELECT ?a " +
            "WHERE {" +
            "      " + p  + " opmv:used ?a " +
            "      }";
    }


    String queryString_wasGeneratedBy(String p) {
        return "PREFIX opmo: <" + OPMO_NS + "> " +
            "PREFIX opmv: <" + OPMV_NS + "> " +
            getPrefixes() +
            "SELECT ?a " +
            "WHERE {" +
            "      ?a  opmv:wasGeneratedBy " + p +
            "      }";
    }


    String queryString_Process() {
        return "PREFIX opmo: <" + OPMO_NS + "> " +
            "PREFIX opmv: <" + OPMV_NS + "> " +
            getPrefixes() +
            "SELECT ?p " +
            "WHERE {" +
            "      ?p a opmv:Process " +
            "      }" +
            "ORDER BY ?p ";
    }


    String queryString_Artifact() {
        return "PREFIX opmo: <" + OPMO_NS + "> " +
            "PREFIX opmv: <" + OPMV_NS + "> " +
            getPrefixes() +
            "SELECT ?a " +
            "WHERE {" +
            "      ?a a opmv:Artifact " +
            "      OPTIONAL {?a opmv:wasGeneratedBy ?p }" +
            "      FILTER (!bound(?p)) } ";
    }

    String queryString_ArtifactUsedInRole(String p) {
        return "PREFIX opmo: <" + OPMO_NS + "> " +
            "PREFIX opmv: <" + OPMV_NS + "> " +
            getPrefixes() +
            "SELECT ?a ?r " +
            "WHERE {" +
            "      " + p  + " opmv:used ?a ." +
            "      ?u a opmo:Used ; "  +
            "         opmo:effect " + p + " ; "  +
            "         opmo:cause ?a; " +
            "         opmo:role ?role. " +
            "      ?role a opmo:Role; " +
            "            opmo:value ?r. " +
            "      }";
    }


    String queryString_ArtifactGeneratedInRole(String p) {
        return "PREFIX opmo: <" + OPMO_NS + "> " +
            "PREFIX opmv: <" + OPMV_NS + "> " +
            getPrefixes() +
            "SELECT ?a ?r " +
            "WHERE {" +
            "      ?a opmv:wasGeneratedBy " + p + "." +
            "      ?u a opmo:WasGeneratedBy ; "  +
            "         opmo:cause " + p + " ; "  +
            "         opmo:effect ?a; " +
            "         opmo:role ?role. " +
            "      ?role a opmo:Role; " +
            "            opmo:value ?r. " +
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
        String query=queryString_used(process);
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
        String query=queryString_wasGeneratedBy(process);
        qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        return results;
    }


    public ResultSet getProcesses() {
        String query=queryString_Process();
        qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        return results;
    }

    public ResultSet getArtifacts() {
        String query=queryString_Artifact();
        qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        return results;
    }


    public ResultSet getInputArtifacts() {
        String query=queryString_Artifact();
        qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        return results;
    }




    public ResultSet getUsedArtifactsAndRoles(String process) {
        String query=queryString_ArtifactUsedInRole(process);
        qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        return results;
    }

    public ResultSet getGeneratedArtifactsAndRoles(String process) {
        String query=queryString_ArtifactGeneratedInRole(process);
        qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        return results;
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