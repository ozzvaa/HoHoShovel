package si.um.feri.tevzki.gameElements;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import si.um.feri.tevzki.assets.RegionNames;
import si.um.feri.tevzki.config.GameConfig;

public class Tile extends Entity {
    public TileType type;
    private Vector2 velocity = new Vector2(); // current movement velocity
    private final float friction  = GameConfig.SNOW_FRICTION;

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

        // Apply velocity
        moveBy(velocity.x * delta, velocity.y * delta);

        // Apply friction
        velocity.scl(1 - friction * delta);

        // Stop if very small
        if (velocity.len2() < 0.001f) {
            velocity.set(0,0);
        }
    }

    public void push(Vector2 playerCenter) {
        // Only snow tiles can be pushed
        if (type != TileType.SNOW) return;

        // Tile center
        float tileCenterX = getX() + getWidth() / 2f;
        float tileCenterY = getY() + getHeight() / 2f;

        Vector2 dir = new Vector2(tileCenterX - playerCenter.x, tileCenterY - playerCenter.y);

        if (dir.len() == 0) return; // avoid zero vector
        dir.nor(); // normalize

        // Set velocity based on push strength
        velocity.set(dir.scl(GameConfig.SNOW_PUSH_SPEED));

    }
}
