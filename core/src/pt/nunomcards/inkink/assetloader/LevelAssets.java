package pt.nunomcards.inkink.assetloader;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by nuno on 20/07/2018.
 */
public class LevelAssets {

    private static TextureRegion[] coinTexture = new TextureRegion[16];
    public static Animation coinAnim;
    public static Texture chest = new Texture("chest_sprite.png");
    public static Texture teleport = new Texture("holo_girl.gif");

    static{
        float frameDuration = 0.1f;
        Texture texturePlayers = new Texture("coin.png");
        int frames = 16;
        int step = texturePlayers.getHeight()/frames;

        for(int i = 0; i<16; i++) {
            TextureRegion texture = new TextureRegion(texturePlayers, 0, step * i, texturePlayers.getWidth(), step);
            coinTexture[i] = texture;
        }
        coinAnim = new Animation(frameDuration, coinTexture);
        coinAnim.setPlayMode( Animation.PlayMode.NORMAL);
    }
}