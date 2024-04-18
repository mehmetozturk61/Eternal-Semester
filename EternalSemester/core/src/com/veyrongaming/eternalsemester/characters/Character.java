package com.veyrongaming.eternalsemester.characters;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.veyrongaming.eternalsemester.Constants;
import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.weapons.Weapon;

public abstract class Character {
    protected final EternalSemester game; // Reference to the main game class
    protected Texture texture; // Character texture for rendering
    protected Vector2 position; // Character's position in the game world
    protected String name; // Character name
    protected int health; // Maximum health points
    protected float speed; // Movement speed
    protected Weapon startingWeapon; // Character's starting weapon
	protected Vector2 direction;
	protected ArrayList<Weapon> weapons;

    public Character(EternalSemester game, String name, int health, float speed, Weapon startingWeapon, Texture texture) {
        this.game = game;
        this.name = name;
        this.health = health;
        this.speed = speed;
        this.startingWeapon = startingWeapon;
		this.texture = texture;

		this.position = new Vector2(Constants.VIEWPORT_WIDTH / 2f, Constants.VIEWPORT_HEIGHT / 2f);
		this.direction = new Vector2(0, 0);
		
		weapons = new ArrayList<Weapon>();
		weapons.add(startingWeapon);
    }

    public abstract void useSpecialAbility(); // Abstract method for character-specific ability

    public void takeDamage(int damage) {
        health -= damage;

        if (health <= 0) {
            // TODO Handle character death (play animation, remove from game world)
        }
    }

    public void update(float delta) {
		direction.x = 0;
		direction.y = 0;
		
		if (Gdx.input.isKeyPressed(Keys.W)) {
			direction.y = 1;
		} else if (Gdx.input.isKeyPressed(Keys.S)) {
			direction.y = -1;
		}
		
		if (Gdx.input.isKeyPressed(Keys.D)) {
			direction.x = 1;
		} else if (Gdx.input.isKeyPressed(Keys.A)) {
			direction.x = -1;
		}
		
		// Handle mouse click input
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			Vector2 clickPosition = new Vector2(Gdx.input.getX(), Constants.VIEWPORT_HEIGHT - Gdx.input.getY());
			// Calculate direction vector from player to click position
			Vector2 clickDirection = clickPosition.sub(getPosition());
			clickDirection.nor(); // Normalize to get unit vector
			direction.x = clickDirection.x;
			direction.y = clickDirection.y;
		}
				
		// Update player position based on input and speed
		position.add(direction.x * speed * delta, direction.y * speed * delta);
		
		// Clamp player position within level bounds
		position.x = MathUtils.clamp(position.x, 0, Constants.VIEWPORT_WIDTH - getTexture().getWidth());
  		position.y = MathUtils.clamp(position.y, 0, Constants.VIEWPORT_HEIGHT - getTexture().getHeight());
	}
	
	public void addWeapon(Weapon weapon) {
		weapons.add(weapon);
	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public Vector2 getDirection() {
		return direction;
	}

    public int getHealth() {
        return health;
    }

    public float getSpeed() {
        return speed;
    }

    public Weapon getStartingWeapon() {
        return startingWeapon;
    }

	public Texture getTexture() {
		return texture;
	}

	public Vector2 getPosition() {
		return new Vector2(position.x, position.y);
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
}
