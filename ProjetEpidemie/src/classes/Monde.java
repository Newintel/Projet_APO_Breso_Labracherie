package classes;

import java.util.ArrayList;
import java.util.Random;

public class Monde {

	private int largeur, longueur;
	private ArrayList<Case> lesCases;
	private ArrayList<Zone> lesZones;
	
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
	
	public void creationZones() {
		lesZones = new ArrayList<>();
		for (int i = 0; i < largeur; i++) {
			for (int j = 0; j < longueur; j++) {
				Zone zone = new Zone(j, i);
				Case caseCentrale = this.getCase(j, i);
				
				// Case haut gauche
				if (j-1 >= 0 && i-1 >= 0) {
					zone.addCase(this.getCase(j-1, i-1));
				}
				
				// Case haut
				if (j-1 >= 0) {
					zone.addCase(this.getCase(j-1, i));
				}
				
				// Case haut droite
				if (j-1 >= 0 && i+1 < largeur) {
					zone.addCase(this.getCase(j-1, i+1));
				}
				
				// Case gauche
				if (i-1 >= 0) {
					zone.addCase(this.getCase(j, i-1));
				}
				
				// Case centrale
				zone.addCase(caseCentrale);
				
				// Case droite
				if (i+1 < largeur) {
					zone.addCase(this.getCase(j, i+1));
				}
				
				// Case bas gauche
				if (j+1 < longueur && i-1 >= 0) {
					zone.addCase(this.getCase(j+1, i-1));
				}
				
				// Case bas
				if (j+1 < longueur) {
					zone.addCase(this.getCase(j+1, i));
				}
				
				// Case bas droite
				if (j+1 < longueur && i+1 < largeur) {
					zone.addCase(this.getCase(j+1, i+1));
				}
				
				lesZones.add(zone);
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
	
	public void contaminer(String etatAContaminer, double tauxContamination) {
		for (Zone laZone : lesZones) {
			ArrayList<Individu> individusZone = laZone.getMesIndividus();
			if (individusZone.size() > 1) {
				if (individusZone.stream().anyMatch(i -> i.getEtat().equals(etatAContaminer)) && individusZone.stream().anyMatch(i -> i.getEtat().equals("contamine"))) {
					for (Individu individu : individusZone) {
						for (int j = 0; j < laZone.getNbContamines() && individu.getEtat().equals(etatAContaminer); j++) {
							if (new Random().nextInt((int) (1/tauxContamination)) == 0) {
								individu.setEtat("contamine");
							}
						}
					}
				}
			}
		}
	}
	
}
