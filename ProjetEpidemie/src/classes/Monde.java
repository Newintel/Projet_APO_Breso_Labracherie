package classes;

import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * Monde est la classe qui contient les cases de la simulation
 *
 */
public class Monde {

	private int largeur, longueur;
	private ArrayList<Case> lesCases;
	
	/**
	 * Constructeur du monde qui crée les cases
	 * 
	 * @param longueur
	 * 		Longueur du monde
	 * @param largeur
	 * 		Largeur du monde
	 */
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
	
	/**
	 * Retourne la largeur du monde
	 * 
	 * @return un entier correspondant à la largeur
	 */
	public int getLargeur() {
		return largeur;
	}
	
	/**
	 * Retourne la longueur du monde
	 * 
	 * @return un entier correspondant à la longueur
	 */
	public int getLongueur() {
		return longueur;
	}
	
	/**
	 * Retourne la liste des cases du monde
	 * 
	 * @return une liste de Case
	 * @see Case
	 */
	public ArrayList<Case> getLesCases() {
		return lesCases;
	}
	
	/**
	 * Retourne une case existante dans le monde de coordonnées (x, y)
	 * 
	 * @param y
	 * 		Coordonnée y de la case recherchée
	 * @param x
	 * 		Coordonnée x de la case recherchée
	 * @return une instance de Case
	 * @see Case
	 */
	public Case getCase(int y, int x) {
		return lesCases.get( (y*this.longueur) + x);
	}
	
	/**
	 * Contamine les individus d'état "étatAContaminer" par rapport au taux de contamination "tauxContamination"
	 * Effectue le test de contamination pour chaque individu contaminé au départ sur la case
	 * 
	 * @param etatAContaminer
	 * 		Etat qui peut être contaminé
	 * @param tauxContamination
	 * 		Taux de contamination
	 * @return un entier correspondant au nombre de contaminés durant le test
	 * @see Etat
	 */
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
