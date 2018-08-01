package pacman.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import pacman.Handler;
import pacman.gfx.Animation;
import pacman.gfx.Assets;
import pacman.states.State;
import pacman.tiles.Tile;

public class Player extends Creature {

    // Animations
    private Animation animUp, animDown, animLeft, animRight;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, DEFAULT_ENTITY_WIDTH, DEFAULT_ENTITY_HEIGHT);

        // Animations
        animDown = new Animation(50, Assets.player_down);
        animUp = new Animation(50, Assets.player_up);
        animLeft = new Animation(50, Assets.player_left);
        animRight = new Animation(50, Assets.player_right);
    }

    @Override
    public void tick() {
        // Animations
        animDown.tick();
        animUp.tick();
        animLeft.tick();
        animRight.tick();

        getInput();
        changeDirection();
        setMoves();
        move();
        checkBounds();
        eat();
        
        if (collisionWithGhost()) {     // pacman died
            handler.getGame().getPacmanDiedState().start();
        }
    }

    private void getInput() {
        switch (handler.getKeyManager().dir) {
            case UP:
                nextDirection = Direction.UP;
                break;
            case DOWN:
                nextDirection = Direction.DOWN;
                break;
            case LEFT:
                nextDirection = Direction.LEFT;
                break;
            case RIGHT:
                nextDirection = Direction.RIGHT;
                break;
        }
    }

    private void changeDirection() {
        switch (nextDirection) {
            case UP:
                if (!collisionWithTile(getXTile(), getYTile() - 1)) {
                    switch (currentDirection) {
                        case RIGHT:
                            if (x < getXTile() * Tile.TILE_WIDTH) {
                                break;
                            }
                        case LEFT:
                            if (x > getXTile() * Tile.TILE_WIDTH) {
                                break;
                            }
                        default:
                            currentDirection = Direction.UP;
                            nextDirection = null;
                            break;
                    }
                }
                break;
            case DOWN:
                if (!collisionWithTile(getXTile(), getYTile() + 1)) {
                    switch (currentDirection) {
                        case RIGHT:
                            if (x < getXTile() * Tile.TILE_WIDTH) {
                                break;
                            }
                        case LEFT:
                            if (x > getXTile() * Tile.TILE_WIDTH) {
                                break;
                            }
                        default:
                            currentDirection = Direction.DOWN;
                            nextDirection = null;
                            break;
                    }
                }
                break;
            case LEFT:
                if (!collisionWithTile(getXTile() - 1, getYTile())) {
                    switch (currentDirection) {
                        case UP:
                            if (y > getYTile() * Tile.TILE_HEIGHT) {
                                break;
                            }
                        case DOWN:
                            if (y < getYTile() * Tile.TILE_HEIGHT) {
                                break;
                            }
                        default:
                            currentDirection = Direction.LEFT;
                            nextDirection = null;
                            break;
                    }
                }
                break;
            case RIGHT:
                if (!collisionWithTile(getXTile() + 1, getYTile())) {
                    switch (currentDirection) {
                        case UP:
                            if (y > getYTile() * Tile.TILE_HEIGHT) {
                                break;
                            }
                        case DOWN:
                            if (y < getYTile() * Tile.TILE_HEIGHT) {
                                break;
                            }
                        default:
                            currentDirection = Direction.RIGHT;
                            nextDirection = null;
                            break;
                    }
                }
                break;
        }
    }

    private void eat() {
        if (handler.getWorld().getTile(getXTile(), getYTile()).isEatable()) {
            handler.getGame().score(handler.getWorld().eatTile(getXTile(), getYTile()));
        }
    }

    private boolean collisionWithGhost() {
        //int x = getXTile(), y = getYTile();

        for (Ghost g : handler.getEntityManager().getGhosts()) {
            if (Math.abs(g.getX() - x) < 20 && Math.abs(g.getY() - y) < 20) {
                if (g.getMode() == Ghost.Mode.SCARED) { // ghost dies
                    g.setMode(Ghost.Mode.DIED);
                    
                    // points for eating a ghost...
                } else {
                    return true;   // pacman dies
                }
            }
        }

        return false;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(), (int) x - 4, (int) y - 4, width + 8, height + 8, null);
    }

    private BufferedImage getCurrentAnimationFrame() {
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

}
