package com.veyrongaming.eternalsemester.weapons;

import com.badlogic.gdx.math.Vector2;
import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.GameScreen;
import com.veyrongaming.eternalsemester.characters.Character;

public abstract class Weapon {
	protected String name;
	protected float cooldown; // Time between attacks
	protected float cooldownTimeLeft = 0.1f;
	protected float damage; // Base damage dealt
	protected float statetime;
	protected Vector2 direction;
	
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
}