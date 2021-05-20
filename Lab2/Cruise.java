public class Cruise {

    private final String identifier;
    private final int arrivalTime;
    private final int numOfLoader;
    private final int serviceTime;

    private static final int MIN_IN_HR = 60;
    private static final int BASE = 10;

    Cruise(String identifierP, int arrivalTimeP, int numLoaderP, int serviceTimeP) {
        this.identifier = identifierP;
        this.arrivalTime = arrivalTimeP;
        this.numOfLoader = numLoaderP;
        this.serviceTime = serviceTimeP;
    }
    
    //Implementing a copy constructor for ease of defensive copy. 
    //This may seem unnecessary as Cruise has no setters but just a defenisve things may change up
    //Also will be wrapped in a copy method to prevent casting issues 
    private Cruise(Cruise cruise) {
        this(cruise.identifier, cruise.arrivalTime, cruise.numOfLoader, cruise.serviceTime);
    }

    public Cruise copy() {
        return new Cruise(this);
    }

    //Relevant getters
    int getArrivalTime() {

        String digitsForminutes = getLastXDigits(arrivalTime, 2);
        int numDigitsForHours = getNumDigits(arrivalTime) - digitsForminutes.length();
        int digitsForHours = getFirstXDigits(arrivalTime,numDigitsForHours);

        return digitsForHours * MIN_IN_HR + Integer.parseInt(digitsForminutes);
    }


    public int getServiceCompletionTime() {
        return getArrivalTime() + serviceTime;
    }

    public int getNumOfLoadersRequired() {
        return numOfLoader;
    }

    //All my helpers meant helping to convert time into minutes
    private int getNumDigits(int num) {

        return num == 0 ? 1 : (int)(Math.log10(num) + 1);
    }

    private String getLastXDigits(int num, int x) {

        String numString = num + ""; 
        StringBuilder stringForDigit = new StringBuilder();

        int digitsAdded = 0;
        for (int i = numString.length() - 1;i >= 0; i--) {

            if (digitsAdded == x) {
                break;
            }

            stringForDigit.append(numString.charAt(i));
            digitsAdded++;
        }

        return stringForDigit.reverse().toString();
    }

    private int getFirstXDigits(int num, int x) {

        return num / (int)Math.pow(BASE, getNumDigits(num) - x);
    }

    @Override
    public String toString() {
        return String.format("%s@%04d", identifier, arrivalTime);
    }
}
