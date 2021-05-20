public class NormalCab extends Driver {

    public NormalCab(String identifier, int waiting_time) {
        super(new JustRide(), new TakeACab(), identifier, waiting_time);
    
    }
    
    @Override
    public String toString() {
        return super.toString() + " NormalCab";
    }

}
