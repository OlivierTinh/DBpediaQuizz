package fr.uha.ensisa.dbpediaquizz;

import fr.uha.ensisa.dbpediaquizz.questions.Question;
import fr.uha.ensisa.dbpediaquizz.questions.QuestionFactory;
import java.util.Scanner;

public class DBpediaQuizz {
    public DBpediaQuizz() {
    }

    public static void main(String[] args) {
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
}