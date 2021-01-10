package classes;

import java.util.ArrayList;
import java.util.Random;

public class Simulation {

	private int s0, i0, p0, jours;
	private double tauxRetirement, tauxContamination, tauxExposition, tauxNaissance, tauxMort;
	private ArrayList<Individu> lesIndividus;
	private Random directionIndividu;
	private Monde leMonde;
	
	public Simulation(int i0, int p0, double tauxR, double tauxC, double tauxE, double tauxN, double tauxM, int jours) {
		this(i0, p0, tauxR, tauxC, tauxE, tauxN, tauxM, jours, 500, 500);
	}
	
	public Simulation(int i0, int p0, double tauxR, double tauxC, double tauxE, double tauxN, double tauxM, int jours, int longueur, int largeur) {
		this.i0 = i0;
		this.p0 = p0;
		this.s0 = this.p0-this.i0;
		this.tauxRetirement = tauxR;
		this.tauxContamination = tauxC;
		this.tauxExposition = tauxE;
		this.tauxNaissance = tauxN;
		this.tauxMort = tauxM;
		this.jours = jours;
		leMonde = new Monde(longueur, largeur);
		lesIndividus = new ArrayList<>();
		
		Random rPlacement = new Random();
		for (int i = 0; i < s0; i++) {
			lesIndividus.add(new Individu(Etat.SAIN, leMonde.getCase(rPlacement.nextInt(longueur), rPlacement.nextInt(largeur))));
		}
		
		for (int i = 0; i < i0; i++) {
			lesIndividus.add(new Individu(Etat.INFECTE, leMonde.getCase(rPlacement.nextInt(longueur), rPlacement.nextInt(largeur))));
		}
		
		leMonde.creationZones();
		
		directionIndividu = new Random();
		
	}
	
	public void SIR() {
		for (int i = 0; i < jours; i++) {
			// faire le test de retirement avant de faire le test de contamination
			this.retirer();
			
			// faire le test de contamination
			leMonde.contaminer(Etat.SAIN, this.tauxContamination);
			
			// d�placer tous les individus
			this.deplacer();
		}
	}
	
	public void SEIR() {
		for (int i = 0; i < jours; i++) {
			// faire le test de retirement avant de faire le test de contamination
			this.retirer();
			
			// faire le test de contamination
			leMonde.contaminer(Etat.EXPOSE, this.tauxContamination);
			
			// passer les individus sains � expos�s
			this.exposer();
			
			// d�placer tous les individus
			this.deplacer();
		}
	}
	
	public void SEIRN() {
		for (int i = 0; i < jours; i++) {
			// faire le test de retirement avant de faire le test de contamination
			this.retirer();
			
			// faire le test de contamination
			leMonde.contaminer(Etat.EXPOSE, this.tauxContamination);
			
			// passer les individus sains � expos�s
			this.exposer();
			
			// d�placer tous les individus
			this.deplacer();
			
			// mort natuelle
			this.mortNaturelle();
			
			// naissance
			this.naissance();
		}
	}
	
	public void deplacer() {
		boolean deplacementImpossible = true;
		for (Individu individu : lesIndividus) {
			int xIndividu = individu.getMaCase().getX();
			int yIndividu = individu.getMaCase().getY();
			do {
				int direction = directionIndividu.nextInt(4);
				switch (direction) {
					case 0:
						// Ouest
						if ( (yIndividu - 1) >= 0) {
							deplacementImpossible = false;
							individu.seDeplacer(leMonde.getCase(xIndividu, yIndividu-1));
						}
						break;
					case 1:
						// Sud
						if ( (xIndividu + 1) < leMonde.getLargeur()) {
							deplacementImpossible = false;
							individu.seDeplacer(leMonde.getCase(xIndividu+1, yIndividu));
						}
						break;
					case 2:
						// Est
						if ( (yIndividu + 1) < leMonde.getLongueur()) {
							deplacementImpossible = false;
							individu.seDeplacer(leMonde.getCase(xIndividu, yIndividu+1));
						}
						break;
					case 3:
						// Nord
						if ( (xIndividu - 1) >= 0) {
							deplacementImpossible = false;
							individu.seDeplacer(leMonde.getCase(xIndividu-1, yIndividu));
						}
						break;
				}
			} while (deplacementImpossible);
					
		}
	}
	
	public void retirer() {
		for (Individu individu : lesIndividus) {
			if (individu.getEtat().equals(Etat.INFECTE) && new Random().nextInt((int) (1/this.tauxRetirement)) == 0) {
				individu.setEtat(Etat.RETIRE);
			}
		}
	}
	
	public void exposer() {
		for (Individu individu : lesIndividus) {
			if (individu.getEtat().equals(Etat.SAIN) && new Random().nextInt((int) (1/this.tauxExposition)) == 0) {
				individu.setEtat(Etat.EXPOSE);
			}
		}
	}
	
	public void naissance() {
		/*Random rPlacement = new Random();
		if (new Random().nextInt((int) (1/this.tauxNaissance)) == 0) {
			lesIndividus.add(new Individu(Etat.Sain, leMonde.getCase(rPlacement.nextInt(leMonde.getLongueur()), rPlacement.nextInt(leMonde.getLargeur()))));
		}*/
	}
	
	public void mortNaturelle() {
		/**/
	}
	
	@Override
	public String toString() {
		String s = "";
		int i = 0;
		for (Case c : leMonde.getLesCases()) {
			if (i%leMonde.getLongueur() == 0) {
				s += "\n---------------------------------------------------------------------------------\n| ";
			}
			s += c.getMesIndividus().size() + " | ";
			i++;
		}
		s += "\n---------------------------------------------------------------------------------\n";
		return s;
	}
	
	public int getNbIndividus(Etat etat) {
		int n = 0;
		for (Individu individu : lesIndividus) {
			if (individu.getEtat().equals(etat)) {
				n++;
			}
		}
		return n;
	}

}
