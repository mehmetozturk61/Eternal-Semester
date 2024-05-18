package com.mygdx.game.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Constants;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.characters.Player;

import java.util.Random;

public abstract class RandomSpawner extends Skill
{
    public EternalSemester game;
    public World world;
    public Body body;
    public Player player;
    public Vector2 position;
    public Vector2 direction;
    public float cooldown;
    public float stateTimer = 0;
    public String name;
    Random rand;

    public RandomSpawner(EternalSemester game, World world, Player player, String name, int damage) {
        this.game = game;
        this.world = world;
        this.player = player;
        this.cooldown = cooldown;
        this.name = name;
        this.damage = damage;
        this.position = player.getPosition();
        this.direction = player.getDirection();
        rand = new Random();
        createBody();

    }

    public void setRandomPosition() {

        float x;
        float y;
        x = rand.nextFloat(player.getPosition().x - (game.width / 3) / Constants.PPM, player.getPosition().x + (game.width / 3)/Constants.PPM);
        y = rand.nextFloat(player.getPosition().y - (game.height / 3 ) / Constants.PPM, player.getPosition().y + (game.height / 3 )/Constants.PPM);

        this.position = new Vector2(x, y);
    }

    public void update(float delta) {

    }

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
