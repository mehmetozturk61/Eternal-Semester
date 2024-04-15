package com.veyrongaming.eternalsemester.characters;

import com.veyrongaming.eternalsemester.weapons.Weapon;

public abstract class Character {
    protected String name; // Character name
    protected int health; // Maximum health points
    protected float movementSpeed; // Movement speed
    protected Weapon startingWeapon; // Character's starting weapon

    public Character(String name, int health, float movementSpeed, Weapon startingWeapon) {
        this.name = name;
        this.health = health;
        this.movementSpeed = movementSpeed;
        this.startingWeapon = startingWeapon;
    }

    public abstract void useSpecialAbility(); // Abstract method for character-specific ability

    public void takeDamage(int damage) {
        health -= damage;

        if (health <= 0) {
            // TODO Handle character death (play animation, remove from game world)
        }
    }

    public int getHealth() {
        return health;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public Weapon getStartingWeapon() {
        return startingWeapon;
    }
}
