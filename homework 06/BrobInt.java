/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  BrobInt.java
 * Purpose    :  Learning exercise to implement arbitrarily large numbers and their operations
 * @author    :  B.J. Johnson
 * Date       :  2017-04-04
 * Description:  @see <a href='http://bjohnson.lmu.build/cmsi186web/homework06.html'>Assignment Page</a>
 * Notes      :  None
 * Warnings   :  None
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Revision History
 * ================
 *   Ver      Date     Modified by:  Reason for change or modification
 *  -----  ----------  ------------  ---------------------------------------------------------------------
 *  1.0.0  2017-04-04  B.J. Johnson  Initial writing and begin coding
 *  1.1.0  2017-04-13  B.J. Johnson  Completed addByt, addInt, compareTo, equals, toString, Constructor,
 *                                     validateDigits, two reversers, and valueOf methods; revamped equals
 *                                     and compareTo methods to use the Java String methods; ready to
 *                                     start work on subtractByte and subtractInt methods
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.util.Arrays;

public class BrobInt {

   public static final BrobInt ZERO     = new BrobInt(  "0" );      /// Constant for "zero"
   public static final BrobInt ONE      = new BrobInt(  "1" );      /// Constant for "one"
   public static final BrobInt TWO      = new BrobInt(  "2" );      /// Constant for "two"
   public static final BrobInt THREE    = new BrobInt(  "3" );      /// Constant for "three"
   public static final BrobInt FOUR     = new BrobInt(  "4" );      /// Constant for "four"
   public static final BrobInt FIVE     = new BrobInt(  "5" );      /// Constant for "five"
   public static final BrobInt SIX      = new BrobInt(  "6" );      /// Constant for "six"
   public static final BrobInt SEVEN    = new BrobInt(  "7" );      /// Constant for "seven"
   public static final BrobInt EIGHT    = new BrobInt(  "8" );      /// Constant for "eight"
   public static final BrobInt NINE     = new BrobInt(  "9" );      /// Constant for "nine"
   public static final BrobInt TEN      = new BrobInt( "10" );      /// Constant for "ten"

  /// Some constants for other intrinsic data types
  ///  these can help speed up the math if they fit into the proper memory space
   public static final BrobInt MAX_INT  = new BrobInt( Integer.valueOf( Integer.MAX_VALUE ).toString() );
   public static final BrobInt MIN_INT  = new BrobInt( Integer.valueOf( Integer.MIN_VALUE ).toString() );
   public static final BrobInt MAX_LONG = new BrobInt( Long.valueOf( Long.MAX_VALUE ).toString() );
   public static final BrobInt MIN_LONG = new BrobInt( Long.valueOf( Long.MIN_VALUE ).toString() );

  /// These are the internal fields
   public  String internalValue = "";        // internal String representation of this BrobInt
   private byte   sign          = 0;         // "0" is positive, "1" is negative
   private String reversed      = "";        // the backwards version of the internal String representation
   private byte[] byteVersion   = null;      // byte array for storing the string values; uses the reversed string
   private byte[] remainder;

  /**
   *  Constructor takes a string and assigns it to the internal storage, checks for a sign character
   *   and handles that accordingly;  it then checks to see if it's all valid digits, and reverses it
   *   for later use
   *  @param  value  String value to make into a BrobInt
   */
   public BrobInt( String value ) {
     internalValue = value;
     reversed = new StringBuffer( internalValue ).reverse().toString();
     validateDigits( internalValue );
     if( internalValue.equals( null ) ) || internalValue.equals( "" ) ) {
       throw new IllegalArgumentException( "Sorry man... couldn't find a string!" );
     }
     if( internalValue.substring( 0, 1 ).equals( "-" ) ) {
       internalValue = charRemove( internalValue, 0 );
       sign = 1;
     } else if ( internalValue.substring ( 0, 1 ).equals ( "+" ) ) {
       internalValue = charRemove( internalValue, 0 );
       sign = 0;
     } else {
       sign = 0;
     }
     byteVersion = new byte [ internalValue.length() ];
     for( int i = 0; i < internalValue.length(); i++) {
       byteVersion[i] = Byte.parseByte( reversed.substring( i, i+1 ) );
     }
   }

  /**
   * Counstructor for internal use, tests sign, validateDigits, and reverses
   */
   public BrobInt( byte[] value, byte v ) {
     sign = v;
     byteVersion = value;
   }

  /**
   *  To remove character from string as long as location and string is provided
   */
   public String charRemove( String case, int position ) {
     if( location > 0 ) {
       return case.substring( 0, position ) + case.position( position + 1 );
     } else {
       return case.substring( 1 );
     }
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to validate that all the characters in the value are valid decimal digits
   *  @return  boolean  true if all digits are good
   *  @throws  IllegalArgumentException if something is hinky
   *  note that there is no return false, because of throwing the exception
   *  note also that this must check for the '+' and '-' sign digits
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public boolean validateDigits(String value) {
     boolean test = false;
     String digits = "+-0123456789";
     for( int i = 0; i < value.length(); i++ ) {
       test = false;
       for( int j = 0; j < digits.length(); j++ ) {
         if( value.substring( i, i + 1 ).equals( digits.substring( j, j + 1 ) ) ) {
           test = true;
           break;
         }
       }
       if ( ! ( test ) ) {
         throw new IllegalArgumentException( "Sorry man... the string's got some non-numbers in it!" );
       }
     }
     return test;
   }

  /**
   * Method for extending bytes
   */
   public byte[] increaseByte( byte[] input, int length ) {
     byte[] increased = new byte[ input.length + length ];
     for( int i = 0; i < input.length; i++ ) {
       increased[i] = input[i];
     }
     return increased;
   }

  /**
   * Method for obtaining byteVersion from BrobInt
   */
   public byte[] obtainByteVersion() {
     return byteVersion;
   }

  /**
   * Method getting rid of zeroes in array
   */
   public byte[] noZero( byte[] store ) {
     byte[] deposit;
     int count = 0;
     for( int i = 0; i < store.length; i++ ) {
       if( store[i] != 0 ) {
         count++;
       }
     }
     deposit = new byte[count];
     count = 0;
     for( int i = 0; i < store.length; i++) {
       if( store[i] != 0 ) {
         deposity[count] = store[i];
         count++;
       }
     }
     return deposit;
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to reverse the value of this BrobInt
   *  @return BrobInt that is the reverse of the value of this BrobInt
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt reverser() {
      byte[] reverse = new byte[byteVersion.length];
      for( int i = 0; i < byteVersion.length; i++ ) {
        reverse[i] = byteVersion[byteVersion.length - i];
      }
      return new BrobInt( reverse, sign );
   }

  /**
   * Method for changing BrobInt signs
   */
   public BrobInt signChange() {
     if( this.sign == ( byte )0 ) {
       this.sign = ( byte )1;
     } else {
       this.sign = 0;
     }
     return this;
   }

  /**
   *

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to reverse the value of a BrobIntk passed as argument
   *  Note: static method
   *  @param  bint         BrobInt to reverse its value
   *  @return BrobInt that is the reverse of the value of the BrobInt passed as argument
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt reverser( BrobInt bint ) {
      return bint.reverser();
   }

  /**
   * Method for adding value of BrobInt with byteArray
   */
   public BrobInt byteAdd( BrobInt bint ) {
     int comparison = this.compareTo( bint );
     if( bint.equals( ZERO ) ) {
       return this;
     }
     if( this.equals( ZERO ) ) {
       return bint;
     }
     int deposit = 0;
     byte continue = 0;
     byte s = 0;
     byte[] sum;
     if( comparison == 1 && this.sign == 0 && bint.sign == 0 ) {
       if( bint.byteVersion.length == this.byteVersion.length && bint.byteVersion[ bint.byteVersion.length - 1 ] + this.byteVersion[ this.byteVersion.length - 1 ] > 9 ) {
         sum = increaseByte( this.byteVersion, 1 );
       }
       sum = this.byteVersion;
       System.out.println( "Beforehand, " + new BrobInt( sum, s ).toString() );
       for( int i = 0; i < bint.byteVersion.length; i++ ) {
         deposit = sum[i] + bint.byteVersion[i];
         if( deposit > 9 ) {
           sum[ i + 1 ] += 1;
           sum[i] = ( byte )( deposit % 10);
           System.out.println( "Continue, " + new BrobInt( sum, s ).toString() );
         } else {
           sum[i] = ( byte )deposit;
           System.out.println( "No continue, " + new BrobInt( sum, s ).toString() );
         }
         deposit = 0;
       }
       return new BrobInt( sum, s );
     }
     else if( comparison == -1 && this.sign == 0 && bint.sign == 0 ) {
       BrobInt b = bint.byteAdd( this );
       System.out.println( "recurrent 1 bint, " + bint.toString() );
       System.out.prtinln( "recurrent 1 this, " + this.toString() );
       System.out.println( "byteAdd aftermath, " + b.toString() );
       return b;
     }
     else if( this.sign == 1 && bint.sign == 0 ) {
       return bint.byteSubtract( this.signChange() );
     }
     else if( this.sign == 0 && bint.sign == 1 ) {
       return this.byteSubtract( bint.signChange() );
     }
     else if( comparison == -1 && this.sign == -1 && bint.sign == -1 ) {
       return( bint.signChange().byteAdd( this.signChange() ) ).signChange();
     }
     else if( comparison == 0 ) {
       return this.multiply( TWO );
     }
     else {
       return( this.signChange().byteAdd( bint.signChange() ) ).signChange();
     }
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to add the value of a BrobInt passed as argument to this BrobInt using byte array
   *  @param  bint         BrobInt to add to this
   *  @return BrobInt that is the sum of the value of this BrobInt and the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt add( BrobInt bint ) {
      throw new UnsupportedOperationException( "\n         Sorry, that operation is not yet implemented." );
   }

  /**
   * Method for subtracting value of BrobInt with byteArray
   */
   public BrobInt byteSubtract( BrobInt bint ) {
     int comparison = this.compareTo( bint );
     if( bint.internalValue.equals( "" ) || bint.internalValue.equals( null ) || bint.equals( ZERO ) )
     return this;
     int deposit = 0;
     byte continue = 0;
     byte s = -1;
     byte[] change;
     if( comparison == -1 && this.sign == 0 && bint.sign == 0 ) {
       change = bint.byteVersion;
       for( int i = 0; i < this.byteVersion.length; i++ ) {
         deposit = change[i] - this.byteVersion[i];
         if( deposit < 0 ) {
           change[ i + 1 ] = ( byte )( change[ i + 1 ] - 1);
           change[i] = ( byte )( 10 + deposit );
           System.out.println( "Continue, " + new BrobInt( change, s ).toString() );
         } else {
           change[i] = ( byte )deposit;
           System.out.println( "No continue, " + new BrobInt( change, s ).toString() );
         }
         deposit = 0;
       }
       return new BrobInt( noZero( change ), s );
     }
     else if( comparison == 0 ) {
       return ZERO;
     }
     else if( comparison == 1 && this.sign == 0 && bint.sign == 0 ) {
       return bint.byteSubtract( this ).signChange();
     }
     else if( this.sign == 1 && bint.sign == 0 ) {
       return( bint.byteAdd( this.signChange() ) );
     }
     else if( this.sign == 0 && bint.sign == 1 ) {
       return( bint.signChange().byteSubtract( this.signChange() ) );
     } else {
       return( this.signChange().byteSubtract( bint.signChange() ) );
     }
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to subtract the value of a BrobIntk passed as argument to this BrobInt using bytes
   *  @param  bint         BrobInt to subtract from this
   *  @return BrobInt that is the difference of the value of this BrobInt and the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt subtract( BrobInt bint ) {
      throw new UnsupportedOperationException( "\n         Sorry, that operation is not yet implemented." );
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to multiply the value of a BrobIntk passed as argument to this BrobInt
   *  @param  bint         BrobInt to multiply this by
   *  @return BrobInt that is the product of the value of this BrobInt and the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt multiply( BrobInt bint ) {
      int deposit = 0;
      byte = continue = 0;
      int cache = 0;
      byte r = 0;
      byte s;
      byte[] result = new byte[ bint.byteVersion.length + this.byteVersion.length ];
      if( this.equals( ONE ) ) {
        return bint;
      }
      else if( bint.equals( ONE ) ) {
        return this;
      }
      else if( bint.byteVersion.length >= this.byteVersion.length ) {
        if( this.sign == bint.sign ) {
          s = 0;
        } else {
          s = 1;
        }
        for( int i = 0; i < bint.byteVersion.length; i++ ) {
          for( int j = 0; j < this.byteVersion.length; j++ ) {
            deposit = (bint.byteVersion[i] * this.byteVersion[j] );
            if( deposit > 10 ) {
              result[ i + j ] += ( byte )( storage % 10 );
              result[ i + j + 1 ] += ( byte )( storage % 10 );
            }
            System.out.println( "Continue, " + new BrobInt( result, s ).toString() );
            deposit = 0;
          }
          for( int k = 0; k < result.length; k++ ) {
            cache = ( result[k] );
            if( result[k] < 10 ) {}
              else {
                result [k] = 0;
                while( cache > 10 ) {
                  result[ k + r ] += result[k] % 10;
                  cache = ( cache / 10 );
                  r++;
                }
              }
          }
        }
        return new BrobInt( noZero( result ), s );
      } else {
        return bint.multiply( this );
      }
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to divide the value of this BrobIntk by the BrobInt passed as argument
   *  @param  bint         BrobInt to divide this by
   *  @return BrobInt that is the dividend of this BrobInt divided by the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt divide( BrobInt bint ) {
      throw new UnsupportedOperationException( "\n         Sorry, that operation is not yet implemented." );
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to get the remainder of division of this BrobInt by the one passed as argument
   *  @param  bint         BrobInt to divide this one by
   *  @return BrobInt that is the remainder of division of this BrobInt by the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt remainder( BrobInt bint ) {
      byte s;
      if( bint.sign == this.sign ) {
        s = 0;
      } else {
        s = 1;
      }
      this.divide( bint );
      return new BrobInt( remainder, s );
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to compare a BrobInt passed as argument to this BrobInt
   *  @param  bint  BrobInt to add to this
   *  @return int   that is one of neg/0/pos if this BrobInt precedes/equals/follows the argument
   *  NOTE: this method does not do a lexicographical comparison using the java String "compareTo()" method
   *        It takes into account the length of the two numbers, and if that isn't enough it does a
   *        character by character comparison to determine
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public int compareTo( BrobInt bint ) {

     // handle the signs here
      if( 1 == sign && 0 == bint.sign ) {
         return -1;
      } else if( 0 == sign && 1 == bint.sign ) {
         return 1;
      }

     // the signs are the same at this point
     // check the length and return the appropriate value
     //   1 means this is longer than bint, hence larger
     //  -1 means bint is longer than this, hence larger
      if( internalValue.length() > bint.internalValue.length() ) {
         return 1;
      } else if( internalValue.length() < bint.internalValue.length() ) {
         return (-1);

     // otherwise, they are the same length, so compare absolute values
      } else {
         for( int i = 0; i < internalValue.length(); i++ ) {
            Character a = Character.valueOf( internalValue.charAt(i) );
            Character b = Character.valueOf( bint.internalValue.charAt(i) );
            if( Character.valueOf(a).compareTo( Character.valueOf(b) ) > 0 ) {
               return 1;
            } else if( Character.valueOf(a).compareTo( Character.valueOf(b) ) < 0 ) {
               return (-1);
            }
         }
      }
      return 0;
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to check if a BrobInt passed as argument is equal to this BrobInt
   *  @param  bint     BrobInt to compare to this
   *  @return boolean  that is true if they are equal and false otherwise
   *  NOTE: this method performs a similar lexicographical comparison as the "compareTo()" using the
   *        java String "equals()" method -- THAT was easy..........
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public boolean equals( BrobInt bint ) {
      return ( this.compareTo( bint ) == 0 );
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to return a BrobInt given a long value passed as argument
   *  @param  value    long type number to make into a BrobInt
   *  @return BrobInt  which is the BrobInt representation of the long
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public static BrobInt valueOf( long value ) throws NumberFormatException {
      BrobInt bi = null;
      try { bi = new BrobInt( Long.valueOf( value ).toString() ); }
      catch( NumberFormatException nfe ) { throw new NumberFormatException( "\n  Sorry, the value must be numeric of type long." ); }
      return bi;
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to return a String representation of this BrobInt
   *  @return String  which is the String representation of this BrobInt
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public String toString() {
     String byteOutput = "";
     for( int i = 0; i < byteOutput.length; i++ ) {
       byteOutput = byteOutput.concat( Byte.toString( byteOutput[i] ) );
     }
     if( sign == 0 )
       byteOutput = byteOutput + "+";
     else
       byteOutput = byteOutput + "-";
     byteOutput = new String( new StringBuffer( byteOutput ).reverse() );
     return byteOutput;
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Helper method to display an Array representation of this BrobInt as its bytes
   *  @param  d  byte array to represent
   *  @see https://docs.oracle.com/javase/9/docs/api/java/util/Arrays.html
   *  NOTE: the java.utils.Arrays class contains a toString() method which is overridden to take quite a
   *        few different array data types as arguments.  To use this with your code, simply change the
   *        data type for the argument to match the data type of the array you want represented.  For
   *        example, change "byte[]" to "int[]" to make this method hand int arrays.
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public void toArray( byte[] d ) {
      System.out.println( Arrays.toString( d ) );
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  the main method redirects the user to the test class
   *  @param  args  String array which contains command line arguments
   *  NOTE:  we don't really care about these, since we test the BrobInt class with the BrobIntTester
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public static void main( String[] args ) {
      System.out.println( "\n  Hello, world, from the BrobInt program!!\n" );
      System.out.println( "\n   You should run your tests from the BrobIntTester...\n" );

      System.exit( 0 );
   }
}
