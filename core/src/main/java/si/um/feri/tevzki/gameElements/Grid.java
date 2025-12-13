package si.um.feri.tevzki.gameElements;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Grid extends Actor {
    public Array<Actor> grid = new Array<>();

    /** Creates a grid of tile Actors */
    public Grid (int width, int height, TextureAtlas gameAtlas) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = new Tile(TileType.SNOW, gameAtlas, x, y);
                grid.add(tile);
            }
        }
    }
}
