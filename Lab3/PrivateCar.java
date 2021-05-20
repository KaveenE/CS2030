public class PrivateCar extends Driver {

    public PrivateCar(String identifier, int waiting_time) {
        super(new JustRide(), new ShareARide(), identifier, waiting_time);
    
    }
    
    @Override
    public String toString() {
        return super.toString() + " PrivateCar";
    }
}
