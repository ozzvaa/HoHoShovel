package si.um.feri.tevzki.gameElements;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import si.um.feri.tevzki.config.GameConfig;

public class TileGrid {
    public Array<Tile> grid = new Array<>();
    public float GRID_WIDTH;
    public float GRID_HEIGHT;

    /** Creates a grid of tile Actors */
    public TileGrid(int width, int height, TextureAtlas gameAtlas) {
        GRID_WIDTH = width*GameConfig.TILE_SIZE;
        GRID_HEIGHT = height*GameConfig.TILE_SIZE;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = new Tile(TileType.SNOW, gameAtlas, x+GameConfig.WORLD_WIDTH/2f-GRID_WIDTH/2f, y+GameConfig.WORLD_HEIGHT/2f-GRID_HEIGHT/2f);
                grid.add(tile);
            }
        }
    }
}
