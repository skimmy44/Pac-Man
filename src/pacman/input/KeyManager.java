package pacman.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import pacman.entities.Creature.Direction;

public class KeyManager implements KeyListener {

    public Direction dir = Direction.UP;

    public KeyManager() {

    }

    public void tick() {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            dir = Direction.UP;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            dir = Direction.DOWN;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            dir = Direction.LEFT;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            dir = Direction.RIGHT;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
