package com.mygdx.game.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Constants;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.Screens.Levels;
import com.mygdx.game.characters.Player;

public class Hunter extends Enemy {
    public static final int WIDTH = 34;
    public static final int HEIGHT = 37;

    public static String name = "Hunter";
    public static int maxHealth = 150;
    public static int speed = 45 / ((int)Constants.PPM / 3);
    public static int damage = 50;
    public static int xp = 25;
    public static float attackCooldown = 0.5f;
    public static float deathDuration = 1.7f;

    public Texture death = new Texture("Enemies/Tribe Hunter/Tribe Hunter-death.png");
    public Texture hit = new Texture("Enemies/Tribe Hunter/Tribe Hunter-hit.png");
    public Texture run = new Texture("Enemies/Tribe Hunter/Tribe Hunter-walk.png");

    public Hunter(EternalSemester game, World world, Player player, Levels level) {
        super(game, world, player, name, maxHealth, speed, damage, attackCooldown, deathDuration, xp, level);

        createAnimation();
    }

    private void createAnimation() {
        animations = new Animation[3];
        TextureRegion deathSheet[][] = TextureRegion.split(death, WIDTH, HEIGHT);
        TextureRegion hitSheet[][] = TextureRegion.split(hit, WIDTH, HEIGHT);
        TextureRegion runSheet[][] = TextureRegion.split(run, WIDTH, HEIGHT);
        TextureRegion deathAnimation[] = new TextureRegion[17];
        TextureRegion hitAnimation[] = new TextureRegion[1];
        TextureRegion runAnimation[] = new TextureRegion[10];

        for (int i = 0; i < 17; i++) {
            deathAnimation[i] = deathSheet[0][i];
        }

        for (int i = 0; i < 1; i++) {
            hitAnimation[i] = hitSheet[0][i];
        }

        for (int i = 0; i < 10; i++) {
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
        shape.setRadius(3 * 18/2 / Constants.PPM );

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
        game.batch.draw(getFrame(delta), getPosition().x - (3*WIDTH/2) / Constants.PPM + (isFacingRight ? 9 : -9) / Constants.PPM , getPosition().y - (3*HEIGHT/2)/ Constants.PPM  + 9/ Constants.PPM , 3*WIDTH/ Constants.PPM , 3*HEIGHT/ Constants.PPM );
        //game.font.draw(game.batch, health + "", position.x, position.y);
    }
}
