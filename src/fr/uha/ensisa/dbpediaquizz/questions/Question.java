package fr.uha.ensisa.dbpediaquizz.questions;

import fr.uha.ensisa.dbpediaquizz.fxml.InterfaceController;
import fr.uha.ensisa.dbpediaquizz.util.Constantes;
import fr.uha.ensisa.dbpediaquizz.util.DBpediaQuery;
import org.apache.jena.query.QuerySolution;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public abstract class Question {

    // Question
    private int categorie;
    private String enonce;
    private String bonneReponse;
    private String[] mauvaisesReponses;

    // Temp
    private String[] reponses;
    private int bonneReponseIndex;
    private int choix;

    // Queries
    String query;
    private List datas;
    private QuerySolution dataLine;

    /**
     * Constructeur définissant la catégorie, le nombre de réponses et initialisant les requêtes
     *
     * @param categorie le nombre correspondant à la catégorie définie dans util/Constantes.java
     */
    public Question(int categorie) {
        this.categorie = categorie;
        this.mauvaisesReponses = new String[Constantes.NB_REPONSES - 1];
        handleRequest();
    }

    /**
     * Utilisé en MODE_GRAPHIQUE.
     * Affiche la catégorie, la question et les différentes propositions possibles
     * pour la question courante lors d'une partie.
     *
     * @param controller le Controller responsable de la partie dynamique de l'interface graphique
     */
    public void displayQuestion(InterfaceController controller) {
        setAnswersIndex();

        controller.setField(Constantes.CATEGORIES[this.categorie]);
        controller.setQuestionLabel(this.enonce);

        for (choix = 0; choix < Constantes.NB_REPONSES; ++choix)
            controller.setAnswer(choix + 1, reponses[choix]);
    }

    /**
     * Utilisé en MODE_CONSOLE.
     * Affiche la catégorie, la question et les différentes propositions possibles
     * pour la question courante lors d'une partie.
     * Demande par la suite à l'utilisateur d'entrer un nombre correspondant à l'index d'une
     * des propositions.
     *
     * @param entry le Scanner permettant de demander un input à l'utilisateur
     * @return 1 si la réponse est correcte, 0 si elle est fausse
     */
    public int ask(Scanner entry) {
        setAnswersIndex();

        System.out.println("Question de " + Constantes.CATEGORIES[this.categorie]);
        System.out.println(this.enonce);

        for(choix = 0; choix < Constantes.NB_REPONSES; ++choix) {
            System.out.println(choix + 1 + ") " + reponses[choix]);
        }

        System.out.print("Votre choix : ");

        do {
            do {
                choix = entry.nextInt();
            } while(choix < 1);
        } while(choix > 10);

        int score = 0;
        if (choix == bonneReponseIndex + 1) {
            System.out.println("BRAVO VOUS AVEZ TROUVÉ LA BONNE RÉPONSE !");
            score = 1;
        } else {
            System.out.println("NON, LA BONNE RÉPONSE ÉTAIT : " + this.bonneReponse);
        }

        return score;
    }

    /**
     * Place aléatoirement une réponse dans une des cellules du tableau contenant toutes les
     * réponses possibles. Cela permet par la suite de pouvoir aisément afficher les réponses
     * en MODE_CONSOLE ou en MODE_GRAPHIQUE.
     */
    private void setAnswersIndex() {
        reponses = new String[Constantes.NB_REPONSES];
        Arrays.fill(reponses, null);
        bonneReponseIndex = (int)(Math.random() * Constantes.NB_REPONSES);
        reponses[bonneReponseIndex] = this.bonneReponse;
        int mauvaisesReponsesPlacees = 0;

        while(mauvaisesReponsesPlacees < Constantes.NB_REPONSES - 1) {
            choix = (int)(Math.random() * Constantes.NB_REPONSES);
            if (reponses[choix] == null) {
                reponses[choix] = this.mauvaisesReponses[mauvaisesReponsesPlacees];
                ++mauvaisesReponsesPlacees;
            }
        }
    }

    /**
     * Vérifie si la réponse n'existe pas déjà parmi les propositions possibles chargées.
     *
     * @param nouvelleReponse la réponse attendue
     * @return true si la réponse ne correspond pas aux propositions chargées, false autrement
     */
    private boolean reponseAbsente(String nouvelleReponse) {
        boolean absent = true;

        if (this.bonneReponse.equalsIgnoreCase(nouvelleReponse)) {
            absent = false;
        } else {
            for (String mauvaisesReponse : this.mauvaisesReponses) {
                if (mauvaisesReponse != null && mauvaisesReponse.equalsIgnoreCase(nouvelleReponse)) {
                    absent = false;
                }
            }
        }

        return absent;
    }

    /**
     * Compare la réponse choisie lors de la partie à la bonne réponse attendue.
     *
     * @param answer le texte du bouton cliqué
     * @return vrai si answer correspond à la bonne réponse, faux autrement
     */
    public boolean isCorrect(String answer) {
        return answer.equals(bonneReponse);
    }



    /* --- Méthodes pour les questions implémentant la fonction --- */

    /**
     * Initialise les variables nécessaires pour l'implémentation des questions.
     */
    private void handleRequest() {
        setQuery();
        execQuery();
        getRandomLine();
    }

    /**
     * Charge la requête SPARQL dans une variable.
     * Doit être définie pour chaque catégorie de Question.
     */
    abstract void setQuery();

    /**
     * Exécute la requête SPARQL et enregistre le résultat dans une liste `datas`.
     */
    private void execQuery() {
        datas = DBpediaQuery.execRequete(query);
    }

    /**
     * Prend une ligne au hasard à partir des `datas` obtenues à l'aide de execQuery().
     */
    private void getRandomLine() {
        dataLine = (QuerySolution) datas.get( (int)(Math.random() * (double) datas.size()) );
    }

    /**
     * Crée un énoncé à partir de la ligne aléatoire obtenue dans getRandomLine().
     *
     * @param sentence question à poser
     * @param queryVariable variable SPARQL correspondant à ce que l'on veut (ex: "?nom", "?date")
     */
    void setEnonce(String sentence, String queryVariable) {
        enonce = sentence + dataLine.getLiteral(queryVariable).getString() + " ?";
    }

    /**
     * Crée un énoncé à partir de la ligne aléatoire obtenue dans getRandomLine().
     *
     * @param sentence première partie de la question à poser
     * @param queryVariable variable SPARQL correspondant au type apparaissant dans la question
     * @param sentence2 deuxième partie de la question à poser
     */
    void setEnonce(String sentence, String queryVariable, String sentence2) {
        enonce = sentence + dataLine.getLiteral(queryVariable).getString() + sentence2;
    }

    /**
     * Charge la bonne et les mauvaises réponses à l'aide de la variable SPARQL.
     *
     * @param queryVariable variable SPARQL correspondant au type de réponse attendu
     */
    void setReponse(String queryVariable) {
        setBonneReponse(queryVariable);
        setMauvaisesReponses(queryVariable);
    }

    /**
     * Charge la bonne réponse dans la variable associée.
     *
     * @param queryVariable variable SPARQL correspondant au type de réponse attendu
     */
    private void setBonneReponse(String queryVariable) {
        bonneReponse = dataLine.getLiteral(queryVariable).getString();
    }

    /**
     * Charge les mauvaises réponses dans le tableau associé.
     * Pour cela, une ligne de la liste `datas` est prise aléatoirement jusqu'à ce que l'on en
     * ait [Nombre total de réponses - 1].
     *
     * @param queryVariable variable SPARQL correspondant au type de réponse attendu
     */
    private void setMauvaisesReponses(String queryVariable) {
        int i = 0;
        while (i < Constantes.NB_REPONSES - 1) {
            dataLine = (QuerySolution) datas.get( (int)(Math.random() * (double) datas.size()) );
            String randomAnswer = dataLine.getLiteral(queryVariable).getString();
            if (reponseAbsente(randomAnswer)) {
                mauvaisesReponses[i] = randomAnswer;
                ++i;
            }
        }
    }

}