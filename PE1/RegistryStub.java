public class RegistryStub extends Registry {
	public RegistryStub() {
		this("");
	};

	public RegistryStub(String identifier) {
		super(identifier);
	}

	@Override
	public Registry add(Token t) {
		return null;
	}

}
