package pacman.states;

import java.awt.Graphics;
import pacman.Handler;
import pacman.gfx.Animation;
import pacman.gfx.Assets;

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
                (int) handler.getEntityManager().getPlayer().getX() - 4,
                (int) handler.getEntityManager().getPlayer().getY() - 4,
                20, 20, null);

        renderScoreAndLives(g);
    }

}
