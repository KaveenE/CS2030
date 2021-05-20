import java.time.Instant;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

/**
 * This program finds different ways one can travel by bus (with a bit of
 * walking) from one bus stop to another.
 *
 * @author: Ooi Wei Tsang
 * @version: CS2030 AY19/20 Semester 1, Lab 10
 */
public class BusMain {
	/**
	 * The program read a sequence of (id, search string) from standard input.
	 * 
	 * @param args Command line arguments
	 */

	public static void main(String[] args) {
		List<CompletableFuture<String>> list = new ArrayList<>();
		Instant start = Instant.now();
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			BusStop srcId = new BusStop(sc.next());
			String searchString = sc.next();
			list.add(BusSg.findBusServicesBetween(srcId, searchString).thenCompose(x -> x.description()));
		}
		sc.close();

		CompletableFuture.allOf(list.toArray(new CompletableFuture<?>[0])).join();

		list.stream().map(x -> x.join()).forEach(System.out::println);

		Instant stop = Instant.now();
		System.out.printf("Took %,dms\n", Duration.between(start, stop).toMillis());
	}
}
