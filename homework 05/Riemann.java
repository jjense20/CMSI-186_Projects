import java.util.Arrays;
public class Riemann {

 private static String PERCENT_SYMBOL = "%";
 private static boolean isPoly = false;
 private static boolean isSin = false;
 private static int coefNumber;
 private static double coefArray[];
 private static double previousTotal;
 private static double fullArea;
 private static double rectangleNum;
 private static double lowerBound;
 private static double upperBound;
 private static double percentage;


 public Riemann() {}

 /**
  * Method to validate the arguments
  */
 public void validateArgs( String args[] ) {
 if ( args[0].equals( "riemanntest" ) && args.length < 4 ){
  String functionType = args[0];
  switch ( functionType ) {
    case "riemanntest":
    riemannTests();
    break;
    default:
    break;
  }
} else {
  if ( args.length < 4 && args[0].equals( "poly" ) ) {
   System.out.println( "Sorry man.. you gotta have more arguments" );
   System.exit( 0 );
  }
  if ( args.length < 2 && args[0].equals( "sin" ) ) {
    System.out.println( "Sorry man.. you gotta have more arguments" );
    System.exit( 0 );
   }
  String functionType = args[0];
  switch ( functionType ) {
   case "poly":
    System.out.println( "This is a poly" );
    isPoly = true;
    poly( args );
    riemannPoly();
    break;
    case "sin":
    System.out.println( "This is a sin" );
    isSin = true;
    sinSetup( args );
    sinRiemann();
    break;
    default:
    break;
  }
 }
}

 /**
  * Method for creating a polynomial
  */
 public static void poly( String args[] ) {
  if ( isPoly == true ) {
   for ( int p = 0; p < args.length; p++ ) {
    if ( p != 0 ) {
     try {
      Double.parseDouble( args[p] );
     } catch ( NumberFormatException e ) {
      if ( p != args.length - 1 || ( p == args.length - 1 && !args[args.length - 1].substring( args[( args.length - 1 )].length() - 1 ).equals( PERCENT_SYMBOL ) ) ) {
       try {
        Double.parseDouble( args[( args.length - 1 )].substring( 0, ( args[( args.length - 1 )].length() - 1 ) ) );
       } catch ( NumberFormatException d ) {
        throw new IllegalArgumentException( "Sorry man.. this isn't a valid number!" );
       }
      }
     }
    }
   }
   if ( args[args.length - 1].substring( args[( args.length - 1 )].length() - 1 ).equals( PERCENT_SYMBOL ) ) {
    percentage = Double.parseDouble( args[( args.length - 1 )].substring( 0, ( args[( args.length - 1 )].length() - 1 ) ) );
    upperBound = Double.parseDouble( args[( args.length - 2 )] );
    lowerBound = Double.parseDouble( args[( args.length - 3 )] );
    coefNumber = args.length - 4;
   } else {
    percentage = 1.0;
    upperBound = Double.parseDouble( args[( args.length - 1 )] );
    lowerBound = Double.parseDouble( args[( args.length - 2 )] );
    coefNumber = args.length - 3;
   }
   coefArray = new double[coefNumber];
   for ( int i = 1; i <= coefNumber; i++ ) {
    coefArray[i - 1] = Double.parseDouble( args[i] );
   }
   if ( upperBound <= lowerBound ) {
    System.out.println( "Sorry man.. your upperBound has to be higher than your lowerBound!" );
    System.exit( 0 );
   }
  }
 }

/**
  * Method for creating the sin function
  */
 public static void sinSetup( String args[] ) {
  if ( isSin == true ) {
   for ( int p = 0; p < args.length; p++ ) {
    if ( p != 0 ) {
     try {
      Double.parseDouble( args[p] );
     } catch (NumberFormatException e) {
      if ( p != args.length - 1 || ( p == args.length - 1 && !args[args.length - 1].substring( args[( args.length - 1 )].length() - 1 ).equals( PERCENT_SYMBOL ) ) ) {
       try {
        Double.parseDouble( args[( args.length - 1 )].substring( 0, ( args[( args.length - 1 )].length() - 1 ) ) );
       } catch ( NumberFormatException d ) {
        throw new IllegalArgumentException( "Sorry man.. this isn't a valid number!" );
       }
      }
     }
    }
   }
   if ( args[args.length - 1].substring( args[( args.length - 1 )].length() - 1 ).equals( PERCENT_SYMBOL ) ) {
    percentage = Double.parseDouble( args[( args.length - 1 )].substring(0, ( args[( args.length - 1 )].length() - 1 ) ) );
    upperBound = Double.parseDouble( args[( args.length - 2 )] );
    lowerBound = Double.parseDouble( args[( args.length - 3 )] );
    coefNumber = args.length - 4;
   } else {
    percentage = 1.0;
    upperBound = Double.parseDouble( args[( args.length - 1 )] );
    lowerBound = Double.parseDouble( args[( args.length - 2 )] );
    coefNumber = args.length - 3;
   }
   coefArray = new double[coefNumber];
   for ( int i = 1; i <= coefNumber; i++ ) {
    coefArray[i - 1] = Double.parseDouble( args[i] );
   }
   if ( upperBound <= lowerBound ) {
    System.out.println( "Sorry man.. your upperBound has to be higher than your lowerBound!" );
    System.exit(0);
   }
  }
 }

 /**
  * Method for validating the polynomial functions
  */
 public static double validateFunction( double x ) {
  int total = 0;
  for ( int i = 0; i < coefNumber; i++ ) {
   total += coefArray[i] * Math.pow( x, i );
  }
  return total;
 }

 /**
   * Method for validating the sin functions
   */
  public static double validateSin( double x ) {
    double total = 0;
    for ( int i = 0; i < coefNumber; i++ ) {
     total += ( ( coefArray[i] * Math.pow( x, i ) ) );
    }
    return Math.sin( total );
   }

   /**
   * Method for finding the sin midpoints
   */
  public static void sinMidpoint( double lowerBound, double upperBound, double[] midpoint ) {
    double deltax = ( upperBound + lowerBound ) / 2;
    midpoint[0] = deltax;
    midpoint[1] = validateSin(deltax);
   }

 /**
  * Method for showing the percentage between two values
  */
 public static double percentChange( double previousTotal, double newTotal ) {
  return Math.abs( newTotal - previousTotal ) / previousTotal * 100;
 }

 /**
  * Method for finding the polynomial midpoints
  */
 public static void polyMidpoint( double lowerBound, double upperBound, double[] midpoint ) {
  double deltax = ( upperBound + lowerBound ) / 2;
  midpoint[0] = deltax;
  midpoint[1] = validateFunction( deltax );
 }

 /**
  * Method for integrating polynomial functions
  */
 public static void riemannPoly() {
  double fullxArea = ( upperBound - lowerBound );
  fullArea = 0;
  double deltax = ( upperBound - lowerBound ) / 2;
  previousTotal = 0;
  rectangleNum = 0;
  double x = lowerBound;
  double[] midpoint = new double[2];
  int i = 1;

  while ( midpoint[0] <= upperBound ) {
   rectangleNum++;
   polyMidpoint( x, deltax + x, midpoint );
   x += deltax;
   previousTotal += ( deltax * midpoint[1] );
   if ( ( x + deltax ) > upperBound ) {
    if ( percentChange( previousTotal, fullArea ) < percentage ) {
     return;
    } else {
     rectangleNum = 0;
     fullArea = previousTotal;
     previousTotal = 0;
     i++;
     deltax = fullxArea / (2 * i);
     x = lowerBound;
    }
   }
  }
 }

 /**
   * Method for integrating sin functions
   */
  public static void sinRiemann() {
    double fullxArea = ( upperBound - lowerBound );
    fullArea = 0;
    double deltax = ( upperBound - lowerBound ) / 2;
    previousTotal = 0;
    rectangleNum = 0;
    double x = lowerBound;
    double[] midpoint = new double[2];
    int i = 1;

    while ( midpoint[0] <= upperBound ) {
     rectangleNum++;
     sinMidpoint( x, deltax + x, midpoint );
     x += deltax;
     previousTotal += ( deltax * midpoint[1] );
     if ( ( x + deltax ) > upperBound ) {
        return;
      } else {
       rectangleNum = 0;
       fullArea = previousTotal;
       previousTotal = 0;
       i++;
       deltax = fullxArea / ( 2 * i );
       x = lowerBound;
      }
     }
    }

 /**
  * Method for printing the string representation of the riemann sums
  */
 public String toString() {
  return "The total number of rectangles is " + Double.toString( rectangleNum ) + "Total area: " + Double.toString( fullArea );
 }

 /**
  * Method to test poly
  */
 public void riemannTests() {
  System.out.println( "Time to test the polynomial function" );
  String[] Test1 = {
  "poly",
   "3",
   "1.1",
   "6",
   "-1",
   "2",
   "5%"
  };
  System.out.println(" You should get a value closer to 28.65 ");
  this.validateArgs( Test1 );
  poly( Test1 );
  riemannPoly();
  System.out.println( this.toString() );
  System.out.println( "\n" );
  String[] Test2 = {
   "poly",
   "0",
   "-2",
   "4",
   "-5",
   "5"
  };

  System.out.println(" You should get a value closer to 333.333 ");
  this.validateArgs( Test2 );
  poly( Test2 );
  riemannPoly();
  System.out.println(this.toString() );
  System.out.println( "\n" );
  String[] Test3 = {
  "poly",
   "0",
   "4.4",
   "-1.2",
   "3.5",
   "0",
   "1"
  };

  System.out.println( " You should get a value closer to 2.675" );
  this.validateArgs( Test3 );
  poly( Test3 );
  riemannPoly();
  System.out.println( this.toString() );
  System.out.println( "\n" );
  String[] Test4 = {
   "poly",
   "100",
   "3.1",
   "6",
   "-2",
   "2"
  };

  System.out.println( " You should get a value closer to 432" );
  this.validateArgs( Test4 );
  poly( Test4 );
  riemannPoly();
  System.out.println( this.toString() );
  System.out.println( "\n" );
  String[] Test5 = {
   "poly",
   "12",
   "1",
   "2",
   "1",
   "2"
  };

  System.out.println( " You should get a value closer to 18.16" );
  this.validateArgs( Test5 );
  poly( Test5 );
  riemannPoly();
  System.out.println(this.toString() );
  System.out.println( "\n" );
  String[] Test6 = {
   "poly",
   "1",
   "1",
   "1",
   "1",
   "1",
   "1",
   "1",
   "-1",
   "1"
  };

  System.out.println( " You should get a value closer to 3.35" );
  this.validateArgs( Test6 );
  poly( Test6 );
  riemannPoly();
  System.out.println( this.toString() );
  System.out.println( "\n" );
  String[] Test7 = {
   //"poly",
   "2000",
   "13.2",
   "2.4",
   "-10",
   "10"
  };

  System.out.println(" You should get a value closer to 41600" );
  this.validateArgs( Test7 );
  poly( Test7 );
  riemannPoly();
  System.out.println( this.toString() );
  System.out.println( "\n" );
  String[] Test8 = {
  "poly",
   "10",
   "10",
   "10",
   "0",
   "10"
  };

  System.out.println( " You should get a value closer to 3933.33" );
  this.validateArgs( Test8 );
  poly( Test8 );
  riemannPoly();
  System.out.println( this.toString() );
  System.out.println("\n");
  String[] Test9 = {
   "poly",
   "0",
   "0",
   "1",
   "-3",
   "3"
  };

  System.out.println( " You should get a value closer to 18" );
  this.validateArgs( Test9 );
  poly( Test9 );
  riemannPoly();
  System.out.println( this.toString() );
  System.out.println("\n");
  String[] Test10 = {
"poly",
   "5",
   "6",
   "7",
   "8",
   "9",
   "1",
   "2"
  };

  System.out.println( "You should get a value closer to 116.13" );
  this.validateArgs( Test10 );
  poly( Test10 );
  riemannPoly();
  System.out.println( this.toString() );
  System.out.println("\n");

 }

 /**
  * The main program starts here
  */
 public static void main( String[] args ) {
  Riemann r1 = new Riemann();
    System.out.println( r1 );
    r1.validateArgs( args );
    System.out.println( r1.toString() );
 }
}
