package pacman.states;

import java.awt.Graphics;

import pacman.Handler;
import pacman.gfx.Assets;
import pacman.worlds.World;

/**
 * Ready State.
 * 
 * - Becomes active after Menu State when space is pressed, when a level is completed
 * or when Pacman dies but it still has some lives left.
 * - This is when a player gets ready for a game. Intro theme is played, new level is
 * loaded if needed and a game can start.
 * - After this state comes the Game State.
 * 
 * @author uross
 */

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
        
        stopBackgroundSounds();
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
