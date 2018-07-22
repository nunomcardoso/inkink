package pt.nunomcards.inkink.assetloader

import com.badlogic.gdx.Gdx

/**
 * Created by nuno on 20/07/2018.
 */
object AudioAssets {

    val mainScreenMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/mainscreen-music.mp3"))
    val spLevelMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/level01-music.mp3"))
    val mpLevelMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/level02-music.mp3"))

    val endLevel = Gdx.audio.newSound(Gdx.files.internal("audio/crystal.wav"))

    val selectSound = Gdx.audio.newSound(Gdx.files.internal("audio/select.wav"))
    val coinSound = Gdx.audio.newSound(Gdx.files.internal("audio/coin.wav"))
    val cannonSound = Gdx.audio.newSound(Gdx.files.internal("audio/cannon.wav"))
    val bombSound = Gdx.audio.newSound(Gdx.files.internal("audio/bomb.wav"))
    val chestOpenSound = Gdx.audio.newSound(Gdx.files.internal("audio/chest-open.wav"))

    init{
        mainScreenMusic.isLooping = true
        spLevelMusic.isLooping = true
        mpLevelMusic.isLooping = true
    }
}