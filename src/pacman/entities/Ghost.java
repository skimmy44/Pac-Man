package pacman.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import pacman.Handler;
import pacman.gfx.Animation;
import pacman.gfx.Assets;

public class Ghost extends Creature {

    public static enum Mode {
        CAGE, CHASE, SCARED, DIED
    }
    private Mode mode = Mode.CHASE;

    // Animations
    private Animation animUp, animDown, animLeft, animRight;
    private Animation scared1, scared2;

    private int id;

    public Ghost(Handler handler, float x, float y, int id) {
        super(handler, x, y, DEFAULT_ENTITY_WIDTH, DEFAULT_ENTITY_HEIGHT);
        this.id = id;

        startAnimations();
    }

    @Override
    public void tick() {
        // Animations
        tickAnimations();

        move();
        checkBounds();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(), (int) x - 8, (int) y - 8, width, height, null);
    }

    private void startAnimations() {
        animUp = new Animation(50, Assets.ghost_up[id]);
        animDown = new Animation(50, Assets.ghost_down[id]);
        animRight = new Animation(50, Assets.ghost_right[id]);
        animLeft = new Animation(50, Assets.ghost_left[id]);

        scared1 = new Animation(50, Assets.ghost_scared_1);
        scared2 = new Animation(50, Assets.ghost_scared_2);
    }

    private void tickAnimations() {
        animUp.tick();
        animDown.tick();
        animLeft.tick();
        animRight.tick();
        scared1.tick();
        scared2.tick();
    }

    private BufferedImage getCurrentAnimationFrame() {
        if (mode == Mode.SCARED) {
            return scared1.getCurrentFrame();
        } else if (mode == Mode.DIED) {
            switch (currentDirection) {
                case UP:
                    return Assets.ghost_eaten[2];
                case DOWN:
                    return Assets.ghost_eaten[3];
                case LEFT:
                    return Assets.ghost_eaten[1];
                case RIGHT:
                    return Assets.ghost_eaten[0];
            }
        }
        switch (currentDirection) {
            case UP:
                return animUp.getCurrentFrame();
            case DOWN:
                return animDown.getCurrentFrame();
            case LEFT:
                return animLeft.getCurrentFrame();
            case RIGHT:
                return animRight.getCurrentFrame();
        }
        return animUp.getCurrentFrame();
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

}
