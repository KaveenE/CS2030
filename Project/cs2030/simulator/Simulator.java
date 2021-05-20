package cs2030.simulator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class Simulator {

	private final int numServers;
	private final int maxQueueSize;
	private final Queue<Double> restDurations;

	private final RandomGenerator generator;
	private final double probResting;
	private final double probGreedy;

	public Simulator(int numServers, int numCustomers) {
		this(numServers, numCustomers, 1, null, -1, -1, -1, -1, -1, -1);

	}

	public Simulator(int numServers, int numCustomers, int maxQueueSize) {
		this(numServers, numCustomers, maxQueueSize, null, -1, -1, -1, -1, -1, -1);
	}

	public Simulator(int numServers, int numCustomers, int maxQueueSize, Queue<Double> restDurations, int seed,
			double arrRate, double servRate, double restRate, double probResting, double probGreedy) {
		this.numServers = numServers;
		this.maxQueueSize = maxQueueSize;

		if (Objects.isNull(restDurations)) {

			restDurations = new ArrayDeque<>();
			for (int i = 1; i <= numCustomers; i++) {
				restDurations.add(0.0);
			}
		}

		this.restDurations = restDurations;

		// Setting up generator-related stuff
		if (probGreedy < 0) {
			generator = null;
		} else {
			generator = new RandomGenerator(seed, arrRate, servRate, restRate);
		}

		this.probResting = probResting;
		this.probGreedy = probGreedy;
	}

	public void simulate(List<Customer> listOfCustomers) {

		// We'll initialize servers and ArriveEvents(since they're the first events by
		// default)
		List<Server> servers = new ArrayList<>(numServers);
		List<Event> events = new ArrayList<>(listOfCustomers.size());

		initializeServers(servers);
		initializeEvents(events, listOfCustomers, servers);

		// Variables for the statistics
		double totalWaitingTime = 0;
		int totalServed = 0;
		int totalLeft = 0;

		// Event queue
		PriorityQueue<Event> pq = new PriorityQueue<>(events);

		// Aux variables for the event-handling logic
		Event currEvent;
		Event updatedEvent;

		while (!pq.isEmpty()) {

			currEvent = pq.poll();

			if (printableState(currEvent))
				System.out.println(currEvent);

			if (currEvent.getState() == State.ARRIVE) {
				updatedEvent = executeArriveEvent(currEvent);
			} else if (currEvent.getState() == State.WAIT) {
				// WaitEvent just for printing. Our wait->serve is simulated by restback->serve
				continue;
			} else if (currEvent.getState() == State.SERVE) {
				updatedEvent = executeServeEvent(currEvent);
			} else if (currEvent.getState() == State.LEAVE) {
				updatedEvent = executeLeaveEvent(currEvent);
			} else if (currEvent.getState() == State.DONE) {
				updatedEvent = executeDoneEvent(currEvent);
			} else if (currEvent.getState() == State.SERVER_REST) {
				updatedEvent = executeRestEvent(currEvent);
			} else {
				updatedEvent = executeBackEvent(currEvent);
			}

			if (updatedEvent.getState() != State.END) {

				if (updatedEvent.getState() == State.SERVE) {
					totalWaitingTime += updatedEvent.getTimeEventOccurred()
							- updatedEvent.getCustomer().getArrivalTime();
					totalServed++;
				} else if (updatedEvent.getState() == State.LEAVE) {
					totalLeft++;
				}

				// Always add for any state: serve,leave,wait,done
				pq.add(updatedEvent);
			}

		}

		System.out.printf("[%.3f %d %d]\n", (double) totalWaitingTime / totalServed, totalServed, totalLeft);

	}

	public void simulate(int numCustomers) {

		// We'll initialize servers and ArriveEvents(since they're the first events by
		// default)
		List<Server> servers = new ArrayList<>(numServers);
		List<Event> events = new ArrayList<>(numCustomers);

		List<Customer> listOfCustomers = new ArrayList<>(numCustomers);
		createCustomers_fromGenerator(listOfCustomers, numCustomers);

		initializeServers(servers);
		initializeEvents(events, listOfCustomers, servers);

		// Variables for the statistics
		double totalWaitingTime = 0;
		int totalServed = 0;
		int totalLeft = 0;

		// Event queue
		PriorityQueue<Event> pq = new PriorityQueue<>(events);

		// Aux variables for the event-handling logic
		Event currEvent;
		Event updatedEvent;

		while (!pq.isEmpty()) {

			currEvent = pq.poll();

			if (printableState(currEvent))
				System.out.println(currEvent);

			if (currEvent.getState() == State.ARRIVE) {
				updatedEvent = executeArriveEvent(currEvent);
			} else if (currEvent.getState() == State.WAIT) {
				// WaitEvent just for printing. Our wait->serve is simulated by restback->serve
				continue;
			} else if (currEvent.getState() == State.SERVE) {
				updatedEvent = executeServeEvent(currEvent);
			} else if (currEvent.getState() == State.LEAVE) {
				updatedEvent = executeLeaveEvent(currEvent);
			} else if (currEvent.getState() == State.DONE) {
				updatedEvent = executeDoneEvent(currEvent);
			} else if (currEvent.getState() == State.SERVER_REST) {
				updatedEvent = executeRestEvent(currEvent);
			} else {
				updatedEvent = executeBackEvent(currEvent);
			}

			if (updatedEvent.getState() != State.END) {

				if (updatedEvent.getState() == State.SERVE) {
					totalWaitingTime += updatedEvent.getTimeEventOccurred()
							- updatedEvent.getCustomer().getArrivalTime();
					totalServed++;
				} else if (updatedEvent.getState() == State.LEAVE) {
					totalLeft++;
				}

				// Always add for any state: serve,leave,wait,done
				pq.add(updatedEvent);
			}

		}

		System.out.printf("[%.3f %d %d]\n", totalServed == 0 ? 0 : (double) totalWaitingTime / totalServed, totalServed,
				totalLeft);

	}

	private void initializeServers(List<Server> servers) {

		for (int i = 0; i <= numServers - 1; i++) {
			servers.add(new Server(i + 1, maxQueueSize));
		}
	}

	private void initializeEvents(List<Event> listOfEvents, List<Customer> listOfCustomers, List<Server> servers) {

		for (int i = 0; i <= listOfCustomers.size() - 1; i++) {
			listOfEvents.add(new Event(listOfCustomers.get(i), servers, restDurations, generator, probResting));
		}
	}

	/*** ArriveEvent-related methods ****/
	public Event executeArriveEvent(Event arriveEvent) {
		Server potentialServer = allocateAvailableServer(arriveEvent);

		Customer updatedCustomer;
		Customer currCustomer = arriveEvent.getCustomer();
		Event eventTransitingTo = null;

		// 3 possible transition from ArriveEvent
		// Note to self: if we had more transitions-->Too much if-else-->Possibly we
		// have to circumvent that by using some software design pattern?
		if (Objects.isNull(potentialServer)) {
			updatedCustomer = currCustomer.setDepartureTime(currCustomer.getArrivalTime());

			eventTransitingTo = new Event(updatedCustomer, updatedCustomer.getArrivalTime());
		} else if (potentialServer.noCustomer() && !potentialServer.isResting()) {
			updatedCustomer = currCustomer.setDepartureTime(
					currCustomer.getArrivalTime() + currCustomer.getServiceTime() + potentialServer.getRestDuration());
			potentialServer.enqueue(updatedCustomer);

			// Serve moment customer arrives
			eventTransitingTo = new Event(updatedCustomer, potentialServer.getID(), arriveEvent.getServers(),
					updatedCustomer.getArrivalTime(), State.SERVE, arriveEvent.getRestDurations(), generator,
					probResting);
		} else {
			updatedCustomer = currCustomer.setWaiting(true);
			potentialServer.enqueue(updatedCustomer);

			// Wait moment customer arrives
			eventTransitingTo = new Event(updatedCustomer, potentialServer.getID(), arriveEvent.getServers(),
					updatedCustomer.getArrivalTime(), State.WAIT, arriveEvent.getRestDurations(),
					arriveEvent.getGenerator(), probResting);
		}

		return eventTransitingTo;
	}

	private Server allocateAvailableServer(Event aEvent) {

		List<Server> servers = aEvent.getServers();
		Server currServer;

		// Check for non-resting no-customer server
		PriorityQueue<Server> nonResting_smallestID = new PriorityQueue<>((x, y) -> x.getID() - y.getID());

		nonResting_smallestID
				.addAll(servers.stream().filter(e -> !e.isResting() && e.noCustomer()).collect(Collectors.toList()));
		currServer = nonResting_smallestID.poll();

		if (currServer != null) {
			return currServer;
		}

		// Check for server that can accommodate us regardless of resting or not
		if (aEvent.getCustomer().isGreedy()) {

			PriorityQueue<Server> smallestQueue_canAccomodate_smallestId = new PriorityQueue<>();

			smallestQueue_canAccomodate_smallestId
					.addAll(servers.stream().filter(e -> e.canAccomodate()).collect(Collectors.toList()));
			currServer = smallestQueue_canAccomodate_smallestId.poll();

			if (currServer != null) {
				return currServer;
			}
		} else {

			PriorityQueue<Server> canAccomodate_smallestID = new PriorityQueue<>((x, y) -> x.getID() - y.getID());

			canAccomodate_smallestID
					.addAll(servers.stream().filter(e -> e.canAccomodate()).collect(Collectors.toList()));
			currServer = canAccomodate_smallestID.poll();

			if (currServer != null) {
				return currServer;
			}
		}

		// No server can accommodate, thus customer will leave
		return null;
	}

	/**** WaitEvent-related methods ****/
	private Event executeWaitEvent(Event waitEvent) {

		// Serve at when customer infront departs
		return new Event(waitEvent.getCustomer(), waitEvent.getServerID(), waitEvent.getServers(),
				waitEvent.getServer().get2ndLast_CustomerDepatureTim(), State.SERVE, waitEvent.getRestDurations(),
				waitEvent.getGenerator(), probResting);
	}

	/**** ServeEvent-related methods ****/
	private Event executeServeEvent(Event serveEvent) {

		// ATP, we change server's waiting status to not waiting. Don't ask me why i
		// didn't change in execute of waitEvent because
		// I only did this after debugging
		Customer updateCustomer = serveEvent.getCustomer().setWaiting(false);
		serveEvent.getServer().updateCustomer_waitingStatus_inQueue(updateCustomer);

		// Done serving at departure time that was calculated before hand in ArriveEvent
		// OR ServerBack
		return new Event(updateCustomer, serveEvent.getServerID(), serveEvent.getServers(),
				serveEvent.getCustomer().getDepatureTime(), State.DONE, serveEvent.getRestDurations(),
				serveEvent.getGenerator(), probResting);
	}

	/**** LeaveEvent-related methods ****/
	private Event executeLeaveEvent(Event leaveEvent) {
		return new Event();
	}

	/**** DoneEvent-related methods ****/
	private Event executeDoneEvent(Event doneEvent) {
		// Q: Why dequeue here instead of ServeEvent?
		// A: If I do that, server's queue becomes empty and logic for to enqueue
		// waiting customer doesn't occur
		doneEvent.getServer().dequeue();

		Server currServer = doneEvent.getServer();
		double restDuration = doneEvent.getNextRestDuration();
		Server updatedServer = currServer.setRestDurationAndTime(restDuration,
				doneEvent.getTimeEventOccurred() + restDuration);

		List<Server> serverList = doneEvent.getServers();

		// Update my server list with the server with updated restDuration if any
		serverList.set(serverList.indexOf(currServer), updatedServer);

		return new Event(doneEvent.getCustomer(), doneEvent.getServerID(), serverList, doneEvent.getTimeEventOccurred(),
				State.SERVER_REST, doneEvent.getRestDurations(), doneEvent.getGenerator(), probResting);

	}

	/**** RestEvent-related methods ****/
	private Event executeRestEvent(Event restEvent) {

		return new Event(restEvent.getCustomer(), restEvent.getServerID(), restEvent.getServers(),
				restEvent.getServer().getRestFinish(), State.SERVER_BACK, restEvent.getRestDurations(),
				restEvent.getGenerator(), probResting);
	}

	/**** ServerBackEvent-related methods ****/
	private Event executeBackEvent(Event backEvent) {
		Server currServer = backEvent.getServer();

		// Rest finished
		Server updatedServer = currServer.setRestDurationAndTime(0, 0);
		List<Server> serverList = backEvent.getServers();

		// Update server list
		serverList.set(serverList.indexOf(currServer), updatedServer);

		// Update next customer's depature time since it might have changed due to the
		// rest times
		if (!backEvent.getServer().noCustomer()) {

			updatedServer.updateCustomer_departureTime_inQueue(backEvent.getServer().getNextCustomer(),
					backEvent.getTimeEventOccurred() + backEvent.getServer().getNextCustomer().getServiceTime());

			return new Event(backEvent.getServer().getNextCustomer(), backEvent.getServerID(), backEvent.getServers(),
					backEvent.getTimeEventOccurred(), State.SERVE, backEvent.getRestDurations(),
					backEvent.getGenerator(), probResting);
		} else {
			return new Event();
		}
	}

	private boolean printableState(Event e) {

		return e.getState() == State.ARRIVE || e.getState() == State.WAIT || e.getState() == State.SERVE
				|| e.getState() == State.LEAVE || e.getState() == State.DONE;
	}

	private boolean checkGreedyProb() {

		return Double.compare(generator.genCustomerType(), probGreedy) < 0;
	}

	private void createCustomers_fromGenerator(List<Customer> listOfCustomers, int numCustomers) {
		// Creating my customers w/o service time as we can't determine it prematurely.
		// Look at codecrunch
		double arrivalTime = 0;
		listOfCustomers.add(new Customer(1, arrivalTime, -1, checkGreedyProb(), generator));

		for (int i = 2; i <= numCustomers; i++) {
			arrivalTime += generator.genInterArrivalTime();
			listOfCustomers.add(new Customer(i, arrivalTime, -1, checkGreedyProb(), generator));
		}

	}

	public static void main(String[] a) {
	}
}
