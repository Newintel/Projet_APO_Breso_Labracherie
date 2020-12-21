package classes;

import java.util.ArrayList;
import java.util.Random;

public class Simulation {

	private int s0, i0, p0;
	private float tauxMortalite, tauxGuerison, tauxContamination;
	private ArrayList<Individu> lesIndividus;
	private Random direction;
	private Monde leMonde;
	
	public Simulation(int s0, int i0, int p0, float tauxM, float tauxG, float tauxC) {
		this(s0, i0, p0, tauxM, tauxG, tauxC, 500, 500);
	}
	
	public Simulation(int s0, int i0, int p0, float tauxM, float tauxG, float tauxC, int longueur, int largeur) {
		this.s0 = s0;
		this.i0 = i0;
		this.p0 = p0;
		this.tauxMortalite = tauxM;
		this.tauxGuerison = tauxG;
		this.tauxContamination = tauxC;
		lesIndividus = new ArrayList<>();
		/*for (int i = 0; i < p0; i++) {
			lesIndividus.add(new IndividuSain([coordonnées]));
		}*/
		direction = new Random();
		leMonde = new Monde(largeur, longueur);
	}
	
}
