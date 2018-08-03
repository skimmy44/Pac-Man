package pacman.entities;

import java.awt.Graphics;
import pacman.Handler;
import pacman.tiles.Tile;

public abstract class Entity {
    
    public static int DEFAULT_ENTITY_WIDTH = 32, DEFAULT_ENTITY_HEIGHT = 32;

    protected Handler handler;
    protected float x, y;
    protected int width, height;

    public Entity(Handler handler, float x, float y, int width, int height) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public abstract void tick();
    
    public abstract void render(Graphics g);
    
    public int getXTile() {
        return Math.round(x / Tile.TILE_WIDTH);
    }
    
    public int getYTile() {
        return Math.round(y / Tile.TILE_HEIGHT);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
}
