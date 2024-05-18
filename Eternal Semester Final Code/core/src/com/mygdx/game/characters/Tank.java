package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Constants;
import com.mygdx.game.EternalSemester;

public class Tank extends Player {
    public static final int TANK_WIDTH = 126;
    public static final int TANK_HEIGHT = 39;
    public static final int SCALE = 3;
    public static final int HEAL = 100;

    public static String name = "Tank";
    public static int maxHealth = 1500;
    public static int initialSpeed = 225 / (int) Constants.PPM;
    public static float specialCooldownTank = 10f;

    public float speedBonusTimer = 0;

    public Texture idle = new Texture("Ball and Chain Bot/idle.png");
    public Texture attack = new Texture("Ball and Chain Bot/idle attack.png");
    public Texture hit = new Texture("Ball and Chain Bot/hit.png");
    public Texture death = new Texture("Ball and Chain Bot/death.png");
    public Texture run = new Texture("Ball and Chain Bot/run.png");

    public Tank(EternalSemester game, World world , Vector2 posit) {
        super(game, name, maxHealth, initialSpeed, specialCooldownTank, world , posit);
        
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
        //TextureRegion hitAnimation[] = new TextureRegion[2];
        TextureRegion hitAnimation = hitSheet [1][0];
        TextureRegion deathAnimation[] = new TextureRegion[5];
        TextureRegion runAnimation[] = new TextureRegion[4];

        for (int i = 0; i < 5; i++) {
            idleAnimation[i] = idleSheet[i][0];
        }
        
        for (int i = 0; i < 4; i++) {
            attackAnimation[i] = attackSheet[i][0];
        }

        /* for (int i = 0; i < 2; i++) {
            hitAnimation[i] = hitSheet[i][0];
        }
 */
        for (int i = 0; i < 5; i++) {
            deathAnimation[i] = deathSheet[i][0];
        }

        for (int i = 0; i < 4; i++) {
            runAnimation[i] = runSheet[i][0];
        }

        animations[0] = new Animation<>(0.1f, deathAnimation);
        animations[1] = new Animation<>(0.1f, attackAnimation);
        animations[2] = new Animation<>(0.4f, hitAnimation);
        animations[3] = new Animation<>(0.1f, runAnimation);
        animations[4] = new Animation<>(0.1f, idleAnimation);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        speedBonusTimer -= delta;

        if (speedBonusTimer > 0) 
            speed = super.initialSpeed * 3 / 2;
        else speed = super.initialSpeed;
    }

    @Override
    public void useSpecial() {
        if (specialTimer < 0) {
            health = Math.min(maxHealth, health + HEAL);
            speedBonusTimer = 3f;
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

        if (previousState == State.HIT)
            isHit = false;

        if (!isFacingRight && !tr.isFlipX()) 
            tr.flip(true, false);
        else if (isFacingRight && tr.isFlipX())
            tr.flip(true, false);
        
        stateTimer = (currentState == previousState ? stateTimer + delta : 0.001f);
        previousState = currentState;
        return tr;
    }

    @Override
    public void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(getPosition().x / Constants.PPM, getPosition().y / Constants.PPM);

        Body body = world.createBody(bodyDef);
		body.setFixedRotation(true);

        CircleShape shape = new CircleShape();
        shape.setRadius(3 * 24 / 2 / Constants.PPM);

        FixtureDef fixture = new FixtureDef();
        fixture.density = 100000f;
        fixture.shape = shape;
        shape.dispose();
        body.createFixture(fixture).setUserData(this);

        this.body = body;
    }

    public void draw(float delta) {
        game.batch.draw(getFrame(delta), getPosition().x - 3 * TANK_WIDTH/2/Constants.PPM, getPosition().y - 3*TANK_HEIGHT/2/Constants.PPM, 3*TANK_WIDTH/Constants.PPM, 3*TANK_HEIGHT/Constants.PPM);
        //game.font.draw(game.batch, position.x + "," + position.y, position.x, position.y);
        //game.font.draw(game.batch, health + "", position.x, position.y + 50);
    }

    @Override
    public void dispose() {
        super.dispose();
        idle.dispose();
        attack.dispose();
        hit.dispose();
        run.dispose();
        death.dispose();
    }    
}
