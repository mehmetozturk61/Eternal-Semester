package com.veyrongaming.eternalsemester.level;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.veyrongaming.eternalsemester.Constants;
import com.veyrongaming.eternalsemester.Enemy;
import com.veyrongaming.eternalsemester.EternalSemester;

public class LevelManager {
	private final EternalSemester game;
	private Level currentLevel;
	private ArrayList<Enemy> enemies;
	private Random random;
	
	public LevelManager(EternalSemester game) {
		this.game = game;
		currentLevel = new Level1(game);
		enemies = new ArrayList<Enemy>();
		random = new Random();
	}
	
	public void update(float delta) {
		// Spawn logic
		spawnEnemies();
		
		// Update enemies
		for (Enemy enemy : enemies) {
			enemy.update(delta, game.player);
			
			// Check for collisions with player
			
			if (enemy.isDead()) {
				enemies.remove(i); // Remove dead enemies
			}
		}
	}
	
	private void spawnEnemies() {
		// Check for spawning conditions (e.g. time-based, wave-based)
		// ...
		
		// Spawn logic
		float spawnX = (Constants.VIEWPORT_WIDTH  - enemyTexture.getWidth()) / 2f + (2 * random.nextInt(1) - 1) * random.nextFloat(Constants.VIEWPORT_WIDTH / 2f, Constants.VIEWPORT_WIDTH / 2f + Constants.ENEMY_SPAWN_BUFFER); 
		float spawnY = (Constants.VIEWPORT_HEIGHT  - enemyTexture.getHeight()) / 2f + (2 * random.nextInt(1) - 1) * random.nextFloat(Constants.VIEWPORT_HEIGHT / 2f, Constants.VIEWPORT_HEIGHT / 2f + Constants.ENEMY_SPAWN_BUFFER); 
		enemies.add(new Enemy(enemyTexture, new Vector2(spawnX, spawnY), enemySpeed, enemyHealth));
	}
	
	public void renderBackground(SpriteBatch batch) {
		currentLevel.renderBackground(batch);
	}
	
	// Methods for handling level transitions, enemy spawning, etc.
}