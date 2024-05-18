package com.mygdx.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
//import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Constants;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.Screens.Level1;
import com.mygdx.game.weapons.Skill;
import com.mygdx.game.weapons.Weapon;

public abstract class Player {
    public enum State {IDLE, RUN, ATTACK, HIT, DEATH, DASH}

    public static final float HIT_DURATION = 0.1f;

    public EternalSemester game;
    public Vector2 position;
    public Vector2 direction;
    public Vector2 targetPos = new Vector2(800, 450);
    public String name;
    public int maxHealth;
    public int health;
    public int speed;
    public int initialSpeed;
    public int damageResistence = 0;
    public int xp = 0;
    public float xpCoefficient = 1;
    public Animation<TextureRegion> animations[];
    public boolean isFacingRight = true;
    public boolean isHit = false;
    public float stateTimer = 0;
    public float specialCooldown;
    public float specialTimer = 0;
    public Body body;
    public World world;
    public Skill skill;
    public State currentState = State.IDLE;
    public State previousState = State.IDLE;

    public Player(EternalSemester game, String name, int maxHealth, int initialSpeed, float specialCooldown, World world , Vector2 pos) {
        this.game = game;
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.initialSpeed = initialSpeed;
        this.speed = initialSpeed;
        this.specialCooldown = specialCooldown;
        this.world = world;

        this.position = new Vector2( pos.x,  pos.y);
        this.direction = new Vector2();

        createBody();
    }

    public abstract void useSpecial();
    public abstract TextureRegion getFrame(float delta);
    public abstract void createBody();
    public abstract void draw(float delta);

    public void update(float delta) {
        specialTimer -= delta;

        move();
    }

    private void move() {
        if (!game.isUsingMouse) {
            direction.x = (isFacingRight ? 0.0000001f : -0.0000001f);
            direction.y = 0;


            if (Gdx.input.isKeyPressed(game.bindings[0]))
                direction.y++;
            if (Gdx.input.isKeyPressed(game.bindings[2]))
                direction.y--;
            
            if (Gdx.input.isKeyPressed(game.bindings[4])) {
                direction.x++;
                isFacingRight = true;
            }
            if (Gdx.input.isKeyPressed(game.bindings[3])) {
                direction.x--;
                isFacingRight = false;
            }

            body.setLinearVelocity(new Vector2(direction.x * speed, direction.y * speed));
        }
        // settings açık yön fare
        else {
            if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
                targetPos = new Vector2(position.x - (game.width / 2 - Gdx.input.getX()) / Constants.PPM , position.y - (game.height / 2 - game.height + Gdx.input.getY()) / Constants.PPM );
                System.out.println(targetPos);
                // Calculate direction vector from player to click position
                Vector2 clickDirection = targetPos.cpy().sub(getPosition());
                float fac = 0;
                if (Math.abs(clickDirection.x) > Math.abs(clickDirection.y))
                    fac = 1 / clickDirection.x;
                else fac = 1 / clickDirection.y;

                clickDirection.scl(Math.abs(fac));

                direction.x = clickDirection.x;
                direction.y = clickDirection.y;

                isFacingRight = (direction.x >= 0) ? true : false;
            }

            body.setLinearVelocity((targetPos.cpy().dst(getPosition()) < 2) ? (direction = new Vector2(0.0000001f, 0)) : direction.cpy().scl(speed));
        }

        position = body.getPosition();

        if(!game.isUsingMouse) {
            if (Gdx.input.isKeyJustPressed(game.bindings[1])) {
                useSpecial();
            }
        }
        else
        {
            if (Gdx.input.isButtonJustPressed(Buttons.RIGHT)) {
                useSpecial();
            }
        }
    }

    public State getState() {
        if (health <= 0)
            return State.DEATH;
        else if (skill != null && (skill.cooldownTimer <= 0 || (currentState == State.ATTACK && (stateTimer >= 0.0001f && stateTimer <= 0.4f))))
            return State.ATTACK;
        else if (isHit || (currentState == State.HIT && (stateTimer >= 0.001f && stateTimer <= HIT_DURATION)))
            return State.HIT;
        else if (Math.abs(direction.x) > 0.0001f || direction.y != 0)
            return State.RUN;
        else
            return State.IDLE;
    }

    public void takeDamage(int damage) {
        health -= damage * (1 - damageResistence / 100);
    }

    public void gainXP(int amount) {
        xp += (int) (amount * xpCoefficient);
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Vector2 getDirection() {
        return new Vector2(direction);
    }

    public Vector2 getPosition() {
        return new Vector2(position);
    }

    public void dispose() {
        world.destroyBody(body);
    }
}
