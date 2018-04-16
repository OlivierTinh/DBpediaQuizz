package fr.uha.ensisa.dbpediaquizz;

import fr.uha.ensisa.dbpediaquizz.questions.Question;
import fr.uha.ensisa.dbpediaquizz.questions.QuestionFactory;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Scanner;

public class DBpediaQuizz extends Application {
    public DBpediaQuizz() {
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();

        primaryStage.setTitle("Qui veut gagner un pignouf");
        primaryStage.setScene(new Scene(root, 400, 300, Color.BEIGE));
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