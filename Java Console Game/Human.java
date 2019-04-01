/**
 * This class includes human player's gameplaye logic
 * This class overrieds prediction and hold number methods
 * according to human player logic.
 */

import java.util.Scanner;
public class Human extends Player {
    private int prediction;
    private int selection;

    /**
     * Constructor for Human object
     */

    public Human(){
        this.prediction = -999;
        this.selection = -999;
    }

    /**
     * User predicts a 4 digit integer.
     * Checks if predicted number is 4 digit or not.
     * Checks if predicted number has identical digits.
     * @return integer as predicted integer
     */

    @Override
    public int prediction(){
        System.out.println("--Human's Turn--");
        Scanner scan = new Scanner(System.in);
        int prediction;
        do{
            System.out.println("Predict Number");
            prediction = scan.nextInt();
            if(lengthChecker(prediction) == 1 && areSameDigits(prediction))
                setPrediction(prediction);
            else
                System.out.println("Predicted is not a 4 digit number");
        }while(lengthChecker(prediction) == 0 || !areSameDigits(prediction));
        return getPrediction();
    }

    /**
     * User holds an integer to play game
     * according to if held number is 4 digit
     * and number has 4 distinct digits
     * @returns the selection
     */

    @Override
    public int holdNumber(){
        Scanner scan = new Scanner(System.in);
        String hold_number;
        int number_value = 0;
        do{

            System.out.println("Hold Number");
            hold_number = scan.next();

            try {
                number_value = Integer.parseInt(hold_number);
            }catch(Exception e){
                System.out.println("Non-numeric value, enter new one");
            }

            if(lengthChecker(number_value) == 1 && areSameDigits(number_value) )
                setSelection(number_value);
            else if(!areSameDigits(number_value))
                System.out.println("Some digits are same, Try a new number");
            else
                System.out.println("Held number is problematic, please try a new number!!");

        }while(lengthChecker(number_value) == 0 || !areSameDigits(number_value));
        return getSelection();
    }

    /**
     * Checks if any number has 4 digits
     * @param number is the number to be checked
     * @return 1 if number has 4 digits
     *         0 else
     */

    public int lengthChecker(int number){
        if(number < 10000 && number >= 1000)
            return 1;
        else
            return 0;
    }


    /**
     * Checks if given number has identical digits
     * @param number to be checked if has identical digits
     * @return true if number does not have identical digit
     *         false else
     */

    @Override
    public boolean areSameDigits(int number){
        if(lengthChecker(number) == 0) {
            System.out.println("Number is not 4 digit");
            return false;
        }
        else {
            return super.areSameDigits(number);
        }
    }

    /** SETTERS & GETTERS*/
    public int getPrediction(){
        return this.prediction;
    }
    public void setPrediction(int prediction){
        this.prediction = prediction;
    }
    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }
}
