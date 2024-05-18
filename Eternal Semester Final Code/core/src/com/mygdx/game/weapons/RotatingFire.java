package com.mygdx.game.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Constants;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.characters.Player;

public class RotatingFire extends Rotators{
    public final int FRAME_WIDTH = 64;
    public final int FRAME_HEIGHT = 64;
    public final float SCALE = 5 / Constants.PPM;
    public final int ANIMATION_SPACE = 30;
    public final float DURATION = 0.21f;

    public static int damage = 25;
    public float timer;

    public Texture attack = new Texture("Weapons/shield of fire.png");
    public Animation<TextureRegion> animation;

    public RotatingFire(EternalSemester game, World world, Player player, Vector2 pos) {
        super(game, world, player, damage);


        //body.setTransform(player.getPosition().add(new Vector2(50,0)),0);

        position = pos;
        TextureRegion attackSheet[][] = TextureRegion.split(attack, 64, 64);
        TextureRegion attackAnimation[];
        attackAnimation = new TextureRegion[5];

        for (int i = 0; i < 5; i++) {
            attackAnimation[i] = attackSheet[0][i];
        }
        animation = new Animation<>( 0.2f, attackAnimation);

    }

    public void update(float delta) {
        //direction = player.getDirection();
        timer += delta;
        //position = body.getPosition();
        for(int i = 0 ; i < bodies.size() ; i++) {
            bodies.get(i).setTransform(player.getPosition().add(new Vector2(88/ Constants.PPM*(float)Math.cos( 2* timer * Math.PI / 3+ i * 2 * Math.PI / 3 - 0.55f), 88/ Constants.PPM*(float)Math.sin(2*timer * Math.PI / 3+ i * 2 * Math.PI / 3 - 0.55f))), 2*timer * (float)Math.PI / 3+ i * 2 * (float)Math.PI / 3 - 0.55f);
        }
    }


    public void draw(float delta) {

        stateTimer += delta;
        TextureRegion tr = (TextureRegion) animation.getKeyFrame(stateTimer, true);
        game.batch.draw(tr, player.position.x - (FRAME_WIDTH * 5f / 2)/ Constants.PPM, player.position.y - (FRAME_HEIGHT* 5f / 2)/ Constants.PPM, FRAME_WIDTH * SCALE, FRAME_HEIGHT * SCALE);

    }

    @Override
    public Body createBody(int x) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(player.position.x, player.position.y);

        Body body = world.createBody(bodyDef);
        body.setFixedRotation(false);

        CircleShape shape = new CircleShape();
        shape.setRadius(22/ Constants.PPM);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.isSensor = true;
        body.createFixture(fixture).setUserData(this);

        shape.dispose();
        body.setTransform(player.getPosition().add(new Vector2(75/ Constants.PPM*(float)Math.cos(x*2*Math.PI/3), 75/ Constants.PPM*(float)Math.sin(x*2*Math.PI/3))), (float)x*2*(float)Math.PI/3);
        return body;
    }



    public void dispose() {
        super.dispose();
    }
}
