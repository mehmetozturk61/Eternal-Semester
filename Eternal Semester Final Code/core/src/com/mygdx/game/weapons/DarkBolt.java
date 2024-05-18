package com.mygdx.game.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Constants;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.Screens.Levels;
import com.mygdx.game.characters.Player;

public class DarkBolt extends RandomSpawner{
    public final int FRAME_WIDTH = 64;
    public final int FRAME_HEIGHT = 88;
    public final float SCALE = 4.2f ;
    public final int ANIMATION_SPACE = 30;
    public final float DURATION = 0.12f * 12f;
    public float timer = 0;
    public Levels level;

    public static int damage = 40;

    public Texture attack = new Texture("Weapons/DarkBolt.png");
    public Animation<TextureRegion> animation;

    public DarkBolt(EternalSemester game, World world, Player player, Levels level) {
        super(game, world, player, "Dark Bolt" ,damage);
        this.level = level;
        setRandomPosition();
        body.setTransform(position , 0);
        TextureRegion attackSheet[][] = TextureRegion.split(attack, FRAME_WIDTH, FRAME_HEIGHT);
        TextureRegion attackAnimation[];
        attackAnimation = new TextureRegion[12];

        for (int i = 0; i < 12; i++) {
            attackAnimation[i] = attackSheet[0][i];
        }
        animation = new Animation<>( 0.1f, attackAnimation);

    }

    @Override
    public void update(float delta) {
        //direction = player.getDirection();
        position = body.getPosition();
        timer += delta;
        if(timer > DURATION )
        {
            level.skills.remove(this);
            this.dispose();
        }
        //body.setTransform(player.getPosition().add(new Vector2(75*(float)Math.cos(direction.angleRad()), 75*(float)Math.sin(direction.angleRad()))), direction.angleRad());
    }

    @Override
    public void draw(float delta) {

        stateTimer += delta;
        TextureRegion tr = (TextureRegion) animation.getKeyFrame(stateTimer, false);
        game.batch.draw(tr, position.x - (FRAME_WIDTH * 15 / 8)/ Constants.PPM, position.y - (FRAME_HEIGHT * 6/5)/ Constants.PPM, SCALE * FRAME_WIDTH/ Constants.PPM , SCALE * FRAME_HEIGHT/ Constants.PPM );

    }

    @Override
    public void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(player.position.x, player.position.y);

        Body body = world.createBody(bodyDef);
        body.setFixedRotation(false);

        CircleShape shape = new CircleShape();
        shape.setRadius(90 / Constants.PPM);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.isSensor = true;
        body.createFixture(fixture).setUserData(this);
        shape.dispose();
        this.body = body;
        //body.setLinearVelocity(direction.x*50, direction.y*50);
    }
}
