package fr.uha.ensisa.dbpediaquizz;

import fr.uha.ensisa.dbpediaquizz.questions.Question;
import fr.uha.ensisa.dbpediaquizz.questions.QuestionFactory;
import fr.uha.ensisa.dbpediaquizz.util.Constantes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class DBpediaQuizz extends Application {

    private static int currentQuestion = 0;
    private static int score = 0;

    /**
     * Fonction appelée si on est en MODE_GRAPHIQUE.
     * On charge le fichier Interface.fxml contenant toutes les informations relatives à la fenêtre graphique.
     * On appelle ensuite le Controller qui gère la partie dynamique de la fenêtre.
     *
     * @param primaryStage le stage JavaFX
     * @throws IOException exception pour load()
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Interface.fxml"));
        Parent root = fxmlLoader.load();
        fxmlLoader.getController();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Fonction appelée si on est en MODE_CONSOLE.
     * Lance le jeu de manière textuelle.
     */
    private static void launchConsole() {
        Scanner entry = new Scanner(System.in);
        System.out.println("******* DBpedia Quizz *******");
        System.out.println("C'est parti pour 10 questions !");

        while(currentQuestion < 10) {
            ++currentQuestion;
            Question question = QuestionFactory.createQuestion();
            System.out.println("***********************************");
            System.out.println("QUESTION " + currentQuestion);
            System.out.println("***********************************");
            score += question.ask(entry);
            System.out.println();
        }

        System.out.println("***********************************");
        System.out.println("SCORE FINAL : " + score + "/" + 10);
        System.out.println("***********************************");
    }

    public static void main(String[] args) {
        // 0: Mode Console
        // 1: Mode Graphique
        int mode = 1;

        switch (mode) {
            case Constantes.MODE_CONSOLE:
                launchConsole();
                break;
            case Constantes.MODE_GRAPHIQUE:
                Application.launch(DBpediaQuizz.class, args);
                break;
        }

    }
}