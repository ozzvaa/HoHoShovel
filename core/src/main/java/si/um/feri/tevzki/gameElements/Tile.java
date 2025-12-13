package si.um.feri.tevzki.gameElements;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import si.um.feri.tevzki.assets.RegionNames;
import si.um.feri.tevzki.config.GameConfig;

public class Tile extends Actor {
    public TileType type;
    private TextureRegion region;


    /**
     * Tile Actor
     * @param tileType
     * @param x
     * @param y
     */
    public Tile(TileType tileType, TextureAtlas gameAtlas, float x, float y) {
        this.type = tileType;

        if (type == TileType.SNOW) {
            region = gameAtlas.findRegion(RegionNames.SNOW_TILE);
        } else if (type == TileType.ROAD) {
            region= gameAtlas.findRegion(RegionNames.ROAD_TILE);
        }
        else if (type == TileType.GRASS) {
            region= gameAtlas.findRegion(RegionNames.GRASS_TILE);
        }

        setSize(GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
        setPosition(x*GameConfig.TILE_SIZE, y*GameConfig.TILE_SIZE);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(
            region,
            getX(),
            getY(),
            getWidth(),
            getHeight()
        );
    }



}
