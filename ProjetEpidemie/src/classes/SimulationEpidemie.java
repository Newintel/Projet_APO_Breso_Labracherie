package classes;

public class SimulationEpidemie {

	public static void main(String[] args) {
		Simulation simu = new Simulation(1, 32, 0.1, 1, 0.5, 1, 30, 4, 4);
		System.out.println(simu.toString());
		System.out.println(simu.getNbIndividus("contamine"));
		System.out.println(simu.getNbIndividus("retire"));
		simu.SEIRN();
		System.out.println(simu.toString());
		System.out.println(simu.getNbIndividus("contamine"));
		System.out.println(simu.getNbIndividus("retire"));
	}

}
