package com.veyrongaming.eternalsemester.weapons;

import com.veyrongaming.eternalsemester.GameWorld;
import com.veyrongaming.eternalsemester.characters.Character;

public abstract class Weapon {
	protected String name;
	protected float cooldown; // Time between attacks
	protected float damage; // Base damage dealt
	
	public Weapon(String name, float cooldown, float damage) {
		this.name = name;
		this.cooldown = cooldown;
		this.damage = damage;
	}
	
	public abstract void attack(Character character, GameWorld gameWorld); // Abstract method for specific attack behavior
	
	public boolean isReady(float elapsedTime) {
		return elapsedTime >= cooldown;
	}
	
	public String getName() {
		return name;
	}
	
	public float getDamage() {
		return damage;
	}
}