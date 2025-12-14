package si.um.feri.tevzki.gameElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

import si.um.feri.tevzki.config.GameConfig;

public class Player extends Entity {
    Array<Tile> tileGrid;
    public Player(TextureRegion region, Array<Tile> tileGrid) {
        this.region = region;
        setSize(GameConfig.PLAYER_WIDTH, GameConfig.PLAYER_HEIGHT);
        setPosition(GameConfig.WORLD_WIDTH/2f-getWidth()/2f, GameConfig.WORLD_HEIGHT/2f-getHeight()/2f);
        this.tileGrid = tileGrid;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        float dx = 0;
        float dy = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) dx -= GameConfig.PLAYER_SPEED * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) dx += GameConfig.PLAYER_SPEED * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) dy += GameConfig.PLAYER_SPEED * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) dy -= GameConfig.PLAYER_SPEED * delta;

        moveBy(dx, dy);

        handleCollision(dx, dy);
    }

    private void handleCollision(float dx, float dy) { // Check for collision with tiles
        Iterator<Tile> iter = tileGrid.iterator(); // tileGrid is your Array<Tile>

        while (iter.hasNext()) {
            Tile tile = iter.next();
            if (collidesWith(tile)) {
                tile.push(getCenter());   // remove from stage
            }
        }
    }


}
