package si.um.feri.tevzki;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import si.um.feri.tevzki.screen.GameScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class ShovelGame extends Game {
    private AssetManager assetManager;
    private SpriteBatch batch;

    @Override
    public void create() {
        // Assets
        assetManager = new AssetManager();

        // Drawing
        batch = new SpriteBatch();

        setScreen(new GameScreen(this));
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
}
