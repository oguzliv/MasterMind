/**
 * This class is for creating a Hint object
 * in order to use positive and negative instances
 * properly.
 */
package sample;

public class Hint {
    private int positive,negative;

    /**
     * Constructor of the Hint object
     * @param positive an integer to initialize positive instance
     * @param negative an integer to initialize negative instance
     */
    public Hint(int positive, int negative){
        this.positive = positive;
        this.negative = negative;
    }

    /** SETTERS AND GETTERS */

    public int getPositive() {
        return positive;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }

    public int getNegative() {
        return negative;
    }

    public void setNegative(int negative) {
        this.negative = negative;
    }
}
