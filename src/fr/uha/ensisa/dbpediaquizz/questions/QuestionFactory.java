package fr.uha.ensisa.dbpediaquizz.questions;

public class QuestionFactory {
	public QuestionFactory() {
	}

	public static Question createQuestion() {
		int questionType = (int)(Math.random() * 3.0D);
		Object question;
		switch(questionType) {
			case 0:
				question = new QuestionCapitale();
				break;
			case 1:
				question = new QuestionRoiEtPredecesseur();
				break;
			default:
				question = new QuestionChampionnatFranceFootball();
		}

		return (Question)question;
	}
}