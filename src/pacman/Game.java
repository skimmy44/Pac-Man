package pacman;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import pacman.display.Display;
import pacman.gfx.Assets;
import pacman.gfx.ImageLoader;
import pacman.states.GameState;
import pacman.states.State;
import pacman.worlds.World;

public class Game implements Runnable {

    // Display
    private Display display;
    private String title;
    private int width, height;

    // Main loop
    private boolean running = false;
    private Thread thread;

    // Graphics
    private BufferStrategy bs;
    private Graphics g;
    
    // States
    private State gameState;
    
    // Handler
    private Handler handler;

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public void init() {
        display = new Display(title, width, height);
        // listeners here...
        
        Assets.init();
        
        handler = new Handler(this);
        
        gameState = new GameState(handler);
        State.setCurrentState(gameState);
    }

    public void tick() {
        // tick listeneres...
        
        if (State.getCurrentState() != null)
            State.getCurrentState().tick();
    }

    public void render(Graphics g) {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {   // calling this method fot the first time
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();

        // Clear screen
        g.clearRect(0, 0, width, height);

        // Draw here:
        if (State.getCurrentState() != null)
            State.getCurrentState().render(g);
        
        // End drawing
        bs.show();
        g.dispose();
    }

    @Override
    public void run() {
        init();

        int fps = 60;
        double timePerTick = 1000000000.0 / fps;
        double delta = 0;
        long now, timer = 0;
        int ticks = 0;
        long lastTime = System.nanoTime();

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if (delta >= 1) {
                tick();
                render(g);
                ticks++;
                delta--;
            }

            if (timer >= 1000000000) {
                System.out.println("Ticks and frames: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }

        stop();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {

    }

}
