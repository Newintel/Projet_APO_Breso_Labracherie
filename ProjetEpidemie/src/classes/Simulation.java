package classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Simulation {

	private int s0, i0, p0;
	private float tauxMortalite, tauxGuerison, tauxContamination;
	private ArrayList<Individu> lesIndividus;
	private Random directionIndividu;
	private Monde leMonde;
	
	public Simulation(int i0, int p0, float tauxM, float tauxG, float tauxC) {
		this(i0, p0, tauxM, tauxG, tauxC, 500, 500);
	}
	
	public Simulation(int i0, int p0, float tauxM, float tauxG, float tauxC, int longueur, int largeur) {
		this.i0 = i0;
		this.p0 = p0;
		this.s0 = this.p0-this.i0;
		this.tauxMortalite = tauxM;
		this.tauxGuerison = tauxG;
		this.tauxContamination = tauxC;
		leMonde = new Monde(longueur, largeur);
		lesIndividus = new ArrayList<>();
		
		Random rPlacement = new Random();
		for (int i = 0; i < s0; i++) {
			lesIndividus.add(new IndividuSain(leMonde.getCase(rPlacement.nextInt(longueur), rPlacement.nextInt(largeur))));
		}
		
		for (int i = 0; i < i0; i++) {
			lesIndividus.add(new IndividuContamine(leMonde.getCase(rPlacement.nextInt(longueur), rPlacement.nextInt(largeur))));
		}
		
		directionIndividu = new Random();
		
	}
	
	public void run() {
		// d�placer tous les individus
		this.deplacer();
		// faire le test de contamination
	}
	
	public void deplacer() {
		boolean deplacementImpossible = true;
		for (Individu individu : lesIndividus) {
			int xIndividu = individu.getMaCase().getX();
			int yIndividu = individu.getMaCase().getY();
			do {
				int direction = directionIndividu.nextInt(3);
				switch (direction) {
					case 0:
						// Ouest
						if ( (yIndividu - 1) >= 0) {
							System.out.println(individu.getMaCase().toString() + "ouest" + leMonde.getCase(xIndividu, yIndividu-1).toString() + "\n");
							deplacementImpossible = false;
							individu.seDeplacer(leMonde.getCase(xIndividu, yIndividu-1));
						}
						break;
					case 1:
						// Sud
						if ( (xIndividu + 1) < leMonde.getLargeur()) {
							System.out.println(individu.getMaCase().toString() + "sud" + leMonde.getCase(xIndividu+1, yIndividu).toString() + "\n");
							deplacementImpossible = false;
							individu.seDeplacer(leMonde.getCase(xIndividu+1, yIndividu));
						}
						break;
					case 2:
						// Est
						if ( (yIndividu + 1) < leMonde.getLongueur()) {
							System.out.println(individu.getMaCase().toString() + "est" + leMonde.getCase(xIndividu, yIndividu+1).toString() + "\n");
							deplacementImpossible = false;
							individu.seDeplacer(leMonde.getCase(xIndividu, yIndividu+1));
						}
						break;
					case 3:
						// Nord
						if ( (xIndividu - 1) >= 0) {
							System.out.println(individu.getMaCase().toString() + "nord" + leMonde.getCase(xIndividu-1, yIndividu).toString() + "\n");
							deplacementImpossible = false;
							individu.seDeplacer(leMonde.getCase(xIndividu-1, yIndividu));
						}
						break;			
				}
			} while (deplacementImpossible);
					
		}
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
	
}
