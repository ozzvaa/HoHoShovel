package si.um.feri.tevzki.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import si.um.feri.tevzki.ShovelGame;
import si.um.feri.tevzki.assets.AssetDescriptors;
import si.um.feri.tevzki.assets.RegionNames;
import si.um.feri.tevzki.config.GameConfig;

public class IntroScreen extends ScreenAdapter {
    private static final float INTRO_DURATION_IN_SEC = 15;
    private final AssetManager assetManager;
    private ShovelGame game;
    private Stage stage;
    private Viewport viewport;
    private TextureAtlas gameAtlas;
    private Sound sound;
    private float duration;
    private boolean bubble1Shown = false;
    private boolean bubble2Shown = false;
    private boolean bubble3Shown = false;


    public IntroScreen(ShovelGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new ScreenViewport();
        stage = new Stage(viewport, game.getBatch());


        // Asset manager
        assetManager.load(AssetDescriptors.GAME_ATLAS);
        assetManager.load(AssetDescriptors.INTRO_SOUND);
        assetManager.finishLoading();


        gameAtlas = assetManager.get(AssetDescriptors.GAME_ATLAS);
        sound = assetManager.get(AssetDescriptors.INTRO_SOUND);

        sound.play();

        setIntroBackground();
        Gdx.input.setInputProcessor(stage);


    }

    private void setIntroBackground() {
        // BACKGROUND
        Image bg = new Image(gameAtlas.findRegion(RegionNames.INTRO_BG));
        bg.setPosition(0, 0);
        bg.setSize(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        stage.addActor(bg);

        // HUSBAND
        TextureRegion husbandRegion = gameAtlas.findRegion(RegionNames.HUSBAND_DOWN);
        Image husband = new Image(husbandRegion);
        husband.setPosition(300, 100); // adjust as needed
        husband.setSize(
            husbandRegion.getRegionWidth()*2,
            husbandRegion.getRegionHeight()*2
        );
        stage.addActor(husband);

        // WIFE
        TextureRegion wifeRegion = gameAtlas.findRegion(RegionNames.WIFE);
        Image wife = new Image(wifeRegion);
        wife.setPosition(700, 100); // adjust as needed
        wife.setSize(
            wifeRegion.getRegionWidth()*2,
            wifeRegion.getRegionHeight()*2
        );
        stage.addActor(wife);

        // DEBUG CLICK (optional)
        bg.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Vector2 stageCoords = bg.localToStageCoordinates(new Vector2(x, y));
                System.out.println("Stage coords: " + stageCoords);
            }
        });
    }

    private Image setBubble(int id) {
        TextureRegion region;

        if (id == 0) {
            region = gameAtlas.findRegion(RegionNames.BUBBLE_1);
        } else if (id == 1) {
            region = gameAtlas.findRegion(RegionNames.BUBBLE_2);
        } else {
            region = gameAtlas.findRegion(RegionNames.BUBBLE_3);
        }

        Image bubble = new Image(region);

        // Positions
        if (id == 0) bubble.setPosition(350, 400);
        if (id == 1) bubble.setPosition(710, 380);
        if (id == 2) bubble.setPosition(434, 230);

        bubble.setSize(region.getRegionWidth(), region.getRegionHeight());

        // IMPORTANT for scaling animation
        bubble.setOrigin(bubble.getWidth() / 2f, bubble.getHeight() / 2f);

        // Initial state (hidden)
        bubble.getColor().a = 0f;
        bubble.setScale(0f);

        // Pop-in animation
        bubble.addAction(
            Actions.sequence(
                Actions.parallel(
                    Actions.fadeIn(0.4f),
                    Actions.scaleTo(1.1f, 1.1f, 0.4f)
                ),
                Actions.scaleTo(1f, 1f, 0.15f)
            )
        );

        return bubble;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(65 / 255f, 159 / 255f, 221 / 255f, 0f);

        duration += delta;

        // ⏱ TIMED EVENTS
        if (duration >= 1f && !bubble1Shown) {
            stage.addActor(setBubble(0));
            bubble1Shown = true;
        }

        if (duration >= 6.5f && !bubble2Shown) {
            stage.addActor(setBubble(1));
            bubble2Shown = true;
        }

        if (duration >= 11 && !bubble3Shown) {
            stage.addActor(setBubble(2));
            bubble3Shown = true;
        }

        stage.act(delta);
        stage.draw();


        if (duration > INTRO_DURATION_IN_SEC ) {
            // Fade out the whole group
            stage.getRoot().addAction(
                Actions.sequence(
                    Actions.fadeOut(1f),
                    Actions.run(() -> game.setScreen(new GameScreen(game)))
                )
            );

        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
