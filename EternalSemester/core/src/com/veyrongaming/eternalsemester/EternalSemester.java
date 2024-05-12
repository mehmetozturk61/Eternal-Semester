package com.veyrongaming.eternalsemester;
 
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.veyrongaming.eternalsemester.Screens.Level1;

public class EternalSemester extends Game {
	public SpriteBatch batch;
	public BitmapFont font;

	@Override
	public void create() {
		setScreen(new Level1(this));
		batch = new SpriteBatch();
		font = new BitmapFont();
	}
	
	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
}
