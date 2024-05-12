package com.veyrongaming.eternalsemester.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.veyrongaming.eternalsemester.EternalSemester;

public class Tank extends Player {
    public static final int TANK_WIDTH = 126;
    public static final int TANK_HEIGHT = 39;
<<<<<<< Updated upstream
    public static final int MAX_HEALTH = 300;
    public static final float SPEED = 500f;
=======
>>>>>>> Stashed changes

    public static String name = "Tank";
    public static int maxHealth = 300;
    public static int speed = 100;
    public static float specialCooldown = 4f;

    public float speedBonusTimer = 0;

    public Texture idle = new Texture("Ball and Chain Bot/idle.png");
    public Texture attack = new Texture("Ball and Chain Bot/idle attack.png");
    public Texture hit = new Texture("Ball and Chain Bot/hit.png");
    public Texture death = new Texture("Ball and Chain Bot/death.png");
    public Texture run = new Texture("Ball and Chain Bot/run.png");

<<<<<<< Updated upstream
    public Tank(EternalSemester game, String name, World world) {
        super(game, name, MAX_HEALTH, SPEED, new BattleAxe(), world);
        this.specialAbilityCooldown = 2000f;
        animations = new Animation[8];
=======
    public Tank(EternalSemester game, World world) {
        super(game, name, maxHealth, speed, specialCooldown, world);
        
        createAnimation();
    }

    private void createAnimation() {
        animations = new Animation[5];
>>>>>>> Stashed changes
        TextureRegion idleSheet[][] = TextureRegion.split(idle, TANK_WIDTH, TANK_HEIGHT);
        TextureRegion attackSheet[][] = TextureRegion.split(attack, TANK_WIDTH, TANK_HEIGHT);
        TextureRegion hitSheet[][] = TextureRegion.split(hit, TANK_WIDTH, TANK_HEIGHT);
        TextureRegion deathSheet[][] = TextureRegion.split(death, TANK_WIDTH, TANK_HEIGHT);
        TextureRegion runSheet[][] = TextureRegion.split(run, TANK_WIDTH, TANK_HEIGHT);
        TextureRegion idleAnimation[] = new TextureRegion[5];
        TextureRegion attackAnimation[] = new TextureRegion[4];
        TextureRegion hitAnimation = hitSheet[1][0];
        TextureRegion deathAnimation[] = new TextureRegion[5];
        TextureRegion runAnimation[] = new TextureRegion[4];

        for (int i = 0; i < 5; i++) {
            idleAnimation[i] = idleSheet[i][0];
        }
        
        for (int i = 0; i < 4; i++) {
            attackAnimation[i] = attackSheet[i][0];
        }

        for (int i = 0; i < 5; i++) {
            deathAnimation[i] = deathSheet[i][0];
        }

<<<<<<< Updated upstream
        animations[0] = new Animation<>(40f, idleAnimation);
        animations[1] = new Animation<>(50f, attackAnimation);
        animations[2] = new Animation<>(50f, hitAnimation);
        animations[3] = new Animation<>(40f, deathAnimation);
=======
        for (int i = 0; i < 4; i++) {
            runAnimation[i] = runSheet[i][0];
        }

        animations[0] = new Animation<>(0.1f, idleAnimation);
        animations[1] = new Animation<>(0.1f, attackAnimation);
        animations[2] = new Animation<>(0.1f, hitAnimation);
        animations[3] = new Animation<>(0.1f, deathAnimation);
        animations[4] = new Animation<>(0.1f, runAnimation);
>>>>>>> Stashed changes
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void useSpecial() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'useSpecial'");
    }

    @Override
    public void draw() {
<<<<<<< Updated upstream
        boolean isIdle = (startingWeapon.getCooldownTimeLeft() > 50f);
        boolean isHit = (lastHit < 25f);

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
            speedBonusTimer = 200f;
            specialAbilityTimer = specialAbilityCooldown;
            System.out.println("Special ability used.");
            System.out.println(health);
        }
=======
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }

    @Override
    public TextureRegion getFrame(float delta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFrame'");
>>>>>>> Stashed changes
    }

    @Override
    public void createBody() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createBody'");
    }
    
}
