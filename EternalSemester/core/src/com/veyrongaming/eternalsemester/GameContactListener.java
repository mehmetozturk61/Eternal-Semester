package com.veyrongaming.eternalsemester;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.veyrongaming.eternalsemester.Bosses.Boss;
import com.veyrongaming.eternalsemester.Enemies.Enemy;
import com.veyrongaming.eternalsemester.characters.Player;
import com.veyrongaming.eternalsemester.weapons.Weapon;

public class GameContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;
        if (fa.getUserData() == null || fb.getUserData() == null) return;

        if (isPlayerEnemyContact(fa, fb)) {
            boolean isFaPlayer = fa.getUserData() instanceof Player;
            Fixture enemyFixture = isFaPlayer ? fb : fa;

            Enemy enemy = (Enemy) enemyFixture.getUserData();

            enemy.canAttack = true;
        }

        if (isWeaponEnemyContact(fa, fb)) {
            boolean isFaWeapon = fa.getUserData() instanceof Weapon;
            Fixture weaponFixture = isFaWeapon ? fa : fb;
            Fixture enemyFixture = isFaWeapon ? fb : fa;

            Enemy enemy = (Enemy) enemyFixture.getUserData();
            Weapon weapon = (Weapon) weaponFixture.getUserData();

            enemy.health -= weapon.damage;
            enemy.isHit = true;
        }

        if (isWeaponBossContact(fa, fb)) {
            boolean isFaWeapon = fa.getUserData() instanceof Weapon;
            Fixture weaponFixture = isFaWeapon ? fa : fb;
            Fixture bossFixture = isFaWeapon ? fb : fa;

            Boss boss = (Boss) bossFixture.getUserData();
            Weapon weapon = (Weapon) weaponFixture.getUserData();

            boss.health -= weapon.damage;
            boss.isHit = true;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;
        if (fa.getUserData() == null || fb.getUserData() == null) return;

        if (isPlayerEnemyContact(fa, fb)) {
            boolean isFaPlayer = fa.getUserData() instanceof Player;
            Fixture enemyFixture = isFaPlayer ? fb : fa;

            Enemy enemy = (Enemy) enemyFixture.getUserData();

            enemy.canAttack = false;
        }

        if (isWeaponEnemyContact(fa, fb)) {
            boolean isFaWeapon = fa.getUserData() instanceof Weapon;
            Fixture enemyFixture = isFaWeapon ? fb : fa;

            Enemy enemy = (Enemy) enemyFixture.getUserData();

            enemy.isHit = false;
        }

        if (isWeaponBossContact(fa, fb)) {
            boolean isFaWeapon = fa.getUserData() instanceof Weapon;
            Fixture bossFixture = isFaWeapon ? fb : fa;

            Boss boss = (Boss) bossFixture.getUserData();

            boss.isHit = false;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
    
    public boolean isPlayerEnemyContact(Fixture a, Fixture b) {
        if (a.getUserData() instanceof Player || b.getUserData() instanceof Player) {
            if (a.getUserData() instanceof Enemy || b.getUserData() instanceof Enemy) {
                return true;
            }
        }

        return false;
    }

    public boolean isWeaponEnemyContact(Fixture a, Fixture b) {
        if ((a.isSensor() && a.getUserData() instanceof Weapon && b.getUserData() instanceof Enemy) ||
            (b.isSensor() && b.getUserData() instanceof Weapon && a.getUserData() instanceof Enemy)) {
                return true;
            }

        return false;
    }

    public boolean isWeaponBossContact(Fixture a, Fixture b) {
        if ((a.isSensor() && a.getUserData() instanceof Weapon && b.getUserData() instanceof Boss) ||
            (b.isSensor() && b.getUserData() instanceof Weapon && a.getUserData() instanceof Boss)) {
                return true;
            }

        return false;
    }
}
