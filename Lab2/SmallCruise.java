public class SmallCruise extends Cruise {
    
    private static final int LOADERS_FOR_SMALLCRUISE = 1;
    private static final int SERVICE_TIME_SMALLCRUISE = 30;
    

    SmallCruise(String identifier, int arrivalTime) {

        super(identifier, arrivalTime, LOADERS_FOR_SMALLCRUISE, SERVICE_TIME_SMALLCRUISE);
    
    }

}
