/**
 * This class includes game logic for computer player.
 * This class overrides prediction and hold number methods
 * according to game play logic of computer player. Also
 * this class includes some helper functions.
 */

import java.util.ArrayList;

public class Computer extends Player {

    private int prediction, selection;
    private ArrayList<Integer> numbers;


    /**
     * Constructor for human object
     */

    public Computer(){
        super();
        numbers = init_list();

    }

    /**
     * Generates 4-digit numbers that numbers in
     * the digits are different from each other and
     * appends each number into a list.
     * @return generated list.
     */
    public ArrayList init_list(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i = 1000; i<10000; i++){
            if(areSameDigits(i)){
                list.add(i);
            }
        }
        return list;
    }

    /**
     * Randomly selects a number from possible
     * number list.
     * @return selected number from the list.
     */

    @Override
    public int prediction(){
        System.out.println("--Computer's Turn--");
        int predicted;
        int range = this.numbers.size();

        do {
            int index = (int) (Math.random()*range);
            predicted = getNumbers().get(index);

        }while(!areSameDigits(predicted));

        setPrediction(predicted);
        return predicted;
    }

    /**
     * selects random number between 1000 and 9999
     * @return selected number
     */

    @Override
    public int holdNumber(){
        System.out.println("***COMPUTER STARTS SELECTING****");
        int selected;

        do {
            selected = (int) (Math.random() * 9000) + 1000;
        }while(!areSameDigits(selected));
        setSelection(selected);
        return selected;
    }

    /** SETTERS & GETTERS */

    @Override
    public int getPrediction() {
        return prediction;
    }

    @Override
    public void setPrediction(int prediction) {
        this.prediction = prediction;
    }

    @Override
    public int getSelection() {
        return selection;
    }

    @Override
    public void setSelection(int selection) {
        this.selection = selection;
    }

    public ArrayList<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(ArrayList<Integer> numbers) {
        this.numbers = numbers;
    }
}
