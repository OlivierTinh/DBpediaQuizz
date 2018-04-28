package fr.uha.ensisa.dbpediaquizz;

import fr.uha.ensisa.dbpediaquizz.questions.Question;
import fr.uha.ensisa.dbpediaquizz.questions.QuestionFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class DBpediaQuizz extends Application {
    public DBpediaQuizz() {
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/interface.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setTitle("Qui veut gagner un pignouf");
        primaryStage.setScene(scene);
        primaryStage.show();

        //doConsoleStuff();
    }

    private void doConsoleStuff() {
        int currentQuestion = 0;
        int score = 0;
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
        Application.launch(DBpediaQuizz.class, args);
    }
}