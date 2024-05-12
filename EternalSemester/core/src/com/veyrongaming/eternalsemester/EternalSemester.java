package com.veyrongaming.eternalsemester;
 
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class EternalSemester extends Game {
	public GameScreen gameScreen;
	public SpriteBatch batch;
	public BitmapFont font;

	@Override
	public void create() {
		setScreen(new GameScreen(this));
		batch = new SpriteBatch();
		font = new BitmapFont();
	}
	
	@Override
	public void render() {
		super.render();
<<<<<<< Updated upstream
		gameScreen.render(1f);
		gameScreen.update(5f);
=======
>>>>>>> Stashed changes
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
}
