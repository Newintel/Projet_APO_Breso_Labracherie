package classes;

import java.util.ArrayList;
import java.util.Random;

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
	
	public int contaminer(Etat etatAContaminer, double tauxContamination) {
		int nbInfectes = 0;
		for (Case laCase : lesCases) {
			ArrayList<Individu> individusZone = laCase.getMesIndividus();
			if (individusZone.size() > 1) {
				if (individusZone.stream().anyMatch(i -> i.getEtat().equals(etatAContaminer)) && individusZone.stream().anyMatch(i -> i.getEtat().equals(Etat.INFECTE))) {
					for (Individu individu : individusZone) {
						for (int j = 0; j < laCase.getNbContamines() && individu.getEtat().equals(etatAContaminer); j++) {
							if (individu.hasMasque()) {
								tauxContamination /= 4;
							}
							if (new Random().nextInt((int) (1/tauxContamination)) == 0) {
								individu.setEtat(Etat.INFECTE);
								nbInfectes++;
							}
						}
					}
				}
			}
		}
		return nbInfectes;
	}
	
}
