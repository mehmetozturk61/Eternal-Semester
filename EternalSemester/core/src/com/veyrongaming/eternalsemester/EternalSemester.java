package com.veyrongaming.eternalsemester;
 
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EternalSemester extends Game {
	public GameScreen gameScreen;
	public SpriteBatch batch;
	public BitmapFont font;
	
	@Override
	public void create() {
		gameScreen = new GameScreen(this);
		batch = new SpriteBatch();
		font = new BitmapFont();
	}
	
	@Override
	public void render() {
		super.render();
		gameScreen.render(1/144f);
		gameScreen.update(1/144f);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		gameScreen.dispose();
	}
}
