package pacman.tiles;

public class WallTile extends Tile {

    public WallTile(int id) {
        super(null, id);
    }
    
    @Override
    public boolean isSolid() {
        return true;
    }

}
