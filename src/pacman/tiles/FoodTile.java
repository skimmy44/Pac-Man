package pacman.tiles;

import pacman.gfx.Assets;

/**
 * Food Tile. It is eatable, when player steps on it, he gets FOOD_SCORE points.
 * 
 * @author uross
 */

public class FoodTile extends Tile {

    public FoodTile(int id) {
        super(Assets.food, id);
    }
    
    @Override
    public boolean isEatable() {
        return true;
    }
    
    @Override
    public int getScore() {
        return FOOD_SCORE;
    }

}
