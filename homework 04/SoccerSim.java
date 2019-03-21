import java.text.DecimalFormat;

public class SoccerSim {

  /**
   * Method to test the vailidity of the number of arguments
   */

   public static void validateArgs ( String args[] ) {
     if ( ( ( args.length < 4 ) || ( ( args.length % 4 ) == 3 ) || ( args.length % 4 ) == 2 ) ) {
       System.out.println ( "\n You input the wrong number of arguements man.. better luck next time \n" );
       System.exit ( 1 );
     }
   }

   /**
    * Method to add balls to the x and y axis
    * Method to verify the time timeSlice
    */

    public static Ball[] createBalls ( String args[] ) {
      int lengthOfArray = args.length / 4;
      Ball[] result = null;
      double timeSlice = 1.0;

      if ( 1 == ( args.length % 4 ) ) {
        try {
          // if (( Clock.validateTimeSliceArg( args[args.length - 1] ) <= 0 ) || ( 1800 <= Integer.parseInt( args[args.length - 1] ) ) ){
          //   throw new IllegalArgumentException ();
          // }
          if (( Clock.validateTimeSliceArg( args[args.length - 1] ) <= 0 )){
            throw new IllegalArgumentException ();
          } else if ( Clock.validateTimeSliceArg( args[args.length - 1] ) >= 1800){
            throw new IllegalArgumentException ();
          }
        } catch ( Exception e ) {
          System.out.println ( "\n You input an invalid value for time slice, try something between 0 and 1800.. better luck next time \n" );
          System.exit ( 1 );
        }
        timeSlice = Double.parseDouble ( args[args.length - 1] );
        lengthOfArray = ( args.length - 1 ) / 4;
      }

      result = new Ball[lengthOfArray];
      int j = 0;
      for ( int i = 0; j < ( lengthOfArray * 4 ); i++ ) {
        try {
          result[i] = new Ball ( Double.parseDouble ( args[j] ), Double.parseDouble ( args[j + 1] ), Double.parseDouble ( args[j + 2] ), Double.parseDouble ( args[j + 3] ), timeSlice);
          j += 4;
        } catch ( Exception e ) {
          System.out.println ( "\n There was a problem creating a new ball.. better luck next time\n" );
          System.exit ( 1 );
        }
      }
      return result;
    }

    /**
     * Method to determine if all balls are out of bounds
     */
     public static boolean allOutOfBounds (Ball[] allBalls) {
       for (int i = 0; i < allBalls.length; i++){
         if (!allBalls[i].isOutOfBounds()){
           return false;
         }
       }
       return true;
     }

     /**
      * This is where the main program starts
      */
    public static void main ( String args[] ) {
      double timeSlice = 1.0;
      boolean checkMovement = true;
      Ball[] allBalls = null;
      DecimalFormat template = new DecimalFormat("##0.00");

      SoccerSim.validateArgs ( args );
      allBalls = SoccerSim.createBalls ( args );

      if ( ( args.length % 4 ) == 1 ) {
        //throw new NumberFormatException ( "Input a valid number for the time slice argument");
        timeSlice = Double.parseDouble ( args[args.length - 1] );
      }
      Clock clock = new Clock ( timeSlice );

      Ball makePole = new Ball ( ( Math.random () * 360 ) - 179, ( Math.random () * 240) - 119, 0, 0, timeSlice);

      System.out.println ( "\n" + " THE FIELD SIZE IS 360 by 240 " + "\n" + " Testing for collision with a " + template.format ( timeSlice ) + " second time slice \n" + " There is a pole at " + makePole.toString () );

      while ( checkMovement ) {

        System.out.println ( "WHEN " + clock.toString () );
        for ( int i = 0; i < allBalls.length; i++ ) {
          System.out.println ( " Ball " + ( i + 1 ) + ": " + allBalls[i].toString () );
          if ( allBalls[i].collision ( makePole ) ) {
            System.out.println ( "\n Ball " + ( i + 1 ) + " has had a collision with the pole" );
            System.exit ( 0 );
          }
        }

        for ( int i = 0; i < allBalls.length; i++) {
          for ( int j = i + 1; j < allBalls.length; j++) {
            if ( allBalls[i].collision ( allBalls[j] ) && ( 159 > Math.abs ( allBalls[i].locationX () ) ) && ( 129 > Math.abs ( allBalls[i].locationY () ) ) ) {
              System.out.println ( "\n Ball " + ( i + 1 ) + " has had a collision with ball " + ( j + 1 ) );
              System.exit ( 0 );
            }
          }
        }

        int counter  = 0;
        for ( int i = 0; i < allBalls.length; i++ ) {
          if ( allBalls[i].noMovement () ) {
            counter++;
          }
        }
        if ( counter == allBalls.length ) {
          break;
        }

        if (allOutOfBounds(allBalls)){
          break;
        }

        for ( int i = 0; i < allBalls.length; i++ ) {
          allBalls[i].move ();
        }
        clock.tick ();
      }

      System.out.println ( "\n THE PROGRAM HAS ENDED... TRY SOME DIFFERENT NUMBERS" );
    }
  }
