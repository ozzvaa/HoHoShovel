package si.um.feri.tevzki.gameElements;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class Entity extends Actor {
    public TextureRegion region;
    public Rectangle rect = new Rectangle(); // Rectangle coordinates should be in World units

    protected float hitboxOffsetX = 0;
    protected float hitboxOffsetY = 0;
    protected float hitboxWidth;
    protected float hitboxHeight;


    @Override
    public void act(float delta) {
        super.act(delta);

        Vector2 worldPos = new Vector2(getX(), getY());

        if (getParent() != null) {
            // Convert local position to stage/world coordinates
            worldPos = localToStageCoordinates(new Vector2(0, 0));
        }

        rect.set( // Rect is always is World coordinates
            worldPos.x + hitboxOffsetX,
            worldPos.y + hitboxOffsetY,
            hitboxWidth > 0 ? hitboxWidth : getWidth(),
            hitboxHeight > 0 ? hitboxHeight : getHeight()
        );
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (region == null) {
            return;
        }
        batch.draw(
            region,
            getX(),
            getY(),
            getWidth(),
            getHeight()
        );
    }

    public boolean collidesWith(Entity other) {
        if (other == null) {
            return false;
        }
        return rect.overlaps(other.rect);
    }

    public float getCenterX() {
        return getX() + getWidth() / 2f;
    }

    public float getCenterY() {
        return getY() + getHeight() / 2f;
    }

    public Vector2 getCenter() {
        return new Vector2(getCenterX(), getCenterY());
    }
}
