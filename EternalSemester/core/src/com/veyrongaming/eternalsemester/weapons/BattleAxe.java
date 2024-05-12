package com.veyrongaming.eternalsemester.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.characters.Player;

public class BattleAxe extends Weapon {
    public final int FRAME_WIDTH = 126;
    public final int FRAME_HEIGHT = 39;
    public final int SCALE = 3;
    public final int ANIMATION_SPACE = 30;
    public final float BATTLE_AXE_DURATION = 0.4f;

    public static float cooldownBattleAxe = 2f;
    public static String name = "Battle Axe";
    public static int damageBattleAxe = 30;

    public Texture attack = new Texture("Ball and Chain Bot/attack animation.png");
    public Animation<TextureRegion> animation;

    public BattleAxe(EternalSemester game, World world, Player player) {
        super(game, world, player, cooldownBattleAxe, name, damageBattleAxe);
        ANIMATION_DURATION = BATTLE_AXE_DURATION;

        TextureRegion attackSheet[][] = TextureRegion.split(attack, 126, 39);
        TextureRegion attackAnimation[];
        attackAnimation = new TextureRegion[4];

        for (int i = 0; i < 4; i++) {
            attackAnimation[i] = attackSheet[i][0];
        }

        animation = new Animation<>(0.1f, attackAnimation);
    }

    
    @Override
    public void attack() {
        body.setActive(true);
    }

    @Override
    public void draw(float delta) {
        if (stateTimer > 0 && stateTimer < ANIMATION_DURATION) {
            stateTimer += delta;
            TextureRegion tr = (TextureRegion) animation.getKeyFrame(stateTimer, false);

            if (direction.x > 0) {
                game.batch.draw(tr, player.getPosition().x - FRAME_WIDTH/2 + ANIMATION_SPACE, player.getPosition().y - FRAME_HEIGHT/2, FRAME_WIDTH/2 - ANIMATION_SPACE, FRAME_HEIGHT/2, FRAME_WIDTH, FRAME_HEIGHT, SCALE, 1.5f * SCALE, direction.angleDeg());
            }
            else if (direction.x <= 0)
                game.batch.draw(tr, player.getPosition().x + FRAME_WIDTH/2 - ANIMATION_SPACE, player.getPosition().y - FRAME_HEIGHT/2, -FRAME_WIDTH/2 + ANIMATION_SPACE, FRAME_HEIGHT/2, -FRAME_WIDTH, FRAME_HEIGHT, SCALE, 1.5f * SCALE, 180 + direction.angleDeg());
            
            if (stateTimer > ANIMATION_DURATION / 2) body.setActive(false);
        }
    }

    @Override
    public void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.KinematicBody;
        bodyDef.position.set(800, 450);

        Body body = world.createBody(bodyDef);
		body.setFixedRotation(false);

        FixtureDef fixture = new FixtureDef();
        fixture.density = 1f;
        fixture.filter.categoryBits = 0;
        fixture.filter.maskBits = 0;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(85, 60);
        fixture.shape = shape;
        
        body.createFixture(fixture).getUserData();
        shape.dispose();
        this.body = body;
        body.setActive(false);
    }
}
