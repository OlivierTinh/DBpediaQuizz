package fr.uha.ensisa.dbpediaquizz.questions;

import fr.uha.ensisa.dbpediaquizz.util.Constantes;

import java.util.List;

class QuestionChampionnatFranceFootball extends Question {

    QuestionChampionnatFranceFootball() {
        super(Constantes.SPORT);
        setEnonce("Quelle équipe a gagné le ", "?nomTournoi");
        setBonneReponse("?nomVainqueur");
        setMauvaisesReponses("?nomVainqueur");
    }

    @Override
    void setRequest() {
        query = "select ?nomTournoi ?nomVainqueur where {<http://fr.dbpedia.org/resource/Championnat_de_France_de_football_1932-1933> <http://dbpedia.org/ontology/followingEvent>* ?tournoi. ?tournoi <http://dbpedia.org/ontology/soccerLeagueWinner> ?vainqueur. ?tournoi <http://www.w3.org/2000/01/rdf-schema#label> ?nomTournoi. ?vainqueur <http://www.w3.org/2000/01/rdf-schema#label> ?nomVainqueur. FILTER(lang(?nomTournoi)='fr'). FILTER(lang(?nomVainqueur)='fr'). } ";
    }

}