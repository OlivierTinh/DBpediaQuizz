package fr.uha.ensisa.dbpediaquizz.questions;

import fr.uha.ensisa.dbpediaquizz.util.Constantes;

class QuestionCapitale extends Question {

    QuestionCapitale() {
        super(Constantes.GEOGRAPHIE);

        if (Math.random() < 0.5D) {
            setEnonce("Quelle est la capitale de ", "?nomPays");
            setReponse("?nomVille");
        } else {
            setEnonce("De quoi ", "?nomVille", " est la capitale ");
            setReponse("?nomPays");
        }

    }

    @Override
    void setQuery() {
        query = "select ?nomPays ?nomVille where {?pays <http://dbpedia.org/ontology/capital> ?ville. ?pays <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://dbpedia.org/ontology/Country>. ?pays <http://www.w3.org/2000/01/rdf-schema#label> ?nomPays. ?ville <http://www.w3.org/2000/01/rdf-schema#label> ?nomVille. FILTER (lang(?nomPays) = 'fr') FILTER (lang(?nomVille) = 'fr')}";
    }
}