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
        move();
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
        if (nextDirection == Direction.UP) {
            if (!collisionWithTile(getXTile(), getYTile() - 1)) {
                currentDirection = Direction.UP;
                nextDirection = null;
                return;
            }
        }
        if (nextDirection == Direction.DOWN) {
            if (!collisionWithTile(getXTile(), getYTile() + 1)) {
                System.out.println("x");
                currentDirection = Direction.DOWN;
                nextDirection = null;
                return;
            }
        }
        if (nextDirection == Direction.LEFT) {
            if (!collisionWithTile(getXTile() - 1, getYTile())) {
                currentDirection = Direction.LEFT;
                nextDirection = null;
                return;
            }
        }
        if (nextDirection == Direction.RIGHT) {
            if (!collisionWithTile(getXTile() + 1, getYTile())) {
                currentDirection = Direction.RIGHT;
                nextDirection = null;
                return;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player_eaten[0], (Math.round(x / Tile.TILE_WIDTH)) * Tile.TILE_WIDTH, Math.round(y / Tile.TILE_HEIGHT) * Tile.TILE_HEIGHT, width, height, null);
    }

}
