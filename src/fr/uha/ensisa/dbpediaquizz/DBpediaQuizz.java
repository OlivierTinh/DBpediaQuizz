package fr.uha.ensisa.dbpediaquizz;

import java.util.Scanner;

import fr.uha.ensisa.dbpediaquizz.questions.Question;
import fr.uha.ensisa.dbpediaquizz.questions.QuestionFactory;
import fr.uha.ensisa.dbpediaquizz.util.Constantes;


public class DBpediaQuizz {

	public static void main(String[] args) {
		int currentQuestion=0, score=0;
		Scanner entry = new Scanner(System.in);
		System.out.println("******* DBpedia Quizz *******");
		System.out.println("C'est parti pour "+Constantes.NB_QUESTIONS+" questions !");
		while(currentQuestion<Constantes.NB_QUESTIONS)
		{
			currentQuestion++;
			Question question=QuestionFactory.createQuestion();
			System.out.println("***********************************");
			System.out.println("QUESTION "+currentQuestion);
			System.out.println("***********************************");
			score+=question.ask(entry);
			System.out.println();
		}
		System.out.println("***********************************");
		System.out.println("SCORE FINAL : "+score+"/"+Constantes.NB_QUESTIONS);
		System.out.println("***********************************");
	}

}
