package classes;

import java.util.ArrayList;

public class Case {

	private int x, y;
	private ArrayList<Individu> mesIndividus;
	
	public Case(int y, int x) {
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
	
	public int getNbContamines() {
		int n = 0;
		for (Individu individu : mesIndividus) {
			if (individu instanceof IndividuContamine) {
				n++;
			}
		}
		return n;
	}
	
	public void remplacerIndividus(ArrayList<IndividuSain> nouveauxContamines) {
		for (IndividuSain individu : nouveauxContamines) {
			mesIndividus.remove(individu);
			individu = null;
			mesIndividus.add(new IndividuContamine(this));
		}
	}
	
	@Override
	public String toString() {
		return "(" + y + ", " + x + ")";
	}
}
