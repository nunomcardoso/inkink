package pt.nunomcards.inkink.assetloader

import com.badlogic.gdx.Gdx

/**
 * Created by nuno on 20/07/2018.
 */
object AudioAssets {

    val mainScreenMusic = Gdx.audio.newSound(Gdx.files.internal("audio/mainscreen-music.mp3"))
    val spLevelMusic = Gdx.audio.newSound(Gdx.files.internal("audio/level01-music.mp3"))
    val mpLevelMusic = Gdx.audio.newSound(Gdx.files.internal("audio/level02-music.mp3"))
}