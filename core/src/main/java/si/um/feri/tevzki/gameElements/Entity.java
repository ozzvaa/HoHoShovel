package si.um.feri.tevzki.gameElements;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class Entity extends Actor {
    public TextureRegion region;
    public Rectangle rect = new Rectangle();


    @Override
    public void act(float delta) {
        super.act(delta);
        rect.set(getX(), getY(), getWidth(), getHeight());
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

    public boolean collidesWith(Entity entity) {
        return rect.overlaps(entity.rect);
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
