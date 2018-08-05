package pacman.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import pacman.Handler;
import pacman.gfx.Animation;
import pacman.gfx.Assets;

public class Ghost extends Creature {

    public static enum Mode {
        CAGE, CHASE, SCATTER, SCARED, DIED
    }
    private Mode mode = Mode.CHASE;

    // Animations
    private Animation animUp, animDown, animLeft, animRight;
    private Animation scared1, scared2;

    /*
    id:
        0 - red (blinky)
        1 - pink (pinky)
        2 - blue (inky)
        3 - orange (clyde)
     */
    private final int id;

    private final int DEFAULT_X_TARGET, DEFAULT_Y_TARGET;
    private int xTarget, yTarget;
    private int xNext, yNext;

    //private Direction previousDirection = Direction.LEFT;
    private Player pacman;

    public Ghost(Handler handler, float x, float y, int id, Player pacman) {
        super(handler, x, y, DEFAULT_ENTITY_WIDTH, DEFAULT_ENTITY_HEIGHT);
        this.id = id;
        this.pacman = pacman;

        switch (id) {
            case 0:
                DEFAULT_X_TARGET = 25;
                DEFAULT_Y_TARGET = -3;
                break;
            case 1:
                DEFAULT_X_TARGET = 2;
                DEFAULT_Y_TARGET = -3;
                break;
            case 2:
                DEFAULT_X_TARGET = 27;
                DEFAULT_Y_TARGET = 32;
                break;
            default:
                DEFAULT_X_TARGET = 0;
                DEFAULT_Y_TARGET = 32;
                break;
        }

        xNext = getXTile() + 1;
        yNext = getYTile();

        startAnimations();
    }

    @Override
    public void tick() {
        // Animations
        tickAnimations();

        retarget();
        turn();
        changeDirection();

        setMoves();
        move();
        checkBounds();
    }

    private void retarget() {
        if (mode == Mode.SCATTER) {
            xTarget = DEFAULT_X_TARGET;
            yTarget = DEFAULT_Y_TARGET;
            return;
        }
        if (mode == Mode.CHASE) {
            switch (id) {
                case 0:
                    xTarget = pacman.getXTile();
                    yTarget = pacman.getYTile();
                    break;
                case 1:
                    switch (pacman.currentDirection) {
                        case UP:
                            xTarget = pacman.getXTile() - 4;
                            yTarget = pacman.getYTile() - 4;
                            break;
                        case DOWN:
                            xTarget = pacman.getXTile();
                            yTarget = pacman.getYTile() + 4;
                            break;
                        case LEFT:
                            xTarget = pacman.getXTile() - 4;
                            yTarget = pacman.getYTile();
                            break;
                        case RIGHT:
                            xTarget = pacman.getXTile() + 4;
                            yTarget = pacman.getYTile();
                            break;
                    }
                    break;
                case 2:
                    switch (pacman.currentDirection) {
                        case UP:
                            xTarget = pacman.getXTile() - 2;
                            yTarget = pacman.getYTile() - 2;
                            break;
                        case DOWN:
                            xTarget = pacman.getXTile();
                            yTarget = pacman.getYTile() + 2;
                            break;
                        case LEFT:
                            xTarget = pacman.getXTile() - 2;
                            yTarget = pacman.getYTile();
                            break;
                        case RIGHT:
                            xTarget = pacman.getXTile() + 2;
                            yTarget = pacman.getYTile();
                            break;
                    }
                    xTarget += xTarget - handler.getEntityManager().getBlinky().getXTile();
                    yTarget += yTarget - handler.getEntityManager().getBlinky().getYTile();
                    break;
                case 3:
                    if (distance(pacman.getXTile(), pacman.getYTile()) > 8) {
                        xTarget = pacman.getXTile();
                        yTarget = pacman.getYTile();
                    } else {
                        xTarget = DEFAULT_X_TARGET;
                        yTarget = DEFAULT_Y_TARGET;
                    }
                    break;
            }
        }
    }

    private void setNext(Direction dir) {
        if (xNext == getXTile() && yNext == getYTile()) {
            switch (dir) {
                case UP:
                    yNext = getYTile() - 1;
                    break;
                case DOWN:
                    yNext = getYTile() + 1;
                    break;
                case LEFT:
                    xNext = getXTile() - 1;
                    if (xNext < 0) {
                        xNext = 27;
                    }
                    break;
                case RIGHT:
                    xNext = getXTile() + 1;
                    if (xNext >= 28) {
                        xNext = 0;
                    }
                    break;
            }
        }
    }

    private void turn() {
        //if (mode == Mode.CHASE) {
        

        if (!(xNext == getXTile() && yNext == getYTile())) {
            return;
        }

        double[] dist = new double[4];
        boolean[] possibleMove = new boolean[4];
        possibleMove[0] = !handler.getWorld().getTile(getXTile(), getYTile() - 1).isSolid();    // up
        possibleMove[1] = !handler.getWorld().getTile(getXTile(), getYTile() + 1).isSolid();    // down
        possibleMove[2] = !handler.getWorld().getTile(getXTile() - 1, getYTile()).isSolid();    // left
        possibleMove[3] = !handler.getWorld().getTile(getXTile() + 1, getYTile()).isSolid();    // right

        switch (currentDirection) {
            case UP:
                possibleMove[1] = false;
                break;
            case DOWN:
                possibleMove[0] = false;
                break;
            case LEFT:
                possibleMove[3] = false;
                break;
            case RIGHT:
                possibleMove[2] = false;
                break;
        }

        dist[0] = distance(getXTile(), getYTile() - 1, xTarget, yTarget);   // up
        dist[1] = distance(getXTile(), getYTile() + 1, xTarget, yTarget);   // down
        dist[2] = distance(getXTile() - 1, getYTile(), xTarget, yTarget);   // left
        dist[3] = distance(getXTile() + 1, getYTile(), xTarget, yTarget);   // right
        
        ArrayList<Integer> possibleIndexes = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (possibleMove[i]) {
                possibleIndexes.add(i);
            }
        }
        
        int index = -1;
        double min = 1000;
        for (int i : possibleIndexes) {
            if (dist[i] < min) {
                index = i;
                min = dist[i];
            }
        }
        
        if (index == -1) {  // no change in direction
            return;
        }
        
        System.out.println("Position: " + getXTile() + ", " + getYTile());
        System.out.println("current: " + currentDirection);
        System.out.println("distances");
        for (int i = 0; i < 4; i++) {
            System.out.println("    " + dist[i]);
        }
        System.out.println("index " + index);
        
        
        switch (index) {
            case 0:
                nextDirection = Direction.UP;
                //currentDirection = Direction.UP;
                break;
            case 1:
                nextDirection = Direction.DOWN;
                //currentDirection = Direction.DOWN;
                break;
            case 2:
                nextDirection = Direction.LEFT;
                //currentDirection = Direction.LEFT;
                break;
            case 3:
                nextDirection = Direction.RIGHT;
                //currentDirection = Direction.RIGHT;
                break;
        }
        
        System.out.println("next: " + nextDirection + "\n");
        
        if (nextDirection != currentDirection) {
            setNext(nextDirection);
        } else {
            setNext(currentDirection);
        }
        System.out.println("Next position: " + xNext + ", " + yNext);
    }

    private double distance(int x, int y) {
        return distance(x, y, getXTile(), getYTile());
    }

    private double distance(int x1, int y1, int x2, int y2) {
        return Math.hypot(x1 - x2, y1 - y2);
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
        if (currentDirection == null) {
            return animUp.getCurrentFrame();
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
            default:
                return animUp.getCurrentFrame();
        }
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

}
