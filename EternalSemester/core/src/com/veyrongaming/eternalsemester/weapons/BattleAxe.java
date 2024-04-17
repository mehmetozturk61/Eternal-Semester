package com.veyrongaming.eternalsemester.weapons;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.veyrongaming.eternalsemester.Enemy;
import com.veyrongaming.eternalsemester.GameScreen;
import com.veyrongaming.eternalsemester.characters.Character;

public class BattleAxe extends Weapon {
    public BattleAxe() {
        super("Battle Axe", 2f, 30);
    }

    @Override
    public void attack(Character character, GameScreen gameWorld) {
        float attackRange = 1.5f;
        float coneAngle = 60f;

        ArrayList<Enemy> enemies = gameWorld.getEnemies();

        for (Enemy enemy : enemies) {
            Vector2 enemyDirection = enemy.getPosition().sub(character.getPosition());

            if (enemyDirection.len() <= attackRange) {
                float angleToEnemy = Math.abs(character.getDirection().angleDeg(enemyDirection));
                if (angleToEnemy <= coneAngle / 2f) {
                    enemy.takeDamage(coneAngle);
                }
            }
        }
    }
}
