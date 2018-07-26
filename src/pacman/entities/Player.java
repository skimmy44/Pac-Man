package pacman.entities;

import java.awt.Graphics;
import pacman.Handler;
import pacman.gfx.Assets;
import pacman.tiles.Tile;

public class Player extends Creature {

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, DEFAULT_ENTITY_WIDTH, DEFAULT_ENTITY_HEIGHT);
    }

    @Override
    public void tick() {
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
            handler.getWorld().eatTile(getXTile(), getYTile());
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player_eaten[0], (int) x, (int) y, width, height, null);
    }

}
