package com.veyrongaming.eternalsemester;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.veyrongaming.eternalsemester.characters.Character;

public class Enemy {
	private Texture texture;
	private Vector2 position;
	private float attack;
	private float speed;
	private float health;
	private float slowFactor;
	private float slowTimer;
	private World world;
	private Body body;
	private float attackCooldown = 3f;
	private float attackTimer = 0;
	private boolean canAttack = false; // Whether the enemy can attack the player or not (range)
	private Character character;
	
	public Enemy(Texture texture, Vector2 spawnPosition, float attack, float speed, float health, World world) {
		this.texture = texture;
		this.position = spawnPosition;
		this.attack = attack;
		this.speed = speed;
		this.health = health;
		this.world = world;
		this.body = createBody();
	}
	
	public void update(float delta, Character character) {
		// Move towards player
		Vector2 direction = character.getPosition().sub(position);
		direction.nor(); // Normalize to get unit vector

		body.setLinearVelocity(new Vector2(direction.x * speed * delta, direction.y * speed * delta));
		position = body.getPosition();

		if (attackTimer > 0) {
			attackTimer -= delta;
		}

		if (canAttack) {
			attack(character);
		}
	}

	public void attack(Character character) {
		if (attackTimer <= 0) {
			character.takeDamage(attack);
			attackTimer = attackCooldown;
		}
	}

	private Body createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(getPosition().x, getPosition().y);

        Body body = world.createBody(bodyDef);
		body.setFixedRotation(true);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(texture.getWidth()/10f, texture.getHeight()/10f);
        body.createFixture(shape, 1.0f).setUserData(this);
        shape.dispose();

        return body;
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
		return new Vector2(position.x, position.y);
	}

	public Texture getTexture() {
		return texture;
	}

	public void setCanAttack(boolean canAttack, Character character) {
		this.canAttack = canAttack;
		this.character = character;
	}
	
	public void dispose() {
		texture.dispose();
		world.destroyBody(body);
	}
}