package fr.uha.ensisa.dbpediaquizz.questions;

import fr.uha.ensisa.dbpediaquizz.fxml.InterfaceController;
import fr.uha.ensisa.dbpediaquizz.util.Constantes;
import fr.uha.ensisa.dbpediaquizz.util.DBpediaQuery;
import org.apache.jena.query.QuerySolution;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public abstract class Question {

    private int categorie;
    private String enonce;
    private String bonneReponse;
    private String[] mauvaisesReponses;

    String query;
    private List datas;
    private QuerySolution dataLine;

    public Question(int categorie) {
        this.categorie = categorie;
        this.mauvaisesReponses = new String[3];
        handleRequest();
    }

    public void display(InterfaceController controller) {
        // setup
        String[] reponses = new String[4];
        Arrays.fill(reponses, null);
        int bonneReponseIndex = (int)(Math.random() * 4.0D);
        reponses[bonneReponseIndex] = this.bonneReponse;
        int mauvaisesReponsesPlacees = 0;

        int choix;
        while(mauvaisesReponsesPlacees < 3) {
            choix = (int)(Math.random() * 4.0D);
            if (reponses[choix] == null) {
                reponses[choix] = this.mauvaisesReponses[mauvaisesReponsesPlacees];
                ++mauvaisesReponsesPlacees;
            }
        }

        controller.setField(Constantes.CATEGORIES[this.categorie]);
        controller.setQuestionLabel(this.enonce);

        for(choix = 0; choix < 4; ++choix) {
            controller.setAnswer(choix + 1, reponses[choix]);
        }
    }

    public boolean isCorrect(String answer) {
        return answer.equals(bonneReponse);
    }

    public int ask(Scanner entry) {
        String[] reponses = new String[4];
        Arrays.fill(reponses, null);
        int bonneReponseIndex = (int)(Math.random() * 4.0D);
        reponses[bonneReponseIndex] = this.bonneReponse;
        int mauvaisesReponsesPlacees = 0;

        int choix;
        while(mauvaisesReponsesPlacees < 3) {
            choix = (int)(Math.random() * 4.0D);
            if (reponses[choix] == null) {
                reponses[choix] = this.mauvaisesReponses[mauvaisesReponsesPlacees];
                ++mauvaisesReponsesPlacees;
            }
        }

        System.out.println("Question de " + Constantes.CATEGORIES[this.categorie]);
        System.out.println(this.enonce);

        for(choix = 0; choix < 4; ++choix) {
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


    /* --- Méthodes pour les questions implémentant la fonction --- */

    /**
     * Initialise les variables nécessaires pour l'implémentation des questions.
     */
    private void handleRequest() {
        setRequest();
        execRequest();
        getRandomLine();
    }

    /**
     * Charge la requête SPARQL dans une variable.
     * Doit être définie pour chaque catégorie de Question.
     */
    abstract void setRequest();

    /**
     * Exécute la requête SPARQL et enregistre le résultat dans une liste `datas`.
     */
    private void execRequest() {
        datas = DBpediaQuery.execRequete(query);
    }

    /**
     * Prend une ligne au hasard à partir des `datas` obtenues à l'aide de execRequest().
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
        enonce = sentence + dataLine.getLiteral(queryVariable).getString() + sentence2 + " ?";
    }

    /**
     * Charge la bonne et les mauvaises réponses à l'aide de la variable SPARQL.
     *
     * @param queryVariable variable SPARQL correspondant au type de réponse attendu
     */
    void setReponses(String queryVariable) {
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
     *
     * @param queryVariable variable SPARQL correspondant au type de réponse attendu
     */
    private void setMauvaisesReponses(String queryVariable) {
        int i = 0;
        while (i < 3) {
            dataLine = (QuerySolution) datas.get( (int)(Math.random() * (double) datas.size()) );
            String randomAnswer = dataLine.getLiteral(queryVariable).getString();
            if (reponseAbsente(randomAnswer)) {
                mauvaisesReponses[i] = randomAnswer;
                ++i;
            }
        }
    }

}