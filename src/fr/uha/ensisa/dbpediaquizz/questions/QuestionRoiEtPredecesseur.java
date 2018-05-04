package fr.uha.ensisa.dbpediaquizz.questions;

import fr.uha.ensisa.dbpediaquizz.util.Constantes;

import java.util.List;

class QuestionRoiEtPredecesseur extends Question {

    QuestionRoiEtPredecesseur() {
        super(Constantes.HISTOIRE);
        setEnonce("Quel est le prédecesseur du roi " , "?nomRoi");
        setBonneReponse("?nomPredecesseur");
        setMauvaisesReponses("?nomPredecesseur");
    }

    @Override
    void setRequest() {
        query = "select distinct ?nomRoi ?nomPredecesseur where {?roi a <http://dbpedia.org/ontology/Royalty>. ?roi <http://fr.dbpedia.org/property/prédécesseur> ?pred. ?roi <http://www.w3.org/2000/01/rdf-schema#label> ?nomRoi. ?pred <http://www.w3.org/2000/01/rdf-schema#label> ?nomPredecesseur. FILTER(lang(?nomRoi)='fr'). FILTER(lang(?nomPredecesseur)='fr').}";
    }

}