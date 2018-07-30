package pacman.states;

import java.awt.Graphics;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import pacman.Game;
import pacman.Handler;
import pacman.gfx.Assets;
import pacman.gfx.TextRenderer;

public abstract class State {

    /* States to be implemented:
        - title
        - ready
        - playing (game state)
        pacman died
        ghost died - can also be a ghost state
        - level completed
        game over
        new record (enter name)
     */
    
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

    public abstract void start();

    public abstract void tick();

    public abstract void render(Graphics g);

    protected void tickScoreAndLives() {
        if (handler.getGame().getLives() < 0) {
            handler.getGame().setLives(0);
        }
        if (handler.getGame().getLives() > Game.MAX_LIVES) {
            handler.getGame().setLives(Game.MAX_LIVES);
        }

        if (handler.getGame().getScore() > handler.getGame().getHighScore()) {
            handler.getGame().setHighScore(handler.getGame().getScore());

            File file = new File("src/res/score/score.txt");
            String source = Integer.toString(handler.getGame().getHighScore()) + " player";
            FileWriter f;

            try {
                f = new FileWriter(file, false);    // overwrite
                f.write(source);
                f.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void renderScoreAndLives(Graphics g) {
        TextRenderer.setDigitSize(20);
        TextRenderer.setLetterSize(20);

        TextRenderer.drawText(g, "score", 20, -50);
        TextRenderer.drawInteger(g, handler.getGame().getScore(), 20, -25);

        int length = handler.getGame().getHighScorePlayer().length();
        TextRenderer.drawText(g, "high score", 225, -50);
        TextRenderer.drawInteger(g, handler.getGame().getHighScore(), 300 - length * 16, -25);

        TextRenderer.drawText(g, "lives", 20, 501);
        for (int i = 0; i < handler.getGame().getLives(); i++) {
            g.drawImage(Assets.player_left[1], 125 + 30 * i, 496, 30, 30, null);
        }

        TextRenderer.setLetterSize(16);
        TextRenderer.drawText(g, handler.getGame().getHighScorePlayer(), 425 - length * 16, -21);
    }

}
