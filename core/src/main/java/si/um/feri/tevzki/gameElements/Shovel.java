package si.um.feri.tevzki.gameElements;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


import si.um.feri.tevzki.config.GameConfig;

public class Shovel extends Entity{
    public SnowPile pile;
    public Shovel(TextureRegion shovelRegion, TextureRegion pileRegion) {
        this.region = shovelRegion;
//        float aspectRatio =(float) shovelRegion.getRegionWidth() / shovelRegion.getRegionHeight();
//        GameConfig.SHOVEL_WIDTH = GameConfig.SHOVEL_HEIGHT*aspectRatio;
        setSize(GameConfig.SHOVEL_WIDTH, GameConfig.SHOVEL_HEIGHT);

        // CENTER the shovel on the player
        // position relative to player
        setPosition(GameConfig.PLAYER_WIDTH/2f-getWidth()/2f,0);
        float pileWidth = getWidth();
        pile = new SnowPile(pileRegion, getShovelCenterX()-pileWidth/2, getY()+0.1f);
        pile.setSize(pileWidth,0);
        // 🔲 HITBOX ONLY AT THE BOTTOM
        hitboxWidth = GameConfig.SHOVEL_HEAD_WIDTH;
        hitboxHeight = GameConfig.SHOVEL_HEAD_HEIGHT;

        hitboxOffsetX = (GameConfig.SHOVEL_WIDTH -GameConfig.SHOVEL_HEAD_WIDTH)/2;
        hitboxOffsetY = 0;

    }

    public void moveTo(float targetX, float targetY) {
        float lerpFactor = 0.2f; // adjust for speed, 1 = instant, 0.1 = slow
        setPosition(
            getX() + (targetX - getX()) * lerpFactor,
            getY() + (targetY - getY()) * lerpFactor
        );
        pile.setPosition(getShovelCenterX()-pile.getWidth()/2, +0.1f);

    }

    public float getShovelCenterX() {
       return getX()+getWidth()/2;
    }
    public void shovelTile(Tile tile) {
        tile.setType(TileType.ROAD);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (pile != null && pile.region != null) {
            batch.draw(
                pile.region,
                pile.getX(),
                pile.getY(),
                pile.getWidth(),
                pile.getHeight()
            );
        }

    }
}
