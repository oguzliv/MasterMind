/**
 * Driver class of the game. Creates a computer and a human
 * objects then by using these two objects creates a game
 * logic object.
 */
public class Main {
    public static void main(String[] args) {
        Computer pc = new Computer();
        Human hum = new Human();
        GameLogic game = new GameLogic(hum,pc);

        game.Play();

    }
}
