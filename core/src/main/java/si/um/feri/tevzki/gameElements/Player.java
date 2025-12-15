package si.um.feri.tevzki.gameElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

import si.um.feri.tevzki.assets.RegionNames;
import si.um.feri.tevzki.config.GameConfig;

public class Player extends Group {
    private static final int STACK_SIZE = 4;
    private static TextureRegion downRegion;
    private static TextureRegion upRegion;
    private static TextureRegion leftRegion;
    private static TextureRegion rightRegion;
    private static TextureRegion shovelDownRegion;
    private static TextureRegion shovelUpRegion;
    private static TextureRegion shovelLeftRegion;
    private static TextureRegion shovelRightRegion;
    public Shovel shovel;
    private Entity body;
    private TileGrid grid;
    private Array<Direction> dirStack = new Array<Direction>();
    private Array<Snowball> snowballs;
    private Level level;
    private int accumulatedSnowTotal = 0;
    private int accumulatedSnow = 0;
    private int strength;
    private boolean spacePressed;


    public Player(TextureAtlas gameAtlas, TileGrid tileGrid, Level level) {
        this.level = level;
        strength = GameConfig.PLAYER_STRENGTH;
        body = new Entity();

        downRegion = gameAtlas.findRegion(RegionNames.HUSBAND_DOWN);
        leftRegion = gameAtlas.findRegion(RegionNames.HUSBAND_LEFT);
        rightRegion = gameAtlas.findRegion(RegionNames.HUSBAND_RIGHT);
        upRegion = gameAtlas.findRegion(RegionNames.HUSBAND_TOP);

        shovelDownRegion = gameAtlas.findRegion(RegionNames.SHOVEL);
        shovelLeftRegion = gameAtlas.findRegion(RegionNames.SHOVEL_LEFT);
        shovelRightRegion = gameAtlas.findRegion(RegionNames.SHOVEL_RIGHT);
        shovelUpRegion = gameAtlas.findRegion(RegionNames.SHOVEL);

        body.region = downRegion;

        float aspectRatio = (float) body.region.getRegionWidth() / body.region.getRegionHeight();
        GameConfig.PLAYER_WIDTH = GameConfig.PLAYER_HEIGHT * aspectRatio;

        body.setSize(GameConfig.PLAYER_WIDTH, GameConfig.PLAYER_HEIGHT);

        // Center origin
//        body.setOrigin(getWidth() / 2f, getHeight() / 2f);

        setSize(GameConfig.PLAYER_WIDTH, GameConfig.PLAYER_HEIGHT);
        setPosition(level.playerX - getWidth() / 2f, level.playerY);

        addActor(body);

        shovel = new Shovel(shovelDownRegion, gameAtlas.findRegion(RegionNames.SNOW_PILE));
        addActor(shovel);

        this.grid = tileGrid;

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        handleMovement(delta);
        handleInput();
        handleCollision();
    }

    private void handleInput() {
        spacePressed = (Gdx.input.isKeyJustPressed(Input.Keys.SPACE));
    }

    private void handleMovement(float delta) {
        float dx = 0;
        float dy = 0;
        float speed = GameConfig.PLAYER_SPEED * delta;

        // 1️⃣ Add key just pressed to stack
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) pushDirection(Direction.LEFT);
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) pushDirection(Direction.RIGHT);
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) pushDirection(Direction.UP);
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) pushDirection(Direction.DOWN);

        // 2️⃣ Remove key if released
        if (!Gdx.input.isKeyPressed(Input.Keys.A)) removeDirection(Direction.LEFT);
        if (!Gdx.input.isKeyPressed(Input.Keys.D)) removeDirection(Direction.RIGHT);
        if (!Gdx.input.isKeyPressed(Input.Keys.W)) removeDirection(Direction.UP);
        if (!Gdx.input.isKeyPressed(Input.Keys.S)) removeDirection(Direction.DOWN);

        // 3️⃣ Move in the last pressed direction (top of stack)
        if (dirStack.size > 0) {
            Direction dir = dirStack.peek(); // last added


            switch (dir) {
                case LEFT:
                    dx = -speed;
                    body.region = leftRegion;
                    shovel.moveTo(-shovel.getWidth(), 0);
                    shovel.setZIndex(1);
                    shovel.region = shovelLeftRegion;
                    break;
                case RIGHT:
                    dx = speed;
                    body.region = rightRegion;
                    shovel.moveTo(getWidth(), 0);
                    shovel.setZIndex(1);
                    shovel.region = shovelRightRegion;
                    break;
                case UP:
                    dy = speed;
                    body.region = upRegion;
                    shovel.moveTo(getWidth() / 2 - shovel.getWidth() / 2, 0.1f);
                    shovel.setZIndex(0);
                    shovel.region = shovelUpRegion;
                    break;
                case DOWN:
                    dy = -speed;
                    body.region = downRegion;
                    shovel.moveTo(getWidth() / 2 - shovel.getWidth() / 2, 0);
                    shovel.setZIndex(1);
                    shovel.region = shovelDownRegion;
                    break;
            }
        }

        float newX = getX() + dx;
        float newY = getY() + dy;

        // World bounds
        if (newX < level.offsetX || newX + getWidth() > level.offsetX + level.width) {
            return;
        }
        if (newY < level.offsetY || newY > level.offsetY + level.height) {
            return;
        }

        moveBy(dx, dy);
    }

    private void pushDirection(Direction dir) {
        // avoid duplicates
        if (!dirStack.contains(dir, true)) {
            if (dirStack.size >= STACK_SIZE) {
                dirStack.removeIndex(0); // remove oldest
            }
            dirStack.add(dir);
        }
    }

    private void removeDirection(Direction dir) {
        dirStack.removeValue(dir, true);
    }

    private void handleCollision() { // Check for collision with tiles
        Vector2 shovelCenter = getShovelCenter();
        Tile[] nearTiles = grid.getCloseTiles(shovelCenter.x, shovelCenter.y);

        for (Tile tile : nearTiles) {
            if (shovel.collidesWith(tile)) {
                if (accumulatedSnow < strength && tile.type == TileType.SNOW) {
                    shovel.shovelTile(tile);
                    accumulatedSnowTotal++;
                    accumulatedSnow++;
                    shovel.pile.setHeight(GameConfig.PILE_SIZE*accumulatedSnow/strength);
                }

                if (spacePressed && tile.type == TileType.ICE && tile.pile == null && accumulatedSnow > 0) {
                    grid.addSnowPile(tile);
                    accumulatedSnow = 0;
                    shovel.pile.setHeight(0);

                }
            }
        }
    }

    public Vector2 getShovelCenter() {
        return shovel.localToStageCoordinates(
            new Vector2(
                shovel.getX() + shovel.getWidth() / 2f,
                shovel.getY() + shovel.getHeight() / 2f
            )
        );
    }


}
