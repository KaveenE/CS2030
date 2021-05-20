package lab6;
import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.stream.DoubleStream;
import java.util.stream.Collectors;
import java.util.function.IntPredicate;
import java.util.Optional;
import java.util.List;

public class Main {

	private static boolean isPrime(int num) {
		return IntStream.rangeClosed(2, num - 1)
						.noneMatch(factor -> num % factor == 0);
	}

	static int[] twinPrimes(int n) {

		return IntStream.iterate(3, x -> x <= (n - 2), x -> x + 2)
						.filter((x -> isPrime(x) && isPrime(x + 2)))
						.flatMap(x -> IntStream.of(x, x + 2)).distinct().toArray();

	}

	static int gcd(int m, int n) {

		int max = Math.max(m, n);
		int min = Math.min(m, n);

		if (max % min == 0) {
			return min;
		}

		Pair<Integer> result = Stream
				.iterate(new Pair<>(max, min),
						pair -> new Pair<>(pair.getSecond(), pair.getFirst() % pair.getSecond()))
				.filter(pair -> pair.getSecond() == 0).findFirst().orElse(new Pair<Integer>(1, 1));

		return result.getFirst();

	}

	static long countRepeats(int... array) {
		/*
		 * Logic: -Take consecutive numbers as 1 set -Count number of sets == this is
		 * your answer -To determine something is a set, number before 'x' should be
		 * same. After 'x' be different
		 */

		return IntStream.rangeClosed(1, array.length - 1)
				.filter(x -> x == array.length - 1 ? array[x] == array[x - 1]
				: array[x] == array[x - 1] && array[x] != array[x + 1])
				.count();

	}

	static double normalizedMean(Stream<Integer> stream) {

		/*
		 * double max = stream.max(Comparator.naturalOrder()).orElse(1); double min =
		 * stream.min(Comparator.naturalOrder()).orElse(0);
		 * 
		 * return stream.map(x -> (x - min) / (max - min)) .reduce(0, (x,y) -> x + y)
		 * .orElse(0);
		 */

		List<Double> dataSource = stream.map(x -> (double)x).collect(Collectors.toList());

		double max = dataSource.stream().reduce((x, y) -> Math.max(x, y)).orElse(1.0);
		double min = dataSource.stream().reduce((x, y) -> Math.min(x, y)).orElse(0.0);

		if (dataSource.size() <= 1 || Double.compare(max, min) == 0) {
			return 0.0;
		}

		return dataSource.stream().map(x -> (x - min) / (max - min)).reduce(0.0, (x, y) -> x + y) / dataSource.size();

	}

}
            
class Pair<V> {

    private final V element1;
    private final V element2;

    public Pair(V one, V two) {
        element1 = one;
        element2 = two;
    }

    public V getFirst() {
        return element1;
    }

    public V getSecond() {
        return element2;
    }

}