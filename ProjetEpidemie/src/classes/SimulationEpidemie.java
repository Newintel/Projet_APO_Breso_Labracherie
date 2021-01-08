package classes;

public class SimulationEpidemie {

	public static void main(String[] args) {
		Simulation simu = new Simulation(1, 32, 0.1, 1, 4, 4);
		System.out.println(simu.toString());
		System.out.println(simu.getNbIndividus("contamine"));
		System.out.println(simu.getNbIndividus("retire"));
		simu.SIR();
		System.out.println(simu.toString());
		System.out.println(simu.getNbIndividus("contamine"));
		System.out.println(simu.getNbIndividus("retire"));
	}

}
