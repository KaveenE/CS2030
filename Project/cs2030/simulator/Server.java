package cs2030.simulator;

import java.util.LinkedList;

public class Server implements Comparable<Server> {

	private final int maxQueueSize;
	private final int id;
	private final LinkedList<Customer> queue;
	private final double resting_duration;
	private final double finishRest;

	public Server(int id) {
		this(id, 1, new LinkedList<>(), 0, 0);
	}

	public Server(int id, int maxQueueSize) {
		this(id, maxQueueSize, new LinkedList<>(), 0, 0);
	}

	// Copy constructor
	private Server(int id, int maxQueueSize, LinkedList<Customer> dq, double rest, double finishRest) {
		this.maxQueueSize = maxQueueSize;
		this.id = id;
		this.resting_duration = rest;
		this.finishRest = finishRest;

		this.queue = new LinkedList<>(dq);
	}

	// Mutator
	public void enqueue(Customer newCustomer) {
		queue.addLast(newCustomer);
	}

	public void dequeue() {
		queue.removeFirst();
	}

	public void updateCustomer_waitingStatus_inQueue(Customer newCustomer) {
		for (Customer c : queue) {

			if (c.getId() == newCustomer.getId()) {
				queue.set(queue.indexOf(c), newCustomer);
				break;
			}
		}
	}

	public void updateCustomer_departureTime_inQueue(Customer oldCustomer, double new_depatureTime) {
		for (Customer c : queue) {

			if (c.getId() == oldCustomer.getId()) {
				queue.set(queue.indexOf(c), oldCustomer.setDepartureTime(new_depatureTime));
				break;
			}
		}
	}

	public Server setRestDurationAndTime(double restTime, double restFinish) {
		return new Server(id, maxQueueSize, queue, restTime, restFinish);
	}

	// Accessor
	public boolean noCustomer() {
		return queue.isEmpty();
	}

	public boolean canAccomodate() {
		return numWaitingCustomers() < maxQueueSize;
	}

	private int numWaitingCustomers() {

		int numWaiting = 0;
		for (Customer c : queue) {

			if (c.isWaiting()) {
				numWaiting++;
			}
		}

		return numWaiting;
	}

	public double nextAvailableTime() {
		return queue.getLast().getDepatureTime();
	}

	public double lastCustomer_ServiceTime() {
		return queue.getLast().getServiceTime();
	}

	public double get2ndLast_CustomerDepatureTim() {

		return queue.get(queue.size() - 2).getDepatureTime();
	}

	public Customer getNextCustomer() {
		return queue.getFirst();
	}

	public int size() {
		return queue.size();
	}

	public int getID() {
		return id;
	}

	public double getRestDuration() {
		return resting_duration;
	}

	public boolean isResting() {
		return Double.compare(resting_duration, 0) != 0;
	}

	public double getRestFinish() {
		return finishRest;
	}

	@Override
	public int compareTo(Server o) {

		int myWaitingCustomers = this.numWaitingCustomers();
		int otherWaitingCustomers = o.numWaitingCustomers();

		if (myWaitingCustomers != otherWaitingCustomers) {
			return myWaitingCustomers - otherWaitingCustomers;
		} else {
			return this.getID() - o.getID();
		}
	}

}
