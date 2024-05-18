package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Constants;
import com.mygdx.game.EternalSemester;

public class Assassin extends Player {
    public static final int WIDTH = 90;
    public static final int HEIGHT = 37;
    public static final int SCALE = 3;

    public static String name = "Assassin";
    public static int maxHealth = 800;
    public static int initialSpeed = 300 / (int) Constants.PPM;
    public static float specialCooldownAssassin = 4f;

    public boolean isDashActive = false;
    public float dashDuration = 0.2f;
    public float dashTimer = 0.21f;

    public Texture all = new Texture("The SwordMaster/Sword Master Sprite Sheet 90x37 without VFX.png");

    public Assassin(EternalSemester game, World world, Vector2 posit) {
        super(game, name, maxHealth, initialSpeed, specialCooldownAssassin, world , posit);

        createAnimation();
    }

    private void createAnimation() {
        animations = new Animation[6];
        TextureRegion sheet[][] = TextureRegion.split(all, WIDTH, HEIGHT);
        TextureRegion idleAnimation[] = new TextureRegion[9];
        TextureRegion attackAnimation[] = new TextureRegion[6];
        TextureRegion hitAnimation = sheet[25][1];
        TextureRegion deathAnimation[] = new TextureRegion[6];
        TextureRegion runAnimation[] = new TextureRegion[8];
        TextureRegion dashAnimation[] = new TextureRegion[6];

        for (int i = 0; i < 9; i++) {
            idleAnimation[i] = sheet[1][i];
        }

        for (int i = 0; i < 6; i++) {
            attackAnimation[i] = sheet[10][i];
        }

        for (int i = 0; i < 6; i++) {
            deathAnimation[i] = sheet[26][i];
        }

        for (int i = 0; i < 8; i++) {
            runAnimation[i] = sheet[3][i];
        }

        for (int i = 0; i < 6; i++) {
            dashAnimation[i] = sheet[12][i];
        }

        animations[0] = new Animation<>(0.1f, deathAnimation);
        animations[1] = new Animation<>(0.2f, attackAnimation);
        animations[2] = new Animation<>(0.4f, hitAnimation);
        animations[3] = new Animation<>(0.1f, runAnimation);
        animations[4] = new Animation<>(0.1f, idleAnimation);
        animations[5] = new Animation<>(0.033f, dashAnimation);
    }
    public State getState() {
        if (health <= 0)
            return State.DEATH;
        else if (isDashActive)
            return State.DASH;
        else if (skill != null && (skill.cooldownTimer <= 0 || (currentState == State.ATTACK && (stateTimer >= 0.0001f && stateTimer <= 0.4f))))
            return State.ATTACK;
        else if (isHit || (currentState == State.HIT && (stateTimer >= 0.001f && stateTimer <= HIT_DURATION)))
            return State.HIT;
        else if (Math.abs(direction.x) > 0.0001f || direction.y != 0)
            return State.RUN;
        else
            return State.IDLE;
    }
    @Override
    public void update(float delta) {
        super.update(delta);

        if (dashTimer <= dashDuration) {
            body.setTransform(getPosition().add(direction.cpy().scl(0.5f * speed / Constants.PPM)), 0);
            position = body.getPosition();
            dashTimer += delta;
            isDashActive = true;
        }
        else isDashActive = false;
    }

    @Override
    public void useSpecial() {
        if (specialTimer < 0) {
            dashTimer = 0;
            specialTimer = specialCooldown;
        }
    }

    @Override
    public TextureRegion getFrame(float delta) {
        currentState = this.getState();
        TextureRegion tr;

        switch (currentState) {
            case DASH:
                tr = animations[5].getKeyFrame(stateTimer, false);
                break;
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
        game.batch.draw(getFrame(delta), getPosition().x - 3* WIDTH /2/Constants.PPM + (isFacingRight ? 52 : -52) / Constants.PPM, getPosition().y - 3* HEIGHT /2/Constants.PPM, 3* WIDTH /Constants.PPM, 3* HEIGHT /Constants.PPM);
        //game.font.draw(game.batch, health + "", position.x, position.y);
        //game.font.draw(game.batch, xp + "", position.x, position.y + 50);
    }

    @Override
    public void dispose() {
        super.dispose();
        all.dispose();
    }
}
