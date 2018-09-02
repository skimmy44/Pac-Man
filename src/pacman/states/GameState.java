package pacman.states;

import java.awt.Graphics;

import pacman.Handler;

/**
 * Game State.
 * 
 * - This state starts after Ready State.
 * - This is when a game is actually played. There isn't much code here because
 * almost all the game logic is implemented in Player and Ghost classes.
 * - This state ends when Pacman dies or when a level is completed.
 * 
 * @author uross
 */

public class GameState extends State {

    public GameState(Handler handler) {
        super(handler);
    }

    @Override
    public void start() {
        State.setCurrentState(this);
    }

    @Override
    public void tick() {
        handler.getWorld().tick();
        tickScoreAndLives();
        tickBackgroundSounds();

        if (handler.getWorld().isCompleted()) {
            handler.getGame().getLevelCompletedState().start();
        }
    }

    @Override
    public void render(Graphics g) {
        handler.getWorld().render(g, true);
        renderScoreAndLives(g);
    }

}
