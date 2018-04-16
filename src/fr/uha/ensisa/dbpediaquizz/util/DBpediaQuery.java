package fr.uha.ensisa.dbpediaquizz.util;

import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

public abstract class DBpediaQuery {
	
	public static List<QuerySolution> execRequete(String requete)
	{
		Query query = QueryFactory.create(requete);
		List<QuerySolution> results = null;
        // Remote execution.
        try ( QueryExecution qexec = QueryExecutionFactory.sparqlService("http://fr.dbpedia.org/sparql", query) ) {
            // Set the DBpedia specific timeout.
            ((QueryEngineHTTP)qexec).addParam("timeout", "10000") ;

            // Execute.
            ResultSet rs = qexec.execSelect();
            //ResultSetFormatter.out(System.out, rs, query);
            results = ResultSetFormatter.toList(rs);            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
	}

}
