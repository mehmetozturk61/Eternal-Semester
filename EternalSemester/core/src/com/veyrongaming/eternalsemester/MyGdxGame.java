package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Screens.KeyBindings;
import com.mygdx.game.Screens.LoginScreen;
import com.mygdx.game.Screens.MainMenu;
import com.mygdx.game.Screens.SettingsScreen;
import com.mygdx.game.Screens.SignUpScreen;




public class MyGdxGame extends Game {

	public MainMenu mainMenuScreen;
	public SettingsScreen settingsScreen;
	public KeyBindings keyBindingsScreen;
	public SignUpScreen signUpScreen;
	public LoginScreen loginScreen;

	public int width = 1920;//3024
    public int height = 1080;//1964

	public boolean isFullScreen = true;
	public boolean isUsingMouse = false;

	public Music music;
	public Float volume = (float) 100;

	public Sound soundEffect;
	public float soundEffectVolume = (float) 50;
	public long id;

	// Array of key bindings and their default keys
	public String[][] bindings = {
		{"Move Up", "W"},
		{"Special", "Space"},
		{"Move Down", "S"},
	   
		{"Move Right", "D"},
	
		{"Move Left", "A"},
		
		{"Play With Mouse", ""},
		
	};

	public void setWidth(int i)
	{
		width = i;
	}
	public void setHeight(int i)
	{
		height = i;
	}

	

	@Override
	public void create() {

		//camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
		//width = Gdx.graphics.getWidth();
		//height = Gdx.graphics.getHeight();
		//System.out.println(width);
		//System.out.println(height);
		music = Gdx.audio.newMusic(Gdx.files.internal("EternalSemesterThemeSong.mp3"));
		soundEffect = Gdx.audio.newSound(Gdx.files.internal("butonefekt.mp3"));
		music.play();
		music.setLooping(true);
		


		mainMenuScreen = new MainMenu(this);
		settingsScreen = new SettingsScreen(this);
		keyBindingsScreen = new KeyBindings(this);
		signUpScreen = new SignUpScreen(this);
		loginScreen = new LoginScreen(this);


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
