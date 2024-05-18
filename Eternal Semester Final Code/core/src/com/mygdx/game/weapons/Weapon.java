package com.mygdx.game.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.mygdx.game.Constants;
import com.mygdx.game.EternalSemester;

import com.mygdx.game.characters.Player;

public abstract class Weapon extends Skill{


	public EternalSemester game;
	public World world;
	public Body body;
	public Player player;
	public Vector2 position;
	public Vector2 direction;
	public float cooldown;
	public float stateTimer = 0;
	public String name;

	public Weapon(EternalSemester game, World world, Player player, float cooldown, String name, int damage) {
		this.game = game;
		this.world = world;
		this.player = player;
		this.cooldown = cooldown;
		this.name = name;
		this.damage = damage;
		this.position = player.getPosition();
		this.direction = player.getDirection();

		createBody();
	}

	public void update(float delta) {
		//body.setTransform(player.getPosition().add(new Vector2(75/ Constants.PPM*(float)Math.cos(direction.angleRad()), 75/ Constants.PPM*(float)Math.sin(direction.angleRad()))), direction.angleRad());
		//body.setTransform(body.getPosition(), direction.angleRad());
		//body.setTransform(player.getPosition().sub(new Vector2(75*(float)Math.cos(direction.angleDeg()), 75*(float)Math.sin(direction.angleDeg()))), body.getAngle());

		cooldownTimer += delta;

		if (cooldownTimer >= cooldown) {
			if(!game.isUsingMouse)
				direction = player.getDirection();
			else
				direction = new Vector2(Gdx.input.getX() - (game.width / 2)/ 1 , -Gdx.input.getY() + game.height / 2 / 1);
			cooldownTimer = 0;
			stateTimer = 0.001f;
			attack();
		}
	}

	public abstract void attack(); // Abstract method for specific attack behavior
	public abstract void draw(float delta);
	public abstract void createBody();

	public Vector2 getPosition() {
		return position.cpy();
	}

	public Vector2 getDirection() {
		return direction.cpy();
	}

	public void dispose() {
		world.destroyBody(body);
	}
}