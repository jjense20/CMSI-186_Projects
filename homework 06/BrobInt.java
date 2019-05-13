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
    private String internalValue         = "";                         // internal String representation of this BrobInt
    private int   sign                   = 0;                          // "0" is positive, "1" is negative
    private StringBuilder reversed       = new StringBuilder();        // the backwards version of the internal String representation
    private static int[] IntVersion      = null;                       // Int array for storing the string values; uses the reversed string
    private StringBuilder stringBuilder  = new StringBuilder();

   /**
    *   Constructor assigns a string to internal storage
    *   checks for the sign and validates the digits
    *   reverses for later use
    */
    public BrobInt( String value ) {
       if ( value.equals( "" ) || value.equals( null ) ) {
         throw new IllegalArgumentException( "Sorry man.. the string wasn't able to pass to the constructor!" );
       }
       internalValue = value;
       StringBuilder stringBuilder = new StringBuilder( internalValue );
       if ( internalValue.charAt( 0 ) == '-' ) {
        sign = 1;
        stringBuilder.deleteCharAt( 0 );
      } else if ( internalValue.charAt( 0 ) == '+' ) {
        sign = 0;
        stringBuilder.deleteCharAt( 0 );
      } else {
        sign = 0;
      }
       validateDigits( stringBuilder.toString() );
       IntVersion = new int[ stringBuilder.length() ];
       for ( int i = IntVersion.length - 1; i > -1; i-- ) {
         reversed.append( stringBuilder.charAt( i ) + "" );
         IntVersion[ i ] = Integer.parseInt( stringBuilder.charAt( i ) + "" );
       }
    }

   /**
    *  Method validating the digits given within the string
    */
    public boolean validateDigits( String value ) throws IllegalArgumentException {
      String digits = "0123456789";
        for ( int i = 0; i < stringBuilder.length(); i++ ) {
          for ( int j = 0; j < digits.length(); j++ ) {
            if ( stringBuilder.toString().charAt( j ) == digits.charAt( j ) ) {
              continue;
            } else if ( ( j == digits.length() - 1 ) && !( stringBuilder.toString().charAt( j ) == digits.charAt( j ) ) ) {
               throw new IllegalArgumentException();
             }
           }
         }
         return true;
    }

   /**
    *  Method reversing the values of this BrobInt
    */
    public BrobInt reverser() {
      return new BrobInt( new StringBuilder( this.stringBuilder ).reverse().toString() );
     }

   /**
    *  Method reversing the values of the BrobInts passed as arguments
    */
    public static BrobInt reverser( BrobInt bint ) {
      return bint.reverser();
    }

   /**
    *  Method getting the sign value of the BrobInt
    */
    public int signReturn() {
     return sign;
    }

   /**
    *  Method setting the sign value of the BrobInt
*/
    public int signChange( int newS ) {
      return sign = newS;
    }

   /**
    *  Method subtracting the BrobInt passed as an argument to one using ints
    */
    public BrobInt add( BrobInt bint ) {
     int addedInts = 0;
     int carry = 0;
     int shortLength, longLength;
     StringBuilder shortInt = new StringBuilder();
     StringBuilder longInt = new StringBuilder();
     StringBuilder addIntStringBuilder = new StringBuilder();
     if ( this.signReturn() == 0 && bint.signReturn() == 1 || this.signReturn() == 1 && bint.signReturn() == 0 ) {
       bint.signChange( 0 );
       this.signChange( 0 );
       this.subtract( bint );
     }
     if ( this.reversed.length() <= bint.reversed.length() ) {
       shortLength = this.reversed.length();
       shortInt = this.reversed;
       longLength = bint.reversed.length();
       longInt = bint.reversed;
     } else {
       shortLength = bint.reversed.length();
       shortInt = bint.reversed;
       longLength = this.reversed.length();
       longInt = this.reversed;
     }
     for ( int i = 0; i < shortLength; i++ ) {
       addedInts = Integer.parseInt( this.reversed.charAt(i) + "" ) + Integer.parseInt( bint.reversed.charAt(i) + "" ) + carry;
       if ( addedInts > 9 ) {
         addedInts -= 10;
         carry = 1;
       } else {
         carry = 0;
       }
       addIntStringBuilder.append( addedInts );
     }
     if ( carry == 1 ) {
       addIntStringBuilder.append( carry );
     }
     for ( int j = shortLength; j < longLength; j++) {
       addIntStringBuilder.append( longInt.charAt(j) + "" );
     }
     if ( addIntStringBuilder.charAt( addIntStringBuilder.length() - 1 ) == '0' ) {
       addIntStringBuilder.deleteCharAt( addIntStringBuilder.length() - 1 );
     }
     if ( this.signReturn() == 1 && bint.signReturn() == 1 ) {
       addIntStringBuilder.append( "-" );
     }
     String addedIntString = new String( addIntStringBuilder.reverse().toString() );
     BrobInt newBrobInt = new BrobInt( addedIntString );
     return newBrobInt;
    }

   /**
    *  Method subtracting the BrobInt passed as an argument to one using ints
    */
    public BrobInt subtract( BrobInt bint ) {
     int subInts = 0;
     int carry = 0;
     int shortLength = 0;
     int longLength = 0;
     StringBuilder shortInt = new StringBuilder();
     StringBuilder longInt = new StringBuilder();
     StringBuilder subIntStringBuilder = new StringBuilder();
     if ( this.compareTo( bint ) == 0 ) {
       return ZERO;
     }
     if ( this.signReturn() == 1 && bint.signReturn() == 1 && this.greater( bint ) ) {
       StringBuilder thisPos = new StringBuilder( this.toString() ).deleteCharAt( 0 );
       StringBuilder bintPos = new StringBuilder( bint.toString() ).deleteCharAt( 0 );
       return new BrobInt( new BrobInt( thisPos.toString() ).subtract( new BrobInt( bintPos.toString() ) ).toString() );
     }
     if ( this.signReturn() == 1 && bint.signReturn() == 1 && !( this.greater( bint ) ) ) {
       StringBuilder thisPos = new StringBuilder( this.toString() ).deleteCharAt( 0 );
       StringBuilder bintPos = new StringBuilder( bint.toString() ).deleteCharAt( 0 );
       return new BrobInt( new StringBuilder( new BrobInt( bintPos.toString() ).subtract( new BrobInt( thisPos.toString() ) ).toString() ).insert( 0, "-" ).toString() );
     }
     if ( this.signReturn() == 0 && bint.signReturn() == 1 ) {
       StringBuilder bintPos = new StringBuilder( bint.toString() ).deleteCharAt( 0 );
       return new BrobInt( this.add( new BrobInt( bintPos.toString() ) ).toString() );
     }
     if ( this.signReturn() == 1 && bint.signReturn() == 0 ) {
       bint.signChange( 1 );
       return new BrobInt( this.add( bint ).toString() );
     }
     if ( !( this.greater( bint ) ) ) {
       shortLength = this.reversed.length();
       shortInt = this.reversed;
       longLength = bint.reversed.length();
       longInt = bint.reversed;
     } else if ( this.greater( bint ) ) {
       shortLength = bint.reversed.length();
       shortInt = bint.reversed;
       longLength = this.reversed.length();
       longInt = this.reversed;
     }
     for ( int i = 0; i < shortLength; i++ ) {
       subInts = Integer.parseInt( longInt.charAt(i) + "" ) - Integer.parseInt( shortInt.charAt(i) + "" ) - carry;
       if ( subInts < 0 ) {
         subInts += 10;
         carry = 1;
       } else {
         carry = 0;
       }
       subIntStringBuilder.append( subInts );
     }
     for ( int j = shortLength; j < longLength; j++ ) {
       subIntStringBuilder.append( longInt.charAt( j ) + "" );
     }
     if ( !( this.greater( bint ) ) && this.reversed.length() < bint.reversed.length() ) {
       subIntStringBuilder.append( "-" );
     }
     String subIntString = new String( subIntStringBuilder.reverse().toString() );
     BrobInt newBrobInt = new BrobInt( subIntString );
     return newBrobInt;
    }

   /**
    *  Method multiplying this BrobInt with the one passed as an argument
    */
    public BrobInt multiply( BrobInt bint ) {
      int i, j, k;
      int shortLength = 0;
      int longLength = 0;
      int oneCounter = 0;
      int[] intArray;
      BrobInt shortBrobInt = new BrobInt( "0" );
      BrobInt longBrobInt = new BrobInt( "0" );
      StringBuilder multIntStringBuilder = new StringBuilder();
      BrobInt newBrobInt = new BrobInt( "0" );
      if ( this.reversed.length() <= bint.reversed.length() ) {
        shortLength = this.reversed.length();
        shortBrobInt = this;
        longLength = bint.reversed.length();
        longBrobInt = bint;
      } else if ( this.reversed.length() > bint.reversed.length() ) {
        shortLength = bint.reversed.length();
        shortBrobInt = bint;
        longLength = this.reversed.length();
        longBrobInt = this;
      }
      int intArrayLength = ( shortLength / 9) + 1;
      intArray = new int[ intArrayLength ];
      if ( shortLength < 10 ){
          intArray[0] = Integer.parseInt( shortBrobInt.internalValue );
       } else {
          for ( j = 0; j < intArrayLength; j++ ){
            if ( j + 9 < longLength ){
              intArray[j] = Integer.parseInt( shortBrobInt.internalValue.substring(j*9, (j*9) + 9) );
            } else {
              intArray[j] = Integer.parseInt( shortBrobInt.internalValue.substring(j*9, (j*9) + (shortLength - j) ) );
            }
          }
       }
       for (  i = 0; i < intArrayLength; i++ ){
          for ( j = 0; j < intArray[i]; j++ ){
            newBrobInt = newBrobInt.add( new BrobInt( longBrobInt.internalValue ) );
          }
       }
       for ( i = 0; i < newBrobInt.internalValue.length(); i++ ) {
         if ( newBrobInt.internalValue.charAt( i ) == '1' ) {
           oneCounter++;
         } else {
           break;
         }
       }
       multIntStringBuilder = new StringBuilder( newBrobInt.toString() );
       if ( oneCounter > 1 ) {
         multIntStringBuilder.delete( 0, oneCounter );
         multIntStringBuilder.insert( 0, oneCounter );
       }
       if ( this.signReturn() != bint.signReturn() ) {
        multIntStringBuilder =  new StringBuilder( newBrobInt.reversed.toString() ).append( "-" ).reverse();
       }
       newBrobInt = new BrobInt( multIntStringBuilder.toString() );
       return newBrobInt;
    }

   /**
    *  Method dividing this BrobInt by the one that was passed as an argument
    */
    public BrobInt divide( BrobInt bint ) {
      BrobInt divisor = bint;
      BrobInt dividend = this;
      BrobInt newDividend = new BrobInt( "0" );
      BrobInt quotient = new BrobInt( "0" );
      StringBuilder divIntStringBuilder = new StringBuilder();
      if ( divisor.equals( ZERO ) ) {
       throw new IllegalArgumentException( "Sorry man.. you can't divide by zero!" );
      } else if ( dividend.equals( divisor ) ) {
        return ONE;
      } else if ( divisor.greater( dividend ) ) {
        return ZERO;
      }
      int divisorLength = divisor.internalValue.length();
      int dividendLength = dividend.internalValue.length();
      int leftoverDividend = 0;
      newDividend = new BrobInt( dividend.toString().substring( 0, divisorLength ) );
      System.out.println( newDividend );
      if ( Integer.parseInt( divisor.toString() ) > Integer.parseInt( newDividend.toString() ) ) {
        divisorLength++;
        newDividend = new BrobInt( dividend.toString().substring( 0, divisorLength ) );
      }
      leftoverDividend += divisorLength;
      while ( divisorLength <= dividend.toString().length() ) {
        while ( Integer.parseInt( newDividend.internalValue ) > Integer.parseInt( divisor.internalValue ) ) {
          newDividend = new BrobInt( ( Integer.parseInt( newDividend.toString() ) - Integer.parseInt( divisor.toString() ) ) + "" );
          quotient = quotient.add( ONE );
         }
         if ( leftoverDividend == dividendLength ) {
            break;
         }
         newDividend = newDividend.multiply( TEN );
         quotient = quotient.multiply( TEN );
         newDividend = new BrobInt( ( Integer.parseInt( newDividend.internalValue ) + Integer.parseInt( dividend.toString().charAt( leftoverDividend  ) + "" ) ) + "");
         leftoverDividend++;
      }
       if ( this.signReturn() != bint.signReturn() ) {
        divIntStringBuilder = new StringBuilder( quotient.reversed.toString() ).append( "-" ).reverse();
        quotient = new BrobInt( divIntStringBuilder.toString() );
       }
      BrobInt newBrobInt = new BrobInt( quotient.toString() );
      return newBrobInt;
    }

   /**
    *  Method to calculate the remainder of the division of this BrobInt and the one passed as an argument
    */
    public BrobInt remainder( BrobInt bint ) {
      BrobInt newBrobInt = new BrobInt ( this.subtract( this.divide( bint ).multiply( bint ) ).toString() );
      StringBuilder remainderStringBuilder = new StringBuilder( newBrobInt.toString() );
      while ( remainderStringBuilder.charAt( 0 ) == '0' ) {
        remainderStringBuilder.deleteCharAt( 0 );
      }
      if ( this.signReturn() == 1 ) {
        remainderStringBuilder = remainderStringBuilder.reverse().append( "-" ).reverse();
      }
      newBrobInt = new BrobInt( remainderStringBuilder.toString() );
      return newBrobInt;
    }

   /**
    *  Method comparing BrobInt within argument to this BrobInt
    */
    public int compareTo( BrobInt bint ) {
       return ( internalValue.compareTo( bint.toString() ) );
    }

   /**
    *  Method checking which BrobInt is greater
    */
    public boolean greater( BrobInt bint ) {
     if ( this.sign == 1 && bint.sign == 0 ) {
       return false;
     } else if ( this.sign == 0 && bint.sign == 1 ) {
       return true;
     } else if ( this.sign == 1 && bint.sign == 1 ) {
       if ( this.internalValue.length() < bint.internalValue.length() ) {
         return true;
       } else if ( this.internalValue.length() > bint.internalValue.length() ) {
         return false;
       } else {
         for ( int i = this.reversed.length() - 1; i > -1; i-- ) {
           if ( Integer.parseInt( this.reversed.charAt( i ) + "" ) > Integer.parseInt( bint.reversed.charAt( i ) + "" ) ) {
             return false;
           }
         }
         return true;
       }
     } else {
       if ( this.internalValue.length() < bint.internalValue.length() ) {
         return false;
       } else if ( this.internalValue.length() > bint.internalValue.length() ) {
         return true;
       } else {
         for ( int i = this.reversed.length() - 1; i > -1; i-- ) {
           if ( Integer.parseInt( this.reversed.charAt( i ) + "" ) > Integer.parseInt( bint.reversed.charAt( i ) + "" ) ) {
             return true;
           }
         }
         return false;
       }
     }
    }

   /**
    *  Method comparing BrobInt within argument to this BrobInt
    */
    public boolean equals( BrobInt bint ) {
       return ( internalValue.equals( bint.toString() ) );
    }

   /**
    *  Method to return a BrobInt given a long value passed as argument
    */
    public static BrobInt valueOf( long value ) throws NumberFormatException {
       BrobInt bi = null;
       try {
          bi = new BrobInt( Long.valueOf( value ).toString() );
       }
       catch( NumberFormatException nfe ) {
          System.out.println( "\n  Sorry man.. your value has to be long of a numeric value!" );
       }
       return bi;
    }

   /**
     *  Method that returns booleans if BrobInt is only composed of zeros
     */
    public boolean allZeroDetect( BrobInt bint ) {
       for( int i = 0; i < bint.toString().length(); i++ ) {
          if( bint.toString().charAt(i) != '0' ) {
             return false;
          }
       }
       return true;
    }

   /**
    *  Method for returning a string representation
    */
    public String toString() {
       String IntVersionOutput = "";
       for( int i = 0; i < IntVersion.length; i++ ) {
          IntVersionOutput = IntVersionOutput.concat( Integer.toString( IntVersion[i] ) );
       }
       IntVersionOutput = new String( new StringBuffer( IntVersionOutput ).reverse() );
       return internalValue;
    }

   /**
    *  Method for displaying the array representations of the BrobInts
    */
    public void toArray( int[] d ) {
       System.out.println( Arrays.toString( d ) );
    }

   /**
    *  main program starts here
    */
    public static void main( String[] args ) {
       System.out.println( "\n  Hello, world, from the BrobInt program!!\n" );
       System.out.println( "\n   You should run your tests from the BrobIntTester...\n" );

       BrobInt g1 = new BrobInt( "765" );
       BrobInt g2 = new BrobInt( "759" );

       System.out.println( g1.subtract( g2 ) );

       System.exit( 0 );
    }
 }
