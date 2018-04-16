package fr.uha.ensisa.dbpediaquizz.questions;

import fr.uha.ensisa.dbpediaquizz.util.DBpediaQuery;
import java.util.List;
import org.apache.jena.query.QuerySolution;

public class QuestionRoiEtPredecesseur extends Question {
    public QuestionRoiEtPredecesseur() {
        super(1);
        String requete = "select distinct ?nomRoi ?nomPredecesseur where {?roi a <http://dbpedia.org/ontology/Royalty>. ?roi <http://fr.dbpedia.org/property/prédécesseur> ?pred. ?roi <http://www.w3.org/2000/01/rdf-schema#label> ?nomRoi. ?pred <http://www.w3.org/2000/01/rdf-schema#label> ?nomPredecesseur. FILTER(lang(?nomRoi)='fr'). FILTER(lang(?nomPredecesseur)='fr').}";
        List lignees = DBpediaQuery.execRequete(requete);
        QuerySolution ligne = (QuerySolution)lignees.get((int)(Math.random() * (double)lignees.size()));
        this.enonce = "Quel est le prédecesseur du roi " + ligne.getLiteral("?nomRoi").getString() + " ?";
        this.bonneReponse = ligne.getLiteral("?nomPredecesseur").getString();
        int index = 0;

        while(index < 3) {
            ligne = (QuerySolution)lignees.get((int)(Math.random() * (double)lignees.size()));
            if (this.reponseAbsente(ligne.getLiteral("?nomPredecesseur").getString())) {
                this.mauvaisesReponses[index] = ligne.getLiteral("?nomPredecesseur").getString();
                ++index;
            }
        }

    }
}