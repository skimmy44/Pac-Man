package pacman.worlds;

import com.sun.javafx.animation.TickCalculation;
import java.awt.Graphics;
import pacman.Handler;
import pacman.gfx.Assets;
import pacman.tiles.Tile;
import pacman.utils.Utils;

public class World {
    
    private Handler handler;
    private int width, height;
    private int[][] tiles;
    
    public World(Handler handler, String path) {
        this.handler = handler;
        loadWorld(path);
    }
    
    public void tick() {
        
    }
    
    public void render(Graphics g) {
        // Background:
        g.drawImage(Assets.world1, 0, 0, width * Tile.TILE_WIDTH, height * Tile.TILE_HEIGHT, null);
        // Board
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                getTile(x, y).render(g, x * Tile.TILE_WIDTH, y * Tile.TILE_HEIGHT);
            }
        }
        
        // Entities (player and ghosts)
        //...
    }
    
    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return Tile.emptyTile;
        
        Tile t = Tile.tiles[tiles[x][y]];
        if (t == null) {
            return Tile.emptyTile;
        }
        return t;
    }
    
    public void eatTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return;
        
        tiles[x][y] = 0;    // empty tile
    }
    
    private void loadWorld(String path) {
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        
        tiles = new int[width][height];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                tiles[x][y] = Utils.parseInt(tokens[2 + (y * width + x)]);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
