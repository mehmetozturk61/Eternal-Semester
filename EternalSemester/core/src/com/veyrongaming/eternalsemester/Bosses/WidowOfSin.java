package com.veyrongaming.eternalsemester.Bosses;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.characters.Player;

public class WidowOfSin extends Boss{

    public enum State {RUN, ATTACK1, ATTACK2, HIT, DEATH}

    public static final float HIT_DURATION = 0.02f;
    public Texture all = new Texture("Boss/Widow of Sin/The_Tarnished_Widow_188x90.png");
    public static String initname = "Widow";
    public static int maxHealth = 3000;
    public static int initialSpeed = 45;
    public WidowOfSin.State currentState = WidowOfSin.State.RUN;
    public WidowOfSin.State previousState = WidowOfSin.State.RUN;
    public float attackcooldown = 3;
    public float attacktimer = 0;
    public boolean isAttacking = false;
    public int damage = 250;
    public boolean cond = false;

    public WidowOfSin(EternalSemester game, World world, Player player) {
        super(game, world, player, initname, maxHealth, initialSpeed);
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
        else if (((attacktimer >= attackcooldown &&((Math.abs(player.getPosition().x - this.getPosition().x) < 200 && Math.abs(player.getPosition().y - this.getPosition().y) < 200))) || (currentState == State.ATTACK1 && (stateTimer>= 0.001f && stateTimer <= 1.8))))
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
        if(cond && (Math.abs(player.getPosition().x - this.getPosition().x) < 150 && Math.abs(player.getPosition().y - this.getPosition().y) < 150))
        {
            player.health -= damage;
            player.isHit = true;
            cond = false;
        }
    }


    public void draw(float delta) {
        game.batch.draw(getFrame(delta), getPosition().x - 3*199f / 2, getPosition().y - 75, 3*199f, 3*90f);
        game.font.draw(game.batch, health + "", position.x, position.y);
    }
    @Override
    public void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getPosition().x, getPosition().y);

        Body body = world.createBody(bodyDef);
        body.setFixedRotation(true);

        CircleShape shape = new CircleShape();
        shape.setRadius(69);

        FixtureDef fixture = new FixtureDef();
        fixture.density = 1000000f;
        fixture.shape = shape;
        body.createFixture(fixture).setUserData(this);
        shape.dispose();

        this.body = body;
    }
}
