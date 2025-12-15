package si.um.feri.tevzki.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import si.um.feri.tevzki.ShovelGame;
import si.um.feri.tevzki.assets.AssetDescriptors;
import si.um.feri.tevzki.assets.RegionNames;
import si.um.feri.tevzki.config.GameConfig;
import si.um.feri.tevzki.gameElements.Level;
import si.um.feri.tevzki.gameElements.Tile;
import si.um.feri.tevzki.gameElements.TileGrid;
import si.um.feri.tevzki.gameElements.Player;

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
    private OrthographicCamera levelCamera;
    private float zoom = 1f;
    private static final float ZOOM_MIN = 0.5f;
    private static final float ZOOM_MAX = 1f;
    private static final boolean debug = GameConfig.DEBUG;
    private Rectangle worldBorder;
    private Level level;


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
        levelCamera = new OrthographicCamera();
        worldBorder = new Rectangle(0,0,GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        levelViewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, levelCamera);
        levelStage = new Stage(levelViewport, game.getBatch());
        levelStage.setDebugAll(debug);

        // Asset manager
        assetManager.load(AssetDescriptors.GAME_ATLAS);
        assetManager.finishLoading();

        gameAtlas = assetManager.get(AssetDescriptors.GAME_ATLAS);

        // Background
        backgroundStage.addActor(createScreenBackground());
        levelStage.addActor(createLevelBackground());

        // TileGrid
        tileGrid = new TileGrid((int) GameConfig.WORLD_WIDTH, (int) GameConfig.WORLD_HEIGHT, gameAtlas, levelStage);

        // Add snow tiles from grid to stage
        for (int y = 0; y < tileGrid.height; y++) {
            for (int x = 0; x < tileGrid.width; x++) {
                Tile tile = tileGrid.grid[y][x];
                levelStage.addActor(tile);
            }
        }

        // Setup playable area
        level = Level.level1(gameAtlas);
//        level = new Level(4,4,4,4,gameAtlas);
        tileGrid.setLevel(level);

        player = new Player(gameAtlas, tileGrid, level);
        levelStage.addActor(player);
        setupInputHandlers();
    }

    private void setupInputHandlers() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean scrolled(float amountX, float amountY) {
                zoom += amountY * 0.1f;   // scroll speed
                zoom = MathUtils.clamp(zoom, ZOOM_MIN, ZOOM_MAX);

                levelCamera.zoom = zoom;
                levelCamera.update();
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                // Convert screen → world coordinates
                Vector3 worldCoords = new Vector3(screenX, screenY, 0);
                levelCamera.unproject(worldCoords);

                System.out.println(
                    "World coords: x=" + worldCoords.x + ", y=" + worldCoords.y
                );

                return true;
            }


        });
    }


    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        backgroundViewport.apply(true);

        Gdx.gl.glClearColor(0, 0, 0, 0); // alpha doesn't matter
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        backgroundStage.act(delta);
        backgroundStage.draw();

        moveCamera();

        levelStage.act(delta);
        levelStage.draw();

        if (debug) drawDebug(game.getShapeRenderer());
    }

    private void moveCamera() {
        float viewportWorldWidth  = levelCamera.viewportWidth  * levelCamera.zoom;
        float viewportWorldHeight = levelCamera.viewportHeight * levelCamera.zoom;

        float halfW = viewportWorldWidth  / 2f;
        float halfH = viewportWorldHeight / 2f;

        float worldW = GameConfig.WORLD_WIDTH;
        float worldH = GameConfig.WORLD_HEIGHT;

        float targetX = player.getX() + player.getWidth()  / 2f;
        float targetY = player.getY() + player.getHeight() / 2f;

        // X axis
        if (viewportWorldWidth >= worldW) {
            levelCamera.position.x = worldW / 2f;
        } else {
            levelCamera.position.x = MathUtils.clamp(
                targetX,
                halfW,
                worldW - halfW
            );
        }

        // Y axis
        if (viewportWorldHeight >= worldH) {
            levelCamera.position.y = worldH / 2f;
        } else {
            levelCamera.position.y = MathUtils.clamp(
                targetY,
                halfH,
                worldH - halfH
            );
        }

        levelCamera.update();
    }

    private void drawDebug(ShapeRenderer shapeRenderer) {
        // ===== LEVEL CAMERA (world space) =====
        shapeRenderer.setProjectionMatrix(levelStage.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(
            player.shovel.rect.x,
            player.shovel.rect.y,
            player.shovel.rect.width,
            player.shovel.rect.height
        );

        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(
            worldBorder.x,
            worldBorder.y,
            worldBorder.width,
            worldBorder.height
        );

        shapeRenderer.setColor(Color.GOLD);
        shapeRenderer.rect(
            level.offsetX,
            level.offsetY,
            level.width,
            level.height
        );

        shapeRenderer.end();

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
    private Actor createScreenBackground() {
        TextureRegion region = gameAtlas.findRegion(RegionNames.BACKGROUND_TILE);

        Texture bgTexture = region.getTexture();
        bgTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        TiledDrawable tiledBg = new TiledDrawable(region);

        Image background = new Image(tiledBg);
        background.setFillParent(true); // fills the whole stage

        return background;
    }

    private Actor createLevelBackground() {
        TextureRegion region = gameAtlas.findRegion(RegionNames.BACKGROUND);

        Image background = new Image(region);
        background.setFillParent(true); // fills the whole stage

        return background;
    }


}
