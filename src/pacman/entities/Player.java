package pacman.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import pacman.Handler;
import pacman.gfx.Animation;
import pacman.gfx.Assets;
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

    private void setMoves() {
        switch (currentDirection) {
            case UP:
                xMove = 0;
                yMove = -speed;
                break;
            case DOWN:
                xMove = 0;
                yMove = speed;
                break;
            case LEFT:
                xMove = -speed;
                yMove = 0;
                break;
            case RIGHT:
                xMove = speed;
                yMove = 0;
                break;
        }
    }

    private void eat() {
        if (handler.getWorld().getTile(getXTile(), getYTile()).isEatable()) {
            handler.getGame().score(handler.getWorld().eatTile(getXTile(), getYTile()));
        }
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
