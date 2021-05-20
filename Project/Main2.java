import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cs2030.simulator.Customer;
import cs2030.simulator.Simulator;

public class Main2 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int numOfServers = sc.nextInt();
		int maxQueue = sc.nextInt();

		List<Customer> listOfCustomers = new ArrayList<>();

		int id = 1;
		while (sc.hasNextDouble()) {
			listOfCustomers.add(new Customer(id++, sc.nextDouble(), sc.nextDouble()));
		}

		Simulator simulator = new Simulator(numOfServers, listOfCustomers.size(), maxQueue);
		simulator.simulate(listOfCustomers);

		sc.close();
	}
}
