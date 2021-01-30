package classes;

public class Individu {

	private Case maCase;
	private boolean deplacementAutorise;
	private boolean masque = false;
	
	private Etat etat;
	
	/**
	 * Constructeur d'un individu avec son état de départ et sa case de départ
	 * 
	 * @param etat
	 * 		Etat initial de l'individu
	 * @param caseDepart
	 * 		Case initiale de l'individu
	 * @see Etat
	 * @see Case
	 */
	public Individu(Etat etat, Case caseDepart) {
		this(etat, caseDepart, true);
	}
	
	/**
	 * Constructeur d'un individu avec son état de départ, sa case de départ, et si son déplacement est autorisé
	 * 
	 * @param etat
	 * 		Etat initial de l'individu
	 * @param caseDepart
	 * 		Case initiale de l'individu
	 * @param dA
	 * 		Booléen qui indique si l'individu peut se déplacer ou non
	 */
	public Individu(Etat etat, Case caseDepart, boolean dA) {
		this.etat = etat;
		this.maCase = caseDepart;
		this.maCase.addIndividu(this);
		this.deplacementAutorise = dA;
	}
	
	/**
	 * Retourne la Case où se situe l'individu
	 * 
	 * @return une Case
	 * @see Case
	 */
	public Case getMaCase() {
		return maCase;
	}
	
	/**
	 * Retourne un booléen pour savoir si l'individu peut se déplacer
	 * 
	 * @return un booléen correspondant au deplacement
	 */
	public boolean hasDeplacementAutorise() {
		return deplacementAutorise;
	}
	
	/**
	 * Met à jour le déplacement
	 * 
	 * @param deplacement
	 * 		Nouvelle indication de déplacement
	 */
	public void setDeplacement(boolean deplacement) {
		this.deplacementAutorise = deplacement;
	}
	
	/**
	 * Change de position l'individu sur une nouvelle case
	 * 
	 * @param destination
	 * 		La nouvelle case où va aller l'individu
	 */
	public void seDeplacer(Case destination) {
		maCase.retirerIndividu(this);
		this.maCase = destination;
		maCase.addIndividu(this);
	}
	
	/**
	 * Met à jour l'état de l'individu
	 * 
	 * @param etat
	 * 		Nouvelle état de l'individu
	 */
	public void setEtat(Etat etat) {
		this.etat = etat;
	}
	
	/**
	 * Retourne l'état de l'individu
	 * 
	 * @return un Etat correspondant à l'état de l'individu
	 * @see Etat
	 */
	public Etat getEtat() {
		return etat;
	}
	
	/**
	 * Met à jour le masque vers true
	 */
	public void setMasque() {
		masque = true;
	}
	
	/**
	 * Retourne un booléen pour savoir si l'individu a un masque
	 * 
	 * @return un booléen correspondant au port du masque
	 */
	public boolean hasMasque() {
		return masque;
	}
}
