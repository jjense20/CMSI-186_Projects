/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  MainProgLoopDemo.java
 *  Purpose       :  Demonstrates the use of input from a command line for use with Yahtzee
 *  Author        :  B.J. Johnson
 *  Date          :  2017-02-14
 *  Description   :
 *  Notes         :  None
 *  Warnings      :  None
 *  Exceptions    :  None
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision Histor
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2017-02-14  B.J. Johnson  Initial writing and release
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class HighRoll{
  //Collaborated with Thomas Kelly to figure out this menu method
public static void menu () {
  System.out.println("\n Try out this Dice Game! \n");
  System.out.println("\n Enter '1' ROLL ALL THE DICE \n");
  System.out.println("\n Enter '2' ROLL A SINGLE DICE \n");
  System.out.println("\n Enter '3' CALCULATE THE SCORE FOR THIS SET \n");
  System.out.println("\n Enter '4' SAVE THIS SCORE AS HIGH SCORE \n");
  System.out.println("\n Enter '5' DISPLAY THE HIGH SCORE \n");
  System.out.println("\n Press the 'q' key to quit the program. \n");
}
   public static void main( String args[] ) {
     if (args.length != 2) {
     System.out.println ("Please input specifically 2 arguments");
     System.exit(0);
   }
   DiceSet ds = new DiceSet (Integer.parseInt (args[0]), Integer.parseInt (args[1]));
   int highScore = 0;

     // This line uses the two classes to assemble an "input stream" for the user to type
     // text into the program
      BufferedReader input = new BufferedReader( new InputStreamReader( System.in ) );
      while ( true ) {
        menu ();
         String inputLine = null;
         try {
            inputLine = input.readLine();
            System.out.println(inputLine);
            if( 0 == inputLine.length()) {
               System.out.println("Pick something from the options");
            } else if ('1' == inputLine.charAt(0)) {
               ds.roll();
               System.out.println ("\n You rolled " + ds.toString() + "\n");
            } else if ('2' == inputLine.charAt(0)) {
               System.out.println ("\n What's the index of the dice you're rolling \n");
               System.out.println (">>");
               int dieIndex = Integer.parseInt (input.readLine());
               ds.rollIndividual(dieIndex);
               System.out.println ("\n You rolled die #" + dieIndex + " within the set\n");
            } else if ('3' == inputLine.charAt(0)) {
               System.out.println("\n The sum of all your rolls is " + ds.sum() + "\n");
            } else if ('4' == inputLine.charAt(0)) {
               highScore = ds.sum();
               System.out.println("\n Your new saved HIGH SCORE is " + highScore + "\n");
            } else if ('5' == inputLine.charAt(0)) {
               System.out.println("\n Your last saved HIGH SCORE WAS " + highScore + "\n");
            } else if( 'q' == inputLine.charAt(0)) {
               System.out.println("\n Thanks for playing! \n");
               break;
            }
          } catch( IOException ioe ) {
            System.out.println( "Caught IOException" );
         }
      }
   }
}
