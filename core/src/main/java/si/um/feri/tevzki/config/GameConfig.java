package si.um.feri.tevzki.config;

public class GameConfig {

    // Smallest unit is tile, it is 64x64 pixels
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
    public static final float SCREEN_ASPECT = (float) SCREEN_WIDTH / SCREEN_HEIGHT;
    public static final float TILE_SIZE = 1; // 64
    public static final float WORLD_WIDTH = TILE_SIZE*40;
    public static final float WORLD_HEIGHT = WORLD_WIDTH/SCREEN_ASPECT;
    public static final boolean DEBUG = false;
    public static final float PILE_SIZE = 1;
    public static float PLAYER_WIDTH;
    public static final float PLAYER_HEIGHT = TILE_SIZE*2;
    public static final float PLAYER_SPEED = TILE_SIZE*4;

    public static final int PLAYER_STRENGTH = 10;
    public static final float SHOVEL_HEIGHT = 0.7f*PLAYER_HEIGHT;

    public static float SHOVEL_WIDTH = 0.4f*PLAYER_HEIGHT;
    public static final float SHOVEL_HEAD_HEIGHT = SHOVEL_WIDTH / 4;
    public static final float SHOVEL_HEAD_WIDTH = SHOVEL_WIDTH / 2;


    private GameConfig() {}
}
