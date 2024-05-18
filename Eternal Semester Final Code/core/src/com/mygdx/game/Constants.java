package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Constants {
	public static final int VIEWPORT_WIDTH = 1600;
	public static final int VIEWPORT_HEIGHT = 900;
	public static final float ENEMY_SPAWN_BUFFER = 150f;
	public static final float PPM = 6f;
	public static boolean enterGame = false;
	public static boolean openLeaderboard = false;
	public static boolean swapPlayer = false;
	public static boolean isTank = true;
	public static Music gameMusic = Gdx.audio.newMusic(Gdx.files.internal("gamee.mp3"));
	public static Music bossMusic = Gdx.audio.newMusic(Gdx.files.internal("bossmusic.mp3"));
	public static Music music = Gdx.audio.newMusic(Gdx.files.internal("EternalSemesterThemeSong.mp3"));
	public static Sound soundEffect = Gdx.audio.newSound(Gdx.files.internal("butonefekt.mp3"));

	//public static Tank player = new Tank();
}
