package classes;

import java.util.ArrayList;

public class Zone {

	private int x, y;
	private ArrayList<Case> mesCases;
	private ArrayList<Individu> mesIndividus;
	
	public Zone(int y, int x) {
		this.x = x;
		this.y = y;
		mesCases = new ArrayList<>();
		mesIndividus = new ArrayList<>();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public ArrayList<Case> getMesCases() {
		return mesCases;
	}
	
	public void addCase(Case nouvelleCase) {
		mesCases.add(nouvelleCase);
	}
	
	public ArrayList<Individu> getMesIndividus() {
		return mesIndividus;
	}
	
	public void addIndividus() {
		for (Case c : mesCases) {
			for (Individu individu : c.getMesIndividus()) {
				mesIndividus.add(individu);
			}
		}
	}
	
	public int getNbContamines() {
		int n = 0;
		for (Individu individu : mesIndividus) {
			if (individu.getEtat().equals("contamine")) {
				n++;
			}
		}
		return n;
	}
	
}
