public class BigCruise extends Cruise {

    private static final int LENGTH_FOR_LOADER = 40;
    private static final int PASSENGER_FOR_MIN = 50;
   
    BigCruise(String identifierP, int arrivalTimeP, int length, int passengers) {

        super(identifierP, arrivalTimeP, computeLoaders(length), computeServiceTime(passengers));
    }
   
    private static int computeServiceTime(double passengers) {
        return (int)Math.ceil(passengers / PASSENGER_FOR_MIN);
    }

    private static int computeLoaders(double length) {
        return (int)Math.ceil(length / LENGTH_FOR_LOADER);
    }

}
