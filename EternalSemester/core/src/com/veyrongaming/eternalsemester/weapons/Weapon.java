package com.veyrongaming.eternalsemester.weapons;

import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.GameScreen;
import com.veyrongaming.eternalsemester.characters.Character;

public abstract class Weapon {
	protected String name;
	protected float cooldown; // Time between attacks
	protected float coolddownTimeLeft = 0.1f;
	protected float damage; // Base damage dealt
	
	public Weapon(String name, float cooldown, float damage) {
		this.name = name;
		this.cooldown = cooldown;
		this.damage = damage;
	}

	public void update(float delta, Character character, GameScreen screen) {
        coolddownTimeLeft -= delta;

        if (coolddownTimeLeft <= 0) {
            attack(character, screen);
            coolddownTimeLeft = cooldown;
        }
    }

	public abstract void attack(Character character, GameScreen gameWorld); // Abstract method for specific attack behavior
	public void draw(EternalSemester game, Character character, float statetime) {}

	public boolean isReady(float elapsedTime) {
		return elapsedTime >= cooldown;
	}
	
	public String getName() {
		return name;
	}

	public float getDamage() {
		return damage;
	}

	public float getCoolddownTimeLeft() {
		return coolddownTimeLeft;
	}
}