package com.veyrongaming.eternalsemester.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.Bosses.WidowOfSin;
import com.veyrongaming.eternalsemester.characters.Character;
import com.veyrongaming.eternalsemester.characters.Player;

public abstract class Enemy {
	public enum State {DEATH, RUN, HIT}

	public static final float HIT_DURATION = 0.05f;

	public EternalSemester game;
	public World world;
	public Body body;
	public Player player;
    public Vector2 position = new Vector2(200,200);
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
	public float deathDuration;
	public boolean canAttack = false; // Whether the enemy can attack the player or not (range)
	public State currentState = State.RUN;
	public State previousState = State.RUN;
	
	public Enemy(EternalSemester game, World world, Player player, String name, int health, int speed, int damage, float attackCooldown, float deathDuration) {
		this.game = game;
		this.world = world;
		this.player = player;
		this.name = name;
		this.health = health;
		this.speed = speed;
		this.damage = damage;
		this.attackCooldown = attackCooldown;
		this.deathDuration = deathDuration;
		this.position = new Vector2(200, 300);

		createBody();
	}
	
	public void update(float delta, Player player) {
		// Move towards player
		Vector2 direction = player.getPosition().sub(position);
		direction.nor(); // Normalize to get unit vector

		body.setLinearVelocity(direction.cpy().scl(speed));
		position = body.getPosition();

		isFacingRight = (direction.x >= 0);

		attackTimer += delta;


		if (canAttack) {
			attack();
		}
	}

	public void attack() {
		if (attackTimer >= attackCooldown) {
			player.health -= damage;
			player.isHit = true;
			attackTimer = 0;
		}
	}

	public abstract TextureRegion getFrame(float delta);
	public abstract void createBody();
	public abstract void draw(float delta);

	public State getState() {
        if (health <= 0)
            return State.DEATH;
        else if (isHit || (currentState == State.HIT && (stateTimer >= 0.001f && stateTimer <= HIT_DURATION)))
            return State.HIT;
        else
            return State.RUN;
    }

	/* public void applySlow(float slowFactor, float duration) {
		this.speed *= slowFactor;
		this.slowFactor = slowFactor;
		this.slowTimer = duration;
	} */
	
	/* public boolean isDead() {
		return health <= 0;
	}

	public void takeDamage(float damage) {
		health -= damage;
	} */

	public Vector2 getPosition() {
		return new Vector2(position.x, position.y);
	}
/* 
	public void setCanAttack(boolean canAttack, Character character) {
		this.canAttack = canAttack;
		this.character = character;
	} */
	
	public void dispose() {
		world.destroyBody(body);
	}
}