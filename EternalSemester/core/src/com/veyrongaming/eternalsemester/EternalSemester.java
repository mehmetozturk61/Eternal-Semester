package com.veyrongaming.eternalsemester;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.veyrongaming.eternalsemester.level.LevelManager;

public class EternalSemester extends Game {
	public SpriteBatch batch;
	public OrthographicCamera camera;
	public World world; 
	public Player player;
	public LevelManager levelManager;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		
		// Initialize world
		world = new World(new Vector2(0, 0), true);
		
		// Initialize Player and Level Manager
		player = new Player(this);
		levelManager = new LevelManager(this);
		
		// Load initial assets
	}
	
	@Override
	public void render() {
		super.render();
		batch.begin();
		
		// Update camera position to center on player
		camera.position.set(player.getPosition().x, player.getPosition().y, 0f);
		
		camera.update();
		
		// Draw level background
		levelManager.renderBackground(batch);
		
		// Draw player and other entities
		player.draw(batch);
		
		// Draw UI elements (health bar, score, etc.)
		
		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		// Dispose of other resources (textures, sounds)
	}
}
