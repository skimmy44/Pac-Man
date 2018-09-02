package pacman.tiles;

import pacman.gfx.Assets;

/**
 * Power Food Tile. It is eatable, when player steps on it, he gets POWER_FOOD_SCORE
 * points and will be able to eat ghosts for some time (ghosts enter SCARED mode).
 * 
 * @author uross
 */

public class PowerFoodTile extends Tile {

    public PowerFoodTile(int id) {
        super(Assets.powerFood, id);
    }
    
    @Override
    public boolean isEatable() {
        return true;
    }
    
    @Override
    public int getScore() {
        return POWER_FOOD_SCORE;
    }

}
