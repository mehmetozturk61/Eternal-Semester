package com.veyrongaming.eternalsemester;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
	private Texture texture;
	private Vector2 position;
	private float speed;
	private float health;
	
	public Enemy(Texture texture, Vector2 spawnPosition, float speed, float health) {
		this.texture = texture;
		this.position = spawnPosition;
		this.speed = speed;
		this.health = health;
	}
	
	public void update(float delta, Player player) {
		// Move towards player
		Vector2 direction = player.getPosition().sub(position);
		direction.nor(); // Normalize to get unit vector
		position.add(direction.x * speed * delta, direction.y * speed * delta);
		
		// Implement attack logic
		// ...
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(texture, position.x - texture.getWidth() / 2f, position.y - texture.getHeight() / 2f);
	}
	
	public boolean isDead() {
		return health <= 0;
	}

	public void takeDamage(float damage) {
		health -= damage;
	}

	public Vector2 getPosition() {
		return position;
	}
}