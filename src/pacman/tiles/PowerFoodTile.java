package pacman.tiles;

import pacman.gfx.Assets;

public class PowerFoodTile extends Tile {

    public PowerFoodTile(int id) {
        super(Assets.powerFood, id);
    }
    
    @Override
    public boolean isEatable() {
        return true;
    }

}
