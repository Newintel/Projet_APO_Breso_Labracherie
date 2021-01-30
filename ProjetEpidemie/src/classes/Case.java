package classes;

import java.util.ArrayList;

/**
 * 
 * Case est la classe qui représente une case du Monde
 * @see Monde
 *
 */
public class Case {

	private int x, y;
	private ArrayList<Individu> mesIndividus;
	
	/**
	 * Constructeur d'une case
	 * 
	 * @param y
	 * 		Coordonnée y
	 * @param x
	 * 		Coordonnée x
	 */
	public Case(int y, int x) {
		this.x = x;
		this.y = y;
		mesIndividus = new ArrayList<>();
	}
	
	/**
	 * Retourne la coordonnée X de la case
	 * 
	 * @return un entier correspondant à la coordonnée X
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Retourne la coordonnée Y de la case
	 * 
	 * @return un entier correspondant à la coordonneé Y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Retourne la liste d'individus contenus sur la case
	 * 
	 * @return une liste d'individus
	 * @see Individu
	 */
	public ArrayList<Individu> getMesIndividus() {
		return mesIndividus;
	}
	
	/**
	 * Ajoute un individu à la liste d'individus
	 * 
	 * @param nouvelleIndividu 
	 * 		L'individu à ajouter dans la liste
	 */
	public void addIndividu(Individu nouvelleIndividu) {
		mesIndividus.add(nouvelleIndividu);
	}
	
	/**
	 * Retire un individu de la liste
	 * 
	 * @param individu
	 * 		L'individu à retirer de la liste
	 * @return true, si l'individu existe dans la liste et a été retiré
	 */
	public boolean retirerIndividu(Individu individu) {
		return mesIndividus.remove(individu);
	}
	
	/**
	 * Retourne le nombre d'individus contaminés
	 * 
	 * @return un entier correspondant au nombre d'individus contaminés
	 */
	public int getNbContamines() {
		int n = 0;
		for (Individu individu : mesIndividus) {
			if (individu.getEtat().equals(Etat.INFECTE)) {
				n++;
			}
		}
		return n;
	}
	
	/**
	 * Affiche dans la console les coordonnées de la case
	 */
	@Override
	public String toString() {
		return "(" + y + ", " + x + ")";
	}
}
