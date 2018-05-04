package fr.uha.ensisa.dbpediaquizz.questions;

import fr.uha.ensisa.dbpediaquizz.util.Constantes;

public class QuestionFactory {
	public QuestionFactory() {
	}

	public static Question createQuestion() {
		int questionType = (int)(Math.random() * Constantes.CATEGORIES.length);
		Object question;
		switch(questionType) {
			case 0:
				question = new QuestionCapitale();
				break;
			case 1:
				question = new QuestionRoiEtPredecesseur();
				break;
			case 2:
				question = new QuestionFilm();
				break;
			case 3:
				question = new QuestionComics();
				break;
			default:
				question = new QuestionChampionnatFranceFootball();
		}

		return (Question)question;
	}
}