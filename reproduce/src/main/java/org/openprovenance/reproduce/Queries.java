package org.openprovenance.reproduce;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
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
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.vocabulary.RDFS;

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

    String getPrefixes() {
        return "PREFIX eg: <http://example.com/reproduce2/> ";
    }


    QueryExecution qe;

    public ResultSet getUsedArtifacts(String process) {
        String query=queryString1(process);
        qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        return results;
    }

    public ResultSet getGeneratedArtifacts(String process) {
        String query=queryString2(process);
        qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        return results;
    }

    public void close() {
        qe.close();
    }



}