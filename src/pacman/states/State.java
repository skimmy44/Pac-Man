package pacman.states;

import java.awt.Graphics;
import pacman.Handler;

public abstract class State {
    
    // Static stuff
    private static State currentState = null;

    public static State getCurrentState() {
        return currentState;
    }

    public static void setCurrentState(State currentState) {
        State.currentState = currentState;
    }
    
    // Class
    protected Handler handler;

    public State(Handler handler) {
        this.handler = handler;
    }
    
    public abstract void tick();
    
    public abstract void render(Graphics g);
    
}
