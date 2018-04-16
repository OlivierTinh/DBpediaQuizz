package fr.uha.ensisa.dbpediaquizz.questions;

import fr.uha.ensisa.dbpediaquizz.util.DBpediaQuery;
import java.util.List;
import org.apache.jena.query.QuerySolution;

public class QuestionCapitale extends Question {
    public QuestionCapitale() {
        super(0);
        String requete = "select ?nomPays ?nomVille where {?pays <http://dbpedia.org/ontology/capital> ?ville. ?pays <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://dbpedia.org/ontology/Country>. ?pays <http://www.w3.org/2000/01/rdf-schema#label> ?nomPays. ?ville <http://www.w3.org/2000/01/rdf-schema#label> ?nomVille. FILTER (lang(?nomPays) = 'fr') FILTER (lang(?nomVille) = 'fr')}";
        List<QuerySolution> capitales = DBpediaQuery.execRequete(requete);
        QuerySolution ligne = (QuerySolution)capitales.get((int)(Math.random() * (double)capitales.size()));
        int index;
        if (Math.random() < 0.5D) {
            this.enonce = "Quelle est la capitale de " + ligne.getLiteral("?nomPays").getString() + " ?";
            this.bonneReponse = ligne.getLiteral("?nomVille").getString();
            index = 0;

            while(index < 3) {
                ligne = (QuerySolution)capitales.get((int)(Math.random() * (double)capitales.size()));
                if (this.reponseAbsente(ligne.getLiteral("?nomVille").getString())) {
                    this.mauvaisesReponses[index] = ligne.getLiteral("?nomVille").getString();
                    ++index;
                }
            }
        } else {
            this.enonce = "De quoi " + ligne.getLiteral("?nomVille").getString() + " est la capitale ?";
            this.bonneReponse = ligne.getLiteral("?nomPays").getString();
            index = 0;

            while(index < 3) {
                ligne = (QuerySolution)capitales.get((int)(Math.random() * (double)capitales.size()));
                if (!this.bonneReponse.equalsIgnoreCase(ligne.getLiteral("?nomPays").getString())) {
                    this.mauvaisesReponses[index] = ligne.getLiteral("?nomPays").getString();
                    ++index;
                }
            }
        }

    }
}