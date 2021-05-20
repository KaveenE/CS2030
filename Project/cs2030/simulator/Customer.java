package cs2030.simulator;

public class Customer {

	private static final double STANDARD_SVC_TIME = 1;
	private final int id;
	private final double arrivalTime;
	private final double serviceTime;
	private final double departureTime;

	private final boolean greedy;
	private final boolean waiting;

	private final RandomGenerator generator;

	public Customer(int id, double arrivalTime) {
		this(id, arrivalTime, STANDARD_SVC_TIME, -1, false, false, null);
	}

	public Customer(int id, double arrivalTime, double serviceTime) {
		this(id, arrivalTime, serviceTime, -1, false, false, null);
	}

	public Customer(int id, double arrivalTime, double serviceTime, boolean greedy, RandomGenerator generator) {
		this(id, arrivalTime, serviceTime, -1, greedy, false, generator);
	}

	// Our inputs do NOT pass departure time. We'll derive it eventually.
	private Customer(int id, double arrivalTime, double serviceTime, double departureTime, boolean greedy,
			boolean waiting, RandomGenerator generator) {
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
		this.departureTime = departureTime;

		this.greedy = greedy;
		this.waiting = waiting;

		this.generator = generator;
	}

	// Accessors
	public int getId() {
		return id;
	}

	public double getArrivalTime() {
		return arrivalTime;
	}

	public double getDepatureTime() {
		return departureTime;
	}

	public double getServiceTime() {
		if (generator != null) {
			return generator.genServiceTime();
		} else {
			return serviceTime;
		}
	}

	public boolean isGreedy() {
		return greedy;
	}

	public boolean isWaiting() {
		return waiting;
	}

	// Mutator
	public Customer setDepartureTime(double newDepartureTime) {
		return new Customer(id, arrivalTime, serviceTime, newDepartureTime, greedy, waiting, generator);
	}

	public Customer setWaiting(boolean isWaiting) {
		return new Customer(id, arrivalTime, serviceTime, departureTime, greedy, isWaiting, generator);
	}

	public String toString() {
		return String.format("%d%s", id, greedy ? "(greedy)" : "");
	}
}
