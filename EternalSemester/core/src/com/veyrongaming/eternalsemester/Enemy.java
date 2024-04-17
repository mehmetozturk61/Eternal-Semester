package com.veyrongaming.eternalsemester;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.veyrongaming.eternalsemester.characters.Character;

public class Enemy {
	private Texture texture;
	private Vector2 position;
	private float speed;
	private float health;
	private float slowFactor;
	private float slowTimer;
	
	public Enemy(Texture texture, Vector2 spawnPosition, float speed, float health) {
		this.texture = texture;
		this.position = spawnPosition;
		this.speed = speed;
		this.health = health;
	}
	
	public void update(float delta, Character character) {
		// Move towards player
		Vector2 direction = character.getPosition().sub(position);
		direction.nor(); // Normalize to get unit vector
		position.add(direction.x * speed * delta, direction.y * speed * delta);

		if (slowTimer > 0) {
			slowTimer -= delta;
			if (slowTimer <= 0) {
				speed /= slowFactor;
			}
		}
		
		// Implement attack logic
		// ...
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(texture, position.x - texture.getWidth() / 2f, position.y - texture.getHeight() / 2f);
	}

	public void applySlow(float slowFactor, float duration) {
		this.speed *= slowFactor;
		this.slowFactor = slowFactor;
		this.slowTimer = duration;
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

	public Rectangle getHitBox() {
		return new Rectangle(position.x - texture.getWidth() / 2, position.y - texture.getHeight() / 2, texture.getWidth(), texture.getHeight());
	}

	public Texture getTexture() {
		return texture;
	}
}