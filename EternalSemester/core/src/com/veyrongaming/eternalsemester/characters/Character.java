package com.veyrongaming.eternalsemester.characters;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.veyrongaming.eternalsemester.Constants;
import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.weapons.Weapon;

public abstract class Character {
    private final EternalSemester game;
    private Texture texture;
    private Vector2 position;
	protected Vector2 direction;
    protected String name; // Character name
    protected int health; // Maximum health points
    protected float movementSpeed; // Movement speed
    protected Weapon startingWeapon; // Character's starting weapon
	protected ArrayList<Weapon> weapons;

    public Character(EternalSemester game, String name, int health, float movementSpeed, Weapon startingWeapon) {
        this.game = game;
        this.name = name;
        this.health = health;
        this.movementSpeed = movementSpeed;
        this.startingWeapon = startingWeapon;
		this.direction = new Vector2(0, 0);

		weapons = new ArrayList<Weapon>();
		weapons.add(startingWeapon);
        texture = new Texture(Gdx.files.internal("assassin.jpg"));
        setPosition(new Vector2(Constants.VIEWPORT_WIDTH / 2f, Constants.VIEWPORT_HEIGHT / 2f));
        
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
		if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
			Vector2 clickPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
			// Calculate direction vector from player to click position
			Vector2 clickDirection = clickPosition.sub(getPosition());
			clickDirection.nor(); // Normalize to get unit vector
			direction.x = clickDirection.x;
			direction.y = clickDirection.y;
		}
		
		// Combine input for diagonal movement
		float movementVectorLength = (float) Math.sqrt(direction.x * direction.x + direction.y * direction.y);
		if (movementVectorLength > 0) {
			direction.x /= movementVectorLength;
			direction.y /= movementVectorLength;
		}
		
		// Update player position based on input and speed
		position.add(direction.x * movementSpeed * delta, direction.y * movementSpeed * delta);
		
		// Clamp player position within level bounds

		
		// Collision detection and response (if using box2D)
		// ...
	}
	
	public void addWeapon(Weapon weapon) {
		weapons.add(weapon);
	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getDirection() {
		return direction;
	}

    public int getHealth() {
        return health;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public Weapon getStartingWeapon() {
        return startingWeapon;
    }

	public Texture getTexture() {
		return texture;
	}
}
