import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class DynamicChangeMaker {
  private static String[] args0Array;
  private static int[] denom;
  private static String badData = "BAD DATA:";

  /**
   * Checking for copies in int[] iArray
   */
   private static void checkForCopy( int[] iArray ) {
     Set<Integer> set = new HashSet<Integer>();
     for ( int i : iArray ) {
       if ( set.contains(i) ) {
         throw new IllegalArgumentException( String.format( "%s Because there are 2 or more copies of the %s coin", badData, i ) );
       } else {
         set.add( i );
       }
     }
   }

   /**
    * Checking for copies in string iArray
    */
    private static void checkForCopy( String[] sArray ) {
      Set<String> set = new HashSet<String>();
      for ( String s : sArray ) {
        if ( set.contains(s) ) {
          throw new IllegalArgumentException( String.format( "%s Because there are 2 or more copies of the %s coin", badData, s ) );
        } else {
          set.add( s );
        }
      }
    }

    /**
     * Checking for copies in int[] iArray
     */
    private static boolean validateArgs(int[] args, int target) {
      boolean validArgs = false;
      try {
        DynamicChangeMaker.checkForCopy( args );
        if ( target < 0 ) {
          throw new IllegalArgumentException( String.format( "%s Because you can't have zeros for the target value!", badData ) );
        }
        for ( int i = 0; i < args.length; i++ ) {
          if ( args[i] == 0 ) {
            throw new IllegalArgumentException( String.format( "%s Because you have zero for the first argument!", badData ) );
          } else if ( args[i] < 0 ) {
            throw new IllegalArgumentException( String.format( "%s Because you have a negative value for the first argument!", badData ) );
          }
        }
        validArgs = true;
      } catch ( Exception error ) {
        System.out.println( String.format( "%s", error.getMessage() ) );
      }
      return validArgs;
    }
    /**
     * Checking for copies in int[] iArray
     */
    private static boolean validateArgs( String[] args ) {
      String[] args0Array = args[0].split( "," );
      boolean validArgs = false;
      try {
        checkForCopy( args0Array );
        for ( int i = 0; i < args0Array.length; i++ ) {
          if ( args0Array[i] == "0" ) {
            throw new IllegalArgumentException( String.format( "%s Because you have zero for the first argument!", badData ) );
          } else if ( Integer.parseInt( args0Array[i]) < 0 ) {
            throw new IllegalArgumentException( String.format( "%s Because you have a negative value for the first argument!", badData ) );
          }
        }
        if ( args[1] == "0" ) {
          throw new IllegalArgumentException( String.format( "%s Because you have zero for the second argument!", badData ) );
        } else if ( Integer.parseInt(args[1]) < 0 ) {
          throw new IllegalArgumentException( String.format( "%s Because you have a negative value for the second argument!", badData ) );
        }
        if ( args.length > 2 ) {
          throw new IllegalArgumentException( String.format( "%s Because there are too many arguments!", badData ) );
        }
        validArgs = true;
      } catch ( Exception error ) {
        System.out.println( String.format( "%s", error.getMessage() ) );
      }
      return validArgs;
    }

    /**
     * Creating a new set for ouput
     */
    public static int[] newSet( String args0) {
      String[] args0Array = args0.split( "," );
      int[] outcome = new int[args0Array.length];
      for ( int j = 0; j < args0Array.length; j++ ) {
        outcome[j] = Integer.parseInt( args0Array[j] );
      }
      denom = new int[outcome.length];
      for ( int k = 0; k < outcome.length; k++ ) {
        denom[k] = outcome[k];
      }
      return denom;
    }

    /**
     * Creating table for tuples
     */
    public static Tuple[][] tableCreate( int rows, int columns ) {
      return new Tuple[rows][columns];
    }

    /**
     * printing lines for table
     */
    private static void linePrint( int lineNum ) {
      for ( int i = 0; i < lineNum * 12; i++ ) {
        if ( i == ( lineNum * 12 ) - 1 ) {
          System.out.println( '-' );
        } else {
          System.out.print( '-' );
        }
      }
    }

    /**
     * Dynamic change maker for tuples
     */
    public static Tuple makeChangeWithDynamicProgramming( int[] denom, int target ) {
      Tuple optimalValue = new Tuple( denom.length );
      Tuple[][] table = new Tuple[0][0];

      if ( DynamicChangeMaker.validateArgs( denom, target ) ) {
        table = tableCreate( denom.length, ( target + 1 ) );
        for ( int i = 0; i < denom.length; i++ ) {
          for ( int j = 0; j < target + 1; j++ ) {
            if ( j == 0 ) {
              table[i][0] = new Tuple( denom.length );
            } else {
              if ( j < denom[i] ) {
                table[i][j] = Tuple.IMPOSSIBLE;
              } else {
                table[i][j] = new Tuple( denom.length );
                table[i][j].setElement( i, 1 );
                if ( table[i][j - denom[i]].isImpossible() ) {
                  table[i][j] = Tuple.IMPOSSIBLE;
                } else {
                  table[i][j] = table[i][j].add( table[i][j - denom[i]] );
                }
              }
            }
            if ( i > 0 ) {
              if ( !table[i][j].isImpossible() && !table[i - 1][j].isImpossible() ) {
                if (table[i][j].total() > table[i - 1][j].total() ) {
                  table[i][j] = table[i - 1][j];
                }
              } else if ( !table[i - 1][j].isImpossible() && table[i][j].isImpossible() ) {
                table[i][j] = table[i - 1][j];
              }
            }
            optimalValue = table[i][j];
          }
        }
        return table[denom.length - 1][target];
      } else {
        return Tuple.IMPOSSIBLE;
      }
    }

    /**
     * Maing program starts here
     */
    public static void main( String[] args) {
      Tuple result = new Tuple( 0 );
      if ( DynamicChangeMaker.validateArgs( args ) ) {
        result = DynamicChangeMaker.makeChangeWithDynamicProgramming( newSet( args[0] ), Integer.parseInt( args[1] ) );
      } else {
        result = Tuple.IMPOSSIBLE;
      }
      System.out.println( result.toString() );
    }
  }
