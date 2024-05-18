package com.mygdx.game.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Constants;
import com.mygdx.game.Enemies.Enemy;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.Screens.Levels;
import com.mygdx.game.characters.Player;

public class ShockBomb extends Projectile{
    public final int FRAME_WIDTH = 32;
    public final int FRAME_HEIGHT = 32;
    public final float SCALE = 8 ;
    public final int ANIMATION_SPACE = 30;
    public final float DURATION = 0.15f * 8f;
    public float timer = 0;
    public Levels level;

    public static int damage = 25;

    public Texture attack = new Texture("Weapons/ShockBomb.png");
    public Animation<TextureRegion> animation;

    Vector2 vectorEnemy;
    Vector2 vectorUs;

    public boolean cond = true;

    public ShockBomb(EternalSemester game, World world, Player player, Levels level) {
        super(game, world, player, damage);
        this.level = level;
        TextureRegion attackSheet[][] = TextureRegion.split(attack, FRAME_WIDTH, FRAME_HEIGHT);
        TextureRegion attackAnimation[];
        attackAnimation = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            attackAnimation[i] = attackSheet[0][i];
        }
        animation = new Animation<>( 0.1f, attackAnimation);

       /* try
        {
            vectorEnemy = getNearestEnemy().getPosition();
        }
        catch(Exception e)
        {
            vectorEnemy = player.getPosition().add(-50,0);
        }

        vectorUs = player.getPosition();

        direction = vectorEnemy.sub(vectorUs);

        body.setTransform(player.getPosition(), direction.angleRad());
        body.setLinearVelocity(direction);*/

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

    @Override
    public void update(float delta) {
        //direction = player.getDirection();

        timer += delta;
        position = body.getPosition();
       /* if(timer >= DURATION) {
            body.setActive(true);
        }*/


        try
        {
            vectorEnemy = getNearestEnemy().getPosition();
        }
        catch(Exception e)
        {
            vectorEnemy = player.getPosition().add(-50,0);
        }

        vectorUs = getPosition();

        direction = vectorEnemy.sub(vectorUs);

        direction.nor();
        direction.scl(200);
        //body.setLinearVelocity(new Vector2(0,0));
        body.setLinearVelocity(direction.x /Constants.PPM, direction.y /Constants.PPM);
        //body.setTransform(player.getPosition().add(new Vector2(300/ Constants.PPM*(float)Math.cos(direction.angleRad()), 300/ Constants.PPM*(float)Math.sin(direction.angleRad()))), direction.angleRad());
        //body.setTransform(player.getPosition().add(new Vector2(75*(float)Math.cos(direction.angleRad()), 75*(float)Math.sin(direction.angleRad()))), direction.angleRad());
        if(timer >= DURATION && cond) {
            CircleShape shape = new CircleShape();
            shape.setRadius(80 / Constants.PPM);

            FixtureDef fixture = new FixtureDef();
            fixture.shape = shape;
            fixture.isSensor = true;
            body.createFixture(fixture).setUserData(this);
            shape.dispose();
            cond = false;
        }
        if(timer > DURATION + 0.8f)
        {
            level.skills.remove(this);
            this.dispose();
        }
    }

    @Override
    public void draw(float delta) {

        TextureRegion tr = (TextureRegion) animation.getKeyFrame(stateTimer, false);
        if(timer >= DURATION)
        {
            stateTimer += delta;
            game.batch.draw(tr, position.x - (FRAME_WIDTH * 4 ) / Constants.PPM, position.y - (FRAME_HEIGHT * 4) / Constants.PPM, FRAME_WIDTH * SCALE / Constants.PPM, FRAME_HEIGHT * SCALE / Constants.PPM);
        }
        else
            game.batch.draw(tr, position.x - (FRAME_WIDTH * 2) / Constants.PPM, position.y - (FRAME_HEIGHT * 2) / Constants.PPM, FRAME_WIDTH * SCALE/2 / Constants.PPM, FRAME_HEIGHT * SCALE/2 / Constants.PPM);

    }

    @Override
    public void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(player.position.x, player.position.y);

        Body body = world.createBody(bodyDef);
        body.setFixedRotation(false);

        CircleShape shape = new CircleShape();
        shape.setRadius(20 / Constants.PPM);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.isSensor = true;
        body.createFixture(fixture).setUserData(this);
        shape.dispose();
        this.body = body;

    }


}
