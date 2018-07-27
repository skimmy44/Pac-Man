package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import pacman.display.Display;
import pacman.entities.Player;
import pacman.gfx.Assets;
import pacman.gfx.ImageLoader;
import pacman.gfx.TextRenderer;
import pacman.input.KeyManager;
import pacman.states.GameState;
import pacman.states.State;
import pacman.utils.Utils;
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
    
    // Input
    private KeyManager keyManager;
    
    // Game
    private Player player;
    private int score = 0, high_score = Utils.parseInt(Utils.loadFileAsString("res/score/score.txt").split("\\s+")[0]);

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        keyManager = new KeyManager();
    }

    public void init() {
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        
        Assets.init();
        
        handler = new Handler(this);
        
        player = new Player(handler, 216, 368);
        
        gameState = new GameState(handler);
        State.setCurrentState(gameState);
    }

    public void tick() {
        // tick listeneres...
        
        if (State.getCurrentState() != null)
            State.getCurrentState().tick();
        
        player.tick();
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
        
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        
        g.translate(0, 55);

        // Draw here:
        if (State.getCurrentState() != null)
            State.getCurrentState().render(g);
        player.render(g);
        
        TextRenderer.drawText(g, "score", 20, -50);
        TextRenderer.drawInteger(g, score, 20, -25);
        
        TextRenderer.drawText(g, "high score", 225, -50);
        TextRenderer.drawInteger(g, high_score, 285, -25);
        
        TextRenderer.drawText(g, "lives", 20, 501);
        
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
                
                //System.out.println("x, y: " + player.getXTile() + " " + player.getYTile());
            }
        }

        stop();
    }
    
    public void score(int x) {
        score += x;
    }

    public KeyManager getKeyManager() {
        return keyManager;
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
