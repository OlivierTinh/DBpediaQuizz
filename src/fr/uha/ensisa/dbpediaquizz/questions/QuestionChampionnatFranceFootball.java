package fr.uha.ensisa.dbpediaquizz.questions;

import fr.uha.ensisa.dbpediaquizz.util.DBpediaQuery;
import java.util.List;
import org.apache.jena.query.QuerySolution;

public class QuestionChampionnatFranceFootball extends Question {
    public QuestionChampionnatFranceFootball() {
        super(2);
        String requete = "select ?nomTournoi ?nomVainqueur where {<http://fr.dbpedia.org/resource/Championnat_de_France_de_football_1932-1933> <http://dbpedia.org/ontology/followingEvent>* ?tournoi. ?tournoi <http://dbpedia.org/ontology/soccerLeagueWinner> ?vainqueur. ?tournoi <http://www.w3.org/2000/01/rdf-schema#label> ?nomTournoi. ?vainqueur <http://www.w3.org/2000/01/rdf-schema#label> ?nomVainqueur. FILTER(lang(?nomTournoi)='fr'). FILTER(lang(?nomVainqueur)='fr'). } ";
        List chamionnats = DBpediaQuery.execRequete(requete);
        QuerySolution ligne = (QuerySolution)chamionnats.get((int)(Math.random() * (double)chamionnats.size()));
        this.enonce = "Quelle équipe a gagné le " + ligne.getLiteral("?nomTournoi").getString() + " ?";
        this.bonneReponse = ligne.getLiteral("?nomVainqueur").getString();
        int index = 0;

        while(index < 3) {
            ligne = (QuerySolution)chamionnats.get((int)(Math.random() * (double)chamionnats.size()));
            if (this.reponseAbsente(ligne.getLiteral("?nomVainqueur").getString())) {
                this.mauvaisesReponses[index] = ligne.getLiteral("?nomVainqueur").getString();
                ++index;
            }
        }

    }
}