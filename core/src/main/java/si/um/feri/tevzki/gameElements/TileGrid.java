package si.um.feri.tevzki.gameElements;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import si.um.feri.tevzki.config.GameConfig;

public class TileGrid {
    public Array<SnowTile> grid = new Array<>();

    /** Creates a grid of tile Actors */
    public TileGrid(int width, int height, TextureAtlas gameAtlas) {
        for (int y = 0; y < height; y++) { // X and Y inside of SnowTile Are world coordinates
            for (int x = 0; x < width; x++) {
                float tileX = x + (GameConfig.WORLD_WIDTH/GameConfig.TILE_SIZE)/2-GameConfig.GRID_WIDTH/2;
                float tileY = y + (GameConfig.WORLD_HEIGHT/GameConfig.TILE_SIZE)/2-GameConfig.GRID_HEIGHT/2;
                SnowTile snowTile = new SnowTile(TileType.SNOW, gameAtlas, tileX, tileY); // LARGE TILES
                grid.add(snowTile);
            }
        }
    }
}
