package pacman.states;

import java.awt.Graphics;
import pacman.Handler;
import pacman.entities.Ghost;
import pacman.entities.Ghost.Mode;
import pacman.gfx.Assets;

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
