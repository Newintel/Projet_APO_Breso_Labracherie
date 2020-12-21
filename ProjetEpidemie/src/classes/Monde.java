package classes;

import java.util.ArrayList;

public class Monde {

	private int largeur, longueur;
	private ArrayList<Case> lesCases;
	
	public Monde(int largeur, int longueur) {
		this.largeur = largeur;
		this.longueur = longueur;
		for (int i = 0; i < longueur; i++) {
			for (int j = 0; j < largeur; j++) {
				lesCases.add(new Case(j, i));
			}
		}
	}
	
	public int getLargeur() {
		return largeur;
	}
	
	public int getLongueur() {
		return longueur;
	}
	
	public ArrayList<Case> getLesCases() {
		return lesCases;
	}
	
}
