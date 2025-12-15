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

    public void setTiles(TileType tileType, Level level) {
        setTiles(tileType, level.offsetX, level.offsetY, level.width, level.height);
    }

    public void setLevel(Level level) {
        for (int y = 0; y < level.height; y++) {
            for (int x = 0; x < level.width; x++) {
                grid[y+level.offsetY][x+level.offsetX].setType(level.getTileType(x,y));
            }
        }
    }

    public Tile[] getCloseTiles(float x, float y) {
        int tileX = (int) x;
        int tileY = (int) y;

        Tile[] neighbors = new Tile[9];
        int index = 0;

        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {

                int nx = tileX + dx;
                int ny = tileY + dy;

                // bounds check
                if (ny >= 0 && ny < grid.length &&
                    nx >= 0 && nx < grid[0].length) {

                    neighbors[index++] = getTile(nx,ny);
                }
            }
        }

        return neighbors;
    }
    public Tile getTile(float x, float y) {
        return grid[(int)y][(int)x];
    }
}
