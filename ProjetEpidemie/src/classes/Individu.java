package classes;

public class Individu {

	private Case maCase;
	private boolean deplacementAutorise;
	private boolean masque = false;
	
	private Etat etat;
	
	public Individu(Etat etat, Case caseDepart) {
		this(etat, caseDepart, true);
	}
	
	public Individu(Etat etat, Case caseDepart, boolean dA) {
		this.etat = etat;
		this.maCase = caseDepart;
		this.maCase.addIndividu(this);
		this.deplacementAutorise = dA;
	}
	
	public Case getMaCase() {
		return maCase;
	}
	
	public boolean hasDeplacementAutorise() {
		return deplacementAutorise;
	}
	
	public void setDeplacement(boolean deplacement) {
		this.deplacementAutorise = deplacement;
	}
	
	public void seDeplacer(Case destination) {
		maCase.retirerIndividu(this);
		this.maCase = destination;
		maCase.addIndividu(this);
	}
	
	public void setEtat(Etat etat) {
		this.etat = etat;
	}
	
	public Etat getEtat() {
		return etat;
	}
	
	public void setMasque() {
		masque = true;
	}
	
	public boolean hasMasque() {
		return masque;
	}
}
