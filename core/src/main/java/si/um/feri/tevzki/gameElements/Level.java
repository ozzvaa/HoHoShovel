package si.um.feri.tevzki.gameElements;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class Level {
    public int offsetX;
    public int offsetY;
    public int width;
    public int height;
    public Rectangle rect;
    public TileType[][] levelGrid;
    private TextureAtlas gameAtlas;
    public float playerX;
    public float playerY;

    public Level(int offsetX, int offsetY, int width, int height, TextureAtlas gameAtlas) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        this.gameAtlas = gameAtlas;
        this.playerX = offsetX + width/2;
        this.playerY = offsetY + height/2;
        this.rect = new Rectangle(offsetX, offsetY, width, height);
        this.levelGrid = new TileType[this.height][this.width];

    }

    public static Level level1(TextureAtlas gameAtlas) {
        int [][] tileNumberMap = new int[][]{
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0,},
            {1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
            {1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
            {1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0,},
            {1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0,},
            {1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0,},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0,},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0,},
            {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0,},
            {1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0,},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,},
        };
        Level level1 = new Level(17, 8, tileNumberMap.length, tileNumberMap[0].length, gameAtlas);
        level1.playerX = level1.offsetX + 1f;
        level1.playerY = level1.offsetY + level1.height;
        level1.levelGrid = new TileType[level1.height][level1.width];

        for (int mapY = 0; mapY < level1.height; mapY++) {
            for (int x = 0; x < level1.width; x++) {

                int worldY = level1.height - 1 - mapY;

                level1.levelGrid[worldY][x] =
                    TileType.fromId(tileNumberMap[mapY][x]);
            }
        }
        return level1;
    }

    public TileType getTileType(int x, int y) {
        return levelGrid[y][x];
    }

}
