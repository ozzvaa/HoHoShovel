package si.um.feri.tevzki.config;

public class GameConfig {

    // One cell (64px) is 1 WORLD UNIT
    public static final float WORLD_WIDTH = 20f;
    public static final float WORLD_HEIGHT = 10f;
    public static final int GRID_WIDTH = (int) WORLD_WIDTH;
    public static final int GRID_HEIGHT = (int)  WORLD_HEIGHT;

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
    public static final float TILE_SIZE = 1f;
    public static final float TILE_BITS_NUM = 8f;
    public static final float TILE_BIT_SIZE = TILE_SIZE / TILE_BITS_NUM;


    public static float PLAYER_WIDTH;
    public static final float PLAYER_HEIGHT = 1f;
    public static final float PLAYER_SPEED = 2f;
    public static final float SHOVEL_HEIGHT =0.7f;

    public static float SHOVEL_WIDTH = 0.4f;
    public static final float SHOVEL_HEAD_HEIGHT = SHOVEL_WIDTH / 4;
    public static final float SHOVEL_HEAD_WIDTH = SHOVEL_WIDTH / 2;
    public static final float SNOW_FRICTION = 0.5f;
    public static final float SNOW_PUSH_SPEED = 1f;


    private GameConfig() {}
}
