package pacman.entities;

import java.awt.Graphics;
import java.util.ArrayList;

import pacman.Handler;

/**
 * Entity Manager.
 * 
 * Object is created in World class. It is one of the objects a Handler contains.
 * We use it to easily access Player (Pacman) and Ghost objects when needed.
 * 
 * @author uross
 */

public class EntityManager {

    private Handler handler;
    private Player player;
    private ArrayList<Ghost> ghosts;
    private ArrayList<Entity> entities;

    public EntityManager(Handler handler) {
        this.handler = handler;
        this.player = new Player(handler, 216, 368);
        
        entities = new ArrayList<>();
        ghosts = new ArrayList<>();
        addEntity(player);
        Ghost g0 = new Ghost(handler, 216, 176, 0, player);
        Ghost g1 = new Ghost(handler, 186, 232, 1, player);
        Ghost g2 = new Ghost(handler, 210, 210, 2, player);
        Ghost g3 = new Ghost(handler, 246, 220, 3, player);
        addEntity(g0);
        ghosts.add(g0);
        addEntity(g1);
        ghosts.add(g1);
        addEntity(g2);
        ghosts.add(g2);
        addEntity(g3);
        ghosts.add(g3);
    }
    
    public void tick() {
        for (Entity e : entities) {
            e.tick();
        }
    }
    
    public void render(Graphics g) {
        for (Entity e : entities) {
            e.render(g);
        }
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }
    
    public Ghost getBlinky() {
        return ghosts.get(0);
    }

    // Getters and Setters
    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Ghost> getGhosts() {
        return ghosts;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

}
