package si.um.feri.tevzki.gameElements;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

import si.um.feri.tevzki.assets.RegionNames;
import si.um.feri.tevzki.config.GameConfig;

public class SnowTile extends Group {
    public Entity baseTile; // the main texture
    public TileType type;

    public Array<SnowBit> snowBits = new Array<>();

    /**
     * SnowTile Actor
     * @param tileType
     * @param tileX
     * @param tileY
     */
    public SnowTile(TileType tileType, TextureAtlas gameAtlas, float tileX, float tileY) {
        this.type = tileType;
        baseTile = new Entity();


        if (type == TileType.SNOW) {
            baseTile.region = gameAtlas.findRegion(RegionNames.SNOW_TILE);
        } else if (type == TileType.ROAD) {
            baseTile.region= gameAtlas.findRegion(RegionNames.ROAD_TILE);
        }
        else if (type == TileType.GRASS) {
            baseTile.region= gameAtlas.findRegion(RegionNames.GRASS_TILE);
        }

        setPosition(tileX*GameConfig.TILE_SIZE, tileY*GameConfig.TILE_SIZE); // Position of SnowTile - group - is in World Coordinates
        setSize(GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);

        baseTile.setPosition(tileX*GameConfig.TILE_SIZE, tileY*GameConfig.TILE_SIZE); // World coordinates
        baseTile.setSize(GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
        baseTile.rect.set(baseTile.getX(), baseTile.getY(), baseTile.getWidth(), baseTile.getHeight());

        float bitSize = GameConfig.TILE_SIZE / GameConfig.TILE_BITS_NUM;
        for (int y = 0; y < GameConfig.TILE_BITS_NUM; y++) {
            for (int x = 0; x < GameConfig.TILE_BITS_NUM; x++) {
                SnowBit bit = new SnowBit(baseTile.region);
                bit.setSize(bitSize, bitSize);
                bit.setPosition(x * bitSize, y * bitSize); // relative to tile group
                addActor(bit);
                snowBits.add(bit);
            }
        }


    }

    public void shovelTile(Shovel shovel){
        Iterator<SnowBit> snowIter = snowBits.iterator();
        while (snowIter.hasNext()) {
            SnowBit bit  = snowIter.next();

            if (shovel.collidesWith(bit)) {
                bit.remove();
                snowIter.remove();
            }
        }

    }

}
