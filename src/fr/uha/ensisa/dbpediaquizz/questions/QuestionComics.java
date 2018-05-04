package fr.uha.ensisa.dbpediaquizz.questions;

import fr.uha.ensisa.dbpediaquizz.util.Constantes;

class QuestionComics extends Question {

    QuestionComics() {
        super(Constantes.BANDE_DESSINEES);

        if (Math.random() < 0.33D) {
            setEnonce("Qui est l'auteur de la BD \"", "?titre", "\" ?");
            setReponse("?auteur");
        } else if (Math.random() < 0.66D) {
            setEnonce("Qui retrouve-t-on dans la série \"", "?titre", "\" ?");
            setReponse("?persoPrincipal");
        } else {
            setEnonce("A quelle bande-dessinée appartient ", "?persoPrincipal");
            setReponse("?serie");
        }
    }

    @Override
    void setQuery() {
        query = "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX prop-fr: <http://fr.dbpedia.org/property/>\n" +
                "PREFIX owl: <http://dbpedia.org/ontology/>\n" +
                "\n" +
                "select ?titre ?auteur ?serie ?persoPrincipal\n" +
                "where {\n" +
                "  ?COMIC a owl:Comic .\n" +
                "  ?COMIC rdfs:label ?titre .\n" +
                "\n" +
                "  ?COMIC owl:writer ?AUTEUR .\n" +
                "  ?AUTEUR rdfs:label ?auteur .\n" +
                "\n" +
                "  ?COMIC prop-fr:série ?SERIE .\n" +
                "  ?SERIE rdfs:label ?serie .\n" +
                "\n" +
                "  ?COMIC owl:mainCharacter ?PERSOPRINCIPAL .\n" +
                "  ?PERSOPRINCIPAL rdfs:label ?persoPrincipal\n" +
                "\n" +
                "  FILTER ( lang(?titre) = 'fr' )\n" +
                "  FILTER ( lang(?auteur) = 'fr' )\n" +
                "  FILTER ( lang(?serie) = 'fr' )\n" +
                "  FILTER ( lang(?serie) = 'fr' )\n" +
                "} LIMIT 500";
    }

}
