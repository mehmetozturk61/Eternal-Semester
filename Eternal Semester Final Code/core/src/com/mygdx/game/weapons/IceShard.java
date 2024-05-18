package com.mygdx.game.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Constants;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.characters.Player;

import java.util.Random;

public class IceShard extends Projectile{
    public final int FRAME_WIDTH = 30;
    public final int FRAME_HEIGHT = 50;
    public final float SCALE = 3 ;
    public final int ANIMATION_SPACE = 30;
    public final float DURATION = 0.21f;

    public static int damage = 40;

    public Texture attack = new Texture("Weapons/iceShard/ice.png");
    public Animation<TextureRegion> animation;

    public IceShard(EternalSemester game, World world, Player player, Vector2 pos) {
        super(game, world, player, damage);
        position = pos;
        TextureRegion attackSheet[][] = TextureRegion.split(attack, 512, 512);
        TextureRegion attackAnimation[];
        attackAnimation = new TextureRegion[7];

        for (int i = 0; i < 7; i++) {
            attackAnimation[i] = attackSheet[0][i];
        }
        animation = new Animation<>( 0.03f, attackAnimation);

    }

    @Override
    public void update(float delta) {
        //direction = player.getDirection();
        position = body.getPosition();
        //body.setTransform(player.getPosition().add(new Vector2(75*(float)Math.cos(direction.angleRad()), 75*(float)Math.sin(direction.angleRad()))), direction.angleRad());
    }

    @Override
    public void draw(float delta) {

        stateTimer += delta;
        TextureRegion tr = (TextureRegion) animation.getKeyFrame(stateTimer, false);

        if (direction.x > 0) {
            game.batch.draw(tr, position.x - (FRAME_WIDTH / 2)/ Constants.PPM, position.y - (FRAME_HEIGHT / 2)/ Constants.PPM, FRAME_WIDTH / 2 / Constants.PPM , FRAME_HEIGHT / 2 / Constants.PPM , FRAME_WIDTH/ Constants.PPM , FRAME_HEIGHT/ Constants.PPM , SCALE,  SCALE, direction.angleDeg());
        }
        else if (direction.x <= 0)
            game.batch.draw(tr, position.x - (FRAME_WIDTH / 2)/ Constants.PPM, position.y - (FRAME_HEIGHT / 2)/ Constants.PPM, FRAME_WIDTH / 2 / Constants.PPM  , FRAME_HEIGHT / 2 / Constants.PPM , FRAME_WIDTH/ Constants.PPM , FRAME_HEIGHT/ Constants.PPM , SCALE,  SCALE, direction.angleDeg());



    }

    @Override
    public void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(player.position.x, player.position.y);

        Body body = world.createBody(bodyDef);
        body.setFixedRotation(false);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(18 / Constants.PPM, 25 / Constants.PPM);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.isSensor = true;
        body.createFixture(fixture).setUserData(this);
        shape.dispose();
        this.body = body;
        //body.setLinearVelocity(direction.x*50, direction.y*50);
    }

    public void dispose() {
        super.dispose();
    }
}
