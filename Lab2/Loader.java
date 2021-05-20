public class Loader {

    private final int identifier;
    private final Cruise currentCruise;

    Loader(int identifierP, Cruise cruise) {
        this.identifier = identifierP;
        this.currentCruise = cruise.copy();

    }

    public boolean canServe(Cruise cruise) {
    
        return cruise.getArrivalTime() >= this.getNextAvailableTime();
    }

    Loader serve(Cruise cruise) {
        
        if (this.canServe(cruise)) {
            
            return new Loader(identifier, cruise);
        }

        return this;
    }

    public int getIdentifier() {
        return identifier;
    }

    public int getNextAvailableTime() {

        return currentCruise.getServiceCompletionTime();
    }

    @Override
    public String toString() {
        return String.format("Loader %d serving %s", identifier, currentCruise);
    }
}
