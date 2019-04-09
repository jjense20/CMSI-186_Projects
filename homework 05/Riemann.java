public class Riemann {

  private static final String PERCENT_SIGN = "%";
  private static double[] coefArray;
  private static double previousTotal;
  private static double currentTotal;
  private static double riemannSlice;
  private static double totalRecs;
  private static double lowerBound;
  private static double upperBound;
  private static double percentInput;
  private static int coefArgCount;
  private static int totalIterations = 0;

  /**
   * Method to create a polynomial function
   */
   public static double polyFunction() {
     try {
       if (args.length < 4) {
         System.out.println("You gotta have more than 3 values man..\n");
         System.exit(0);
       }
       if (arg[args.length - 1].substring(args[(args.length - 1)].length - 1).contains("%")) {
         percentInput = Double.parseDouble(args[(args.length - 1)].substring(0, (args[(args.length - 1)].length() - 1)));
         lowerBound = Double.parseDouble(args[(args.length - 3)]);
         upperBound = Double.parseDouble(args[(args.length - 2)]);
         coefArgCount = args.length - 4;
         if (percentInput <= 0 || percentInput >= 100) {
           System.out.println("You gotta input a valid percentage value within the 0-100 range..\n");
           System.exit(0);
         }
       } else {
         percentInput = 1.0;
         lowerBound = Double.parseDouble(args[(args.length - 2)]);
         upperBound = Double.parseDouble(args[(args.length - 1)]);
         coefArgCount = args.length - 3;
       }
       coefArray = new double[coefArgCount];
       for (int i = 1; i <= coefArgCount; i++) {
         coefArray[i - 1] = Double.parseDouble(args[i]);
       }
       if (upperBound <= lowerBound) {
         System.out.println("Upper bound's gotta be higher than the lower bound man..\n");
         System.exit(0);
       }
     } catch(NumberFormatException nfe) {
       System.out.println("You gotta input a valid value man..\n");
       System.exit(0);
     }
   }

   /**
    * Method to intergrate a given polynomial functions
    */
   public static double riemann() {
     totalIterations = 0;
     previousTotal = 0.0;
     currentTotal = 0.0;
     riemannSlice = (upperBound - lowerBound);
     while ((Math.abs(currentTotal - previousTotal) / Math.abs(previousTotal) * 100) > percentInput || totalIterations <= 2) {
       previousTotal = currentTotal;
       currentTotal = 0.0;
       for (double i = lowerBound + riemannSlice; i <=upperBound; i+= riemannSlice) {
         for (int j = 0; j <=coefArgCount; j++) {
           currentTotal += (coefArray[j] * Math.pow(i, j) * riemannSlice);
         }
       }
       riemannSlice /= 2;
       totalIterations ++;
     }
     totalRecs = Math.pow(2, (totalIterations - 1));
     return currentTotal;
   }

   /**
    * Method to integrate a given sin function
    */
   public static double sinRiemann() {
     totalIterations = 0;
     previousTotal = 0.0;
     currentTotal = 0.0;
     riemannSlice = (upperBound - lowerBound);
     while ((Math.abs(currentTotal - previousTotal) / Math.abs(previousTotal) * 100) > percentInput || totalIterations <= 2) {
       previousTotal = currentTotal;
       currentTotal = 0.0;
       for (double i = lowerBound + riemannSlice; i <=upperBound; i+= riemannSlice) {
         currentTotal += Math.sin(i) * riemannSlice;
       }
       riemannSlice /= 2;
       totalIterations ++;
    }
    totalRecs = Math.pow(2, (totalIterations - 1));
    return currentTotal;
  }


   /**
    * Method to show the percentage change between two values
    */
   public double percentDifference(double currentTotal - double previousTotal) {
     double differences = Math.abs(currentTotal - previousTotal) / previousTotal * 100;
   }

   /**
    * Method to print to return a string representation of the Riemann sums
    */
   public String toString() {
     String result = "";
     result = "The type of the function is " + this.function + "\n";
   }

   public static void validateArgs(args[]) {

   }

   /**
    * The main program starts here
    */
   public static void main(String[] args) {
     if (arg[0] == "poly");
     polyFunction();
     return riemann;
     if (arg[0] == "sin");
   }

}
