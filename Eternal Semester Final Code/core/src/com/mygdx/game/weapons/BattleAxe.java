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

public class BattleAxe extends Weapon {
    public final int FRAME_WIDTH = 126;
    public final int FRAME_HEIGHT = 39;
    public final float SCALE = 3;
    public final int ANIMATION_SPACE = 30;
    public final float BATTLE_AXE_DURATION = 0.8f;

    public static float cooldownBattleAxe = 2f;
    public static String name = "Battle Axe";
    public static int damageBattleAxe = 80;

    public Texture attack = new Texture("Ball and Chain Bot/attack animation.png");
    public Animation<TextureRegion> animation;

    public BattleAxe(EternalSemester game, World world, Player player) {
        super(game, world, player, cooldownBattleAxe, name, damageBattleAxe);

        TextureRegion attackSheet[][] = TextureRegion.split(attack, 126, 39);
        TextureRegion attackAnimation[];
        attackAnimation = new TextureRegion[4];

        for (int i = 0; i < 4; i++) {
            attackAnimation[i] = attackSheet[i][0];
        }

        animation = new Animation<>(0.2f, attackAnimation);
    }


    @Override
    public void attack() {
        body.setActive(true);
    }

    @Override
    public void draw(float delta) {
        if (stateTimer > 0 && stateTimer < BATTLE_AXE_DURATION) {
            stateTimer += delta;
            TextureRegion tr = (TextureRegion) animation.getKeyFrame(stateTimer, false);

            if (direction.x > 0) {
                game.batch.draw(tr, player.getPosition().x - (FRAME_WIDTH/2 - ANIMATION_SPACE)/ Constants.PPM, player.getPosition().y - (FRAME_HEIGHT/2)/ Constants.PPM, (FRAME_WIDTH/2 - ANIMATION_SPACE)/ Constants.PPM , FRAME_HEIGHT/2/ Constants.PPM , FRAME_WIDTH/ Constants.PPM , FRAME_HEIGHT / Constants.PPM , SCALE, 1.5f * SCALE, direction.angleDeg());
            }
            else if (direction.x <= 0)
                game.batch.draw(tr, player.getPosition().x + (FRAME_WIDTH/2 - ANIMATION_SPACE)/ Constants.PPM, player.getPosition().y - (FRAME_HEIGHT/2)/ Constants.PPM, (-FRAME_WIDTH/2 + ANIMATION_SPACE)/ Constants.PPM , FRAME_HEIGHT/2/ Constants.PPM , -FRAME_WIDTH/ Constants.PPM , FRAME_HEIGHT/ Constants.PPM , SCALE, 1.5f * SCALE, 180 + direction.angleDeg());

            if (stateTimer > BATTLE_AXE_DURATION / 2) body.setActive(false);
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
        shape.setAsBox(85 / Constants.PPM, 60 / Constants.PPM);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.isSensor = true;
        body.createFixture(fixture).setUserData(this);
        shape.dispose();
        this.body = body;
        body.setActive(false);
    }
    public void update(float delta) {
        body.setTransform(player.getPosition().add(new Vector2(75/ Constants.PPM*(float)Math.cos(direction.angleRad()), 75/ Constants.PPM*(float)Math.sin(direction.angleRad()))), direction.angleRad());
        super.update(delta);
    }
    @Override
    public void dispose() {
        super.dispose();
        attack.dispose();
    }
}
