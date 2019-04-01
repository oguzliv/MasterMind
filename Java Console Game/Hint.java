/**
 * This class is for Hint object.
 * Hint object has 2 instances. Positive
 * and negative. In order to handle proper operations
 * for these two instances this class is required.
 */
public class Hint {
    private int positive,negative;

    /**
     * Constructor of hint object
     * @param positive takes an integer value to assign positive instance of hint
     * @param negative takes an integer value to assign negative instance of hint
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
