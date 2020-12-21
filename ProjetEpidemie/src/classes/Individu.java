package classes;

public abstract class Individu {

	private Case maCase;
	private boolean deplacementAutorise;
	
	public Individu(Case caseDepart) {
		this(caseDepart, true);
	}
	
	public Individu(Case caseDepart, boolean dA) {
		this.maCase = caseDepart;
		this.deplacementAutorise = dA;
	}
	
	public Case getMaCase() {
		return maCase;
	}
	
	public boolean isDeplacementAutorise() {
		return deplacementAutorise;
	}
	
	public void seDeplacer(Case destination) {
		this.maCase = destination;
	}
	
}
