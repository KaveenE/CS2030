package cs2030.simulator;

public enum State {
	ARRIVE("arrives"), WAIT("waits at"), SERVE("serves by"), DONE("done serving by"), LEAVE("leaves"), SERVER_REST(""),
	SERVER_BACK(""), END("DUMMY");

	private final String STRINGVERSION;

	private State(String strVersion) {
		this.STRINGVERSION = strVersion;
	}

	@Override
	public String toString() {
		return STRINGVERSION;
	}
}
