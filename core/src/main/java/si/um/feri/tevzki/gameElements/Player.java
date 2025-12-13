package si.um.feri.tevzki.gameElements;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import si.um.feri.tevzki.config.GameConfig;

public class Player extends Actor {
    TextureRegion region;
    public Player(TextureRegion region) {
        this.region = region;
        setSize(GameConfig.PLAYER_WIDTH, GameConfig.PLAYER_HEIGHT);
        setPosition(GameConfig.WORLD_WIDTH/2f, GameConfig.WORLD_HEIGHT/2f);
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
