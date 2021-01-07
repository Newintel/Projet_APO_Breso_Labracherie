package classes;

public abstract class Individu {

	private Case maCase;
	private boolean deplacementAutorise;
	
	public Individu(Case caseDepart) {
		this(caseDepart, true);
	}
	
	public Individu(Case caseDepart, boolean dA) {
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
	
}
