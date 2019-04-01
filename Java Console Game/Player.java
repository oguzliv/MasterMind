/**
 * This class is the highest class of the hierarchy.
 * Player and Computer originates from this class. This
 * class provides basic playing mechanisms such as prediction,
 * holding a number. Also this class contains some helper
 * functions such as areSameDigits.
 */
public class Player {
    private int prediction, selection;

    /**
     * Constructor of Player object
     */

    public Player(){
        prediction = -999;
        selection = -999;
    }
    /** holdNumber() and prediction() functions' signatures */

    public int holdNumber(){
        return selection;
    }

    public int prediction(){
        return prediction;
    }

    /**
     * This method directly checks if given integer has
     * identical digits
     * @param number is 4-digit integer
     * @return true if any identical digits
     *         false else
     */

    public boolean areSameDigits(int number){
        String string_number = Integer.toString(number);
        char first,second,third,fourth;
        first = string_number.charAt(0);
        second = string_number.charAt(1);
        third = string_number.charAt(2);
        fourth = string_number.charAt(3);

        if(first != second && first != third && first != fourth){
            if(second != third && second != fourth){
                if(third != fourth){
                    return true;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
        return false;
    }

    /** SETTERS AND GETTERS */
    public int getPrediction() {
        return prediction;
    }

    public void setPrediction(int prediction) {
        this.prediction = prediction;
    }

    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }
}
