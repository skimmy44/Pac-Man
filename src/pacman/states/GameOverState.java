package pacman.states;

import java.awt.Graphics;
import pacman.Handler;
import pacman.gfx.Assets;

public class GameOverState extends State {

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

        if (timer >= 3000) {
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
