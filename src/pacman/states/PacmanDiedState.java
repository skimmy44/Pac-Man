package pacman.states;

import java.awt.Graphics;

import pacman.Handler;
import pacman.entities.Entity;
import pacman.gfx.Animation;
import pacman.gfx.Assets;

/**
 * Pacman Died State.
 * 
 * - Becomes active when Pacman dies (obviously).
 * - During this state we only play a sound and show an animation of Pacman dying.
 * - After this state we start the Ready State if a player has some lives left, 
 * otherwise the Game Over State is activated.
 * 
 * @author uross
 */

public class PacmanDiedState extends State {

    private Animation animation;

    private long now, lastTime, timer;
    private final int FRAME_LENGTH = 100;

    public PacmanDiedState(Handler handler) {
        super(handler);
    }

    @Override
    public void start() {
        animation = new Animation(FRAME_LENGTH, Assets.player_eaten);

        timer = 0;
        lastTime = System.currentTimeMillis();

        State.setCurrentState(this);
        
        stopBackgroundSounds();
        Assets.sound_pacman_died.play();
    }

    @Override
    public void tick() {
        if (timer < FRAME_LENGTH * Assets.player_eaten.length) {
            animation.tick();
        }

        now = System.currentTimeMillis();
        timer += now - lastTime;
        lastTime = now;

        if (timer >= FRAME_LENGTH * Assets.player_eaten.length + 500) {
            handler.getGame().setLives(handler.getGame().getLives() - 1);
            if (handler.getGame().getLives() > 0) {
                handler.getGame().getReadyState().start();
            } else {
                handler.getGame().getGameOverState().start();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        handler.getWorld().render(g, false);

        g.drawImage(animation.getCurrentFrame(),
                (int) handler.getEntityManager().getPlayer().getX() - 8,
                (int) handler.getEntityManager().getPlayer().getY() - 8,
                Entity.DEFAULT_ENTITY_WIDTH, Entity.DEFAULT_ENTITY_HEIGHT, null);

        renderScoreAndLives(g);
    }

}
