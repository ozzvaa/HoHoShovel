package si.um.feri.tevzki.config;

public class GameConfig {

    // Smallest unit is bit, it is 64x64 pixels
    public static final float TILE_BIT_SIZE = 0.5f; // 64x64
    public static final float TILE_SIZE = 8f * TILE_BIT_SIZE; // 512
    public static final float TILE_BITS_NUM = 8f; // number of bits in one tile
    public static final float WORLD_WIDTH = TILE_SIZE*20;
    public static final float WORLD_HEIGHT = TILE_SIZE*10;
    public static final int GRID_WIDTH = 3; // number of tiles in a grid
    public static final int GRID_HEIGHT = 3; // number of tiles in a grid
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
    public static float PLAYER_WIDTH;
    public static final float PLAYER_HEIGHT = TILE_SIZE/2;
    public static final float PLAYER_SPEED = TILE_SIZE*2;
    public static final float SHOVEL_HEIGHT = 0.7f*PLAYER_HEIGHT;

    public static float SHOVEL_WIDTH = 0.4f*PLAYER_HEIGHT;
    public static final float SHOVEL_HEAD_HEIGHT = SHOVEL_WIDTH / 4;
    public static final float SHOVEL_HEAD_WIDTH = SHOVEL_WIDTH / 2;


    private GameConfig() {}
}
