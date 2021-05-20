import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class Aggregate<S, T> {

	private final static int count = 0;
	private final S seed;
	private final T value;
	private final Function<S, Pair<S, T>> seedToPair;
	private final boolean invalid;

	public Aggregate(S seed) {
		this(seed, null, null);
	}

	public Aggregate(S seed, T value, Function<S, Pair<S, T>> func) {
		this.seed = (S) seed;
		this.value = (T) value;
		this.seedToPair = func;
		invalid = false;
	}

	Aggregate(S seed, T value, Function<S, Pair<S, T>> func, boolean flag) {
		this.seed = seed;
		this.value = value;
		this.seedToPair = func;
		invalid = true;
	}

	public static <S, T> Aggregate<S, T> seed(S seed) {

		return new Aggregate<>(seed);
	}

	static <S, T> Aggregate<S, T> of(Function<S, Pair<S, T>> function) {
		return new Aggregate<>(null, null, function);
	}

	public Aggregate<S, T> map(Function<S, S> function, T value) {
		if (seed == null)
			return new Aggregate<>(null, null, null, true);
		S newSeed = function.apply(seed);

		return new Aggregate<>(newSeed, value, null);
	}

	public Aggregate<S, T> map(Function<S, Pair<S, T>> function) {
		if (seed == null)
			return new Aggregate<>(null, null, null, true);
		Pair<S, T> pair = function.apply(seed);
		return new Aggregate<>(pair.first(), pair.second(), null);
	}

	public <R> Aggregate<S, R> flatMap(Function<T, Aggregate<S, R>> function) {

		if (value == null)
			return new Aggregate<>(null, null, null, true);
		Aggregate<S, R> newAggreg = function.apply(value);
		Pair<S, R> pair = newAggreg.seedToPair.apply(seed);

		// dsa;
		// da;

		return new Aggregate<S, R>(pair.first(), pair.second(), null);

	}

	@Override
	public String toString() {
		if (seedToPair == null && !invalid) {
			if (value == null) {
				return String.format("(%s)", seed);
			} else {
				return String.format("(%s, %s)", seed, value);
			}
		}
		return String.format("%s", invalid ? "Invalid Aggregate" : "Aggregate");
	}

	S getSeed() {
		return seed;
	}

}
