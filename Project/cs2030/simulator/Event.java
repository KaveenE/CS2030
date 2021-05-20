package cs2030.simulator;

import java.util.List;
import java.util.Queue;

public class Event implements Comparable<Event> {

	private final Customer customer;
	private final int serverID;
	private final List<Server> servers;
	private final Queue<Double> restDurations;

	private final RandomGenerator generator;
	private final double probRest;

	// NOTE to self:
	// This state was tricky. Is always determined by some combination of customer's
	// arrival/departure/service time
	private final double timeEventOccurred;

	private final State state;

	public Event(Customer customer, List<Server> servers, Queue<Double> restDurations, RandomGenerator generator,
			double probRest) {
		this(customer, -1, servers, customer.getArrivalTime(), State.ARRIVE, restDurations, generator, probRest);
	}

	public Event() {
		this(null, -1, null, -1, State.END, null, null, -1);
	}

	public Event(Customer customer, double timeEventOccurred) {
		this(customer, -1, null, timeEventOccurred, State.LEAVE, null, null, -1);
	}

	public Event(Customer customer, int serverID, List<Server> servers, double timeEventOccurred, State state,
			Queue<Double> restDurations, RandomGenerator generator, double probRest) {
		this.customer = customer;
		this.serverID = serverID;
		this.servers = servers;
		this.timeEventOccurred = timeEventOccurred;
		this.restDurations = restDurations;

		this.state = state;

		this.generator = generator;
		this.probRest = probRest;
	}

	// Accessors
	public Customer getCustomer() {
		return customer;
	}

	public List<Server> getServers() {
		return servers;
	}

	public double getTimeEventOccurred() {
		return timeEventOccurred;
	}

	public int getServerID() {
		return serverID;
	}

	public Server getServer() {

		for (Server s : servers) {

			if (s.getID() == serverID) {
				return s;
			}
		}

		return null;
	}

	public State getState() {
		return state;
	}

	public Event update() {
		return new Event();
	};

	public Queue<Double> getRestDurations() {
		return restDurations;
	}

	public double getNextRestDuration() {

		if (generator == null) {
			return restDurations.poll();
		} else {

			if (Double.compare(generator.genRandomRest(), probRest) < 0) {
				return generator.genRestPeriod();
			} else {
				return 0;
			}
		}
	}

	public RandomGenerator getGenerator() {
		return generator;
	}

	@Override
	public int compareTo(Event o) {

		if (Double.compare(this.timeEventOccurred, o.timeEventOccurred) != 0) {

			return Double.compare(this.timeEventOccurred, o.timeEventOccurred);
		} else {
			return this.customer.getId() - o.customer.getId();
		}
	}

	@Override
	public String toString() {

		StringBuilder defaultString = new StringBuilder(
				String.format("%.3f %s %s", timeEventOccurred, customer, state));

		if (getState() != State.ARRIVE && getState() != State.LEAVE) {
			defaultString.append(String.format(" server %d", getServer().getID()));
		}

		return defaultString.toString();
	}

}
