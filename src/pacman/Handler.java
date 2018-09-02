package pacman;

import pacman.entities.EntityManager;
import pacman.input.KeyManager;
import pacman.worlds.World;

/**
 * Helper class. We create one Handler object which holds references to multiple
 * useful objects. Then we pass this single Handler object as a parameter instead
 * of passing all the managers one by one.
 * 
 * @author uross
 */

public class Handler {

    private Game game;
    private World world;
    private EntityManager entityManager;

    public Handler(Game game) {
        this.game = game;
    }
    
    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }
    
    public int getWidth() {
        return game.getWidth();
    }
    
    public int getHeight() {
        return game.getHeight();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
}
