import java.text.DecimalFormat;

public class Ball {
  /**
   *  Class field definintions go here
   */
   private double x = 0;
   private double y = 0;
   private double velocityX = 0;
   private double velocityY = 0;
   private double timeSlice = 0;
   /**
    *  Constructor goes here
    */

   public Ball(double x, double y, double velocityX, double velocityY, double timeSlice) {
      this.x = x;
      this.y = y;
      this.velocityX = velocityX;
      this.velocityY = velocityY;
      this.timeSlice = timeSlice;
   }

   /**
    *  Method to locate the position of the ball on the x and y axis
    */
   public double locationX () {
     return this.x;
   }

   public double locationY () {
     return this.y;
   }

   /**
    *  Mothod that makes the ball move on the x and y axis
    */
    public void move () {
      this.x += this.velocityX;
      this.y += this.velocityY;
      this.speedChange ();
    }

    /**
     *  Method to calculate speed with friction affecting the velocity concurrently
     *  Method to equate the velocity of the ball to 0 if the ball is moving less that a foot per second
     */
    public void speedChange() {
      this.velocityX = this.velocityX * ( Math.pow ( 0.99, this.timeSlice ) );
      this.velocityY = this.velocityY * ( Math.pow ( 0.99, this.timeSlice ) );
      if ( Math.hypot ( Math.sqrt ( velocityX ), Math.sqrt ( velocityY ) ) < 0.083 );
    }

    /**
     * Method to determine whether there is movement or not
     */
     public boolean noMovement () {
       return ( ( -0.083 <= this.velocityX ) && ( this.velocityX <= 0.083 ) ) && ( ( -0.083 <= this.velocityY ) && ( this.velocityY <= 0.083 ) );
     }

     /**
      * Method to determine whether there is a collision between balls or not
      */
      public boolean collision ( Ball ball ) {
        if ( Math.sqrt ( Math.pow ( this.locationX () - ball.locationX (), 2 ) + Math.pow ( this.locationY () - ball.locationY (), 2 ) ) < 0.7416666 ) {
          return true;
        }
        return false;
      }

      /**
       * Method to determine if a ball is out of isOutOfBounds
       */
      public boolean isOutOfBounds () {
        return ( (this.x >= 360) || (this.x <= -360) || (this.y >= 240) || (this.y <= -240) );
      }

     /**
      * Method that transforms the ball's position and speed into string format
      */
      public String toString () {
        DecimalFormat template = new DecimalFormat( "##0.000" );
        StringBuffer result = new StringBuffer ( "Location = " );
        //System.out.println("locationX = " + this.locationX ()); // print line to work out whether locationX was being printed correcly
        if ( ( 360 < Math.abs( this.locationX ()) ) || ( 240 < Math.abs(this.locationY ()) ) ) {
          result.append ( "Sadly.. out of bounds! " );
          // System.exit ( 0 );
        } else {
          result.append ( " (" + template.format ( this.locationX () ) + ", " + template.format ( this.locationY () ) + ") ");
        }
        result.append ( " Velocity of Object = ");
        if ( ( 0.083 > Math.abs ( this.velocityX ) ) && ( 0.083 > Math.abs ( this.velocityY ) ) ) {
          result.append ( " At rest " );
        } else {
          result.append ( " < " + template.format ( this.velocityX ) + ", " + template.format ( this.velocityY ) + " > " + " ft/sec on an x and y plane");
        }
        return result.toString ();
      }

      public static void main ( String args[] ) {
        Ball create = new Ball ( 0.0, 0.0, Math.random () * 10.0, Math.random () * 10.0, 1.0);
        Clock clock = new Clock ( 1.0 );
        System.out.println ( "\n Testing the methods for the Ball.java file");
        System.out.println ( "IMPORTANT: toString () is using locationX () and locationY (), while move () is using speedChange ()");
        System.out.println ( "Creating a ball starting at " + create.toString () + "\n");
        for ( int i = 0; i < 10; i++) {
          System.out.println ( " When it's " + clock.toString () + ":\n" + create.toString () + "\n" );
          create.move ();
          clock.tick ();
        }
        System.out.println ( "Testing collision () by making two balls" );
        Ball collisionTest1 = new Ball ( create.locationX () + 1, create.locationY () + 1, 0.0, 0.0, 1.0 );
        Ball collisionTest2 = new Ball ( create.locationX () + 0.390, create.locationY (), 0.0, 0.0, 1.0 );
        System.out.println ( "Running test for collision () for ball and testBall 1");
        System.out.println ( " Ball " + create.toString () );
        System.out.println ( " Ball 1 " + collisionTest1.toString () );
        System.out.println ( " Test result " + create.collision ( collisionTest1 ) );
        System.out.println ( "Running test for collision () for ball and testBall 2");
        System.out.println ( " Ball " + create.toString () );
        System.out.println ( " Ball 2 " + collisionTest2.toString () );
        System.out.println ( "Test result " + create.collision ( collisionTest2 ) );
      }
    }
