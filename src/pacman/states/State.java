package pacman.states;

import java.awt.Graphics;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import pacman.Game;
import pacman.Handler;
import pacman.entities.Ghost;
import pacman.gfx.Assets;
import pacman.gfx.TextRenderer;

/**
 * State class.
 * 
 * currentState - a static variable, we tick it in Game class.
 * 
 * Every class that extends this class will have:
 *  - start(), tick() and render(Graphics g) methods which will be overriden
 *  - several protected methods which handle some game variables that are not 
 *    linked to a specific State
 *
 * @author uross
 */
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

    private boolean highScoreVisible = true;
    private long currentMoment, lastMoment = -1, total = 0;

    public State(Handler handler) {
        this.handler = handler;
    }

    public abstract void start();

    public abstract void tick();

    public abstract void render(Graphics g);

    // The following methods are to be used by all classes that extend this class:
    //
    protected void tickScoreAndLives() {
        if (lastMoment < 0) {
            lastMoment = System.currentTimeMillis();
        } else {
            currentMoment = System.currentTimeMillis();
            total += currentMoment - lastMoment;
            lastMoment = currentMoment;
        }

        if (handler.getGame().getLives() < 0) {
            handler.getGame().setLives(0);
        }
        if (handler.getGame().getLives() > Game.MAX_LIVES) {
            handler.getGame().setLives(Game.MAX_LIVES);
        }

        if (handler.getGame().getScore() > handler.getGame().getHighScore()) {
            handler.getGame().setHighScore(handler.getGame().getScore());
            handler.getGame().setNewRecord(true);
        }

        if (handler.getGame().isNewRecord()) {
            if (total < 300) {
                highScoreVisible = true;
            } else if (total > 500) {
                total = 0;
            } else {
                highScoreVisible = false;
            }
        } else {
            highScoreVisible = true;
        }
    }

    protected void tickBackgroundSounds() {
        if (getCurrentState() == handler.getGame().getGameState()) {
            int sound = 0;
            for (Ghost g : handler.getEntityManager().getGhosts()) {
                if (g.getMode() == Ghost.Mode.DIED) {
                    sound = 2;
                    break;
                } else if (g.getMode() == Ghost.Mode.SCARED) {
                    sound = 1;
                }
            }
            switch (sound) {
                case 0: // ghosts are chasing
                    Assets.sound_pacman_chase.stop();
                    Assets.sound_ghost_return.stop();
                    if (!Assets.sound_ghost_chase.isActive()) {
                        Assets.sound_ghost_chase.loop();
                    }
                    break;
                case 1: // pacman is chasing
                    Assets.sound_ghost_chase.stop();
                    Assets.sound_ghost_return.stop();
                    if (!Assets.sound_pacman_chase.isActive()) {
                        Assets.sound_pacman_chase.loop();
                    }
                    break;
                case 2: // ghost returning to cage
                    Assets.sound_pacman_chase.stop();
                    Assets.sound_ghost_chase.stop();
                    if (!Assets.sound_ghost_return.isActive()) {
                        Assets.sound_ghost_return.loop();
                    }
                    break;
            }
        } else {
            stopBackgroundSounds();
        }
    }

    protected void stopBackgroundSounds() {
        Assets.sound_pacman_chase.stop();
        Assets.sound_ghost_chase.stop();
        Assets.sound_ghost_return.stop();
    }

    // writes new high score in score.txt (overwrites it if needed, only 1 score is remembered)
    protected void writeNewHighScore() {
        File file = new File("./score.txt");
        String source = Integer.toString(handler.getGame().getHighScore()) + " " + handler.getGame().getHighScorePlayer();
        FileWriter f;

        try {
            f = new FileWriter(file, false);    // overwrite
            f.write(source);
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void renderScoreAndLives(Graphics g) {
        TextRenderer.setDigitSize(20);
        TextRenderer.setLetterSize(20);

        TextRenderer.drawText(g, "score", 20, -50);
        TextRenderer.drawInteger(g, handler.getGame().getScore(), 20, -25);

        int length = handler.getGame().getHighScorePlayer().length();
        int lengthPixels = handler.getGame().isNewRecord() || length == 0 ? 0 : length * 16 + 5;
        TextRenderer.drawText(g, "high score", 225, -50);
        if (highScoreVisible) {
            TextRenderer.drawInteger(g, handler.getGame().getHighScore(), 305 - lengthPixels, -25);
        }

        TextRenderer.drawText(g, "lives", 20, 501);
        for (int i = 0; i < handler.getGame().getLives(); i++) {
            g.drawImage(Assets.player_left[1], 125 + 30 * i, 496, 30, 30, null);
        }

        if (lengthPixels > 0) { // if the record isn't broken
            TextRenderer.setDigitSize(16);
            TextRenderer.setLetterSize(16);
            TextRenderer.drawText(g, handler.getGame().getHighScorePlayer(), 425 - length * 16, -21);
        }
    }

}
