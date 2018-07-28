package pacman.states;

import java.awt.Graphics;
import pacman.Handler;
import pacman.gfx.Assets;

public class ReadyState extends State {

    private long now, lastTime;
    private int timer;
    private final int T1 = 3000, T2 = 5000;

    public ReadyState(Handler handler) {
        super(handler);
        timer = 0;
        lastTime = -1;
    }

    @Override
    public void tick() {
        if (lastTime == -1) {
            lastTime = System.currentTimeMillis();
        } else {
            now = System.currentTimeMillis();
            timer += now - lastTime;
            lastTime = now;
        }

        if (timer >= T2) {
            State.setCurrentState(handler.getGame().getGameState());
        } else if (timer >= T1) {

        }
    }

    @Override
    public void render(Graphics g) {
        handler.getWorld().render(g);
        
        g.drawImage(Assets.ready, 185, 275, 80, 10, null);
        
        renderScoreAndLives(g);
    }

}
