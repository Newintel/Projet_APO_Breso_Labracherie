package classes;

import java.util.Scanner;

public class SimulationEpidemie {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int s0 = 0, i0 = 0;
		int nbJours = 0;
		double tauxC = -1, tauxR = -1, tauxE = -1, tauxM = -1;
		int propN = -1, propM = -1, propV = -1;
		int longueur = 0, largeur = 0;
		boolean spatialisation = false, politiquesActives = false, confinement = false, quarantaine = false;
		
		System.out.println("Bienvenue dans la simulation d'une épidémie.\nVous pouvez choisir entre les modèles SIR (1), SEIR (2) et SEIR avec évolution de population (3).\n");
		boolean wrong = true;
		String modele;
		do {
			System.out.println("Veuillez indiquer votre choix (1, 2, 3) :");
			modele = s.nextLine();
			if (modele.equals("1") || modele.equals("2") || modele.equals("3")) {
				wrong = false;
			}
		} while (wrong);
			
		String populationInitiale;
		do {
			System.out.println("Veuillez indiquer le nombre d'individus initiaux :");
			populationInitiale = s.nextLine();
			try {
				s0 = Integer.valueOf(populationInitiale);
				if (s0 > 0) {
					wrong = false;
				} else {
					System.out.println("Le nombre doit être supérieur à 0.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Veuillez indiquer un nombre.");
			}
		} while (wrong);
		
		String infectesInitiaux;
		do {
			System.out.println("Veuillez indiquer le nombre de contamines initiaux :");
			infectesInitiaux = s.nextLine();
			try {
				i0 = Integer.valueOf(infectesInitiaux);
				if (i0 > 0) {
					wrong = false;
				} else {
					System.out.println("Le nombre doit être supérieur à 0.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Veuillez indiquer un nombre.");
			}
		} while (wrong);
		
		String jours;
		wrong = true;
		do {
			System.out.println("Veuillez indiquer le nombre de jours à simuler (un jour = un déplacement):");
			jours = s.nextLine();
			try {
				nbJours = Integer.valueOf(jours);
				if (nbJours > 0) {
					wrong = false;
				} else {
					System.out.println("Le nombre doit être positif.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Veuillez indiquer un nombre.");
			}			
		} while (wrong);
		
		String tauxContamination;
		wrong = true;
		do {
			System.out.println("Veuillez indiquer le taux de contamination, compris entre 0 et 1 :");
			tauxContamination = s.nextLine();
			try {
				tauxC = Double.valueOf(tauxContamination);
				if (tauxC >= 0 && tauxC <= 1) {
					wrong = false;
				} else {
					System.out.println("Le nombre doit être compris entre 0 et 1.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Veuillez indiquer un nombre.");
			}			
		} while (wrong);
		
		String tauxRetirement;
		wrong = true;
		do {
			System.out.println("Veuillez indiquer le taux de désinféction (guérison ou décès), compris entre 0 et 1 :");
			tauxRetirement = s.nextLine();
			try {
				tauxR = Double.valueOf(tauxRetirement);
				if (tauxR >= 0 && tauxR <= 1) {
					wrong = false;
				} else {
					System.out.println("Le nombre doit être compris entre 0 et 1.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Veuillez indiquer un nombre.");
			}			
		} while (wrong);
		
		if (!modele.equals("1")) {
			String tauxExposition;
			wrong = true;
			do {
				System.out.println("Veuillez indiquer le taux d'exposition des individus, compris entre 0 et 1 :");
				tauxExposition = s.nextLine();
				try {
					tauxE = Double.valueOf(tauxExposition);
					if (tauxE >= 0 && tauxE <= 1) {
						wrong = false;
					} else {
						System.out.println("Le nombre doit être compris entre 0 et 1.");
					}
				} catch (NumberFormatException e) {
					System.out.println("Veuillez indiquer un nombre.");
				}			
			} while (wrong);
		}
		
		if (modele.equals("3")) {
			String propNaissance;
			wrong = true;
			do {
				System.out.println("Veuillez indiquer la proportion de nouveaux individus sur 100 individus, soit une valeur comprise entre 0 et 100 :");
				propNaissance = s.nextLine();
				try {
					propN = Integer.valueOf(propNaissance);
					if (propN >= 0 && propN <= 100) {
						wrong = false;
					} else {
						System.out.println("Le nombre doit être compris entre 0 et 100.");
					}
				} catch (NumberFormatException e) {
					System.out.println("Veuillez indiquer un nombre.");
				}			
			} while (wrong);
			
			String tauxMortalite;
			wrong = true;
			do {
				System.out.println("Veuillez indiquer le taux de mortalité naturel, compris entre 0 et 1 :");
				tauxMortalite = s.nextLine();
				try {
					tauxM = Double.valueOf(tauxMortalite);
					if (tauxM >= 0 && tauxM <= 1) {
						wrong = false;
					} else {
						System.out.println("Le nombre doit être compris entre 0 et 1.");
					}
				} catch (NumberFormatException e) {
					System.out.println("Veuillez indiquer un nombre.");
				}			
			} while (wrong);
		}
		
		String taille;
		wrong = true;
		do {
			System.out.println("Veuillez indiquer la longueur de la zone de simulation :");
			taille = s.nextLine();
			try {
				longueur = Integer.valueOf(taille);
				if (longueur > 0) {
					wrong = false;
				} else {
					System.out.println("Le nombre doit être positif.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Veuillez indiquer un nombre.");
			}			
		} while (wrong);
		
		wrong = true;
		do {
			System.out.println("Veuillez indiquer la largeur de la zone de simulation :");
			taille = s.nextLine();
			try {
				largeur = Integer.valueOf(taille);
				if (largeur > 0) {
					wrong = false;
				} else {
					System.out.println("Le nombre doit être positif.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Veuillez indiquer un nombre.");
			}			
		} while (wrong);
		
		wrong = true;
		do {
			System.out.println("Voulez vous activer la spatialisation des individus ? (o, n) :");
			switch (s.nextLine()) {
			case "o":
				spatialisation = true;
				wrong = false;
				break;
			case "n":
				wrong = false;
				break;
			}
		} while (wrong);
		
		wrong = true;
		do {
			System.out.println("Voulez vous activer les politiques publiques ? (o, n) :");
			switch (s.nextLine()) {
			case "o":
				politiquesActives = true;
				wrong = false;
				break;
			case "n":
				wrong = false;
				break;
			}
		} while (wrong);
		
		if (politiquesActives) {
			wrong = true;
			do {
				System.out.println("Voulez vous activer le confinement ? Cela reduira fortement le taux de contamination. (o, n) :");
				switch (s.nextLine()) {
				case "o":
					confinement = true;
					wrong = false;
					break;
				case "n":
					wrong = false;
					break;
				}
			} while (wrong);
			
			String propMasques;
			wrong = true;
			do {
				System.out.println("Veuillez indiquer la proportion d'individus portant un masque, sur 100 individus, soit une valeur comprise entre 0 et 100 :");
				propMasques = s.nextLine();
				try {
					propM = Integer.valueOf(propMasques);
					if (propM >= 0 && propM <= 100) {
						wrong = false;
					} else {
						System.out.println("Le nombre doit être compris entre 0 et 100.");
					}
				} catch (NumberFormatException e) {
					System.out.println("Veuillez indiquer un nombre.");
				}			
			} while (wrong);
			
			wrong = true;
			do {
				System.out.println("Voulez vous activer la quarantaine ? Cela empechera les infectes de contaminer. (o, n) :");
				switch (s.nextLine()) {
				case "o":
					quarantaine = true;
					wrong = false;
					break;
				case "n":
					wrong = false;
					break;
				}
			} while (wrong);
			
			String propVaccins;
			wrong = true;
			do {
				System.out.println("Veuillez indiquer la proportion d'individus sains pouvant se faire vacciner (ils passeront en état retiré), sur 100 individus, soit une valeur comprise entre 0 et 100 :");
				propVaccins = s.nextLine();
				try {
					propV = Integer.valueOf(propVaccins);
					if (propV >= 0 && propV <= 100) {
						wrong = false;
					} else {
						System.out.println("Le nombre doit être compris entre 0 et 100.");
					}
				} catch (NumberFormatException e) {
					System.out.println("Veuillez indiquer un nombre.");
				}			
			} while (wrong);
		}

		Simulation simu = new Simulation(s0, i0, tauxC, tauxR, tauxE, propN/100, tauxM, nbJours, spatialisation, politiquesActives, confinement, propM/100, quarantaine, propV/100, longueur, largeur);
		switch (modele) {
		case "1":
			simu.SIR();
			break;
		case "2":
			simu.SEIR();
			break;
		case "3":
			simu.SEIRN();
			break;
		}
	}

}
