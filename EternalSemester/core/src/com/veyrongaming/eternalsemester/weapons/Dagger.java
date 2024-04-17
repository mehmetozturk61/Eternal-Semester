package com.veyrongaming.eternalsemester.weapons;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.veyrongaming.eternalsemester.Enemy;
import com.veyrongaming.eternalsemester.GameScreen;
import com.veyrongaming.eternalsemester.characters.Character;

public class Dagger extends Weapon {
    public Dagger() {
        super("Dagger", 0.2f, 20);
    }

    @Override
    public void attack(Character character, GameScreen gameScreen) {
        float attackRange = 150f;
        ArrayList<Enemy> enemies = gameScreen.getEnemies();

        for (Enemy enemy : enemies) {
            float distance = Vector2.dst(character.getPosition().x, character.getPosition().y, enemy.getPosition().x, enemy.getPosition().y);
            if (distance <= attackRange) {
                enemy.takeDamage(damage); // Deal damage to the enemy
            }
        }
    }
}
