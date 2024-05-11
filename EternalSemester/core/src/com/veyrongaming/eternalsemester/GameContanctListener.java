package com.veyrongaming.eternalsemester;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.veyrongaming.eternalsemester.characters.Character;

public class GameContanctListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;
        if (fa.getUserData() == null || fb.getUserData() == null) return;

        if (isEnemyPlayerContact(fa, fb)) {
            boolean isFaPlayer = fa.getUserData() instanceof Character;
            Fixture characterFixture = isFaPlayer ? fa : fb;
            Fixture enemyFixture = isFaPlayer ? fb : fa;

            Character character = (Character) characterFixture.getUserData();
            Enemy enemy = (Enemy) enemyFixture.getUserData();

            enemy.setCanAttack(true, character);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;
        if (fa.getUserData() == null || fb.getUserData() == null) return;

        if (isEnemyPlayerContact(fa, fb)) {
            boolean isFaPlayer = fa.getUserData() instanceof Character;
            Fixture characterFixture = isFaPlayer ? fa : fb;
            Fixture enemyFixture = isFaPlayer ? fb : fa;

            Character character = (Character) characterFixture.getUserData();
            Enemy enemy = (Enemy) enemyFixture.getUserData();

            enemy.setCanAttack(false, character);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
    
    public boolean isEnemyPlayerContact(Fixture a, Fixture b) {
        if (a.getUserData() instanceof Character || b.getUserData() instanceof Character) {
            if (a.getUserData() instanceof Enemy || b.getUserData() instanceof Enemy) {
                return true;
            }
        }

        return false;
    }
}
