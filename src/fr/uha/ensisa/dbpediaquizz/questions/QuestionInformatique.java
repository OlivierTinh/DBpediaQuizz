package fr.uha.ensisa.dbpediaquizz.questions;

import fr.uha.ensisa.dbpediaquizz.util.Constantes;

class QuestionInformatique extends Question {

    QuestionInformatique() {
        super(Constantes.INFORMATIQUE);

        setEnonce("Le langage ", "?language", " a été créé en...");
        setReponse("?annee");
    }

    @Override
    void setQuery() {
        query = "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX prop-fr: <http://fr.dbpedia.org/property/>\n" +
                "PREFIX owl: <http://dbpedia.org/ontology/>\n" +
                "\n" +
                "select ?language ?annee\n" +
                "where {\n" +
                "  ?LANGUAGE a owl:ProgrammingLanguage .\n" +
                "  ?LANGUAGE rdfs:label ?language .\n" +
                "\n" +
                "  ?LANGUAGE prop-fr:année ?annee\n" +
                "\n" +
                "  FILTER ( lang(?language) = 'fr' )\n" +
                "}";
    }
}
