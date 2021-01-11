package classes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Simulation {

	private int s0, i0, p0, jours;
	private double tauxRetirement, tauxContamination, tauxExposition, propNaissance, tauxMort, propVaccination;
	private ArrayList<Individu> lesIndividus;
	private Random directionIndividu;
	private Monde leMonde;
	
	// attributs pour les polititques publiques
	private boolean spatialisation, politiquesActives, quarantaine;
	private int deplacementP = 0;
	private double propMasques;
	
	// attributs pour les résultats finaux
	private int[] resumeSains, resumeExposes, resumeInfectes, resumeRetires, resumeMorts;
	
	public Simulation(int s0, int i0, double tauxC, double tauxR, double tauxE, double propN, double tauxM, int jours, boolean spatialisation, boolean politiquesActives, boolean confinement, double propMasques, boolean quarantaine, double propV, int longueur, int largeur) {
		this.i0 = i0;
		this.p0 = s0-i0;
		this.s0 = s0;
		this.tauxContamination = tauxC/100;
		this.tauxRetirement = tauxR/100;
		this.tauxExposition = tauxE/100;
		this.propNaissance = propN;
		this.tauxMort = tauxM/100;
		this.jours = jours;
		this.spatialisation = spatialisation;
		this.politiquesActives = politiquesActives;
		this.propMasques = propMasques;
		this.quarantaine = quarantaine;
		this.propVaccination = propV;
		directionIndividu = new Random();
		
		leMonde = new Monde(longueur, largeur);
		lesIndividus = new ArrayList<>();
		
		Random rPlacement = new Random();
		for (int i = 0; i < s0; i++) {
			lesIndividus.add(new Individu(Etat.SAIN, leMonde.getCase(rPlacement.nextInt(longueur), rPlacement.nextInt(largeur))));
		}
		
		for (int i = 0; i < i0; i++) {
			lesIndividus.add(new Individu(Etat.INFECTE, leMonde.getCase(rPlacement.nextInt(longueur), rPlacement.nextInt(largeur))));
		}
		
		this.attribuerMasque();
		
		if (confinement) {
			// En augmentant cette valeur, les individus ont moins de chance de se déplacer
			this.deplacementP = 16;
		}
		
		resumeSains = new int[jours+1];
		resumeExposes = new int[jours+1]; 
		resumeInfectes = new int[jours+1]; 
		resumeRetires = new int[jours+1];
		resumeMorts = new int[jours+1];
		
		resumeSains[0] = this.s0;
		resumeExposes[0] = 0;
		resumeInfectes[0] = this.i0;
		resumeRetires[0] = 0;
		resumeMorts[0] = 0;
		
	}
	
	public void attribuerMasque() {
		Collections.shuffle(lesIndividus);
		int nbMasques = (int) this.propMasques*lesIndividus.size();
		for (int i = 0; nbMasques > 0 && i < lesIndividus.size(); i++) {
			Individu individu = lesIndividus.get(i);
			if (!individu.hasMasque()) {
				individu.setMasque();
			} 
			nbMasques--;
		}
	}
	
	public void SIR() {
		for (int i = 0; i < jours; i++) {
			
			System.out.println("Jour " + i + ":\n\tSains : " + resumeSains[i] + "\n\tInfectes : " + resumeInfectes[i] + "\n\tRetires : " + resumeRetires[i]);
			
			// faire le test de retirement avant de faire le test de contamination
			 int nbRetires = this.retirer();
			
			// faire le test de contamination
			int nbInfectes = 0;
			if (!this.quarantaine) {
				nbInfectes = this.contaminer(Etat.SAIN);
			}
			
			if (this.propVaccination > 0) {
				nbRetires += this.vacciner();
			}
			
			// déplacer tous les individus
			this.deplacer();
			
			resumeSains[i+1] = resumeSains[i] - nbInfectes;
			resumeInfectes[i+1] = resumeInfectes[i] + nbInfectes - nbRetires;
			resumeRetires[i+1] = resumeRetires[i] + nbRetires;
		}
		System.out.println("Jour " + jours + ":\n\tSains : " + resumeSains[jours] + "\n\tInfectes : " + resumeInfectes[jours] + "\n\tRetires : " + resumeRetires[jours]);
	}
	
	public void SEIR() {
		for (int i = 0; i < jours; i++) {
			
			System.out.println("Jour " + i + ":\n\tSains : " + resumeSains[i] + "\n\tExposes : " + resumeExposes[i] + "\n\tInfectes : " + resumeInfectes[i] + "\n\tRetires : " + resumeRetires[i]);
			
			// faire le test de retirement avant de faire le test de contamination
			int nbRetires = this.retirer();
			
			// faire le test de contamination
			int nbInfectes = 0;
			if (!this.quarantaine) {
				nbInfectes = leMonde.contaminer(Etat.EXPOSE, this.tauxContamination);
			}
			
			if (this.propVaccination > 0) {
				nbRetires += this.vacciner();
			}
			
			// passer les individus sains à exposés
			int nbExposes = this.exposer();
			
			// déplacer tous les individus
			this.deplacer();
			
			resumeSains[i+1] = resumeSains[i] - nbExposes;
			resumeExposes[i+1] = resumeExposes[i] + nbExposes - nbInfectes;
			resumeInfectes[i+1] = resumeInfectes[i] + nbInfectes - nbRetires;
			resumeRetires[i+1] = resumeRetires[i] + nbRetires;
		}
		
		System.out.println("Jour " + jours + ":\n\tSains : " + resumeSains[jours] + "\n\tExposes : " + resumeExposes[jours] + "\n\tInfectes : " + resumeInfectes[jours] + "\n\tRetires : " + resumeRetires[jours]);
	}
	
	public void SEIRN() {
		for (int i = 0; i < jours; i++) {
			
			System.out.println("Jour " + i + ":\n\tSains : " + resumeSains[i] + "\n\tExposes : " + resumeExposes[i] + "\n\tInfectes : " + resumeInfectes[i] + "\n\tRetires : " + resumeRetires[i] + "\n\tMorts : " + resumeMorts[i]);
			
			// faire le test de retirement avant de faire le test de contamination
			int nbRetires = this.retirer();
			
			// faire le test de contamination
			int nbInfectes = 0;
			if (!this.quarantaine) {
				nbInfectes = leMonde.contaminer(Etat.EXPOSE, this.tauxContamination);
			}
			
			if (this.propVaccination > 0) {
				nbRetires += this.vacciner();
			}
			
			// passer les individus sains à exposés
			int nbExposes = this.exposer();
			
			// déplacer tous les individus
			this.deplacer();
			
			// mort natuelle
			ArrayList<Individu> individusMorts = this.mortNaturelle();
			
			int nbSains = resumeSains[i];
			
			for (Individu individu : individusMorts) {
				switch (individu.getEtat()) {
				case SAIN:
					nbSains--;
					break;
				case EXPOSE:
					nbExposes--;
					break;
				case INFECTE:
					nbInfectes--;
					break;
				case RETIRE:
					nbRetires--;
					break;
				}
			}
			
			// naissance
			int nbNaissance = this.naissance();
			
			resumeSains[i+1] = nbSains + nbNaissance - nbExposes;
			resumeExposes[i+1] = resumeExposes[i] + nbExposes - nbInfectes;
			resumeInfectes[i+1] = resumeInfectes[i] + nbInfectes - nbRetires;
			resumeRetires[i+1] = resumeRetires[i] + nbRetires;
			resumeMorts[i+1] = resumeMorts[i] + individusMorts.size();
		}
		
		System.out.println("Jour " + jours + ":\n\tSains : " + resumeSains[jours] + "\n\tExposes : " + resumeExposes[jours] + "\n\tInfectes : " + resumeInfectes[jours] + "\n\tRetires : " + resumeRetires[jours] + "\n\tMorts : " + resumeMorts[jours]);
	}
	
	public void deplacer() {
		boolean deplacementImpossible = true;
		for (Individu individu : lesIndividus) {
			int xIndividu = individu.getMaCase().getX();
			int yIndividu = individu.getMaCase().getY();
			do {
				int direction = directionIndividu.nextInt(4 + this.deplacementP);
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
					default:
						// pas de déplacement
						deplacementImpossible = false;
						break;
				}
			} while (deplacementImpossible);
					
		}
	}
	
	public int contaminer(Etat etat) {
		if (this.spatialisation) {
			return leMonde.contaminer(etat, this.tauxContamination);
		} else {
			int nbInfectes = 0;
			if (lesIndividus.stream().anyMatch(i -> i.getEtat().equals(etat)) && lesIndividus.stream().anyMatch(i -> i.getEtat().equals(Etat.INFECTE))) {
				for (Individu individu : lesIndividus) {
					for (int j = 0; j < this.getNbIndividus(Etat.INFECTE) && individu.getEtat().equals(etat); j++) {
						if (individu.hasMasque()) {
							this.tauxContamination /= 4;
						}
						if (new Random().nextInt((int) (1/tauxContamination)) == 0) {
							individu.setEtat(Etat.INFECTE);
							nbInfectes++;
						}
					}
				}
			}
			return nbInfectes;
		}
		
	}
	
	public int retirer() {
		int nbRetire = 0;
		for (Individu individu : lesIndividus) {
			if (individu.getEtat().equals(Etat.INFECTE) && new Random().nextInt((int) (1/this.tauxRetirement)) == 0) {
				individu.setEtat(Etat.RETIRE);
				nbRetire++;
			}
		}
		return nbRetire;
	}
	
	public int exposer() {
		int nbExpose = 0;
		for (Individu individu : lesIndividus) {
			if (individu.getEtat().equals(Etat.SAIN) && new Random().nextInt((int) (1/this.tauxExposition)) == 0) {
				individu.setEtat(Etat.EXPOSE);
				nbExpose++;
			}
		}
		return nbExpose;
	}
	
	public int naissance() {
		int nbNaissance = 0;
		while(nbNaissance < ((int) this.propNaissance*lesIndividus.size())) {
			Random rPlacement = new Random();
			lesIndividus.add(new Individu(Etat.SAIN, leMonde.getCase(rPlacement.nextInt(leMonde.getLongueur()), rPlacement.nextInt(leMonde.getLargeur()))));
			nbNaissance++;
		}
		return nbNaissance;
	}
	
	public ArrayList<Individu> mortNaturelle() {
		ArrayList<Individu> individusMorts = new ArrayList<>();
		for (Individu individu : lesIndividus) {
			if (new Random().nextInt((int) (1/this.tauxMort)) == 0) {
				individusMorts.add(individu);
			}
		}
		return individusMorts;
	}
	
	public int vacciner() {
		int nbVaccine = 0;
		for (int i = 0; i < lesIndividus.size() && nbVaccine < ((int) this.propVaccination*lesIndividus.size()); i++) {
			Individu individu = lesIndividus.get(i);
			if (individu.getEtat().equals(Etat.SAIN)) {
				individu.setEtat(Etat.RETIRE);
				nbVaccine++;
			}
		}
		return nbVaccine;
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
