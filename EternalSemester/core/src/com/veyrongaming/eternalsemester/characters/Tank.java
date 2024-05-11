package com.veyrongaming.eternalsemester.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.weapons.BattleAxe;

public class Tank extends Character {
    public static final int TANK_WIDTH = 126;
    public static final int TANK_HEIGHT = 39;
    public static final int MAX_HEALTH = 300;
    public static final float SPEED = 50000f;

    private float speedBonusTimer = 0;


    Texture idle = new Texture("Ball and Chain Bot/idle.png");
    Texture attack = new Texture("Ball and Chain Bot/idle attack.png");
    Texture hit = new Texture("Ball and Chain Bot/hit.png");
    Texture death = new Texture("Ball and Chain Bot/death.png");

    public Tank(EternalSemester game, String name, World world) {
        super(game, name, MAX_HEALTH, SPEED, new BattleAxe(), world);
        this.specialAbilityCooldown = 2f;
        animations = new Animation[8];
        TextureRegion idleSheet[][] = TextureRegion.split(idle, TANK_WIDTH, TANK_HEIGHT);
        TextureRegion attackSheet[][] = TextureRegion.split(attack, TANK_WIDTH, TANK_HEIGHT);
        TextureRegion hitSheet[][] = TextureRegion.split(hit, TANK_WIDTH, TANK_HEIGHT);
        TextureRegion deathSheet[][] = TextureRegion.split(death, TANK_WIDTH, TANK_HEIGHT);
        TextureRegion idleAnimation[] = new TextureRegion[5];
        TextureRegion attackAnimation[] = new TextureRegion[4];
        TextureRegion hitAnimation = hitSheet[1][0];
        TextureRegion deathAnimation[] = new TextureRegion[5];

        for (int i = 0; i < 5; i++) {
            idleAnimation[i] = idleSheet[i][0];
        }
        
        for (int i = 0; i < 4; i++) {
            attackAnimation[i] = attackSheet[i][0];
        }

        for (int i = 0; i < 5; i++) {
            deathAnimation[i] = deathSheet[i][0];
        }

        animations[0] = new Animation<>(0.05f, idleAnimation);
        animations[1] = new Animation<>(0.05f, attackAnimation);
        animations[2] = new Animation<>(0.05f, hitAnimation);
        animations[3] = new Animation<>(0.05f, deathAnimation);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        speedBonusTimer -= delta;

        if (speedBonusTimer > 0) {
            speed = 2 * SPEED;
        }
        else {
            speed = SPEED;
        }
    }

    @Override
    public void draw() {
        boolean isIdle = (startingWeapon.getCooldownTimeLeft() > 0.05f);
        boolean isHit = (lastHit < 0.1f);

        if (isDead() && isFacingRight) {
            game.batch.draw((TextureRegion) animations[3].getKeyFrame(deadtime, false), getPosition().x - 3*TANK_WIDTH/2, getPosition().y - 3*TANK_HEIGHT/2, 3*TANK_WIDTH, 3*TANK_HEIGHT);
        }
        else if (isDead() && !isFacingRight) {
            game.batch.draw((TextureRegion) animations[3].getKeyFrame(deadtime, false), getPosition().x + 3*TANK_WIDTH/2, getPosition().y - 3*TANK_HEIGHT/2, - 3*TANK_WIDTH, 3*TANK_HEIGHT);
        }
        else if (isHit && isFacingRight) {
            game.batch.draw((TextureRegion) animations[2].getKeyFrame(statetime, false), getPosition().x - 3*TANK_WIDTH/2, getPosition().y - 3*TANK_HEIGHT/2, 3*TANK_WIDTH, 3*TANK_HEIGHT);
        }
        else if (isHit && !isFacingRight) {
            game.batch.draw((TextureRegion) animations[2].getKeyFrame(statetime, false), getPosition().x + 3*TANK_WIDTH/2, getPosition().y - 3*TANK_HEIGHT/2, - 3*TANK_WIDTH, 3*TANK_HEIGHT);
        }
        else if (isIdle && isFacingRight) {
            game.batch.draw((TextureRegion) animations[0].getKeyFrame(statetime, true), getPosition().x - 3*TANK_WIDTH/2, getPosition().y - 3*TANK_HEIGHT/2, 3*TANK_WIDTH, 3*TANK_HEIGHT);
        }
        else if (isIdle && !isFacingRight) {
            game.batch.draw((TextureRegion) animations[0].getKeyFrame(statetime, true), getPosition().x + 3*TANK_WIDTH/2, getPosition().y - 3*TANK_HEIGHT/2, - 3*TANK_WIDTH, 3*TANK_HEIGHT);
        }
        else if (!isIdle && isFacingRight) {
            game.batch.draw((TextureRegion) animations[1].getKeyFrame(statetime, false), getPosition().x - 3*TANK_WIDTH/2, getPosition().y - 3*TANK_HEIGHT/2, 3*TANK_WIDTH, 3*TANK_HEIGHT);
        }
        else {
            game.batch.draw((TextureRegion) animations[1].getKeyFrame(statetime, false), getPosition().x + 3*TANK_WIDTH/2, getPosition().y - 3*TANK_HEIGHT/2, - 3*TANK_WIDTH, 3*TANK_HEIGHT);
        }

        startingWeapon.draw(game, this);
    }

    @Override
    public void useSpecialAbility() {
        System.out.println(health);
        if (specialAbilityTimer < 0) {
            heal(50);
            speedBonusTimer = 1f;
            specialAbilityTimer = specialAbilityCooldown;
            System.out.println("Special ability used.");
            System.out.println(health);
        }
    }

    private void heal(float amount) {
        health = Math.min(MAX_HEALTH, health + amount);
    }
}
