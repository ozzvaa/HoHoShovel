package si.um.feri.tevzki.gameElements;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import si.um.feri.tevzki.config.GameConfig;

public class TileGrid {
    public Tile[][] grid;
    public int width;
    public int height;


    /** Creates a grid of tile Actors */
    public TileGrid(int width, int height, TextureAtlas gameAtlas) {
        this.width = width;
        this.height = height;
        grid = new Tile[height][width];


        for (int y = 0; y < height; y++) { // X and Y inside of Tile Are world coordinates
            for (int x = 0; x < width; x++) {

                float worldX = x * GameConfig.TILE_SIZE;
                float worldY = y * GameConfig.TILE_SIZE;

                TileType tileType = TileType.NONE;

                if (x == 0) {
                    tileType = TileType.GRASS;
                }

                grid[y][x] = new Tile(
                    tileType,
                    gameAtlas,
                    worldX,
                    worldY
                );
            }
        }
    }

    public void setTiles(TileType tileType, int offsetX, int offsetY, int width, int height) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y+offsetY][x+offsetX].setType(tileType);
            }
        }
    }
}
