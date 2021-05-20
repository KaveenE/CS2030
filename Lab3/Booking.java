public class Booking implements Comparable<Booking> {

    private final Driver driver;
    private final Request request;
    private final ChargeAble chargeUnder;

    public Booking(Driver driver, Request request) {
        this.driver = driver;
        this.request = request;

        
        if(driver.getService1().computeFare(request) < driver.getService2().computeFare(request)) {
            chargeUnder = driver.getService1();
        }
        else {
            chargeUnder = driver.getService2();
        }
    }

    public int getLeastFare() { 
            return Math.min(driver.getService1().computeFare(request), driver.getService2().computeFare(request));
    }

    private double convertToDollar(int cents) {
        double dollars = (double)cents / 100;

        return dollars;
    }

    @Override
    public String toString() {
        return String.format("$%.2f using %s (%s)", convertToDollar(getLeastFare()), driver.toString(),
                chargeUnder.toString() );
    
    }        


    @Override
    public boolean equals(Object other) {
        
        if( !(other instanceof Booking) ) return false;
        return this.toString().equals(other.toString());
    }

    @Override
    public int compareTo(Booking other) {
        if(this.getLeastFare() != other.getLeastFare() )
            return this.getLeastFare() - other.getLeastFare();
        else
            return this.driver.getWaitingTime() - other.driver.getWaitingTime();
    }
    
    
}
