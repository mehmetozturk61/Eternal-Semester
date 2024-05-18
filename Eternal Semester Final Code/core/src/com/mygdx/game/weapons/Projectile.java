package com.mygdx.game.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Constants;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.characters.Player;
import com.mygdx.game.Enemies.Enemy;

import java.util.Random;

public abstract class Projectile extends Skill
{

    public EternalSemester game;
    public World world;
    public Body body;
    public Player player;
    public Vector2 position;
    public Vector2 direction;
    public float cooldown;
    public float cooldownTimer = 0;
    public float stateTimer = 0;


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

        body.setTransform(player.getPosition().add(new Vector2(75/ Constants.PPM*(float)Math.cos(direction.angleRad()) , 75/ Constants.PPM*(float)Math.sin(direction.angleRad()))), direction.angleRad());
        body.setLinearVelocity(direction.x * 500/ Constants.PPM , direction.y * 500/ Constants.PPM );
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
