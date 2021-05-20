public class Request
{
    private final int distance;
    private final int passengers;
    private final int timeBooked;

    public Request(int distance, int passengers, int timeBooked)
    {
        this.distance = distance;
        this.passengers = passengers;
        this.timeBooked = timeBooked;
    }
    
    public int getDistance() {
        return distance;
    }

    public int getPassengers() {
        return passengers;
    }

    public int timeBooked() {
        return timeBooked;
    }
    @Override
    public String toString() {
        
        return String.format("%dkm for %dpax @ %dhrs", distance, passengers, timeBooked);
    
    }
}
