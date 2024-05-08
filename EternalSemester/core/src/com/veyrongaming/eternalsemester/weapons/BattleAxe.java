package com.veyrongaming.eternalsemester.weapons;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.veyrongaming.eternalsemester.Enemy;
import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.GameScreen;
import com.veyrongaming.eternalsemester.characters.Character;

public class BattleAxe extends Weapon {
    Texture attack = new Texture("Ball and Chain Bot/attack animation.png");
    private Animation<TextureRegion> animation;
    private TextureRegion attackAnimation[];

    public BattleAxe() {
        super("Battle Axe", 750f, 30);

        TextureRegion attackSheet[][] = TextureRegion.split(attack, 126, 39);
        attackAnimation = new TextureRegion[5];

        for (int i = 0; i < 5; i++) {
            attackAnimation[i] = attackSheet[i][0];
        }

        animation = new Animation<>(50f, attackAnimation);
    }

    @Override
    public void draw(EternalSemester game, Character character) {
        if (direction.x >= 0)
            game.batch.draw((TextureRegion) animation.getKeyFrame(statetime, false), character.getPosition().x - 3*126/2 + 30, character.getPosition().y - 3*39/2, 3*126/2 - 30, 3*39/2, 3*126, 3*39, 1, 1, direction.angleDeg());
        else
            game.batch.draw((TextureRegion) animation.getKeyFrame(statetime, false), character.getPosition().x + 3*126/2 - 30, character.getPosition().y - 3*39/2, -3*126/2 + 30, 3*39/2, -3*126, 3*39, 1, 1, 180 + direction.angleDeg());
        }

    @Override
    public void attack(Character character, GameScreen gameScreen) {
        float attackRange = 150f;
        float coneAngle = 90f;

        ArrayList<Enemy> enemies = gameScreen.getEnemies();

        for (Enemy enemy : enemies) {
            Vector2 enemyDirection = enemy.getPosition().sub(character.getPosition());

            if (enemyDirection.len() <= attackRange) {
                float angleToEnemy = 180 - Math.abs(180 - direction.angleDeg(enemyDirection));
                if (angleToEnemy <= coneAngle / 2f || enemyDirection.len() <= attackRange / 10f) {
                    enemy.takeDamage(coneAngle);
                }
            }
        }
    }
}
