package pt.nunomcards.inkink.assetloader;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.LinkedList;
import java.util.List;

import pt.nunomcards.inkink.model.PaintColor;

/**
 * Created by nuno on 19/07/2018.
 */

public class PlayerAssetLoader {

    public static TextureRegion[] playerWhite=    new TextureRegion[4];
    public static TextureRegion[] playerRed=      new TextureRegion[4];
    public static TextureRegion[] playerOrange=   new TextureRegion[4];
    public static TextureRegion[] playerYellow=   new TextureRegion[4];
    public static TextureRegion[] playerGreen=    new TextureRegion[4];
    public static TextureRegion[] playerBlue=     new TextureRegion[4];
    public static TextureRegion[] playerPurple=   new TextureRegion[4];

    private static Animation playerWhiteAnim;
    private static Animation playerRedAnim;
    private static Animation playerOrangeAnim;
    private static Animation playerYellowAnim;
    private static Animation playerGreenAnim;
    private static Animation playerBlueAnim;
    private static Animation playerPurpleAnim;

    static{
        Texture texturePlayers = new Texture("player_sprite.png");
        int step = 16; //pixels
        float frameDuration = 0.2f;

        for(int color= 0; color<=7; color++) {
            for (int pos= 0; pos<=3; pos++){
                TextureRegion texture = new TextureRegion(texturePlayers, step * pos, step * color, step, step);
                switch(color){
                    case(0): playerWhite[pos]=texture;  break;
                    case(1): playerRed[pos]=texture;    break;
                    case(2): playerOrange[pos]=texture; break;
                    case(3): playerYellow[pos]=texture; break;
                    case(4): playerGreen[pos]=texture;  break;
                    case(5): playerBlue[pos]=texture;   break;
                    case(6): playerPurple[pos]=texture; break;
                }
            }
        }
        // Animation Creator
        playerWhiteAnim=    new Animation(frameDuration, playerWhite);
        playerWhiteAnim.setPlayMode( Animation.PlayMode.NORMAL);

        playerRedAnim=      new Animation(frameDuration, playerRed);
        playerRedAnim.setPlayMode( Animation.PlayMode.NORMAL);

        playerOrangeAnim=   new Animation(frameDuration, playerOrange);
        playerOrangeAnim.setPlayMode( Animation.PlayMode.NORMAL);

        playerYellowAnim=   new Animation(frameDuration, playerYellow);
        playerYellowAnim.setPlayMode( Animation.PlayMode.NORMAL);

        playerGreenAnim=    new Animation(frameDuration, playerGreen);
        playerGreenAnim.setPlayMode( Animation.PlayMode.NORMAL);

        playerBlueAnim=     new Animation(frameDuration, playerBlue);
        playerBlueAnim.setPlayMode( Animation.PlayMode.NORMAL);

        playerPurpleAnim=   new Animation(frameDuration, playerPurple);
        playerPurpleAnim.setPlayMode( Animation.PlayMode.NORMAL);
    }

    public static TextureRegion getKeyFrameTexture(PaintColor color, float elapsed){
        switch(color){
            case WHITE:
                return (TextureRegion) playerWhiteAnim.getKeyFrame(elapsed, true);
            case RED:
                return (TextureRegion) playerRedAnim.getKeyFrame(elapsed, true);
            case ORANGE:
                return (TextureRegion) playerOrangeAnim.getKeyFrame(elapsed, true);
            case YELLOW:
                return (TextureRegion) playerYellowAnim.getKeyFrame(elapsed, true);
            case GREEN:
                return (TextureRegion) playerGreenAnim.getKeyFrame(elapsed, true);
            case BLUE:
                return (TextureRegion) playerBlueAnim.getKeyFrame(elapsed, true);
            case PURPLE:
                return (TextureRegion) playerPurpleAnim.getKeyFrame(elapsed, true);
            default:
                return (TextureRegion) playerWhiteAnim.getKeyFrame(elapsed, true);
        }
    }
}