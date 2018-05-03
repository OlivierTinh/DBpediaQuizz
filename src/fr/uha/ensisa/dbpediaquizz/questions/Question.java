package fr.uha.ensisa.dbpediaquizz.questions;

import fr.uha.ensisa.dbpediaquizz.fxml.InterfaceController;
import fr.uha.ensisa.dbpediaquizz.util.Constantes;
import java.util.Arrays;
import java.util.Scanner;

public abstract class Question {
    protected int categorie;
    protected String enonce;
    protected String bonneReponse;
    protected String[] mauvaisesReponses;

    public Question(int categorie) {
        this.categorie = categorie;
        this.mauvaisesReponses = new String[3];
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

        controller.setMatiere(Constantes.CATEGORIES[this.categorie]);
        controller.setQuestionLabel(this.enonce);

        for(choix = 0; choix < 4; ++choix) {
            controller.setAnswer(choix + 1, reponses[choix]);
        }
    }

    public boolean isCorrect(String answer) {
        System.out.println("- " + bonneReponse);
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

    protected boolean reponseAbsente(String nouvelleReponse) {
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
}