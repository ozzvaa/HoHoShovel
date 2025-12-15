package si.um.feri.tevzki.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetDescriptors {
    // Atlas
    public static final AssetDescriptor<TextureAtlas> GAME_ATLAS =
        new AssetDescriptor<TextureAtlas>(AssetPaths.GAME_ATLAS, TextureAtlas.class);

    public static final AssetDescriptor<Sound> INTRO_SOUND =
        new AssetDescriptor<Sound>(AssetPaths.INTRO_SOUND, Sound.class);

    private AssetDescriptors() {
    }
}
