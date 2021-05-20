public class Count<T> extends Aggregate<Integer, T> {

	public Count(T value) {
		super(1, value, null);
	}

	Count(Integer count, T value) {
		super(count, value, null);
	}

	static <T> Count<T> of(T seed) {
		return new Count<>(seed);
	}

	public Count<T> map(T value) {
		return new Count<>(super.getSeed() + 1, value);
	}

}
