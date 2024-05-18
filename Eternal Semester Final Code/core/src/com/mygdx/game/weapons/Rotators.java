package com.mygdx.game.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.characters.Player;

import java.util.ArrayList;
import java.util.Random;

public abstract class Rotators extends Skill{


    public EternalSemester game;
    public World world;
    public Player player;
    public Vector2 position;
    public Vector2 direction;
    public float cooldown;
    public float cooldownTimer = 0;
    public float stateTimer = 0;
    public ArrayList <Body> bodies;

    public Rotators(EternalSemester game, World world, Player player, int damage) {
        this.game = game;
        this.world = world;
        this.player = player;
        this.damage = damage;
        this.position = player.getPosition();

        bodies = new ArrayList<Body>();
        bodies.add(createBody(1));
        bodies.add(createBody(2));
        bodies.add(createBody(3));
    }

    public abstract void update(float delta);
    public abstract void draw(float delta);
    public abstract Body createBody(int x);

    public Vector2 getPosition() {
        return position.cpy();
    }

    public Vector2 getDirection() {
        return direction.cpy();
    }

    public void dispose() {
        for(Body body : bodies) {world.destroyBody(body);}
    }
}
