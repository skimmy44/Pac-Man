package pacman;

/**
 * Main class, game starts here.
 * 
 * @author uross
 */

public class Launcher {

    public static void main(String[] args) {
        new Game("Pacman", 448, 496 + 55 + 30).start();
    }
    
}
