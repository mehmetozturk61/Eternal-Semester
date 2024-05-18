package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Screens.*;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EternalSemester extends Game {

	public MainMenu mainMenuScreen;
	public SettingsScreen settingsScreen;
	public KeyBindings keyBindingsScreen;
	public SignUpScreen signUpScreen;
	public LoginScreen loginScreen;
	public Level1 level1Screen;
	public Dorm dorm;
	public KeyCodeMap keyCodeMap;
	public String username = null;
	public Leaderboard leaderboardScreen;

	public int width = 1920;//3024
	public int height = 1080;//1964

	public boolean isFullScreen = true;
	public boolean isUsingMouse = false;

	public Float volume = (float) 100;

	public float soundEffectVolume = (float) 50;
	public long id;

	public SpriteBatch batch;
	public BitmapFont font;

	// Array of key bindings and their default keys
	/*public String[][] bindings = {
			{"Move Up", "W"},
			{"Special", "Space"},
			{"Move Down", "S"},

			{"Move Right", "D"},w

			{"Move Left", "A"},

			{"Play With Mouse", ""},

	};*/

	public Integer[] bindings = {51,62,47,29,32};

	public void setWidth(int i)
	{
		width = i;
	}
	public void setHeight(int i)
	{
		height = i;
	}
	public void updateUserPreferences(String username){
		bindings = DatabaseUtil.getKeyBindings(username);
		String[] resolution = ((String)DatabaseUtil.getResolution(username)).split("x");
		width = Integer.parseInt(resolution[0]);
		height = Integer.parseInt(resolution[1]);
		isFullScreen = DatabaseUtil.getIsFullscreen(username);
		isUsingMouse = DatabaseUtil.getPlayWithMouse(username);
	}


	@Override
	public void create() {

		//camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
		//width = Gdx.graphics.getWidth();
		//height = Gdx.graphics.getHeight();
		//System.out.println(width);
		//System.out.println(height);

		Constants.music.play();
		Constants.music.setLooping(true);

		batch = new SpriteBatch();
		font = new BitmapFont();



		mainMenuScreen = new MainMenu(this);
		settingsScreen = new SettingsScreen(this);
		keyBindingsScreen = new KeyBindings(this, keyCodeMap);
		signUpScreen = new SignUpScreen(this);
		loginScreen = new LoginScreen(this);
		//level1Screen = new Level1(this);
		leaderboardScreen = new Leaderboard(this);
		dorm = new Dorm(this , leaderboardScreen);

		setScreen(loginScreen);
	}

	public void dispose() {
		mainMenuScreen.dispose();
		settingsScreen.dispose();
	}

	public void render() {
		super.render();
	}
}
