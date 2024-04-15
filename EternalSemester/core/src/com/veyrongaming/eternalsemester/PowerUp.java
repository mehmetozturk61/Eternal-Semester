package com.veyrongaming.eternalsemester;

public abstract class PowerUp {
	private String name;
	private String description;
	
	public PowerUp(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public abstract void applyEffect(Player player);
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
}