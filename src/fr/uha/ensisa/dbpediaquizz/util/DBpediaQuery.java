package fr.uha.ensisa.dbpediaquizz.util;

import java.util.List;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

public abstract class DBpediaQuery {
    public DBpediaQuery() {
    }

    public static List execRequete(String requete) {
        Query query = QueryFactory.create(requete);
        List results = null;

        try {
            Throwable var3;

            try {

                try (QueryExecution qexec = QueryExecutionFactory.sparqlService("http://fr.dbpedia.org/sparql", query)) {
                    ((QueryEngineHTTP) qexec).addParam("timeout", "10000");
                    ResultSet rs = qexec.execSelect();
                    results = ResultSetFormatter.toList(rs);
                }
            } catch (Throwable var14) {
                var3 = var14;
                throw var3;
            }
        } catch (Throwable var15) {
            var15.printStackTrace();
        }

        return results;
    }
}