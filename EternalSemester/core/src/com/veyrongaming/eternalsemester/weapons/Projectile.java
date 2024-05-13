package com.veyrongaming.eternalsemester.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.characters.Player;
import com.veyrongaming.eternalsemester.Enemies.Enemy;

import java.util.Random;

public abstract class Projectile
{
    public static float ANIMATION_DURATION;

    public EternalSemester game;
    public World world;
    public Body body;
    public Player player;
    public Vector2 position;
    public Vector2 direction;
    public float cooldown;
    public float cooldownTimer = 0;
    public float stateTimer = 0;
    public int damage;

    public Projectile(EternalSemester game, World world, Player player, int damage) {
        this.game = game;
        this.world = world;
        this.player = player;
        this.damage = damage;
        this.position = player.getPosition();

        Random rand = new Random();

        float x = (float)Math.random() * 10;
        float y = (float)Math.random() * 10;
        if(rand.nextInt() % 2 == 0)
            x = -x;
        if(rand.nextInt() % 2 == 0)
            y = -y;

        direction = new Vector2(x, y);

        createBody();

        body.setTransform(player.getPosition().add(new Vector2(75*(float)Math.cos(direction.angleRad()), 75*(float)Math.sin(direction.angleRad()))), direction.angleRad());
        body.setLinearVelocity(direction.x * 500 , direction.y * 500 );
    }

    public abstract void update(float delta);
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
