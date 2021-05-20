import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cs2030.simulator.Customer;
import cs2030.simulator.Simulator;

public class Main3 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int numOfServers = sc.nextInt();
		int maxQueue = sc.nextInt();

		List<Customer> listOfCustomers = new ArrayList<>();

		int id = 1;
		double arrivalTime;
		double serviceTime;
		boolean greedy;

		while (sc.hasNextDouble()) {
			arrivalTime = sc.nextDouble();
			serviceTime = sc.nextDouble();
			greedy = sc.nextBoolean();

			listOfCustomers.add(new Customer(id++, arrivalTime, serviceTime, greedy, null));

		}

		Simulator simulator = new Simulator(numOfServers, listOfCustomers.size(), maxQueue);
		simulator.simulate(listOfCustomers);

		sc.close();

	}

}
