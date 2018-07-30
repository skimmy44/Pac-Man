package pacman.states;

import java.awt.Graphics;
import pacman.Handler;
import pacman.gfx.Assets;
import pacman.worlds.World;

public class ReadyState extends State {

    private long now, lastTime;
    private int timer;
    private final int T1 = 3000, T2 = 5000;

    private World world;

    public ReadyState(Handler handler) {
        super(handler);
        world = new World(handler, "/res/maps/map_test.txt");
        handler.setWorld(world);
    }

    public void start() {
        if (world.isCompleted()) {
            world = new World(handler, "/res/maps/map_test.txt");
            handler.setWorld(world);
        }

        timer = 0;
        lastTime = System.currentTimeMillis();

        State.setCurrentState(this);
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