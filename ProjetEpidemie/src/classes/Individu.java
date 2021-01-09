package classes;

public class Individu {

	private Case maCase;
	private boolean deplacementAutorise;
	
	private String etat;
	
	public Individu(String etat, Case caseDepart) {
		this(etat, caseDepart, true);
	}
	
	public Individu(String etat, Case caseDepart, boolean dA) {
		this.etat = etat;
		this.maCase = caseDepart;
		this.maCase.addIndividu(this);
		this.deplacementAutorise = dA;
	}
	
	public Case getMaCase() {
		return maCase;
	}
	
	public boolean isDeplacementAutorise() {
		return deplacementAutorise;
	}
	
	public void seDeplacer(Case destination) {
		maCase.retirerIndividu(this);
		this.maCase = destination;
		maCase.addIndividu(this);
	}
	
	public void setEtat(String etat) {
		this.etat = etat;
	}
	
	public String getEtat() {
		return etat;
	}
}
