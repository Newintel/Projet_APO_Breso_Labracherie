package classes;

public class Individu {

	private Case maCase;
	private boolean deplacementAutorise;
	private boolean masque = false;
	
	private Etat etat;
	
	/**
	 * Constructeur d'un individu avec son �tat de d�part et sa case de d�part
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
	 * Constructeur d'un individu avec son �tat de d�part, sa case de d�part, et si son d�placement est autoris�
	 * 
	 * @param etat
	 * 		Etat initial de l'individu
	 * @param caseDepart
	 * 		Case initiale de l'individu
	 * @param dA
	 * 		Bool�en qui indique si l'individu peut se d�placer ou non
	 */
	public Individu(Etat etat, Case caseDepart, boolean dA) {
		this.etat = etat;
		this.maCase = caseDepart;
		this.maCase.addIndividu(this);
		this.deplacementAutorise = dA;
	}
	
	/**
	 * Retourne la Case o� se situe l'individu
	 * 
	 * @return une Case
	 * @see Case
	 */
	public Case getMaCase() {
		return maCase;
	}
	
	/**
	 * Retourne un bool�en pour savoir si l'individu peut se d�placer
	 * 
	 * @return un bool�en correspondant au deplacement
	 */
	public boolean hasDeplacementAutorise() {
		return deplacementAutorise;
	}
	
	/**
	 * Met � jour le d�placement
	 * 
	 * @param deplacement
	 * 		Nouvelle indication de d�placement
	 */
	public void setDeplacement(boolean deplacement) {
		this.deplacementAutorise = deplacement;
	}
	
	/**
	 * Change de position l'individu sur une nouvelle case
	 * 
	 * @param destination
	 * 		La nouvelle case o� va aller l'individu
	 */
	public void seDeplacer(Case destination) {
		maCase.retirerIndividu(this);
		this.maCase = destination;
		maCase.addIndividu(this);
	}
	
	/**
	 * Met � jour l'�tat de l'individu
	 * 
	 * @param etat
	 * 		Nouvelle �tat de l'individu
	 */
	public void setEtat(Etat etat) {
		this.etat = etat;
	}
	
	/**
	 * Retourne l'�tat de l'individu
	 * 
	 * @return un Etat correspondant � l'�tat de l'individu
	 * @see Etat
	 */
	public Etat getEtat() {
		return etat;
	}
	
	/**
	 * Met � jour le masque vers true
	 */
	public void setMasque() {
		masque = true;
	}
	
	/**
	 * Retourne un bool�en pour savoir si l'individu a un masque
	 * 
	 * @return un bool�en correspondant au port du masque
	 */
	public boolean hasMasque() {
		return masque;
	}
}
