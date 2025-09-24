import Game.Game;

public class Application {
    private static Game game;

    public static void main(String[] args) {
        // Set AWT property if necessary
        System.setProperty("java.awt.headless", "false");

        // Initialize the game
        game = new Game();

        // Run the game
        Game.main(args);
    }
}
