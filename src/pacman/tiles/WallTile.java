package pacman.tiles;

/**
 * Wall Tile. This is the only solid tile, meaning that no creature can move through it.
 * 
 * @author uross
 */

public class WallTile extends Tile {

    public WallTile(int id) {
        super(null, id);
    }
    
    @Override
    public boolean isSolid() {
        return true;
    }

}
