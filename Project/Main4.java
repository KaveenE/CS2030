import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import cs2030.simulator.Customer;
import cs2030.simulator.Simulator;

public class Main4 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int numOfServers = sc.nextInt();
		int maxQueue = sc.nextInt();
		int numCustomers = sc.nextInt();

		List<Customer> listOfCustomers = new ArrayList<>();

		int id = 1;
		double arrivalTime;
		double serviceTime;

		for (int i = 1; i <= numCustomers; i++) {
			arrivalTime = sc.nextDouble();
			serviceTime = sc.nextDouble();

			listOfCustomers.add(new Customer(id++, arrivalTime, serviceTime));

		}

		Queue<Double> restDurations = new ArrayDeque<>();

		for (int i = 1; i <= numCustomers; i++) {
			restDurations.add(sc.nextDouble());

		}

		Simulator simulator = new Simulator(numOfServers, numCustomers, maxQueue, restDurations, -1, -1, -1, -1, -1,
				-1);
		simulator.simulate(listOfCustomers);

		sc.close();

	}

}
