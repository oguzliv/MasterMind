/**
 * This class consists of whole game logic by combining
 * Human, Hint and computer objects.
 */
public class GameLogic {

    private Human hum;
    private Computer pc;
    private Hint human_hint,pc_hint;

    private int human_selection,pc_selection;

    /**
     * Constructor game object
     * @param hum takes an Human object to assign it
     * @param pc takes a Computer object assign it
     */
    public GameLogic(Human hum, Computer pc){
        this.hum = hum;
        this.pc = pc;

        human_hint = new Hint(0,0);
        pc_hint = new Hint(0,0);

        human_selection = hum.holdNumber();
        pc_selection = pc.holdNumber();
    }

    /**
     * Whole game functionality is combined here
     * Mostly turn logic and appropriate messages
     * for the game play are achieved here.
     */
    public void Play(){

        while(getPc_hint().getPositive() < 4 && getHuman_hint().getPositive() < 4){
            human_predict();
            System.out.println("HUMAN PREDICTION:\t" + getHum().getPrediction()+"\tPOS:\t"+getHuman_hint().getPositive()+"\tNEG:\t"+getHuman_hint().getNegative());
            computer_predict();
            System.out.println("COMPUTER PREDICTION:\t" + getPc().getPrediction()+"\tPOS:\t"+getPc_hint().getPositive()+"\tNEG:\t"+getPc_hint().getNegative());
            System.out.println("CONDITION : " + (getPc_hint().getPositive() == 4 ||getHuman_hint().getPositive() == 4));
        }
        if(getPc_hint().getPositive() < getHuman_hint().getPositive())
            System.out.println("HUMAN WON!!!!!");
        else if(getPc_hint().getPositive() > getHuman_hint().getPositive())
            System.out.println("COMPUTER WON!!!!");
        else
            System.out.println("HONOURS EVEN!");

        System.out.println("Computer number:\t"+getPc_selection());
        System.out.println("Human number:\t"+getHuman_selection());
    }

    /**
     * Compares human prediction and pc selections
     * @return a Hint object according to comparison result
     */
    public Hint human_predict(){

        Hint human_hint_result = answer_hint(this.getHum().prediction(),this.getPc().getSelection());

        human_hint.setPositive(human_hint_result.getPositive());
        human_hint.setNegative(human_hint_result.getNegative());


        return human_hint;
    }

    /**
     * Computer prediction mechanism for gameplay logic.
     */
    public void computer_predict(){

        Hint pc_hint_result = answer_hint(this.pc.prediction(),getHum().getSelection());
        int pos_res = pc_hint_result.getPositive();
        int neg_res = pc_hint_result.getNegative();

        int count = 0;

        /**
         * Below loop is for pruning numbers that are suitable for further predictions
         * of computer.
         */

        for(int i = 0 ;  i< this.pc.getNumbers().size(); i++){
            Hint possible_hints = answer_hint(getPc().getPrediction(),this.pc.getNumbers().get(i));
            int pos = possible_hints.getPositive();
            int neg = possible_hints.getNegative();
            if( (pos_res != pos) || (neg_res != neg)){
                count++;
                this.pc.getNumbers().remove(i--);
            }
        }
        setPc_hint(pc_hint_result);
    }

    /**
     * compares prediction and selection as integers
     * @param prediction one operand of comparison as an integer
     * @param selection oe operand of comparison as an integer
     * @return a Hint object as a result of comparison
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

    /** GETTERS AND SETTERS */

    public Human getHum() {
        return hum;
    }

    public Computer getPc() {
        return pc;
    }

    public int getHuman_selection() {
        return human_selection;
    }

    public int getPc_selection() {
        return pc_selection;
    }

    public Hint getHuman_hint() {
        return human_hint;
    }

    public Hint getPc_hint() {
        return pc_hint;
    }

    public void setPc_hint(Hint pc_hint) {
        this.pc_hint = pc_hint;
    }
}
