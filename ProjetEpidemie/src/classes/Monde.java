package classes;

import java.util.ArrayList;

public class Monde {

	private int largeur, longueur;
	private ArrayList<Case> lesCases;
	
	public Monde(int longueur, int largeur) {
		this.longueur = longueur;
		this.largeur = largeur;
		for (int i = 0; i < longueur; i++) {
			for (int j = 0; j < largeur; j++) {
				lesCases.add(new Case(i, j));
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
	
	public Case getCase(int x, int y) {
		return lesCases.get( (x*this.largeur) + y);
	}
	
}
