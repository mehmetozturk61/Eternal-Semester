package com.mygdx.game.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Constants;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.Bosses.WidowOfSin;
import com.mygdx.game.Screens.Levels;
import com.mygdx.game.characters.Player;

import java.util.Random;

public abstract class Enemy {
	public enum State {DEATH, RUN, HIT}

	public static final float HIT_DURATION = 0.05f;

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
	public int xp;
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
	public Random rand = new Random();
	public Levels level;
	public boolean cond = true;

	public Enemy(EternalSemester game, World world, Player player, String name, int health, int speed, int damage, float attackCooldown, float deathDuration, int xp , Levels level) {
		this.game = game;
		this.world = world;
		this.player = player;
		this.name = name;
		this.health = health;
		this.speed = speed;
		this.damage = damage;
		this.attackCooldown = attackCooldown;
		this.deathDuration = deathDuration;
		this.xp = xp;
		this.level = level;

		setRandomPosition();

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
		if(health <= 0 && cond)
		{
			body.setActive(false);
			level.killCount++;
			player.gainXP(xp);
			cond = false;
		}
		if(health <= 0)
		{
			body.setLinearVelocity(0, 0);
		}
		if (previousState == State.DEATH && stateTimer  >= deathDuration ) {
			level.enemies.remove(this);
			this.dispose();
		}
	}

	public void attack() {
		if (attackTimer >= attackCooldown) {
			player.takeDamage(damage);
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

	public void setRandomPosition() {
		float x;
		float y;
		do {
			x = rand.nextFloat(player.getPosition().x - (game.width / 2 + Constants.ENEMY_SPAWN_BUFFER) / Constants.PPM, player.getPosition().x + (game.width / 2 + Constants.ENEMY_SPAWN_BUFFER)/Constants.PPM);
			y = rand.nextFloat(player.getPosition().y - (game.height / 2 + Constants.ENEMY_SPAWN_BUFFER) / Constants.PPM, player.getPosition().y + (game.height / 2 + Constants.ENEMY_SPAWN_BUFFER)/Constants.PPM);
		}
		while ((x < 1140 || y < 810) || (x > 1100*2 || y > 785 *2) ||(x > player.getPosition().x - game.width / 2 / Constants.PPM && x < player.getPosition().x + game.width / 2 / Constants.PPM) &&
				(y > player.getPosition().y - game.height / 2 / Constants.PPM && y < player.getPosition().y + game.height / 2 / Constants.PPM));
		//(x < 6800 || y < 4870) || (x > 6690*2 || y > 4760 *2) ||
		this.position = new Vector2(x, y);
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
