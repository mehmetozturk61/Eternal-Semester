package com.veyrongaming.eternalsemester;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player {
	private final EternalSemester game;
	private Texture texture;
	private Vector2 position;
	private float speed;
	
	public Player(EternalSemester game) {
		this.game = game;
		texture = new Texture(Gdx.files.internal("player.png"));
		setPosition(new Vector2(Constants.VIEWPORT_WIDTH / 2f, Constants.VIEWPORT_HEIGHT / 2f));
		speed = 100f;
	}
	
	public void update(float delta) {
		// Handle WASD key input
		float xInput = 0;
		float yInput = 0;
		
		if (Gdx.input.isKeyPressed(Keys.W)) {
			yInput = 1;
		} else if (Gdx.input.isKeyPressed(Keys.S)) {
			yInput = -1;
		}
		
		if (Gdx.input.isKeyPressed(Keys.D)) {
			xInput = 1;
		} else if (Gdx.input.isKeyPressed(Keys.A)) {
			xInput = -1;
		}
		
		// Handle mouse click input
		if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
			Vector2 clickPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
			// Calculate direction vector from player to click position
			Vector2 direction = clickPosition.sub(getPosition());
			direction.nor(); // Normalize to get unit vector
			xInput = direction.x;
			yInput = direction.y;
		}
		
		// Combine input for diagonal movement
		float movementVectorLength = (float) Math.sqrt(xInput * xInput + yInput * yInput);
		if (movementVectorLength > 0) {
			xInput /= movementVectorLength;
			yInput /= movementVectorLength;
		}
		
		// Update player position based on input and speed
		getPosition().add(xInput * speed * delta, yInput * speed * delta);
		
		// Clamp player position within level bounds

		
		// Collision detection and response (if using box2D)
		// ...
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(texture, getPosition().x - texture.getWidth() / 2f, getPosition().y - texture.getHeight() / 2f);
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
}