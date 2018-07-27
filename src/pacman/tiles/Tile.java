package pacman.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {

    // Static stuff
    public static Tile[] tiles = new Tile[256];
    public static Tile emptyTile = new EmptyTile(0);
    public static Tile wallTile = new WallTile(1);
    public static Tile foodTile = new FoodTile(2);
    public static Tile powerFoodTile = new PowerFoodTile(3);

    public static int FOOD_SCORE = 10, POWER_FOOD_SCORE = 50;
    
    // Class
    public static final int TILE_WIDTH = 16, TILE_HEIGHT = 16;

    protected BufferedImage texture;
    protected final int id;

    public Tile(BufferedImage texture, int id) {
        this.texture = texture;
        this.id = id;

        tiles[id] = this;
    }

    public void tick() {

    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }
    
    public boolean isSolid() {
        return false;
    }
    
    public boolean isEatable() {
        return false;
    }
    
    public int getId() {
        return id;
    }
    
    public int getScore() {
        return 0;
    }

}
