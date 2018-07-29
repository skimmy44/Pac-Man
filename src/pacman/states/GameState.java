package pacman.states;

import java.awt.Graphics;
import pacman.Handler;
import pacman.worlds.World;

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
