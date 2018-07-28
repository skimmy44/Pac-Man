package pacman.entities;

import pacman.Handler;
import pacman.tiles.Tile;

public abstract class Creature extends Entity {

    public static enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    protected Direction currentDirection = Direction.RIGHT, nextDirection = null;

    protected float speed = 2.0f;
    protected float xMove, yMove;

    public Creature(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        xMove = speed;
        yMove = -speed;
    }

    public void move() {
        if (!canMove()) {
            currentDirection = nextDirection;
            nextDirection = null;
        } else {
            x += xMove;
            y += yMove;
        }
    }

    public boolean canMove() {
        switch (currentDirection) {
            case UP:
                if (collisionWithTile(getXTile(), getYTile() - 1)) {
                    if (y + yMove >= getYTile() * Tile.TILE_HEIGHT) {
                        return true;
                    } else {
                        y = getYTile() * Tile.TILE_HEIGHT;
                    }
                    return false;
                }
                break;
            case DOWN:
                if (collisionWithTile(getXTile(), getYTile() + 1)) {
                    if (y + yMove <= getYTile() * Tile.TILE_HEIGHT) {
                        return true;
                    } else {
                        y = getYTile() * Tile.TILE_HEIGHT;
                    }
                    return false;
                }
                break;
            case LEFT:
                if (collisionWithTile(getXTile() - 1, getYTile())) {
                    if (x + xMove >= getXTile() * Tile.TILE_WIDTH) {
                        return true;
                    } else {
                        x = getXTile() * Tile.TILE_WIDTH;
                    }
                    return false;
                }
                break;
            case RIGHT:
                if (collisionWithTile(getXTile() + 1, getYTile())) {
                    if (x + xMove <= getXTile() * Tile.TILE_WIDTH) {
                        return true;
                    } else {
                        x = getXTile() * Tile.TILE_WIDTH;
                    }
                    return false;
                }
                break;
        }
        return true;
    }

    protected boolean collisionWithTile(int x, int y) {
        return handler.getWorld().getTile(x, y).isSolid();
    }

    protected void checkBounds() {
        if (getYTile() != 14) {
            return;
        }
        if (x > -Tile.TILE_WIDTH && x < handler.getWidth()) {
            return;
        }
        if (x <= -Tile.TILE_WIDTH) {
            x = handler.getWidth();
            return;
        }
        if (x >= handler.getWidth()) {
            x = -Tile.TILE_WIDTH;
            return;
        }
    }

    // Getters and Setters
    public Direction getNextDirection() {
        return nextDirection;
    }

    public void setNextDirection(Direction nextDirection) {
        this.nextDirection = nextDirection;
    }

    public float getxMove() {
        return xMove;
    }

    public void setxMove(float xMove) {
        this.xMove = xMove;
    }

    public float getyMove() {
        return yMove;
    }

    public void setyMove(float yMove) {
        this.yMove = yMove;
    }
}
