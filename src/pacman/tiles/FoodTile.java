package pacman.tiles;

import java.awt.image.BufferedImage;
import pacman.gfx.Assets;

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
