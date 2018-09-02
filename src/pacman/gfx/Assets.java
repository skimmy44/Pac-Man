package pacman.gfx;

import java.awt.image.BufferedImage;

import pacman.sounds.Sound;

/**
 * This class contains all the assets (images, sprites, sounds, fonts) used in the game code.
 * 
 * @author uross
 */

public class Assets {

    private static final int DIGIT_SIZE = 9, LETTER_SIZE = 9, GHOST_SIZE = 16, PLAYER_SIZE = 16;

    public static BufferedImage title, ready, game_over, underscore;
    public static BufferedImage[] digits, letters;
    public static BufferedImage[][] ghost_up, ghost_down, ghost_left, ghost_right;
    public static BufferedImage[] ghost_scared_1, ghost_scared_2, ghost_eaten;
    public static BufferedImage[] player_eaten, player_up, player_down,
            player_left, player_right;
    public static BufferedImage world1, world2, food, powerFood;
    public static BufferedImage[] points;

    public static Sound 
            sound_beginning, sound_eat, sound_pacman_died, 
            sound_ghost_died, sound_intermission, sound_extralife,
            sound_ghost_chase, sound_pacman_chase, sound_ghost_return;

    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/res/textures/sheet.png"));

        title = sheet.crop(0, 0, 182, 46);
        ready = sheet.crop(0, 286, 44, 7);
        game_over = sheet.crop(44, 286, 68, 7);

        letters = loadArray(sheet, 0, 293, 26, LETTER_SIZE);
        digits = loadArray(sheet, 0, 302, 10, DIGIT_SIZE);
        points = loadArray(sheet, 0, 236, 4, 16);
        underscore = sheet.crop(234, 294, 8, 8);

        ghost_right = new BufferedImage[4][2];
        for (int i = 0; i < 4; i++) {
            ghost_right[i] = loadArray(sheet, 0, 124 + i * GHOST_SIZE, 2, GHOST_SIZE);
        }

        ghost_left = new BufferedImage[4][2];
        for (int i = 0; i < 4; i++) {
            ghost_left[i] = loadArray(sheet, GHOST_SIZE * 2, 124 + i * GHOST_SIZE, 2, GHOST_SIZE);
        }

        ghost_up = new BufferedImage[4][2];
        for (int i = 0; i < 4; i++) {
            ghost_up[i] = loadArray(sheet, GHOST_SIZE * 4, 124 + i * GHOST_SIZE, 2, GHOST_SIZE);
        }

        ghost_down = new BufferedImage[4][2];
        for (int i = 0; i < 4; i++) {
            ghost_down[i] = loadArray(sheet, GHOST_SIZE * 6, 124 + i * GHOST_SIZE, 2, GHOST_SIZE);
        }

        ghost_scared_1 = loadArray(sheet, 0, 124 + GHOST_SIZE * 4, 2, GHOST_SIZE);
        ghost_scared_2 = loadArray(sheet, GHOST_SIZE * 2, 124 + GHOST_SIZE * 4, 2, GHOST_SIZE);
        ghost_eaten = loadArray(sheet, GHOST_SIZE * 4, 124 + GHOST_SIZE * 4, 4, GHOST_SIZE);

        player_eaten = loadArray(sheet, 0, 258, 16, PLAYER_SIZE);
        player_right = loadArray(sheet, 0, 89, 4, PLAYER_SIZE);
        player_left = loadArray(sheet, 0, 105, 4, PLAYER_SIZE);
        player_up = loadArray(sheet, 64, 89, 4, PLAYER_SIZE);
        player_down = loadArray(sheet, 64, 105, 4, PLAYER_SIZE);

        world1 = sheet.crop(202, 0, 224, 248);
        world2 = sheet.crop(432, 0, 224, 248);

        food = sheet.crop(0, 66, 8, 8);
        powerFood = sheet.crop(8, 66, 18, 18);
        
        sound_beginning = new Sound("beginning");
        sound_pacman_died = new Sound("pacman_died");
        sound_ghost_died = new Sound("ghost_died");
        sound_intermission = new Sound("intermission");
        sound_extralife = new Sound("extralife");
        sound_eat = new Sound("pacman_eat");
        sound_ghost_chase = new Sound("ghost_chase");
        sound_ghost_return = new Sound("ghost_return");
        sound_pacman_chase = new Sound("pacman_chase");
    }

    private static BufferedImage[] loadArray(SpriteSheet sheet, int x, int y, int n, int SIZE) {
        BufferedImage[] arr = new BufferedImage[n];

        for (int i = 0; i < n; i++) {
            arr[i] = sheet.crop(x + i * SIZE, y, SIZE, SIZE);
        }

        return arr;
    }

}
