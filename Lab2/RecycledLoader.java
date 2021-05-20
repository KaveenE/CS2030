public class RecycledLoader extends Loader {

    private static final int RESTTIME_AFTER_SERVICE = 60;

    RecycledLoader(int identifierP, Cruise cruise) {
        super(identifierP, cruise);
    }

    @Override
    public boolean canServe(Cruise cruise) {

        return cruise.getArrivalTime() >= super.getNextAvailableTime() + RESTTIME_AFTER_SERVICE;
    }

    @Override
    public String toString() {
        return "Recycled " + super.toString();
    }

    @Override
    Loader serve(Cruise cruise) {
        
        if (this.canServe(cruise)) {
            
            return new RecycledLoader(super.getIdentifier(), cruise);
        }

        return this;
    }

}
