package pacman.states;

import com.sun.javafx.scene.traversal.Direction;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import pacman.Handler;
import pacman.gfx.Animation;
import pacman.gfx.Assets;
import pacman.gfx.TextRenderer;

public class MenuState extends State {

    private boolean pressSpaceVisible = false;
    private boolean titleLoaded = false;

    private int xTitle, yTitle;
    private int xAnim, yAnim;

    private long now, lastTime, t0;
    private int delta;
    private final int v0 = 10;
    private final double a = 0.01;

    // Animations
    private Animation playerLeft, playerRight;
    private Animation[] ghostLeft, ghostRight;
    private Direction currentDirection = Direction.RIGHT;
    private final int speed = 2;

    public MenuState(Handler handler) {
        super(handler);
    }

    @Override
    public void start() {
        handler.getGame().newGame();

        titleLoaded = false;
        pressSpaceVisible = false;

        xTitle = 50;
        yTitle = -150;
        xAnim = -300;
        yAnim = 330;
        t0 = System.currentTimeMillis();
        lastTime = t0;
        delta = 0;

        // Animations
        playerLeft = new Animation(50, Assets.player_left);
        playerRight = new Animation(50, Assets.player_right);

        ghostLeft = new Animation[4];
        for (int i = 0; i < 4; i++) {
            ghostLeft[i] = new Animation(50, Assets.ghost_left[i]);
        }

        ghostRight = new Animation[4];
        for (int i = 0; i < 4; i++) {
            ghostRight[i] = new Animation(50, Assets.ghost_scared_1);
        }

        State.setCurrentState(this);
    }

    @Override
    public void tick() {
        now = System.currentTimeMillis();
        delta += now - lastTime;
        lastTime = now;

        if (!titleLoaded) {
            if (delta >= 1) {
                yTitle += v0;
                delta = 0;
            }
            if (yTitle >= 50) {
                double dy = a * (System.currentTimeMillis() - t0);
                if (dy < v0) {  // prevents title from going too much backwards which might happen if the program takes too much time to start
                    yTitle -= dy;
                }
            }
            if (yTitle >= 150) {
                titleLoaded = true;
            }
        } else {
            if (delta < 300) {
                pressSpaceVisible = true;
            } else if (delta >= 400) {
                delta = 0;
            }

            // Animations
            playerLeft.tick();
            playerRight.tick();
            for (int i = 0; i < 4; i++) {
                ghostLeft[i].tick();
                ghostRight[i].tick();
            }

            if (xAnim > handler.getWidth() + 100) {
                currentDirection = Direction.LEFT;
            }
            if (xAnim <= -300) {
                currentDirection = Direction.RIGHT;
            }

            if (currentDirection == Direction.RIGHT) {
                xAnim += speed;
            } else {
                xAnim -= speed;
            }
        }

        if (titleLoaded && handler.getKeyManager().space) {
            handler.getGame().getReadyState().start();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.title, xTitle, yTitle, 350, 90, null);

        TextRenderer.setLetterSize(16);
        TextRenderer.setDigitSize(16);

        if (pressSpaceVisible) {
            TextRenderer.drawText(g, "press space to start", 68, 270);
            pressSpaceVisible = false;
        }

        g.drawImage(getCurrentPlayerFrame(), xAnim, yAnim, 40, 40, null);
        for (int i = 0; i < 4; i++) {
            BufferedImage img;
            if (currentDirection == Direction.RIGHT) {
                img = ghostRight[i].getCurrentFrame();
            } else {
                img = ghostLeft[i].getCurrentFrame();
            }
            g.drawImage(img, xAnim + (i + 1) * 40, yAnim, 40, 40, null);
        }

        TextRenderer.drawText(g, "programmed by uros 2018", 40, 440);
        TextRenderer.drawText(g, "original game by namco 1980", 8, 465);

        renderScoreAndLives(g);
    }

    public BufferedImage getCurrentPlayerFrame() {
        if (currentDirection == Direction.RIGHT) {
            return playerRight.getCurrentFrame();
        } else {
            return playerLeft.getCurrentFrame();
        }
    }

}
