package com.veyrongaming.eternalsemester.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.veyrongaming.eternalsemester.Constants;
import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.Screens.Level1;
import com.veyrongaming.eternalsemester.weapons.Weapon;

public abstract class Player {
    public enum State {IDLE, RUN, ATTACK, HIT, DEATH}

    public static final float HIT_DURATION = 0.05f;

    public EternalSemester game;
    public Vector2 position;
    public Vector2 direction;
    public Vector2 targetPos = new Vector2(800, 450);
    public String name;
    public int health;
    public int speed;
    public Animation<TextureRegion> animations[];
    public boolean isFacingRight = true;
    public boolean isHit = false;
    public float stateTimer = 0;
    public float specialCooldown;
    public float specialTimer = 0;
    public Body body;
    public World world;
    public Weapon weapon;
    public State currentState = State.IDLE;
    public State previousState = State.IDLE;

    public Player(EternalSemester game, String name, int health, int speed, float specialCooldown, World world) {
        this.game = game;
        this.name = name;
        this.health = health;
        this.speed = speed;
        this.specialCooldown = specialCooldown;
        this.world = world;

        this.position = new Vector2(Constants.VIEWPORT_WIDTH / 2, Constants.VIEWPORT_HEIGHT / 2);
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
        if (true) {
            direction.x = (isFacingRight ? 0.0000001f : -0.0000001f);
            direction.y = 0;


            if (Gdx.input.isKeyPressed(Keys.W)) 
                direction.y++;
            if (Gdx.input.isKeyPressed(Keys.S))
                direction.y--;
            
            if (Gdx.input.isKeyPressed(Keys.D)) {
                direction.x++;
                isFacingRight = true;
            }
            if (Gdx.input.isKeyPressed(Keys.A)) {
                direction.x--;
                isFacingRight = false;
            }

            body.setLinearVelocity(direction.cpy().scl(speed));
        }
        // settings açık yön fare
        else {
            if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
                targetPos = new Vector2(position.x - Constants.VIEWPORT_WIDTH / 2 + Gdx.input.getX(), position.y - Constants.VIEWPORT_HEIGHT / 2 + Constants.VIEWPORT_HEIGHT - Gdx.input.getY());
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

        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            useSpecial();
        }

        isHit = false;
    }

    public State getState() {
        if (health <= 0)
            return State.DEATH;
        else if (weapon != null && (weapon.cooldownTimer <= 0 || (currentState == State.ATTACK && (stateTimer >= 0.001f && stateTimer <= weapon.ANIMATION_DURATION))))
            return State.ATTACK;
        else if (isHit || (currentState == State.HIT && (stateTimer >= 0.001f && stateTimer <= HIT_DURATION)))
            return State.HIT;
        else if (Math.abs(direction.x) > 0.0001f || direction.y != 0)
            return State.RUN;
        else
            return State.IDLE;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Vector2 getDirection() {
        return new Vector2(direction);
    }

    public Vector2 getPosition() {
        return new Vector2(position);
    }

    public void dispose() {
        
    }
}
