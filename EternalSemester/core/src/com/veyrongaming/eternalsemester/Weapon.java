package com.veyrongaming.eternalsemester;

public abstract class Weapon {
	private String name;
	private float cooldown; // Time between attacks
	private float damage; // Base damage dealt
	
	public Weapon(String name, float cooldown, float damage) {
		this.name = name;
		this.cooldown = cooldown;
		this.damage = damage;
	}
	
	public abstract void attack(Player player, float delta); // Abstract method for specific attack behavior
	
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