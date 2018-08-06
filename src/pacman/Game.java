package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import pacman.display.Display;
import pacman.gfx.Assets;
import pacman.input.KeyManager;
import pacman.states.GameOverState;
import pacman.states.GameState;
import pacman.states.LevelCompletedState;
import pacman.states.MenuState;
import pacman.states.NewRecordState;
import pacman.states.PacmanDiedState;
import pacman.states.ReadyState;
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
    private State menuState;
    private State readyState;
    private State levelCompletedState;
    private State pacmanDiedState;
    private State gameOverState;
    private State newRecordState;

    // Handler
    private Handler handler;

    // Input
    private KeyManager keyManager;

    // Game
    private int score = 0, highScore;
    public final boolean SCORE_TRACKING;
    private boolean newRecord;
    private String highScorePlayer;
    private int lives = 3;
    public static final int MAX_LIVES = 5;

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        keyManager = new KeyManager();

        /*
            The following piece of code checks whether the file with high score data exists.
            If it doesn't exist (meaning that it's the first time we run the application),
            we set the high score to 0, and we don't write any name with it. As soon as this 
            high score gets beaten, the file with new high score will be created and after
            this, every time we run the application again, we will load that current high score.
         */
        String file = Utils.loadExternalFileAsString("./score.txt");
        if (file != null) {
            String[] tokens = file.split("\\s+");
            highScore = Utils.parseInt(tokens[0]);
            highScorePlayer = tokens[1];
            SCORE_TRACKING = true;
        } else {
            highScore = 0;
            highScorePlayer = "";
            SCORE_TRACKING = false;
        }
    }

    public void init() {
        // Display
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);

        // Assets
        Assets.init();

        // Handler
        handler = new Handler(this);

        // States
        menuState = new MenuState(handler);
        readyState = new ReadyState(handler);
        gameState = new GameState(handler);
        levelCompletedState = new LevelCompletedState(handler);
        pacmanDiedState = new PacmanDiedState(handler);
        gameOverState = new GameOverState(handler);
        newRecordState = new NewRecordState(handler);

        menuState.start();
    }

    public void newGame() {
        lives = 3;
        score = 0;
        newRecord = false;
        
        handler.setWorld(new World(handler, "/res/maps/map.txt"));
    }

    public void tick() {
        keyManager.tick();

        if (State.getCurrentState() != null) {
            State.getCurrentState().tick();
        }
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

        // all black
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);

        g.translate(0, 55);

        // Draw here:
        if (State.getCurrentState() != null) {
            State.getCurrentState().render(g);
        }

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
                //System.out.println("Ticks and frames: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }

        stop();
    }

    public void score(int x) {
        if (score / 10000 != (score + x) / 10000) { // extra life for every 10,000 points scored
            lives++;
        }
        score += x;
    }

    // Getters and Setters
    public KeyManager getKeyManager() {
        return keyManager;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public State getMenuState() {
        return menuState;
    }

    public State getGameState() {
        return gameState;
    }

    public State getReadyState() {
        return readyState;
    }

    public State getLevelCompletedState() {
        return levelCompletedState;
    }

    public State getPacmanDiedState() {
        return pacmanDiedState;
    }

    public State getGameOverState() {
        return gameOverState;
    }

    public State getNewRecordState() {
        return newRecordState;
    }

    public void setHighScorePlayer(String highScorePlayer) {
        this.highScorePlayer = highScorePlayer;
    }

    public String getHighScorePlayer() {
        return highScorePlayer;
    }

    public boolean isNewRecord() {
        return newRecord;
    }

    public void setNewRecord(boolean newRecord) {
        this.newRecord = newRecord;
    }

    // Thread manipulation
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
