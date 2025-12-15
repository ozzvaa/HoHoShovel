package si.um.feri.tevzki.gameElements;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import si.um.feri.tevzki.config.GameConfig;

public class SnowPile extends Entity {
    public SnowPile(TextureRegion region, float x, float y) {
        this.region = region;
        setPosition(x, y);
        setSize(GameConfig.PILE_SIZE, GameConfig.PILE_SIZE);
    }

}
