import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Collatz {
    public static BrobInt counter = new BrobInt(  "0" );
 /**
   *  Constructor takes a string and uses BrobInt to show a large Collatz sequence
   */
    public static BrobInt Collatz(String value) {
        BrobInt ZERO = new BrobInt( "0" );
        BrobInt ONE = new BrobInt( "1" );
        BrobInt TWO = new BrobInt( "2" );
        BrobInt n = new BrobInt( value );
        System.out.println( " The Sequence value is " + n );
        if ( n.compareTo( new BrobInt( "1" ) ) == 0) {
            return counter;
        } else if ( n.allZeroDetect( n.remainder(TWO) ) ) {
            counter = counter.add( ONE );
            Collatz( ( n.divide(TWO) ).toString() );
        }
        else {
            counter = counter.add( ONE );
            Collatz( ( n.multiply( new BrobInt( "3" ) ) ).add( ONE ).toString() );
        }
        return counter;
    }
  /**
   *  the main method allows use to see the steps in collatz when inputting an argument into the terminal
   */
    public static void main( String[] args ) {
        BrobInt n = new BrobInt( args[0] );
        System.out.println( "These are the steps within Collatz " + Collatz( n.toString() ) );
    }

}
