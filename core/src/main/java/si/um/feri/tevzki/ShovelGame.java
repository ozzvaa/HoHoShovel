package si.um.feri.tevzki;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import si.um.feri.tevzki.screen.GameScreen;
import si.um.feri.tevzki.screen.IntroScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class ShovelGame extends Game {
    private AssetManager assetManager;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        // Assets
        assetManager = new AssetManager();

        // Drawing
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        setScreen(new IntroScreen(this));
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        batch.dispose();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }
}
