public class Pair<K, V> {
	private final K one;
	private final V two;

	public Pair(K one, V two) {
		this.one = one;
		this.two = two;
	}

	public static <K, V> Pair<K, V> of(K one, V two) {
		return new Pair<>(one, two);
	}

	public K first() {
		return one;
	}

	public V second() {
		return two;
	}

	public String toString() {
		return String.format("(%s, %s)", one, two);
	}
}
