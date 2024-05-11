package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Screens.MainMenu;
import com.mygdx.game.Screens.SettingsScreen;




public class MyGdxGame extends Game {

	public MainMenu mainMenuScreen;
	public SettingsScreen settingsScreen;

	

	@Override
	public void create() {

		//camera.setToOrtho(false, V_WIDTH, V_HEIGHT);

		mainMenuScreen = new MainMenu(this);
		settingsScreen = new SettingsScreen(this);

		setScreen(mainMenuScreen);
	}
	
	public void dispose() {
        
       
        mainMenuScreen.dispose();
        settingsScreen.dispose();
    }

	public void render() {
        super.render();

        }
    
	
}
