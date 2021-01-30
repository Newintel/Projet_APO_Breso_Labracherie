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
	 * Constructeur du monde qui cr�e les cases
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
	 * @return un entier correspondant � la largeur
	 */
	public int getLargeur() {
		return largeur;
	}
	
	/**
	 * Retourne la longueur du monde
	 * 
	 * @return un entier correspondant � la longueur
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
	 * Retourne une case existante dans le monde de coordonn�es (x, y)
	 * 
	 * @param y
	 * 		Coordonn�e y de la case recherch�e
	 * @param x
	 * 		Coordonn�e x de la case recherch�e
	 * @return une instance de Case
	 * @see Case
	 */
	public Case getCase(int y, int x) {
		return lesCases.get( (y*this.longueur) + x);
	}
	
	/**
	 * Contamine les individus d'�tat "�tatAContaminer" par rapport au taux de contamination "tauxContamination"
	 * Effectue le test de contamination pour chaque individu contamin� au d�part sur la case
	 * 
	 * @param etatAContaminer
	 * 		Etat qui peut �tre contamin�
	 * @param tauxContamination
	 * 		Taux de contamination
	 * @return un entier correspondant au nombre de contamin�s durant le test
	 * @see Etat
	 */
	public int contaminer(Etat etatAContaminer, double tauxContamination) {
		int nbInfectes = 0;
		for (Case laCase : lesCases) {
			
			// On r�cup�re les individus de chaque case
			ArrayList<Individu> individusZone = laCase.getMesIndividus();
			
			// Si il y a au moins 2 individus sur la case => contamination possible
			if (individusZone.size() > 1) {
				
				// On regarde si parmis les individus, au moins 1 est contamin�, et au moins 1 est contaminable
				if (individusZone.stream().anyMatch(i -> i.getEtat().equals(etatAContaminer)) && individusZone.stream().anyMatch(i -> i.getEtat().equals(Etat.INFECTE))) {
				
					for (Individu individu : individusZone) {
						// Puis si l'individu est contaminable, on fait le test de contamination pour chaque personne contamin�
						for (int j = 0; j < laCase.getNbContamines() && individu.getEtat().equals(etatAContaminer); j++) {
							// S'il porte un masque, la contamination est r�duite
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
