public abstract class Registry {
	private final String identifier;
	private final DataStore db;

	public Registry(String identifier) {
		this.identifier = identifier;
		db = new DataStore();
	}

	public void store(String msg) {
		db.write(msg);
	}

	@Override
	public String toString() {
		return String.format("[%s]", identifier);
	}

	public abstract Registry add(Token token);

	public String getID() {
		return identifier;
	}

	public void alert(int time) {
	}
}
