public class SafeEntry extends Token {
	public SafeEntry(int otherTokenID, int time) {
		super(otherTokenID, time);
	}

	@Override
	public String toString() {
		String[] splitInput = super.toString().split(" ");
		
		splitInput[0] = "SafeEntry";
		splitInput[1] = "";
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i <= splitInput.length - 1; i++)
			if (!splitInput[i].isBlank())
				sb.append(splitInput[i] + " ");
		
		return sb.toString().strip();
	}
}
