package com.mygdx.game.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.Constants;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.Screens.Levels;
import com.mygdx.game.characters.Player;

public class TribeWarrior extends Enemy {
    public static final int WIDTH = 62;
    public static final int HEIGHT = 69;

    public static String name = "TamedBeast";
    public static int maxHealth = 80;
    public static int speed = 53 / ((int)Constants.PPM / 3);
    public static int damage = 25;
    public static int xp = 12;
    public static float attackCooldown = 0.4f;
    public static float deathDuration = 1.6f;

    public Texture death = new Texture("Enemies/Tribe Warrior/Tribe Warrior-death.png");
    public Texture hit = new Texture("Enemies/Tribe Warrior/Tribe Warrior-hit.png");
    public Texture run = new Texture("Enemies/Tribe Warrior/Tribe Warrior-Walk.png");

    public TribeWarrior(EternalSemester game, World world, Player player, Levels level) {
        super(game, world, player, name, maxHealth, speed, damage, attackCooldown, deathDuration, xp , level);

        createAnimation();
    }

    private void createAnimation() {
        animations = new Animation[3];
        TextureRegion deathSheet[][] = TextureRegion.split(death, WIDTH, HEIGHT);
        TextureRegion hitSheet[][] = TextureRegion.split(hit, WIDTH, HEIGHT);
        TextureRegion runSheet[][] = TextureRegion.split(run, WIDTH, HEIGHT);
        TextureRegion deathAnimation[] = new TextureRegion[16];
        TextureRegion hitAnimation[] = new TextureRegion[1];
        TextureRegion runAnimation[] = new TextureRegion[12];

        for (int i = 0; i < 16; i++) {
            deathAnimation[i] = deathSheet[0][i];
        }

        hitAnimation[0] = hitSheet[0][0];


        for (int i = 0; i < 12; i++) {
            runAnimation[i] = runSheet[0][i];
        }

        animations[0] = new Animation<>(0.1f, deathAnimation);
        animations[1] = new Animation<>(0.1f, hitAnimation);
        animations[2] = new Animation<>(0.1f, runAnimation);
    }


    @Override
    public TextureRegion getFrame(float delta) {
        currentState = getState();
        TextureRegion tr;

        switch (currentState) {
            case DEATH:
                tr = animations[0].getKeyFrame(stateTimer, false);
                break;
            case HIT:
                tr = animations[1].getKeyFrame(stateTimer, true);
                break;
            default:
                tr = animations[2].getKeyFrame(stateTimer, true);
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

        CircleShape shape = new CircleShape();
        shape.setRadius(3 * 15/2 / Constants.PPM );

        FixtureDef fixture = new FixtureDef();
        fixture.density = 10f;
        fixture.shape = shape;
        body.createFixture(fixture).setUserData(this);

        /* PolygonShape shape = new PolygonShape();
        shape.setAsBox(3 * 18 / 2, 3 * 18 / 2); */
        shape.dispose();

        this.body = body;
    }

    @Override
    public void draw(float delta) {
        game.batch.draw(getFrame(delta), getPosition().x - (3*WIDTH/2)/ Constants.PPM   + (isFacingRight ? 15 : -15)/ Constants.PPM , getPosition().y - (3*HEIGHT/2)/ Constants.PPM  + 18/ Constants.PPM , 3*WIDTH/ Constants.PPM , 3*HEIGHT/ Constants.PPM );
        //game.font.draw(game.batch, health + "", position.x, position.y );
    }


}
