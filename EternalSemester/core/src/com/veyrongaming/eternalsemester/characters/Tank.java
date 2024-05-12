package com.veyrongaming.eternalsemester.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.veyrongaming.eternalsemester.EternalSemester;

public class Tank extends Player {
    public static final int TANK_WIDTH = 126;
    public static final int TANK_HEIGHT = 39;
    public static final int SCALE = 3;
    public static final int HEAL = 50;

    public static String name = "Tank";
    public static int maxHealth = 300;
    public static int initialSpeed = 300;
    public static float specialCooldownTank = 4f;

    public float speedBonusTimer = 0;

    public Texture idle = new Texture("Ball and Chain Bot/idle.png");
    public Texture attack = new Texture("Ball and Chain Bot/idle attack.png");
    public Texture hit = new Texture("Ball and Chain Bot/hit.png");
    public Texture death = new Texture("Ball and Chain Bot/death.png");
    public Texture run = new Texture("Ball and Chain Bot/run.png");

    public Tank(EternalSemester game, World world) {
        super(game, name, maxHealth, initialSpeed, specialCooldownTank, world);
        
        createAnimation();
    }

    private void createAnimation() {
        animations = new Animation[5];
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

        for (int i = 0; i < 4; i++) {
            runAnimation[i] = runSheet[i][0];
        }

        animations[0] = new Animation<>(0.1f, deathAnimation);
        animations[1] = new Animation<>(0.1f, attackAnimation);
        animations[2] = new Animation<>(0.1f, hitAnimation);
        animations[3] = new Animation<>(0.1f, runAnimation);
        animations[4] = new Animation<>(0.1f, idleAnimation);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        speedBonusTimer -= delta;

        if (speedBonusTimer > 0) 
            speed = 2 * initialSpeed;
        else speed = initialSpeed;
    }

    @Override
    public void useSpecial() {
        if (specialTimer < 0) {
            health = Math.min(maxHealth, health + HEAL);
            speedBonusTimer = 1f;
            specialTimer = specialCooldown;
        }
    }

    @Override
    public TextureRegion getFrame(float delta) {
        currentState = getState();
        TextureRegion tr;

        switch (currentState) {
            case DEATH:
                tr = animations[0].getKeyFrame(stateTimer, false);
                break;
            case ATTACK:
                tr = animations[1].getKeyFrame(stateTimer, false);
                break;
            case HIT:
                tr = animations[2].getKeyFrame(stateTimer, true);
                break;
            case RUN:
                tr = animations[3].getKeyFrame(stateTimer, true);
                break;
            default:
                tr = animations[4].getKeyFrame(stateTimer, true);
                break;
        }

        if (!isFacingRight && !tr.isFlipX()) 
            tr.flip(true, false);
        else if (isFacingRight && tr.isFlipX())
            tr.flip(true, false);
        
        stateTimer = (currentState == previousState ? stateTimer + delta : 0);
        previousState = currentState;
        return tr;
    }

    @Override
    public void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(getPosition().x, getPosition().y);

        Body body = world.createBody(bodyDef);
		body.setFixedRotation(true);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(3 * 24 / 2, 3 * 24 / 2);
        body.createFixture(shape, 10.0f).setUserData(this);
        shape.dispose();

        this.body = body;
    }

    public void draw(float delta) {
        game.batch.draw(getFrame(delta), getPosition().x - 3*TANK_WIDTH/2, getPosition().y - 3*TANK_HEIGHT/2, 3*TANK_WIDTH, 3*TANK_HEIGHT);
        game.font.draw(game.batch, position.x + ", " + position.y, position.x, position.y);
    }

    
}
