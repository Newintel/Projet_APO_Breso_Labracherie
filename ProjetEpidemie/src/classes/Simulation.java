package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.io.*;

/**
 * 
 * Simulation est la classe qui effectuera toutes les actions en lien avec l'�pid�mie
 *
 */
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
	
	// attributs pour les r�sultats finaux
	private int[] resumeSains, resumeExposes, resumeInfectes, resumeRetires, resumeMorts;
	
	/**
	 * Constructeur de la simulation qui initialise tous les param�tres
	 * 
	 * @param s0
	 * 		Population initiale
	 * @param i0
	 * 		Nombre de contamin�s initial
	 * @param tauxC
	 * 		Taux de contamination
	 * @param tauxR
	 * 		Taux de retirement
	 * @param tauxE
	 * 		Taux d'exposition
	 * @param propN
	 * 		Proportion de nouveaux individus
	 * @param tauxM
	 * 		Taux de mortalit�
	 * @param jours
	 * 		Nombre de jours de la simulation
	 * @param spatialisation
	 * 		Bool�en qui indique si la spatialisation est active
	 * @param politiquesActives
	 * 		Bool�en qui indique si les politiques publiques sont actives
	 * @param confinement
	 * 		Bool�en qui indique si le confinement est actif
	 * @param propMasques
	 * 		Proportion de personnes portant le masque
	 * @param quarantaine
	 * 		Bool�en qui indique si la quarantaine est active
	 * @param propV
	 * 		Proportion de personnes pouvant �tre vaccin�es
	 * @param longueur
	 * 		Longueur du monde
	 * @param largeur
	 * 		Largeur du monde
	 */
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
			// En augmentant cette valeur, les individus ont moins de chance de se d�placer
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
	
	/**
	 * Applique le boolean masque � un certain nombre d'individus
	 */
	private void attribuerMasque() {
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
	
	/**
	 * Effectue la simulation de type SIR
	 */
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
			
			// d�placer tous les individus
			this.deplacer();
			
			resumeSains[i+1] = resumeSains[i] - nbInfectes;
			resumeInfectes[i+1] = resumeInfectes[i] + nbInfectes - nbRetires;
			resumeRetires[i+1] = resumeRetires[i] + nbRetires;
		}
		System.out.println("Jour " + jours + ":\n\tSains : " + resumeSains[jours] + "\n\tInfectes : " + resumeInfectes[jours] + "\n\tRetires : " + resumeRetires[jours]);
		try {
			this.toCSV("SIR");
		} catch (IOException e) {
			System.out.println("Erreur lors de l'écriture dans le fichier.");
		}
		
	}
	
	/**
	 * Effectue la simulation de type SEIR 
	 */
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
			
			// passer les individus sains � expos�s
			int nbExposes = this.exposer();
			
			// d�placer tous les individus
			this.deplacer();
			
			resumeSains[i+1] = resumeSains[i] - nbExposes;
			resumeExposes[i+1] = resumeExposes[i] + nbExposes - nbInfectes;
			resumeInfectes[i+1] = resumeInfectes[i] + nbInfectes - nbRetires;
			resumeRetires[i+1] = resumeRetires[i] + nbRetires;
		}
		
		System.out.println("Jour " + jours + ":\n\tSains : " + resumeSains[jours] + "\n\tExposes : " + resumeExposes[jours] + "\n\tInfectes : " + resumeInfectes[jours] + "\n\tRetires : " + resumeRetires[jours]);
		try {
			this.toCSV("SEIR");
		} catch (IOException e) {
			System.out.println("Erreur lors de l'écriture dans le fichier.");
		}
	}
	
	/**
	 * Effectue la simulation de type SEIR et avec �volution de population
	 */
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
			
			// passer les individus sains � expos�s
			int nbExposes = this.exposer();
			
			// d�placer tous les individus
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
		try {
			this.toCSV("SEIRN");
		} catch (IOException e) {
			System.out.println("Erreur lors de l'écriture dans le fichier.");
		}
	}
	
	/**
	 * Deplace al�atoirement d'une case chaque individu dans la simulation
	 */
	private void deplacer() {
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
						// pas de d�placement
						deplacementImpossible = false;
						break;
				}
			} while (deplacementImpossible);
					
		}
	}
	
	/**
	 * Contamine les individus d'�tat "�tatAContaminer" par rapport au taux de contamination
	 * Si la spatialisation est active, contamine par rapport aux cases,
	 * sinon contamine peu importe l'endroit des individus
	 * 
	 * @param etat
	 * 		Etat qui peut �tre contamin�
	 * @return un entier correspondant au nombre de personnes contamin�es
	 * @see Monde#contaminer(Etat, double)
	 */
	private int contaminer(Etat etat) {
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
	
	/**
	 * Passe un certain nombre d'individu de l'�tat INFECTE � l'�tat RETIRE en fonction du taux de retirement "tauxRetirement"
	 * 
	 * @return un entier correspondant au nombre de personnes retir�es suite au test
	 */
	private int retirer() {
		int nbRetire = 0;
		for (Individu individu : lesIndividus) {
			if (individu.getEtat().equals(Etat.INFECTE) && new Random().nextInt((int) (1/this.tauxRetirement)) == 0) {
				individu.setEtat(Etat.RETIRE);
				nbRetire++;
			}
		}
		return nbRetire;
	}
	
	/**
	 * Passe un certain nombre d'individu de l'�tat SAIN � l'�tat EXPOSE en fonction du taux d'exposition "tauxExposition"
	 * 
	 * @return un entier correspondant au nombre de personnes expos�es suite au test
	 */
	private int exposer() {
		int nbExpose = 0;
		for (Individu individu : lesIndividus) {
			if (individu.getEtat().equals(Etat.SAIN) && new Random().nextInt((int) (1/this.tauxExposition)) == 0) {
				individu.setEtat(Etat.EXPOSE);
				nbExpose++;
			}
		}
		return nbExpose;
	}
	
	/**
	 * Augmente le nombre d'individus en fonction de la proportion de naissance
	 * 
	 * @return un entier correspondant au nombre de nouvelles personnes
	 */
	private int naissance() {
		int nbNaissance = 0;
		while(nbNaissance < ((int) this.propNaissance*lesIndividus.size())) {
			Random rPlacement = new Random();
			lesIndividus.add(new Individu(Etat.SAIN, leMonde.getCase(rPlacement.nextInt(leMonde.getLongueur()), rPlacement.nextInt(leMonde.getLargeur()))));
			nbNaissance++;
		}
		return nbNaissance;
	}
	
	/**
	 * Effectue le test de mort naturelle par rapport au taux de mort naturelle "tauxMort"
	 * 
	 * @return une liste des personnes d�ced�es
	 */
	private ArrayList<Individu> mortNaturelle() {
		ArrayList<Individu> individusMorts = new ArrayList<>();
		for (Individu individu : lesIndividus) {
			if (new Random().nextInt((int) (1/this.tauxMort)) == 0) {
				individusMorts.add(individu);
			}
		}
		return individusMorts;
	}
	
	/**
	 * Effectue un test de vaccin sur un certain nombre d'individus
	 * 
	 * @return un entier correspondant au nombre de personnes vaccin�es
	 */
	private int vacciner() {
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
	
	/**
	 * Retourne le nombre d'individus par rapport � un Etat donn�
	 * 
	 * @param etat
	 * 		Etat dont on cherche le nombre
	 * @return un entier correspondant au nombre d'individus de l'�tat donn�e
	 * @see Etat
	 */
	private int getNbIndividus(Etat etat) {
		int n = 0;
		for (Individu individu : lesIndividus) {
			if (individu.getEtat().equals(etat)) {
				n++;
			}
		}
		return n;
	}

	/**
	 * Créer un fichier CSV pour analyser les données de la simulation
	 * 
	 * @param categorie
	 * 		Type de simulation effectué
	 * @throws IOException
	 */
	private void toCSV(String categorie) throws IOException {
		
		char separateur = ',';
		char delimiteur = '\n';
		String exposes = "";
		String mort = "";
		int[][] data = new int[][]{resumeSains, null, resumeInfectes, resumeRetires, null};

		try {
            File don = new File("../../donnees.csv");
  
            // Créer un nouveau fichier
            // Vérifier s'il n'existe pas
            if (!don.createNewFile()){
				don.delete();
			}
        }
        catch (Exception e) {
            System.err.println(e);
        }

		File d = new File("donnees.csv");

		if (d.exists()){
			d.delete();
		}

		try {
			FileWriter donnees = new FileWriter("donnees.csv");

			if (categorie != "SIR"){
				exposes = " Exposés,";
				data[1] = resumeExposes;
			}

			if (categorie == "SEIRN"){
				data[4] = resumeMorts;
				mort = ", Morts";
			}

			String header = "Jour, Sains," + exposes + " Infectés, Retirés" + mort + delimiteur;
			donnees.append(header);
			
			for (int i = 0; i <= jours; i++){
				String line = "";
				line += Integer.toString(i);
				for (int j = 0; j < 5; j++){
					if (data[j] != null){
						line += separateur + Integer.toString(data[j][i]);
					}
				}
				line += delimiteur;
				donnees.append(line);
			}
			donnees.close();
			System.out.println("CSV");
		} catch (IOException e) {
			throw new IOException();
		}
	
	}

}
