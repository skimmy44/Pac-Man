package pacman.entities;

import pacman.Handler;

public abstract class Creature extends Entity {

    public static enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    protected Direction currentDirection = Direction.UP, nextDirection = null;

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
            if (currentDirection == Direction.UP) {
                System.out.println("up");
                y -= speed;
            } else if (currentDirection == Direction.DOWN) {
                System.out.println("down");
                y += speed;
            } else if (currentDirection == Direction.LEFT) {
                x -= speed;
            } else if (currentDirection == Direction.RIGHT) {
                x += speed;
            }
        }
    }

    public boolean canMove() {
        if (currentDirection == Direction.UP) {
            if (collisionWithTile(getXTile(), getYTile() - 1)) {
                return false;
            }
        }
        if (currentDirection == Direction.DOWN) {
            if (collisionWithTile(getXTile(), getYTile() + 1)) {
                return false;
            }
        }
        if (currentDirection == Direction.LEFT) {
            if (collisionWithTile(getXTile() - 1, getYTile())) {
                return false;
            }
        }
        if (currentDirection == Direction.RIGHT) {
            if (collisionWithTile(getXTile() + 1, getYTile())) {
                return false;
            }
        }
        return true;
    }

    protected boolean collisionWithTile(int x, int y) {
        return handler.getWorld().getTile(x, y).isSolid();
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
