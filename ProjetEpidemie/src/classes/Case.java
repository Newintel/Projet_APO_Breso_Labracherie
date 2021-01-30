package classes;

import java.util.ArrayList;

/**
 * 
 * Case est la classe qui repr�sente une case du Monde
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
	 * 		Coordonn�e y
	 * @param x
	 * 		Coordonn�e x
	 */
	public Case(int y, int x) {
		this.x = x;
		this.y = y;
		mesIndividus = new ArrayList<>();
	}
	
	/**
	 * Retourne la coordonn�e X de la case
	 * 
	 * @return un entier correspondant � la coordonn�e X
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Retourne la coordonn�e Y de la case
	 * 
	 * @return un entier correspondant � la coordonne� Y
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
	 * Ajoute un individu � la liste d'individus
	 * 
	 * @param nouvelleIndividu 
	 * 		L'individu � ajouter dans la liste
	 */
	public void addIndividu(Individu nouvelleIndividu) {
		mesIndividus.add(nouvelleIndividu);
	}
	
	/**
	 * Retire un individu de la liste
	 * 
	 * @param individu
	 * 		L'individu � retirer de la liste
	 * @return true, si l'individu existe dans la liste et a �t� retir�
	 */
	public boolean retirerIndividu(Individu individu) {
		return mesIndividus.remove(individu);
	}
	
	/**
	 * Retourne le nombre d'individus contamin�s
	 * 
	 * @return un entier correspondant au nombre d'individus contamin�s
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
	 * Affiche dans la console les coordonn�es de la case
	 */
	@Override
	public String toString() {
		return "(" + y + ", " + x + ")";
	}
}
