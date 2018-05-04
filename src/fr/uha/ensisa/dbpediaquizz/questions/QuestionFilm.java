package fr.uha.ensisa.dbpediaquizz.questions;

import fr.uha.ensisa.dbpediaquizz.util.Constantes;

class QuestionFilm extends Question {

    QuestionFilm() {
        super(Constantes.CINEMA);

        if (Math.random() < 0.5D) {
            setEnonce("En quelle année est sorti \"", "?titre", "\"");
            setReponses("?date");
        } else {
            setEnonce("Qui est le directeur de \"", "?titre", "\"");
            setReponses("?directeur");
        }

    }

    @Override
    void setRequest() {
        query = "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX prop-fr: <http://fr.dbpedia.org/property/>\n" +
                "PREFIX owl: <http://dbpedia.org/ontology/>\n" +
                "\n" +
                "select ?titre ?directeur ?date\n" +
                "where {\n" +
                "  ?FILM a owl:Film .\n" +
                "  ?FILM rdfs:label ?titre .\n" +
                "  ?FILM prop-fr:annéeDeSortie ?date .\n" +
                "  ?FILM owl:director ?DIRECTEUR .\n" +
                "  ?DIRECTEUR rdfs:label ?directeur\n" +
                "\n" +
                "  FILTER ( lang(?titre) = 'fr' )\n" +
                "  FILTER ( lang(?directeur) = 'fr' )\n" +
                "}\n" +
                "\n" +
                "LIMIT 500";
    }

}
