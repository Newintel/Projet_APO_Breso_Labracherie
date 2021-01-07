package classes;

import java.util.ArrayList;

public class Monde {

	private int largeur, longueur;
	private ArrayList<Case> lesCases;
	
	public Monde(int longueur, int largeur) {
		this.longueur = longueur;
		this.largeur = largeur;
		lesCases = new ArrayList<>();
		for (int i = 0; i < largeur; i++) {
			for (int j = 0; j < longueur; j++) {
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
	
	public Case getCase(int y, int x) {
		return lesCases.get( (y*this.longueur) + x);
	}
	
}
