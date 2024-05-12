package com.veyrongaming.eternalsemester;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.veyrongaming.eternalsemester.characters.Character;
import com.veyrongaming.eternalsemester.characters.Player;

public class Enemy {
	public EternalSemester game;
	public World world;
	public Body body;
	public Player player;
    public Vector2 position;
    public Vector2 direction;
    public String name;
    public int health;
    public int speed;
	public int damage;
    public Animation<TextureRegion> animations[];
    public boolean isFacingRight = true;
    public boolean isHit = false;
    public float stateTimer = 0;
	public float attackCooldown;
	public float attackTimer = 0;
	private boolean canAttack = false; // Whether the enemy can attack the player or not (range)
	
	public Enemy(EternalSemester game, World world, Player player, String name, int health, int speed, int damage, float attackCooldown) {
		this.game = game;
		this.world = world;
		this.player = player;
		this.name = name;
		this.health = health;
		this.speed = speed;
		this.damage = damage;
		this.attackCooldown = attackCooldown;

		createBody();
	}
	
	public void update(float delta, Player player) {
		// Move towards player
		Vector2 direction = player.getPosition().sub(position);
		direction.nor(); // Normalize to get unit vector

		body.setLinearVelocity(direction.cpy().scl(speed));
		position = body.getPosition();

		attackTimer += delta;


		if (canAttack) {
			attack();
		}
	}

	public void attack() {
		if (attackTimer >= attackCooldown) {
			player.health -= damage;
			attackTimer = 0;
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