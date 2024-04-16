package com.veyrongaming.eternalsemester.weapons;

import com.badlogic.gdx.math.Vector2;
import com.veyrongaming.eternalsemester.GameWorld;
import com.veyrongaming.eternalsemester.characters.Character;

public class MagicalStaff extends Weapon {
    public MagicalStaff() {
        super("Magical Staff", 20, 25);
    }

    @Override
    public void attack(Character character, GameWorld gameWorld) {
        float projectileSpeed = 5f;
        float projectileLifetime = 2f;

        Vector2 projectilePosition = character.getPosition().add(character.getDirection().scl(0.5f));
        Projectile projectile = new Projectile(projectilePosition, character.getDirection().scl(projectileSpeed), damage, projectileLifetime, gameWorld);

        gameWorld.addProjectile(projectile);
    }
}
