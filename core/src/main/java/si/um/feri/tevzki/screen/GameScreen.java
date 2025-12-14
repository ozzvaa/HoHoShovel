package si.um.feri.tevzki.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import java.util.Iterator;

import si.um.feri.tevzki.ShovelGame;
import si.um.feri.tevzki.assets.AssetDescriptors;
import si.um.feri.tevzki.assets.RegionNames;
import si.um.feri.tevzki.config.GameConfig;
import si.um.feri.tevzki.gameElements.TileGrid;
import si.um.feri.tevzki.gameElements.Player;
import si.um.feri.tevzki.gameElements.Tile;

/** First screen of the application.
 * Loads Assets and prepares systems. Is the Screen that creates the engine
 * Displayed after the application is created.
 *
 * */
public class GameScreen extends ScreenAdapter {
    private ShovelGame game;
    private AssetManager assetManager;
    private TextureAtlas gameAtlas;
    private Viewport backgroundViewport;
    private Viewport levelViewport;
    private Stage backgroundStage;
    private Stage levelStage;
    private Player player;
    private TileGrid tileGrid;

    public GameScreen(ShovelGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        // Background stage
        backgroundViewport = new FillViewport(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        backgroundStage = new Stage(backgroundViewport, game.getBatch());

        // Game stage
        levelViewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        levelStage = new Stage(levelViewport, game.getBatch());
        levelStage.setDebugAll(true);
        // Asset manager
        assetManager.load(AssetDescriptors.GAME_ATLAS);
        assetManager.finishLoading();

        gameAtlas = assetManager.get(AssetDescriptors.GAME_ATLAS);

        // Background
        backgroundStage.addActor(createBackground());

        // TileGrid
        tileGrid = new TileGrid(3, 3, gameAtlas);

        // Add snow tiles from grid to stage
        for (Actor tile: tileGrid.grid) {
            levelStage.addActor(tile);
        }

        player = new Player(gameAtlas.findRegion(RegionNames.HUSBAND), tileGrid.grid);
        levelStage.addActor(player);
    }




    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        backgroundViewport.apply(true);

        Gdx.gl.glClearColor(0, 0, 0, 0); // alpha doesn't matter
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        backgroundStage.act(delta);
        backgroundStage.draw();

        levelViewport.apply(true);
        levelStage.act(delta);
        levelStage.draw();

    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;
        backgroundViewport.update(width, height, true);
        levelViewport.update(width, height, true);

        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        backgroundStage.dispose();
        levelStage.dispose();
    }


    /**
     * Create a repeating background pattern Actor
     * */
    private Actor createBackground() {
        TextureRegion region = gameAtlas.findRegion(RegionNames.BACKGROUND_TILE);

        Texture bgTexture = region.getTexture();
        bgTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        TiledDrawable tiledBg = new TiledDrawable(region);

        Image background = new Image(tiledBg);
        background.setFillParent(true); // fills the whole stage

        return background;
    }


}
