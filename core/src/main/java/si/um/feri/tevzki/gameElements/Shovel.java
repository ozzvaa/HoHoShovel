package si.um.feri.tevzki.gameElements;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import si.um.feri.tevzki.config.GameConfig;

public class Shovel extends Entity{

    public Shovel(TextureRegion region) {
        this.region = region;

//        float aspectRatio =(float) region.getRegionWidth() / region.getRegionHeight();
//        GameConfig.SHOVEL_WIDTH = GameConfig.SHOVEL_HEIGHT*aspectRatio;
        setSize(GameConfig.SHOVEL_WIDTH, GameConfig.SHOVEL_HEIGHT);

        // CENTER the shovel on the player
        setPosition(GameConfig.PLAYER_WIDTH/2f-GameConfig.SHOVEL_WIDTH/2f,0);

        // 🔲 HITBOX ONLY AT THE BOTTOM
        hitboxWidth = GameConfig.SHOVEL_WIDTH;
        hitboxHeight = GameConfig.SHOVEL_HEIGHT * 0.25f;

        hitboxOffsetX = 0;
        hitboxOffsetY = 0;

    }



}
