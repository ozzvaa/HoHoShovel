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
        // position relative to player
        setPosition(GameConfig.PLAYER_WIDTH/2f-getWidth()/2f,0);

        // 🔲 HITBOX ONLY AT THE BOTTOM
        hitboxWidth = GameConfig.SHOVEL_HEAD_WIDTH;
        hitboxHeight = GameConfig.SHOVEL_HEAD_HEIGHT;

        hitboxOffsetX = (GameConfig.SHOVEL_WIDTH -GameConfig.SHOVEL_HEAD_WIDTH)/2;
        hitboxOffsetY = GameConfig.TILE_BIT_SIZE/2;

    }

    public void moveTo(float targetX, float targetY) {
        float lerpFactor = 0.2f; // adjust for speed, 1 = instant, 0.1 = slow
        setPosition(
            getX() + (targetX - getX()) * lerpFactor,
            getY() + (targetY - getY()) * lerpFactor
        );

    }



}
