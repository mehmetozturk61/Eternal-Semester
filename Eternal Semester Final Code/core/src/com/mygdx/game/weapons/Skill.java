package com.mygdx.game.weapons;

public abstract class Skill {

    public int damage = 0;
    public float cooldown;
    public float cooldownTimer=0;

    public abstract void draw(float delta);

    public abstract void update(float delta);
}
