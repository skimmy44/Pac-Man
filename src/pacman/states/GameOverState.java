package pacman.states;

import java.awt.Graphics;

import pacman.Handler;
import pacman.gfx.Assets;

/**
 * Game Over State.
 * 
 * - Starts when Pacman loses all of his lives.
 * - Nothing happens during this state other then showing 'Game Over' sign for some time.
 * - If a new record is set (high score is broken) New Record State is activated, 
 * otherwise, we go directly to Menu State and new game can start.
 * 
 * @author uross
 */

public class GameOverState extends State {

    private final int T = 3000;
    private long now, lastTime, timer;

    public GameOverState(Handler handler) {
        super(handler);
    }

    @Override
    public void start() {
        timer = 0;
        lastTime = System.currentTimeMillis();

        State.setCurrentState(this);
        
        stopBackgroundSounds();
    }

    @Override
    public void tick() {
        now = System.currentTimeMillis();
        timer += now - lastTime;
        lastTime = now;

        if (timer >= T) {
            if (handler.getGame().isNewRecord()) {
                handler.getGame().getNewRecordState().start();
            } else {
                handler.getGame().getMenuState().start();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        handler.getWorld().render(g, false);

        g.drawImage(Assets.game_over, 175, 275, 100, 10, null);

        renderScoreAndLives(g);
    }

}
