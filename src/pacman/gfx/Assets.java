package pacman.gfx;

import java.awt.image.BufferedImage;

public class Assets {

    private static final int WIDTH = 8, HEIGHT = 8;

    private static final int DIGIT_SIZE = 9, LETTER_SIZE = 9, GHOST_SIZE = 16, PLAYER_SIZE = 16;

    public static BufferedImage title, ready, game_over;
    public static BufferedImage[] digits, letters;
    public static BufferedImage[][] ghost_up, ghost_down, ghost_left, ghost_right;
//    public static BufferedImage[] 
//            ghost_red_up, ghost_red_down, ghost_red_left, ghost_red_right,
//            ghost_pink_up, ghost_pink_down, ghost_pink_left, ghost_pink_right,
//            ghost_blue_up, ghost_blue_down, ghost_blue_left, ghost_blue_right,
//            ghost_orange_up, ghost_orange_down, ghost_orange_left, ghost_orange_right;
    public static BufferedImage[]
            ghost_scared_1, ghost_scared_2, ghost_eaten;
    public static BufferedImage[] player_eaten, player_up, player_down,
            player_left, player_right;
    public static BufferedImage world1, world2, food, powerFood;

    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/res/textures/sheet.png"));

        title = sheet.crop(0, 0, 182, 46);
        ready = sheet.crop(0, 286, 44, 7);
        game_over = sheet.crop(44, 286, 68, 7);

        letters = loadArray(sheet, 0, 293, 26, LETTER_SIZE);
        digits = loadArray(sheet, 0, 302, 10, DIGIT_SIZE);
        
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
            ghost_down[i] = loadArray(sheet, 0, 124 + i * GHOST_SIZE, 2, GHOST_SIZE);
        }

//        ghost_red_right = loadArray(sheet, 0, 124, 2, GHOST_SIZE);
//        ghost_red_left = loadArray(sheet, GHOST_SIZE * 2, 124, 2, GHOST_SIZE);
//        ghost_red_up = loadArray(sheet, GHOST_SIZE * 4, 124, 2, GHOST_SIZE);
//        ghost_red_down = loadArray(sheet, GHOST_SIZE * 6, 124, 2, GHOST_SIZE);
//        
//        ghost_pink_right = loadArray(sheet, 0, 124 + GHOST_SIZE, 2, GHOST_SIZE);
//        ghost_pink_left = loadArray(sheet, GHOST_SIZE * 2, 124 + GHOST_SIZE, 2, GHOST_SIZE);
//        ghost_pink_up = loadArray(sheet, GHOST_SIZE * 4, 124 + GHOST_SIZE, 2, GHOST_SIZE);
//        ghost_pink_down = loadArray(sheet, GHOST_SIZE * 6, 124 + GHOST_SIZE, 2, GHOST_SIZE);
//        
//        ghost_blue = loadArray(sheet, 0, 124 + GHOST_SIZE * 2, 8, GHOST_SIZE);
//        ghost_orange = loadArray(sheet, 0, 124 + GHOST_SIZE * 3, 8, GHOST_SIZE);
        
        ghost_scared_1 = loadArray(sheet, 0, 124 + GHOST_SIZE * 4, 2, GHOST_SIZE);
        ghost_scared_2 = loadArray(sheet, GHOST_SIZE * 2, 124 + GHOST_SIZE * 4, 2, GHOST_SIZE);
        ghost_eaten = loadArray(sheet, GHOST_SIZE * 4, 124 + GHOST_SIZE * 4, 4, GHOST_SIZE);

        player_eaten = loadArray(sheet, 0, 258, 14, PLAYER_SIZE);
        player_right = loadArray(sheet, 0, 89, 4, PLAYER_SIZE);
        player_left = loadArray(sheet, 0, 105, 4, PLAYER_SIZE);
        player_up = loadArray(sheet, 64, 89, 4, PLAYER_SIZE);
        player_down = loadArray(sheet, 64, 105, 4, PLAYER_SIZE);

        world1 = sheet.crop(202, 4, 164, 212);
        world2 = sheet.crop(370, 4, 164, 212);

        food = sheet.crop(0, 66, 8, 8);
        powerFood = sheet.crop(8, 66, 18, 18);
    }

    private static BufferedImage[] loadArray(SpriteSheet sheet, int x, int y, int n, int SIZE) {
        BufferedImage[] arr = new BufferedImage[n];

        for (int i = 0; i < n; i++) {
            arr[i] = sheet.crop(x + i * SIZE, y, SIZE, SIZE);
        }

        return arr;
    }

}
