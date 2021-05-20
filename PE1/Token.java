public class Token {

	private final int identifier;
	private final int timePingedByOther;
	private final int[] timePingedOtherTokens = new int[200];

	public Token(int id) {
		this(id, 0);
	}

	public Token(int tokenID, int timePinged) {

		identifier = timePingedByOther = tokenID;
		timePingedOtherTokens[tokenID] = timePinged;

	}

	public Token(Token another) {

		this.identifier = another.identifier;
		this.timePingedByOther = another.timePingedByOther;

		for (int i = 0; i <= another.timePingedOtherTokens.length - 1; i++) {

			if (another.timePingedOtherTokens[i] != 0)
				this.timePingedOtherTokens[i] = another.timePingedOtherTokens[i];
		}
	}

	public Token copy() {
		return new Token(this);
	}

	public int getID() {
		return identifier;
	}

	public int[] getTimePingedOtherTokens() {
		return timePingedOtherTokens;
	}

	public Token ping(Token tokenPinged, int timePinged) {

		Token dupToken = this.copy();

		dupToken.timePingedOtherTokens[tokenPinged.getID()] = timePinged;

		return dupToken;

	}

	public String returnStringForPingedTokens() {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i <= timePingedOtherTokens.length - 1; i++) {

			if (timePingedOtherTokens[i] != 0)
				sb.append("#" + i + "@" + timePingedOtherTokens[i] + " ");
		}

		return sb.toString();

	}

	@Override
	public String toString() {

		if (returnStringForPingedTokens().length() == 0)
			return String.format("Token #%d: none", identifier);
		else {
			return String.format("Token #%d: %s", identifier,
					returnStringForPingedTokens().substring(0, returnStringForPingedTokens().length() - 1));

		}

	}

}
