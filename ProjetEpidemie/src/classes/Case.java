package classes;

import java.util.ArrayList;

public class Case {

	private int x, y;
	private ArrayList<Individu> mesIndividus;
	
	public Case(int x, int y) {
		this.x = x;
		this.y = y;
		mesIndividus = new ArrayList<>();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public ArrayList<Individu> getMesIndividus() {
		return mesIndividus;
	}
	
	public void addIndividu(Individu nouvelleIndividu) {
		mesIndividus.add(nouvelleIndividu);
	}
	
	public boolean retirerIndividu(Individu individu) {
		return mesIndividus.remove(individu);
	}
}
