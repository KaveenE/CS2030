public abstract class Driver{
    
    private final ChargeAble service1;
    private final ChargeAble service2;
    private final String identifier;
    private final int waiting_time;

    public Driver(ChargeAble service1, ChargeAble service2,String identifier,int waiting_time) {
        
        this.service1 = service1;
        this.service2 = service2;
        this.identifier = identifier;
        this.waiting_time = waiting_time;
    
    }
        
    public ChargeAble getService1() {
    
        return service1;
    }

    public ChargeAble getService2() {
    
        return service2;
    }
 
    public int getWaitingTime() {
    
        return waiting_time;
    }
    
    @Override
    public String toString() {
        return String.format("%s (%d mins away)", identifier, waiting_time);
    }

}
