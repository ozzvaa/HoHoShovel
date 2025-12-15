package si.um.feri.tevzki.gameElements;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

import si.um.feri.tevzki.assets.RegionNames;
import si.um.feri.tevzki.config.GameConfig;

public class Tile extends Entity {
    public TileType type;
    private TextureAtlas gameAtlas;
    public SnowPile pile;
    /**
     * Tile Actor
     *
     * @param tileType
     * @param worldX
     * @param worldY
     */
    public Tile(TileType tileType, TextureAtlas gameAtlas, float worldX, float worldY) {
        this.type = tileType;
        this.gameAtlas = gameAtlas;
        this.pile = null;
        setSize(GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
        setPosition(worldX, worldY); // Position of Tile - is in World Coordinates
    }

    public void setType(TileType type) {
        this.type = type;
        if (type == TileType.SNOW) {
            region = gameAtlas.findRegion(RegionNames.SNOW_TILE);
        } else if (type == TileType.ROAD) {
            region = gameAtlas.findRegion(RegionNames.ROAD_TILE);
        } else if (type == TileType.GRASS) {
            region = gameAtlas.findRegion(RegionNames.GRASS_TILE);
        } else if (type == TileType.ICE) {
            region = gameAtlas.findRegion(RegionNames.ICE_TILE);
        } else {
            region = null;
        }
    }

    public void setType(int typeId) {
        setType(TileType.fromId(typeId));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (pile != null) {
            pile.draw(batch, parentAlpha);
        }
    }
}
