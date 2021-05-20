import java.util.Scanner;

import cs2030.simulator.Simulator;

public class Main5 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int numServer = scanner.nextInt();
		int maxQ = scanner.nextInt();
		int numCustomers = scanner.nextInt();
		int seed = scanner.nextInt();
		double arrRate = scanner.nextDouble();
		double servRate = scanner.nextDouble();
		double restRate = scanner.nextDouble();
		double probResting = scanner.nextDouble();
		double probGreedy = scanner.nextDouble();

		Simulator simulator = new Simulator(numServer, numCustomers, maxQ, null, seed, arrRate, servRate, restRate,
				probResting, probGreedy);
		simulator.simulate(numCustomers);

		scanner.close();

	}

}
