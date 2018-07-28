package pacman.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import pacman.entities.Creature.Direction;

public class KeyManager implements KeyListener {

    public Direction dir = Direction.RIGHT;
    public boolean space = false;

    public KeyManager() {

    }

    public void tick() {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
            dir = Direction.UP;
        } else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
            dir = Direction.DOWN;
        } else if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            dir = Direction.LEFT;
        } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            dir = Direction.RIGHT;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            space = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            space = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
