package si.um.feri.tevzki.gameElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

import si.um.feri.tevzki.config.GameConfig;

public class Player extends Group {
    private Entity body;
    private Shovel shovel;
    private Array<Tile> tileGrid;


    private Array<Direction> dirStack = new Array<Direction>();
    private static final int STACK_SIZE = 4;


    public Player(TextureRegion region, Array<Tile> tileGrid, TextureRegion shovelRegion) {
        body = new Entity();
        body.region = region;

        float aspectRatio =(float) region.getRegionWidth() / region.getRegionHeight();
        GameConfig.PLAYER_WIDTH = GameConfig.PLAYER_HEIGHT*aspectRatio;

        body.setSize(GameConfig.PLAYER_WIDTH, GameConfig.PLAYER_HEIGHT);

        // Center origin
        body.setOrigin(getWidth() / 2f, getHeight() / 2f);

        shovel = new Shovel(shovelRegion);


        setPosition(GameConfig.WORLD_WIDTH/2f-getWidth()/2f, GameConfig.WORLD_HEIGHT/2f-getHeight()/2f);

        addActor(body);
        addActor(shovel);
        this.tileGrid = tileGrid;

    }

    @Override
    public void act(float delta) {
        super.act(delta);




        Vector2 movement =  handleMovement(delta);
        float dx = movement.x;
        float dy = movement.y;

        moveBy(dx, dy);
        handleCollision(dx, dy);
    }

    private Vector2 handleMovement(float delta) {
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
                case LEFT: dx = -speed; break;
                case RIGHT: dx = speed; break;
                case UP: dy = speed; break;
                case DOWN: dy = -speed; break;
            }
        }

        return new Vector2(dx, dy);
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

    private void handleCollision(float dx, float dy) { // Check for collision with tiles
        Iterator<Tile> iter = tileGrid.iterator(); // tileGrid is your Array<Tile>

        while (iter.hasNext()) {
            Tile tile = iter.next();
            if (shovel.collidesWith(tile)) {
                tile.remove();
                iter.remove();
            }
        }
    }


}
