public class TakeACab implements ChargeAble {
    
    private static final int PRICE_PER_DISTANCE = 33;
    private static final int BOOKING_FEE = 200;
    @Override
    public int computeFare(Request someRequest) {
        int fare = 0;
        
        fare += PRICE_PER_DISTANCE * someRequest.getDistance() + BOOKING_FEE;

        return fare;
    
    }

    @Override
    public String toString() {
        return "TakeACab";
    }

}
