package com.veyrongaming.eternalsemester.characters;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.veyrongaming.eternalsemester.Constants;
import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.weapons.Weapon;

public abstract class Character {
    protected final EternalSemester game; // Reference to the main game class
    protected Vector2 position; // Character's position in the game world
    protected String name; // Character name
    protected int health; // Maximum health points
    protected float speed; // Movement speed
    public Weapon startingWeapon; // Character's starting weapon
	protected Vector2 direction;
	protected ArrayList<Weapon> weapons;
	public Animation<TextureRegion> animations[];
	public boolean isFacingRight = true;
	public float statetime;
	public Body body;
	public World world;

    public Character(EternalSemester game, String name, int health, float speed, Weapon startingWeapon, World world) {
        this.game = game;
        this.name = name;
        this.health = health;
        this.speed = speed;
        this.startingWeapon = startingWeapon;
		this.world = world;

		this.position = new Vector2(Constants.VIEWPORT_WIDTH / 2f, Constants.VIEWPORT_HEIGHT / 2f);
		this.direction = new Vector2();
		this.body = createBody();
		
		weapons = new ArrayList<Weapon>();
		weapons.add(startingWeapon);
    }

    public abstract void useSpecialAbility(); // Abstract method for character-specific ability
	public abstract void draw();

    public void takeDamage(int damage) {
        health -= damage;

        if (health <= 0) {
            // TODO Handle character death (play animation, remove from game world)
        }
    }

    public void update(float delta) {
		statetime += delta;

		direction.x = (isFacingRight ? 1 : -1) * 0.001f;
		direction.y = 0;
		
		if (Gdx.input.isKeyPressed(Keys.W)) {
			direction.y = 1;
		} else if (Gdx.input.isKeyPressed(Keys.S)) {
			direction.y = -1;
		}
		
		if (Gdx.input.isKeyPressed(Keys.D)) {
			direction.x = 1;
			isFacingRight = true;
		} else if (Gdx.input.isKeyPressed(Keys.A)) {
			direction.x = -1;
			isFacingRight = false;
		}
		
		// Handle mouse click input
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			Vector2 clickPosition = new Vector2(Gdx.input.getX(), Constants.VIEWPORT_HEIGHT - Gdx.input.getY());
			// Calculate direction vector from player to click position
			Vector2 clickDirection = clickPosition.sub(getPosition());
			clickDirection.nor(); // Normalize to get unit vector
			direction.x = clickDirection.x;
			direction.y = clickDirection.y;
			
			if (direction.x >= 0)
				isFacingRight = true;
			else isFacingRight = false;
		}
				
		// Update player position based on input and speedss
		body.setLinearVelocity(new Vector2(1000 * direction.x * speed * delta, 1000 * direction.y * speed * delta));
		position = body.getPosition();

		// Special ability usage
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			useSpecialAbility();
		}
	}

	public Body createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(getPosition().x + 10, getPosition().y);

        Body body = world.createBody(bodyDef);
		body.setFixedRotation(true);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(3 * 24 / 2, 3 * 24 / 2);
        body.createFixture(shape, 10.0f);
        shape.dispose();

        return body;
    }
	
	public void addWeapon(Weapon weapon) {
		weapons.add(weapon);
	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public Vector2 getDirection() {
		return new Vector2(direction.x, direction.y);
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

	public Vector2 getPosition() {
		return new Vector2(position.x, position.y);
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public void dispose() {
		world.destroyBody(body);
	}
}
