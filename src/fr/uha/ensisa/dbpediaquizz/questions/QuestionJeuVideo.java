package fr.uha.ensisa.dbpediaquizz.questions;

import fr.uha.ensisa.dbpediaquizz.util.Constantes;

class QuestionJeuVideo extends Question {

    QuestionJeuVideo() {
        super(Constantes.JEUX_VIDEO);

        if (Math.random() < 0.5D) {
            setEnonce("", "?game", " est un jeu publié par...");
            setReponse("?publisher");
        } else {
            setEnonce("A quel genre de jeu correspond ", "?game");
            setReponse("?genre");
        }
    }

    @Override
    void setQuery() {
        query = "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX prop-fr: <http://fr.dbpedia.org/property/>\n" +
                "PREFIX owl: <http://dbpedia.org/ontology/>\n" +
                "\n" +
                "select ?game ?publisher ?genre\n" +
                "where {\n" +
                "  ?GAME a owl:VideoGame .\n" +
                "  ?GAME rdfs:label ?game .\n" +
                "\n" +
                "  ?GAME owl:publisher ?PUBLISHER .\n" +
                "  ?PUBLISHER rdfs:label ?publisher .\n" +
                "\n" +
                "  ?GAME owl:genre ?GENRE .\n" +
                "  ?GENRE rdfs:label ?genre\n" +
                "\n" +
                "  FILTER ( lang(?game) = 'fr' )\n" +
                "  FILTER ( lang(?publisher) = 'fr' )\n" +
                "  FILTER ( lang(?genre) = 'fr' )\n" +
                "} LIMIT 500";
    }

}
