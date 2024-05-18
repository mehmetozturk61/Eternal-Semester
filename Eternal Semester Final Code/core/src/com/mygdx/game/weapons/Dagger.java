package com.mygdx.game.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Constants;
import com.mygdx.game.EternalSemester;
import com.mygdx.game.characters.Player;

public class Dagger extends Weapon {
    public final int WIDTH = 90;
    public final int HEIGHT = 37;
    public final float SCALE = 3;
    public final int ANIMATION_SPACE = 10;
    public final float DAGGER_DURATION = 0.9f;

    public static float cooldownDagger = 2f;
    public static String name = "Dagger";
    public static int damageDagger = 80;

    public Texture attack = new Texture("The SwordMaster/attack.png");
    public Animation<TextureRegion> animation;

    public Dagger(EternalSemester game, World world, Player player) {
        super(game, world, player, cooldownDagger, name, damageDagger);

        TextureRegion attackSheet[][] = TextureRegion.split(attack, WIDTH, HEIGHT);
        TextureRegion attackAnimation[];
        attackAnimation = new TextureRegion[6];

        for (int i = 0; i < 6; i++) {
            attackAnimation[i] = attackSheet[0][i];
        }

        animation = new Animation<>(DAGGER_DURATION / 6f, attackAnimation);
    }


    @Override
    public void attack() {
        body.setActive(true);
    }

    @Override
    public void draw(float delta) {
        if (stateTimer > 0 && stateTimer < DAGGER_DURATION) {
            stateTimer += delta;
            TextureRegion tr = (TextureRegion) animation.getKeyFrame(stateTimer, false);

            if (direction.x > 0) {
                game.batch.draw(tr, player.getPosition().x - (WIDTH /2 - ANIMATION_SPACE)/ Constants.PPM, player.getPosition().y - (HEIGHT/2 - 7.5f)/ Constants.PPM, (WIDTH /2 - ANIMATION_SPACE)/ Constants.PPM , (HEIGHT/2-7.5f)/ Constants.PPM , WIDTH / Constants.PPM , HEIGHT / Constants.PPM , SCALE, 1.5f * SCALE, direction.angleDeg());
            }
            else if (direction.x <= 0)
                game.batch.draw(tr, player.getPosition().x + (WIDTH /2 - ANIMATION_SPACE)/ Constants.PPM, player.getPosition().y - (HEIGHT/2 - 7.5f)/ Constants.PPM, (-WIDTH /2 + ANIMATION_SPACE)/ Constants.PPM , (HEIGHT/2-7.5f)/ Constants.PPM , -WIDTH / Constants.PPM , HEIGHT/ Constants.PPM , SCALE, 1.5f * SCALE, 180 + direction.angleDeg());

            if (stateTimer > DAGGER_DURATION / 2) body.setActive(false);
        }
    }

    @Override
    public void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.KinematicBody;
        bodyDef.position.set(800 / Constants.PPM, 450 / Constants.PPM);

        Body body = world.createBody(bodyDef);
        body.setFixedRotation(false);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(140 / Constants.PPM, 45 / Constants.PPM);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.isSensor = true;
        body.createFixture(fixture).setUserData(this);
        shape.dispose();
        this.body = body;
        body.setActive(false);
    }
    public void update(float delta) {
        body.setTransform(player.getPosition().add(new Vector2( Constants.PPM*(float)Math.cos(direction.angleRad()),  Constants.PPM*(float)Math.sin(direction.angleRad()))), direction.angleRad());
        super.update(delta);
    }
    @Override
    public void dispose() {
        super.dispose();
        attack.dispose();
    }
}
