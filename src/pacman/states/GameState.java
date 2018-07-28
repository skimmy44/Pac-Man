package pacman.states;

import java.awt.Graphics;
import pacman.Handler;
import pacman.worlds.World;

public class GameState extends State {

    private World world;
    
    public GameState(Handler handler) {
        super(handler);
        world = new World(handler, "src/res/maps/map.txt");
        handler.setWorld(world);
    }

    @Override
    public void tick() {
        world.tick();
        tickScoreAndLives();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
        renderScoreAndLives(g);
    }

}
