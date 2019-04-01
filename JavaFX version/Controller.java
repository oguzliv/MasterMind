/**
 * Controller class of the game. Graphics and
 * game logic are combine in this class. Appropriate
 * game logic is provided in this class. Interactivity and
 * responsiveness of the game is achieved in this class.
 */
package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;


public class Controller {
    @FXML
    Label TurnLabel,ComputerPrediction,ComputerHint,WinnerLabel;
    @FXML
    TextArea StatusArea;
    @FXML
    TextField PlayerPrediction,PosField,NegField;
    @FXML
    Button HintButton,PredictButton,StartButton;

    private ArrayList<Integer> AllNumbers;
    private int computer_selection,computer_prediction,user_prediction;
    private Hint user_to_computer_hint;
    private int turn;
    private boolean userTurn;
    private boolean computerTurn;


    /**
     * This method is a native FXML method. Initializes the
     * graphics and variables.
     */
    public void initialize (){
        AllNumbers = Possible_4_Digit_Numbers();
        user_to_computer_hint = new Hint(0,0);
        turn = 0;
        userTurn = false;
        computerTurn = false;
        ComputerPrediction.setVisible(false);
        ComputerHint.setVisible(false);
        WinnerLabel.setVisible(false);
        PredictButton.setDisable(true);
        HintButton.setDisable(true);
        PlayerPrediction.setDisable(true);
        PosField.setDisable(true);
        NegField.setDisable(true);

    }


    /**
     * Event handler of the Start Button. Starts the game logic and
     * game graphics according to whether player or computer stars.
     */
    public void Play(){
        int t = (int)(Math.random()*2);
        computer_selection = computerSelects();
        turn = 0;

        if(t == 1){
            userTurn= true;
            computerTurn = false;
            StatusArea.setText("User Starts...");
            PredictButton.setDisable(false);
            PlayerPrediction.setDisable(false);
            TurnLabel.setText(Integer.toString(turn));
        }
        if(t == 0){
            userTurn= false;
            computerTurn = true;
            StatusArea.setText("Computer\nStarts...");
            computerPredicts();
            PosField.setDisable(false);
            NegField.setDisable(false);
            HintButton.setDisable(false);
        }
    }

    /**
     * Event handler of the Hint button. User hints to
     * computer by using this function. Also this function
     * changes the turn from computer to player to get player
     * prediction. Also handles proper user interface actions.
     */
    public void userHintsToComputer(){
        int pos = -99;
        int neg = -99;

        try{
            pos = Integer.parseInt(PosField.getText());
            neg = Integer.parseInt(NegField.getText());
        }catch(Exception e){

        }
        System.out.println("POS :" + pos + " NEG : " + neg);
        int sum = pos + (neg * -1);
        if(pos == -99 && neg == -99)
            StatusArea.setText("Enter numeric values\nfor POS and NEG");
        else if((pos < 0 || pos > 4) || (neg > 0 || neg < -4) || (sum > 4))
            StatusArea.setText("INVALID HINT !\nPOS values: 0,1,2,3,4\nNEG values: -1,-2,-3,-4\nPOS+NEG must be\nless than 4");
        else{
            user_to_computer_hint.setNegative(neg);
            user_to_computer_hint.setPositive(pos);
            computerPrunes(user_to_computer_hint);

            PosField.setDisable(true);
            NegField.setDisable(true);
            HintButton.setDisable(true);
            PredictButton.setDisable(false);
            PlayerPrediction.setDisable(false);
        }

        if(pos == 4 && neg == 0){
            WinnerLabel.setVisible(true);
            WinnerLabel.setText("!!COMPUTER!!");

            PredictButton.setDisable(true);
            HintButton.setDisable(true);
            PlayerPrediction.setDisable(true);
            PosField.setDisable(true);
            NegField.setDisable(true);
        }
    }

    /**
     * Compares human prediction and computer selection
     * and displays the result on the graphics.
     */
    public void computerHintsToHuman(){
        System.out.println("Computer hints");
        int userPrediction = user_prediction;
        Hint hintToPC = answer_hint(userPrediction,computer_selection);
        int pos = hintToPC.getPositive();
        int neg = hintToPC.getNegative();

        ComputerHint.setVisible(true);
        ComputerHint.setText("POS : " + pos + " NEG : " + neg);
    }

    /**
     * Prunes possible numbers list according to parameter
     * @param human_hint a Hint object
     */
    public void computerPrunes(Hint human_hint){
        System.out.println("Computer prunes");
        int pos = human_hint.getPositive();
        int neg = human_hint.getNegative();

        for(int i = 0; i < AllNumbers.size(); i++){
            Hint hint = answer_hint(computer_prediction,AllNumbers.get(i));
            int p = hint.getPositive();
            int n = hint.getNegative();

            if((pos != p) || (neg != n)){
                AllNumbers.remove(i--);
            }
        }
    }

    /**
     * Selects random number from possible numbers list and enables
     * disables UI components.
     */
    public void computerPredicts(){
        userTurn = true;
        computerTurn = false;

        int prediction = computerSelects();
        String p = Integer.toString(prediction);
        computer_prediction = prediction;
        ComputerPrediction.setVisible(true);
        ComputerPrediction.setText(p);

    }

    /**
     * Holds a number from possible number list.
     * @return
     */
    public int computerSelects(){
        int range = AllNumbers.size();
        int index = (int)(Math.random() * range);
        int selection = AllNumbers.get(index);
        return selection;
    }

    /**
     * Event Handler of Predict button. Migrates user's prediction data
     * into logic level. Also changes turn from user to computer. Enables disables
     * some UI components for the sake of user-friendly code.
     * @return
     */
    public int userPredicts(){
        int prediction = 0 ;
        try {
            prediction = Integer.parseInt(PlayerPrediction.getText());
        }catch(Exception e){

        }
        user_prediction = prediction;
        if(prediction == 0)
            StatusArea.setText("Non-numeric value\nplease enter\n4-digit number");

        else if(lengthChecker(prediction) == 0)
            StatusArea.setText("Enter a 4-digit number");

        else if(lengthChecker(prediction) == 1)

            if(!areSameDigits(prediction))
                StatusArea.setText("Enter a number\nwith different digits");
        else
            StatusArea.setText("Successful prediction");

        if((PlayerPrediction.getText().equals(Integer.toString(computer_selection)))) {
            WinnerLabel.setVisible(true);
            WinnerLabel.setText("!!PLAYER!!");
        }

        if(AllNumbers.size() == 0 ){
            StatusArea.setText("Wrong hint at\none of\nprevious turns.\nStart game over");
        }

        turn++;
        TurnLabel.setText(Integer.toString(turn));
        if(userTurn){
            userTurn = false;
            computerTurn = true;
            computerPredicts();
            computerHintsToHuman();
            PosField.setDisable(false);
            NegField.setDisable(false);
            HintButton.setDisable(false);

            PredictButton.setDisable(true);
            PlayerPrediction.setDisable(true);
        }

        return prediction;
    }

    /**
     * Helper function that checks length of number.
     * @param number an integer
     * @return 1 if length is 4, 0 if not
     */
    public int lengthChecker(int number){
        if(number < 10000 && number >= 1000)
            return 1;
        else
            return 0;
    }

    /**
     * Checks if given number has same numbers in its digits.
     * @param num an integer to be checked
     * @return true if digits are not same, else false.
     */
    public boolean areSameDigits(int num){
        String number = Integer.toString(num);
        char first,second,third,fourth;
        first = number.charAt(0);
        second = number.charAt(1);
        third = number.charAt(2);
        fourth = number.charAt(3);

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

    /**
     * Generates possible 4-digit numbers that has different numbers
     * in its each digit.
     * @return list that contains possible numbers.
     */
    public ArrayList<Integer> Possible_4_Digit_Numbers(){
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 1000 ; i < 10000; i++){
            if(areSameDigits(i))
                list.add(i);
        }
        return list;
    }

    /**
     * Compares prediction and selection
     * @param prediction one operand for comparison as an integer
     * @param selection one operand for comparison as an integer
     * @return result of a prediction as a Hint object.
     */
    public Hint answer_hint(int prediction, int selection){
        int neg_hint = 0,pos_hint = 0;
        Hint hint = new Hint(pos_hint,neg_hint);
        String str_prediction = Integer.toString(prediction);
        String str_selection = Integer.toString(selection);

        for(int i = 0 ; i < 4 ; i++){
            int pos = str_prediction.indexOf(str_selection.charAt(i));
            if (pos >= 0){
                if (pos == i)
                    pos_hint++;
                else
                    neg_hint--;
            }
        }
        hint.setNegative(neg_hint);
        hint.setPositive(pos_hint);
        return hint;
    }
}