package classes;

import java.util.ArrayList;
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
	
}
