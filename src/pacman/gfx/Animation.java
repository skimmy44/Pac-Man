package pacman.gfx;

import java.awt.image.BufferedImage;

/**
 * Animation class. Loops through the array of frames in certain speed and
 * creates the illusion of movement - animation.
 * 
 * @author uross
 */

public class Animation {
    
    private int speed;  // ms
    private int index;  // index of current animation frame
    private long lastTime, timer;
    private BufferedImage[] frames;

    public Animation(int speed, BufferedImage[] frames) {
        this.speed = speed;
        this.frames = frames;
        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }
    
    public void tick() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        
        if (timer > speed) {
            index++;
            timer = 0;
            if (index >= frames.length) {
                index = 0;
            }
        }
    }
    
    public BufferedImage getCurrentFrame() {
        return frames[index];
    }

}
