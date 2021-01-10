package classes;

public class SimulationEpidemie {

	public static void main(String[] args) {
		Simulation simu = new Simulation(6, 32, 0.1, 1, 0.5, 1, 0.1, 30, 4, 4);
		System.out.println(simu.toString());
		System.out.println(simu.getNbIndividus(Etat.INFECTE));
		System.out.println(simu.getNbIndividus(Etat.RETIRE));
		simu.SEIRN();
		System.out.println(simu.toString());
		System.out.println(simu.getNbIndividus(Etat.INFECTE));
		System.out.println(simu.getNbIndividus(Etat.RETIRE));
	}

}
