package pacman.states;

import java.awt.Graphics;
import pacman.Handler;
import pacman.gfx.Assets;
import pacman.tiles.Tile;

public class LevelCompletedState extends State {
    
    private int map = 1;
    private long now, lastTime;
    private int timer;
    private int how_many_times;
    private final int HOW_MANY_TIMES = 5;

    public LevelCompletedState(Handler handler) {
        super(handler);
    }

    @Override
    public void start() {
        timer = 0;
        lastTime = System.currentTimeMillis();
        how_many_times = HOW_MANY_TIMES;
        
        State.setCurrentState(this);
        
        stopBackgroundSounds();
    }

    @Override
    public void tick() {
        now = System.currentTimeMillis();
        timer += now - lastTime;
        lastTime = now;
        
        if (timer >= 400) {
            map = 1;
            timer -= 400;
            how_many_times--;
        } else if (timer >= 200) {
            map = 2;
        }
        
        if (how_many_times <= 0) {
            handler.getGame().getReadyState().start();
        }
    }

    @Override
    public void render(Graphics g) {
        if (map == 1) {
            g.drawImage(Assets.world1, 0, 0, handler.getWorld().getWidth() * Tile.TILE_WIDTH, handler.getWorld().getHeight() * Tile.TILE_HEIGHT, null);
        } else {
            g.drawImage(Assets.world2, 0, 0, handler.getWorld().getWidth() * Tile.TILE_WIDTH, handler.getWorld().getHeight() * Tile.TILE_HEIGHT, null);
        }
        
        handler.getEntityManager().getPlayer().render(g);
        
        renderScoreAndLives(g);
    }

}
