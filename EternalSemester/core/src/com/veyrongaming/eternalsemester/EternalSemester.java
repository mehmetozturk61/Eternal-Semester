package com.veyrongaming.eternalsemester;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EternalSemester extends Game {
	public GameScreen gameScreen;
	public SpriteBatch batch;
	
	@Override
	public void create() {
		gameScreen = new GameScreen(this);
		batch = new SpriteBatch();
	}
	
	@Override
	public void render() {
		super.render();
		gameScreen.render(1f);
		gameScreen.update(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose() {
		super.dispose();
		gameScreen.dispose();
	}
}
