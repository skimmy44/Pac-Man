package pacman.gfx;

import java.awt.Graphics;

public class TextRenderer {
    
    public static final int LETTER_WIDTH = 16, LETTER_HEIGHT = 16,
            DIGIT_WIDTH = 16, DIGIT_HEIGHT = 16,
            VERTICAL_SPACING = 0, HORIZONTAL_SPACING = 0;
    
    public static void drawText(Graphics g, String text, int x, int y) {
        text = text.toUpperCase();
        
        int dx = 0, dy = 0;
        
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            
            if (c == '\n') {
                dx = 0;
                dy += LETTER_HEIGHT + VERTICAL_SPACING;
                continue;
            } else if (c == ' ') {
                dx += LETTER_WIDTH + HORIZONTAL_SPACING;
            }
            
            if (c >= 'A' && c <= 'Z') { // letter
                g.drawImage(Assets.letters[c - 'A'], dx + x, dy + y, LETTER_WIDTH, LETTER_HEIGHT, null);
                dx += LETTER_WIDTH + HORIZONTAL_SPACING;
            } else if (c >= '0' && c <= '9') {  // digit
                g.drawImage(Assets.digits[c - '0'], dx + x, dy + y, DIGIT_WIDTH, DIGIT_HEIGHT, null);
                dx += DIGIT_WIDTH + HORIZONTAL_SPACING;
            } else {    // special characters
                // not supported yet
            }
        }
    }
    
    public static void drawInteger(Graphics g, int num, int x, int y) {
        String text = Integer.toString(1000000 + num);  // draws correctly numbers up to 999999
        
        for (int i = 1, dx = 0; i < text.length(); i++, dx += DIGIT_WIDTH + HORIZONTAL_SPACING) {
            g.drawImage(Assets.digits[text.charAt(i) - '0'], dx + x, y, DIGIT_WIDTH, DIGIT_HEIGHT, null);
        }
    }

}
