package pacman.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import pacman.Handler;
import pacman.gfx.Animation;
import pacman.gfx.Assets;
import pacman.tiles.Tile;

public class Ghost extends Creature {

    public static int ghostsEaten = 0;

    public static enum Mode {
        CAGE, CHASE, SCATTER, SCARED, DIED
    }
    private Mode mode = Mode.CAGE;

    // Animations
    private Animation animUp, animDown, animLeft, animRight;
    private Animation animScared, scared1, scared2;

    /*
        id:
            0 - red (blinky)
            1 - pink (pinky)
            2 - blue (inky)
            3 - orange (clyde)
     */
    private final int id;

    // Target constants, for scatter and died mode
    private final int DEFAULT_X_TARGET, DEFAULT_Y_TARGET;
    private final int DEFAULT_X_CAGE = 13, DEFAULT_Y_CAGE = 11;

    // Time constants, in milliseconds
    private final int TIME_CAGE,
            TIME_CHASE_1 = 7000, TIME_SCATTER_2 = 27000,
            TIME_CHASE_2 = 34000, TIME_SCATTER_3 = 54000,
            TIME_CHASE_3 = 59000,
            TIME_SCARED_1 = 5000, TIME_SCARED_2 = 8000;

    private boolean outOfCage = false, startedEscape = false;

    private int xTarget, yTarget;
    private int xNext, yNext;
    
    private int xDied, yDied, indexDied, pointsDied;

    private Random randomizer;

    private Player pacman;

    private long now, lastTime = -1, timer, timerScared = 0;

    public Ghost(Handler handler, float x, float y, int id, Player pacman) {
        super(handler, x, y, DEFAULT_ENTITY_WIDTH, DEFAULT_ENTITY_HEIGHT);
        this.id = id;
        this.pacman = pacman;

        randomizer = new Random();

        currentDirection = Direction.UP;
        switch (id) {
            case 0:
                DEFAULT_X_TARGET = 25;
                DEFAULT_Y_TARGET = -3;
                TIME_CAGE = 0;
                currentDirection = Direction.RIGHT;
                break;
            case 1:
                DEFAULT_X_TARGET = 2;
                DEFAULT_Y_TARGET = -3;
                TIME_CAGE = 2000;
                break;
            case 2:
                DEFAULT_X_TARGET = 27;
                DEFAULT_Y_TARGET = 32;
                TIME_CAGE = 3000;
                break;
            default:
                DEFAULT_X_TARGET = 0;
                DEFAULT_Y_TARGET = 32;
                TIME_CAGE = 4000;
                break;
        }

        //xNext = getXTile() + 1;
        //yNext = getYTile();
        startAnimations();
    }

    @Override
    public void tick() {
        // Animations
        tickAnimations();

        // Ticking timers
        if (lastTime < 0) {
            lastTime = System.currentTimeMillis();
            timer = 0;
        } else {
            now = System.currentTimeMillis();
            timer += now - lastTime;
            timerScared += now - lastTime;
            lastTime = now;
        }

        // Timed changes between chase and scatter mode
        switchScatterChase();

        tickCageMode();
        tickScaredMode();
        tickDiedMode();

        // Deciding where to move
        retarget();
        turn();
        changeDirection();

        // Moving
        setMoves();
        move();
        checkBounds();
    }

    private void tickCageMode() {
        if (mode == Mode.CAGE) {
            if (timer >= TIME_CAGE || getYTile() <= 12) {
                startedEscape = true;
            }
            if (startedEscape) {
                handler.getWorld().unlockCage();
                outOfCage |= getYTile() <= 12;
                if (outOfCage) {
                    handler.getWorld().lockCage();
                    setMode(Mode.SCATTER);
                }
            }
        }
    }

    private void tickScaredMode() {
        if (mode == Mode.SCARED) {
            if (timerScared >= TIME_SCARED_2) {
                speed /= 0.9;
                setMode(Mode.CHASE);
            } else if (timerScared >= TIME_SCARED_1) {
                animScared = timerScared % 200 < 100 ? scared2 : scared1;
            }
        }
    }

    private void tickDiedMode() {
        if (mode == Mode.DIED) {
            if (getXTile() == xTarget && getYTile() == yTarget) {   // returned to cage
                speed /= 3;
                setMode(Mode.CHASE);
            }
        }
    }

    public void enterScaredMode() {
        if (mode != Mode.CAGE) {
            Ghost.ghostsEaten = 0;
            timerScared = 0;
            speed *= 0.9;
            setMode(Mode.SCARED);
        }
    }

    public void enterDiedMode() {
        if (mode != Mode.DIED) {
            indexDied = ghostsEaten;
            xDied = getXTile();
            yDied = getYTile();
            pointsDied = (int) (200 * Math.pow(2, ghostsEaten++));
            handler.getGame().score(pointsDied);
            
            speed /= 0.9;
            speed *= 3;
            setMode(Mode.DIED);
        }
    }

    private void switchScatterChase() {
        if (mode == Mode.SCATTER || mode == Mode.CHASE) {
            if (timer < TIME_CHASE_1) {
                setMode(Mode.SCATTER);
            } else if (timer < TIME_SCATTER_2) {
                setMode(Mode.CHASE);
            } else if (timer < TIME_CHASE_2) {
                setMode(Mode.SCATTER);
            } else if (timer < TIME_SCATTER_3) {
                setMode(Mode.CHASE);
            } else if (timer < TIME_CHASE_3) {
                setMode(Mode.SCATTER);
            } else {
                setMode(Mode.SCATTER);
            }
        }
    }

    private void retarget() {
        if (mode == Mode.SCATTER) {
            xTarget = DEFAULT_X_TARGET;
            yTarget = DEFAULT_Y_TARGET;
            return;
        }
        if (mode == Mode.DIED) {
            xTarget = DEFAULT_X_CAGE;
            yTarget = DEFAULT_Y_CAGE;
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
            return;
        }
        if (mode == Mode.SCARED) {
            xTarget = randomizer.nextInt(28);
            yTarget = randomizer.nextInt(31);
        }
    }

    private void setNext(Direction dir) {
        if (dir == null) {
            xNext = getXTile();
            yNext = getYTile();
            return;
        }
        switch (dir) {
            case UP:
                yNext = getYTile() - 1;
                xNext = getXTile();
                break;
            case DOWN:
                yNext = getYTile() + 1;
                xNext = getXTile();
                break;
            case LEFT:
                xNext = getXTile() - 1;
                if (xNext < 0) {
                    xNext = 27;
                }
                yNext = getYTile();
                break;
            case RIGHT:
                xNext = getXTile() + 1;
                if (xNext >= 28) {
                    xNext = 0;
                }
                yNext = getYTile();
                break;
        }
    }

    private void turn() {
        if (mode == Mode.CAGE) {
            if (!canMove()) {
                nextDirection = (currentDirection == Direction.UP) ? Direction.DOWN : Direction.UP;
                setNext(nextDirection);
            }
            return;
        }

        if (!(xNext == getXTile() && yNext == getYTile())) {
//            if (currentDirection == null) {
//                currentDirection = Direction.RIGHT;
//            }
            setNext(currentDirection);
            return;
        }

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

        double[] dist = new double[4];
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

        switch (index) {
            case 0:
                nextDirection = Direction.UP;
                break;
            case 1:
                nextDirection = Direction.DOWN;
                break;
            case 2:
                nextDirection = Direction.LEFT;
                break;
            case 3:
                nextDirection = Direction.RIGHT;
                break;
        }

        if (nextDirection != currentDirection) {
            setNext(nextDirection);
        } else {
            setNext(currentDirection);
        }
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
        
        if (mode == Mode.DIED) {
            g.drawImage(Assets.points[indexDied], xDied * Tile.TILE_WIDTH - 8, yDied * Tile.TILE_HEIGHT - 8, 32, 32, null);
        }
    }

    private void startAnimations() {
        animUp = new Animation(50, Assets.ghost_up[id]);
        animDown = new Animation(50, Assets.ghost_down[id]);
        animRight = new Animation(50, Assets.ghost_right[id]);
        animLeft = new Animation(50, Assets.ghost_left[id]);

        scared1 = new Animation(50, Assets.ghost_scared_1);
        scared2 = new Animation(50, Assets.ghost_scared_2);
        animScared = scared1;
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
            return animScared.getCurrentFrame();
        }
        if (mode == Mode.DIED) {
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
