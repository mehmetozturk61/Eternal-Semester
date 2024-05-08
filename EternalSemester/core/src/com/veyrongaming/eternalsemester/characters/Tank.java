package com.veyrongaming.eternalsemester.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.weapons.BattleAxe;

public class Tank extends Character {
    public static final int TANK_WIDTH = 126;
    public static final int TANK_HEIGHT = 39;


    Texture idle = new Texture("Ball and Chain Bot/idle.png");
    Texture attack = new Texture("Ball and Chain Bot/idle attack.png");

    public Tank(EternalSemester game, String name, World world) {
        super(game, name, 300, 0.5f, new BattleAxe(), world);
        animations = new Animation[8];
        TextureRegion idleSheet[][] = TextureRegion.split(idle, TANK_WIDTH, TANK_HEIGHT);
        TextureRegion attackSheet[][] = TextureRegion.split(attack, TANK_WIDTH, TANK_HEIGHT);
        TextureRegion idleAnimation[] = new TextureRegion[5];
        TextureRegion attackAnimation[] = new TextureRegion[4];

        for (int i = 0; i < 5; i++) {
            idleAnimation[i] = idleSheet[i][0];
        }
        
        for (int i = 0; i < 4; i++) {
            attackAnimation[i] = attackSheet[i][0];
        }

        animations[0] = new Animation<>(40f, idleAnimation);
        animations[1] = new Animation<>(50f, attackAnimation);
    }

    @Override
    public void draw() {
        boolean isIdle = (startingWeapon.getCooldownTimeLeft() > 50f);

        if (isIdle && isFacingRight) {
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
        // TODO Implement logic for Tank's heal and speed boost ability (e.g., increase health, movement speed for a duration) 
    }
}
