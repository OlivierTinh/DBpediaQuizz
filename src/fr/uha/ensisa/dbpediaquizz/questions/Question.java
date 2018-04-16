package fr.uha.ensisa.dbpediaquizz.questions;

import java.util.Arrays;
import java.util.Scanner;

import fr.uha.ensisa.dbpediaquizz.util.Constantes;

public abstract class Question {
	
	protected int categorie;
	protected String enonce;
	protected String bonneReponse;
	protected String[] mauvaisesReponses;
	
	protected Question(int categorie)
	{
		this.categorie=categorie;
		this.mauvaisesReponses= new String[3];
	}
	
	/**
	 * Pose la question à l'utilisateur, attend sa réponse et renvoie 1 si la réponse est bonne, 0 sinon.
	 * @param entry
	 * @return
	 */
	public int ask(Scanner entry)
	{		
		//À compléter
		return 0;
	}

	

}
