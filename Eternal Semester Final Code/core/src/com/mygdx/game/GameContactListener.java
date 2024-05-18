package com.mygdx.game;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Bosses.Boss;
import com.mygdx.game.Enemies.Enemy;
import com.mygdx.game.characters.Player;
import com.mygdx.game.weapons.Projectile;
import com.mygdx.game.weapons.Skill;

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

        if (isSkillEnemyContact(fa, fb)) {
            boolean isFaSkill = fa.getUserData() instanceof Skill;
            Fixture weaponFixture = isFaSkill ? fa : fb;
            Fixture enemyFixture = isFaSkill ? fb : fa;

            Enemy enemy = (Enemy) enemyFixture.getUserData();
            Skill weapon = (Skill) weaponFixture.getUserData();

            enemy.health -= weapon.damage;
            enemy.isHit = true;
        }

        if (isSkillBossContact(fa, fb)) {
            boolean isFaSkill = fa.getUserData() instanceof Skill;
            Fixture weaponFixture = isFaSkill ? fa : fb;
            Fixture bossFixture = isFaSkill ? fb : fa;

            Boss boss = (Boss) bossFixture.getUserData();
            Skill weapon = (Skill) weaponFixture.getUserData();

            boss.health -= weapon.damage;
            boss.isHit = true;
        }

        /*if (isProjectileEnemyContact(fa, fb)) {
            boolean isFaProjectile = fa.getUserData() instanceof Projectile;
            Fixture projectileFixture = isFaProjectile ? fa : fb;
            Fixture enemyFixture = isFaProjectile ? fb : fa;

            Enemy enemy = (Enemy) enemyFixture.getUserData();
            Projectile projectile = (Projectile) projectileFixture.getUserData();

            enemy.health -= projectile.damage;
            enemy.isHit = true;
        }

        if (isProjectileBossContact(fa, fb)) {
            boolean isFaProjectile = fa.getUserData() instanceof Projectile;
            Fixture projectileFixture = isFaProjectile ? fa : fb;
            Fixture bossFixture = isFaProjectile ? fb : fa;

            Boss boss = (Boss) bossFixture.getUserData();
            Projectile projectile = (Projectile) projectileFixture.getUserData();

            boss.health -= projectile.damage;
            boss.isHit = true;
        }*/
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

        if (isSkillEnemyContact(fa, fb)) {
            boolean isFaSkill = fa.getUserData() instanceof Skill;
            Fixture enemyFixture = isFaSkill ? fb : fa;

            Enemy enemy = (Enemy) enemyFixture.getUserData();

            enemy.isHit = false;
        }

        if (isSkillBossContact(fa, fb)) {
            boolean isFaSkill = fa.getUserData() instanceof Skill;
            Fixture bossFixture = isFaSkill ? fb : fa;

            Boss boss = (Boss) bossFixture.getUserData();

            boss.isHit = false;
        }

        /*if (isProjectileEnemyContact(fa, fb)) {
            boolean isFaProjectile = fa.getUserData() instanceof Projectile;
            Fixture enemyFixture = isFaProjectile ? fb : fa;

            Enemy enemy = (Enemy) enemyFixture.getUserData();

            enemy.isHit = false;
        }

        if (isProjectileBossContact(fa, fb)) {
            boolean isFaProjectile = fa.getUserData() instanceof Projectile;
            Fixture bossFixture = isFaProjectile ? fb : fa;

            Boss boss = (Boss) bossFixture.getUserData();

            boss.isHit = false;
        }*/
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

    public boolean isSkillEnemyContact(Fixture a, Fixture b) {
        if ((a.isSensor() && a.getUserData() instanceof Skill && b.getUserData() instanceof Enemy) ||
                (b.isSensor() && b.getUserData() instanceof Skill && a.getUserData() instanceof Enemy)) {
            return true;
        }

        return false;
    }

    public boolean isSkillBossContact(Fixture a, Fixture b) {
        if ((a.isSensor() && a.getUserData() instanceof Skill && b.getUserData() instanceof Boss) ||
                (b.isSensor() && b.getUserData() instanceof Skill && a.getUserData() instanceof Boss)) {
            return true;
        }

        return false;
    }

    public boolean isProjectileEnemyContact(Fixture a, Fixture b) {
        if ((a.isSensor() && a.getUserData() instanceof Projectile && b.getUserData() instanceof Enemy) ||
                (b.isSensor() && b.getUserData() instanceof Projectile && a.getUserData() instanceof Enemy)) {
            return true;
        }

        return false;
    }

    public boolean isProjectileBossContact(Fixture a, Fixture b) {
        if ((a.isSensor() && a.getUserData() instanceof Projectile && b.getUserData() instanceof Boss) ||
                (b.isSensor() && b.getUserData() instanceof Projectile && a.getUserData() instanceof Boss)) {
            return true;
        }

        return false;
    }
}