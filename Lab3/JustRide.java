public class JustRide implements ChargeAble {
    
    private static final int PRICE_PER_DISTANCE = 22;

    @Override
    public int computeFare(Request someRequest) {
        int fare = 0;

        if(someRequest.timeBooked() <= 900 && someRequest.timeBooked() >= 600) {
            fare += 500;
        }
        
        fare += PRICE_PER_DISTANCE * someRequest.getDistance();

        return fare;
    
    }

    @Override
    public String toString() {
        return String.format("JustRide");
    }
}
