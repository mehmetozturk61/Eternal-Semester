package com.mygdx.game.Bosses;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Constants;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.characters.Player;

public class WidowOfSin extends Boss{

    public enum State {RUN, ATTACK1, ATTACK2, HIT, DEATH}

    public static final float HIT_DURATION = 0.02f;
    public Texture all = new Texture("Boss/Widow of Sin/The_Tarnished_Widow_188x90.png");
    public Texture red = new Texture("red.png");
    public static String initname = "Widow";
    public static int maxHealth = 1800;
    public static int initialSpeed = 42 / ((int) Constants.PPM / 3);
    public static int xp = 300;
    public WidowOfSin.State currentState = WidowOfSin.State.RUN;
    public WidowOfSin.State previousState = WidowOfSin.State.RUN;
    public float attackcooldown = 3;
    public float attacktimer = 0;
    public boolean isAttacking = false;
    public int damage = 250;
    public boolean cond = false;

    public WidowOfSin(EternalSemester game, World world, Player player) {
        super(game, world, player, initname, maxHealth, initialSpeed, xp);
        createAnimation();
    }

    public void createAnimation() {
        animations = new Animation[4];

        TextureRegion sheet[][] = TextureRegion.split(all, 188, 90);
        TextureRegion moveAnimation[] = new TextureRegion[11];
        TextureRegion deathAnimation[] = new TextureRegion[14];
        TextureRegion attack1Animation[] = new TextureRegion[18];
        TextureRegion hitAnimation = sheet[7][0];

        for(int i = 0 ; i < 11 ; i++)
            moveAnimation[i] = sheet[1][i];
        for(int i = 0 ; i < 14 ; i++)
            deathAnimation[i] = sheet[7][i];
        for(int i = 0 ; i < 18 ; i++)
            attack1Animation[i] = sheet[2][i];

        animations[0] = new Animation<>( 0.1f , moveAnimation);
        animations[1] = new Animation<>( 0.1f , attack1Animation);
        animations[2] = new Animation<>(0.02f, hitAnimation);
        animations[3] = new Animation<>( 0.15f , deathAnimation);

    }
    @Override
    public void update(float delta, Player player) {
        attacktimer += delta;
        State state = getState();
        position = body.getPosition();
        if(state == WidowOfSin.State.RUN) {
            direction = player.getPosition().sub(position);

            direction.nor(); // Normalize to get unit vector

            body.setLinearVelocity(direction.cpy().scl(speed));
            position = body.getPosition();

            if(this.direction.x > 0)
                isFacingRight = true;
            else
                isFacingRight = false;
            cond = true;
        }
        else if (state == State.ATTACK1) {
            body.setLinearVelocity(0,0);
            if(previousState == State.ATTACK1 && stateTimer >= 0.65f && stateTimer <= 1f) {
                attack1(delta, player);
            }
        }

    }

    public WidowOfSin.State getState() {
        if (health <= 0)
            return State.DEATH;
        else if (((attacktimer >= attackcooldown &&((Math.abs(player.getPosition().x - this.getPosition().x) < 250 / Constants.PPM && Math.abs(player.getPosition().y - this.getPosition().y) < 250 / Constants.PPM))) || (currentState == State.ATTACK1 && (stateTimer>= 0.001f && stateTimer <= 1.8))))
            return State.ATTACK1;
        else if (isHit || (currentState == WidowOfSin.State.HIT && (stateTimer >= 0.001f && stateTimer <= HIT_DURATION)))
            return State.HIT;
        else
            return State.RUN;
    }
    
    public TextureRegion getFrame(float delta) {
        currentState = getState();
        TextureRegion tr;

        switch (currentState) {
            case DEATH:
                tr = animations[3].getKeyFrame(stateTimer, false);
                break;
            case ATTACK1:
                tr = animations[1].getKeyFrame(stateTimer, true);
                break;
            case HIT:
                tr = animations[2].getKeyFrame(stateTimer, true);
                break;
            default:
                tr = animations[0].getKeyFrame(stateTimer, true);
                break;
        }

        if (!isFacingRight && !tr.isFlipX())
            tr.flip(true, false);
        else if (isFacingRight && tr.isFlipX())
            tr.flip(true, false);

        stateTimer = (currentState == previousState ? stateTimer + delta : 0);

        if(previousState == State.ATTACK1)
            attacktimer = 0;

        previousState = currentState;
        return tr;
    }
    @Override
    public void attack1(float delta, Player player) {
        if(cond && (Math.abs(player.getPosition().x - this.getPosition().x) < 250 / Constants.PPM && Math.abs(player.getPosition().y - this.getPosition().y) < 250 / Constants.PPM))
        {
            player.takeDamage(damage);
            player.isHit = true;
            cond = false;
        }
    }


    public void draw(float delta) {
        if(getState() == WidowOfSin.State.ATTACK1)
           game.batch.draw(red,position.x - 42, position.y - 42 , 500 / Constants.PPM, 500 / Constants.PPM);
        game.batch.draw(getFrame(delta), getPosition().x - 3*199f / 2 / Constants.PPM, getPosition().y - 75/ Constants.PPM, 3*199f/ Constants.PPM, 3*90f/ Constants.PPM);
        //game.font.draw(game.batch, health + "", position.x, position.y);
    }
    @Override
    public void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getPosition().x , getPosition().y );

        Body body = world.createBody(bodyDef);
        body.setFixedRotation(true);

        CircleShape shape = new CircleShape();
        shape.setRadius(69/ Constants.PPM);

        FixtureDef fixture = new FixtureDef();
        fixture.density = 10000000f;
        fixture.shape = shape;
        body.createFixture(fixture).setUserData(this);
        shape.dispose();

        this.body = body;
    }
}
