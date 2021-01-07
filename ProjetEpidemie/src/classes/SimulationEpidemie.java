package classes;

public class SimulationEpidemie {

	public static void main(String[] args) {
		Simulation simu = new Simulation(1, 1, 0, 0, 0, 4, 4);
		System.out.println(simu.toString());
		simu.deplacer();
		System.out.println(simu.toString());
	}

}
