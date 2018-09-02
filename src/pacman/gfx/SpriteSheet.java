package pacman.gfx;

import java.awt.image.BufferedImage;

/**
 * Handles the sprite sheet. crop() method extracts a part of the image, and
 * that is how we get single sprites from the whole sheet.
 * 
 * @author uross
 */

public class SpriteSheet {

    private BufferedImage sheet;

    public SpriteSheet(BufferedImage sheet) {
        this.sheet = sheet;
    }
    
    public BufferedImage crop(int x, int y, int width, int height) {
        return sheet.getSubimage(x, y, width, height);
    }
    
}
