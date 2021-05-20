public class RegistryImpl extends Registry {
	private final Token[] pingedTokens;

	public RegistryImpl(String id) {
		this(id, new Token[] {});
	}

	public RegistryImpl(String id, Token[] tokens) {
		super(id);
		pingedTokens = new Token[tokens.length];
		int i = 0;
		for (Token t : tokens) {
			pingedTokens[i++] = t;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(
				super.toString() + String.format(": %d tokens registered\n", pingedTokens.length));
		for (Token t : pingedTokens) {
			sb.append(t + "\n");
		}
		return sb.toString().strip();
	}

	public void alert(int time) {
		StringBuilder sb = new StringBuilder();
		int[] pinged;
		boolean appended;
		for (Token t : pingedTokens) {
			appended = false;
			pinged = t.getTimePingedOtherTokens();
			String tString = t.toString();
			if (tString.charAt(0) == 'S' && tString.contains("@" + time))
				tString = tString.replaceFirst(":", "");
			sb.append(tString.contains(":") ? tString.split(":")[0] + ": " : tString.split(" ")[0] + " ");
			for (int i = 0; i <= pinged.length - 1; i++) {
				if (pinged[i] == time) {
					sb.append(String.format("#%d@%d ", i, time));
					appended = true;
				}
			}
			if (!appended && sb.charAt(0) == 'S') {
				sb.append("#" + t.getID() + ": none");
				super.store(sb.toString());
			} else if (!appended) {
				sb.append("none");
				super.store(sb.toString());
			} else
				super.store(sb.toString().strip());
			sb.setLength(0);
		}
	}

	@Override
	public Registry add(Token newToken) {
		Token[] dupTokens = new Token[pingedTokens.length + 1];
		int i = 0;
		for (Token t : pingedTokens) {
			dupTokens[i++] = t;
		}
		dupTokens[i++] = newToken;
		return new RegistryImpl(super.getID(), dupTokens);
	}
}
