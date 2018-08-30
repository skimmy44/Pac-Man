package pacman.states;

import java.awt.Graphics;
import pacman.Handler;
import pacman.gfx.Assets;
import pacman.sounds.Sound;
import pacman.worlds.World;

public class ReadyState extends State {

    private long now, lastTime;
    private int timer;
    private final int T1 = 4000, T2 = 5000;

    public ReadyState(Handler handler) {
        super(handler);
    }

    @Override
    public void start() {
        if (handler.getWorld().isCompleted()) {
            handler.setWorld(new World(handler, "/res/maps/map.txt"));
        } else {
            handler.getWorld().createEntityManager();
        }

        timer = 0;
        lastTime = System.currentTimeMillis();

        State.setCurrentState(this);
        
        Assets.sound_beginning.play();
    }

    @Override
    public void tick() {
        now = System.currentTimeMillis();
        timer += now - lastTime;
        lastTime = now;

        if (timer >= T2) {
            handler.getGame().getGameState().start();
        }
    }

    @Override
    public void render(Graphics g) {
        if (timer >= T1) {
            handler.getWorld().render(g, true);
        } else {
            handler.getWorld().render(g, false);
        }

        g.drawImage(Assets.ready, 185, 275, 80, 10, null);

        renderScoreAndLives(g);
    }

}
