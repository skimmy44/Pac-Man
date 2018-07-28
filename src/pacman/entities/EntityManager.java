package pacman.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import pacman.Handler;

public class EntityManager {

    private Handler handler;
    private Player player;
    private ArrayList<Entity> entities;

    public EntityManager(Handler handler) {
        this.handler = handler;
        this.player = new Player(handler, 216, 368);

        entities = new ArrayList<>();
        addEntity(player);
        
        // add ghosts here...
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

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

}
