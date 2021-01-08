package classes;

public class SimulationEpidemie {

	public static void main(String[] args) {
		Simulation simu = new Simulation(1, 32, 0, 0, 1, 4, 4);
		System.out.println(simu.toString());
		System.out.println(simu.getNbContamines());
		simu.run();
		System.out.println(simu.toString());
		System.out.println(simu.getNbContamines());
	}

}
