public class RegisteredToken extends Token {
    
    private final Registry registry;
    //Using inheritance instead of composition so that I change lesser lines. I need to pass :(
    public RegisteredToken(Token token, Registry registry) {
        super(token);
        this.registry = registry;
    }

    public void contact() {
        registry.store("Test contact");   
    }
}
