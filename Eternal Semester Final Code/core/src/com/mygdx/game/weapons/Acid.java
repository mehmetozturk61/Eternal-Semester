package com.mygdx.game.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Constants;
import com.mygdx.game.Enemies.Enemy;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.Screens.Level1;
import com.mygdx.game.Screens.Levels;
import com.mygdx.game.characters.Player;

import java.util.Random;

public class Acid extends Skill
{

    public EternalSemester game;
    public World world;
    public Body body;
    public Player player;
    public Vector2 position;
    public Vector2 direction;
    public float cooldown;
    public float cooldownTimer = 0;
    public float stateTimer = 0;
    public final int FRAME_WIDTH = 56;
    public final int FRAME_HEIGHT = 32;
    public final float SCALE = 3 ;
    public final int ANIMATION_SPACE = 30;
    public final float DURATION = 0.05f * 17f;
    public Levels level;
    public float timer = 0;

    public Texture attack = new Texture("Weapons/AcidVFX.png");
    public Animation<TextureRegion> animation;

    Vector2 vectorEnemy;
    Vector2 vectorUs;

    public Acid(EternalSemester game, World world, Player player, Levels level ,int damage) {
        this.game = game;
        this.world = world;
        this.player = player;
        this.damage = damage;
        this.level = level;
        this.position = player.getPosition();


        createBody();

        //body.setTransform(player.getPosition().add(new Vector2(75/ Constants.PPM*(float)Math.cos(direction.angleRad()) , 75/ Constants.PPM*(float)Math.sin(direction.angleRad()))), direction.angleRad());

        TextureRegion attackSheet[][] = TextureRegion.split(attack, 56, 32);
        TextureRegion attackAnimation[];
        attackAnimation = new TextureRegion[17];

        for (int i = 0; i < 17; i++) {
            attackAnimation[i] = attackSheet[i][0];
        }
        animation = new Animation<>( 0.05f, attackAnimation);

        try
        {
            vectorEnemy = getNearestEnemy().getPosition();
        }
        catch(Exception e)
        {
            vectorEnemy = player.getPosition().add(5,0);
        }
        vectorUs = getPosition();

        direction = vectorEnemy.sub(vectorUs);
        // body.setTransform(player.getPosition(), vectorEnemy.sub(vectorUs).angleRad());
        body.setTransform(player.getPosition().add(new Vector2(270/ Constants.PPM*(float)Math.cos(direction.angleRad()), 270/ Constants.PPM*(float)Math.sin(direction.angleRad()))), direction.angleRad());

    }

    public Enemy getNearestEnemy()
    {
        if(level.enemies.isEmpty())
            return null;
        Enemy enema = level.enemies.get(0);
        Vector2 me = new Vector2(this.getPosition().x, this.getPosition().y);
        float x = Math.abs(me.x - enema.getPosition().x);
        float y = Math.abs(me.y - enema.getPosition().y);
        float mindist = x * x + y * y;
        for(int i = 1 ; i < level.enemies.size() ; i++)
        {
            float xx = Math.abs(me.x - level.enemies.get(i).getPosition().x);
            float yy = Math.abs(me.y - level.enemies.get(i).getPosition().y);
            float dist = xx * xx + yy * yy;
            if(dist < mindist) {
                enema = level.enemies.get(i);
                mindist = dist;
            }
        }
        return enema;
    }
    public void update(float delta) {
        //direction = player.getDirection();
        timer += delta;
        if(timer > DURATION )
        {
            level.skills.remove(this);
            this.dispose();
        }

        position = body.getPosition();

        try
        {
            vectorEnemy = getNearestEnemy().getPosition();
        }
        catch(Exception e)
        {
            vectorEnemy = getPosition().add(5,0);
        }

        vectorUs = player.getPosition();

        direction = vectorEnemy.sub(vectorUs);


        body.setTransform(player.getPosition().add(new Vector2(300/ Constants.PPM*(float)Math.cos(direction.angleRad()), 300/ Constants.PPM*(float)Math.sin(direction.angleRad()))), direction.angleRad());

        //body.setTransform(player.getPosition().add(new Vector2(75*(float)Math.cos(vectorEnemy.sub(vectorUs).angleRad()), 75*(float)Math.sin(vectorEnemy.sub(vectorUs).angleRad()))), vectorEnemy.sub(vectorUs).angleRad());
    }

    public void draw(float delta) {

        stateTimer += delta;
        TextureRegion tr = (TextureRegion) animation.getKeyFrame(stateTimer, false);
        game.batch.draw(tr, position.x - (FRAME_WIDTH / 2)/ Constants.PPM, position.y - (FRAME_HEIGHT / 2)/ Constants.PPM, FRAME_WIDTH / 2 / Constants.PPM , FRAME_HEIGHT / 2 / Constants.PPM , FRAME_WIDTH/ Constants.PPM , FRAME_HEIGHT/ Constants.PPM , 3.1f *SCALE,  2* SCALE, direction.angleDeg());


    }

    public void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(player.position.x, player.position.y);

        Body body = world.createBody(bodyDef);
        body.setFixedRotation(false);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(250 / Constants.PPM, 25 / Constants.PPM);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.isSensor = true;
        body.createFixture(fixture).setUserData(this);
        shape.dispose();
        this.body = body;
    }

    public Vector2 getPosition() {
        return position.cpy();
    }

    public Vector2 getDirection() {
        return direction.cpy();
    }

    public void dispose() {
        world.destroyBody(body);
    }
}
