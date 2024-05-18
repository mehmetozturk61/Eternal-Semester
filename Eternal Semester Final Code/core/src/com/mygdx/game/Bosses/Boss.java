package com.mygdx.game.Bosses;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Constants;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.characters.Player;

public abstract class Boss {
    public EternalSemester game;
    public World world;
    public Body body;
    public Player player;
    public Vector2 position;
    public Vector2 direction;
    public String name;
    public int health;
    public int speed;
    public int xp;
    public Animation<TextureRegion> animations[];
    public boolean isHit = false;
    public float stateTimer = 0;
    public boolean isFacingRight = true;

    public Boss(EternalSemester game, World world, Player player, String name, int health, int speed, int xp) {
        this.game = game;
        this.world = world;
        this.player = player;
        this.name = name;
        this.health = health;
        this.speed = speed;
        this.position = new Vector2(player.getPosition().add(60,60));
        this.direction = new Vector2( 0 , 0);
        this.xp = xp;;

        createBody();

    }

    public abstract void update(float delta, Player player);
    public abstract void attack1(float delta, Player player);
    public abstract void createBody();
    public abstract void draw(float delta);


    public boolean isDead() {
        return health <= 0;
    }

    public void takeDamage(float damage) {
        health -= damage;
    }

    public Vector2 getPosition() {
        return new Vector2(position.x, position.y);
    }

    public void dispose() {
        world.destroyBody(body);
    }
}
