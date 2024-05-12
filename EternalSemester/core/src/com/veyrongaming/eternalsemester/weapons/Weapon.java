package com.veyrongaming.eternalsemester.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.GameScreen;
import com.veyrongaming.eternalsemester.characters.Character;

public abstract class Weapon {
	protected String name;
	protected float cooldown; // Time between attacks
	protected float cooldownTimeLeft = cooldown;
	protected float damage; // Base damage dealt
	protected float statetime;
	protected Vector2 direction;
	protected Character character;
	protected World world;
	protected Body body;
	
	public Weapon(String name, float cooldown, float damage) {
		this.name = name;
		this.cooldown = cooldown;
		this.damage = damage;
		this.direction = new Vector2(0, 0);
	}

	public void update(float delta, Character character, GameScreen screen) {
        cooldownTimeLeft -= delta;
		statetime += delta;

		if (cooldownTimeLeft == 50) {
			direction = character.getDirection();
		}

        if (cooldownTimeLeft <= 0) {
            attack(character, screen);
			statetime = 0;
            cooldownTimeLeft = cooldown;
        }
    }

	public abstract void attack(Character character, GameScreen gameWorld); // Abstract method for specific attack behavior
	public void draw(EternalSemester game, Character character) {}
	
	public String getName() {
		return name;
	}

	public float getDamage() {
		return damage;
	}

	public float getCooldownTimeLeft() {
		return cooldownTimeLeft;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public void setWorld(World world) {
		this.world = world;
	}
}