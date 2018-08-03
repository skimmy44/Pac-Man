package pacman.states;

import java.awt.Graphics;
import pacman.Handler;
import pacman.gfx.TextRenderer;

public class NewRecordState extends State {

    private final int MIN_LENGTH = 4, MAX_LENGTH = 8;

    private boolean newRecordVisible = true;
    private boolean typingVisible = true;
    private boolean canType = false;

    private long now, lastTime, delta, total;

    private char[] name;
    private int length;

    public NewRecordState(Handler handler) {
        super(handler);
    }

    @Override
    public void start() {
        name = new char[MAX_LENGTH];
        length = 0;

        newRecordVisible = true;
        typingVisible = true;
        canType = false;

        delta = 0;
        total = 0;
        lastTime = System.currentTimeMillis();

        State.setCurrentState(this);
    }

    @Override
    public void tick() {
        now = System.currentTimeMillis();
        delta += now - lastTime;
        total += now - lastTime;
        lastTime = now;

        if (!canType) {
            if (delta < 300) {
                newRecordVisible = true;
            } else if (delta > 500) {
                delta = 0;
            } else {
                newRecordVisible = false;
            }
            if (total > 2500) {
                canType = true;
                newRecordVisible = true;
            }
        } else {
            addSymbol();
            deleteSymbol();

            if (delta < 300) {
                typingVisible = true;
            } else if (delta > 500) {
                delta = 0;
            } else {
                typingVisible = false;
            }

            if (handler.getKeyManager().enter) {
                if (validName()) {
                    handler.getGame().setHighScorePlayer(name());

                    writeNewHighScore();

                    handler.getGame().getMenuState().start();
                }
            }
        }
    }

    private void addSymbol() {
        if (length >= MAX_LENGTH) {
            return;
        }
        for (int i = 0; i < 26; i++) {
            if (handler.getKeyManager().letters[i]) {
                name[length++] = (char) ('A' + i);
                return;
            }
        }
        for (int i = 0; i < 10; i++) {
            if (handler.getKeyManager().digits[i]) {
                name[length++] = (char) ('0' + i);
                return;
            }
        }
        if (handler.getKeyManager().underscore) {
            name[length++] = '_';
        }
    }

    private void deleteSymbol() {
        if (handler.getKeyManager().backspace && length > 0) {
            length--;
        }
    }

    private boolean validName() {
        if (name[0] >= '0' && name[0] <= '9') {
            return false;
        }
        return length >= MIN_LENGTH && length <= MAX_LENGTH;
    }

    private String name() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            builder.append(name[i]);
        }

        return builder.toString();
    }

    @Override
    public void render(Graphics g) {
        if (newRecordVisible) {
            TextRenderer.setLetterSize(35);
            TextRenderer.drawText(g, "new record", 50, 150);
        }

        if (canType) {
            if (typingVisible) {
                TextRenderer.setLetterSize(16);
                TextRenderer.setDigitSize(16);
                TextRenderer.drawText(g, "type your name", handler.getWidth() / 2 - 14 * 16 / 2, 220);
            }

            String currentName = name();
            currentName += typingVisible ? '_' : ' ';
            int x = handler.getWidth() / 2 - currentName.length() * 20 / 2;
            TextRenderer.setLetterSize(20);
            TextRenderer.setDigitSize(20);
            TextRenderer.drawText(g, currentName, x, 270);
        }

        renderScoreAndLives(g);
    }

}
