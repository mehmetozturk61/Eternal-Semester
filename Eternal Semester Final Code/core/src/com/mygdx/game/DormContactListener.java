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

public class DormContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if (fa == null || fb == null) return;
        System.out.println(fa.getUserData() +" "+fb.getUserData());
        if (fa.getUserData() == null || fb.getUserData() == null) return;


        if (fa.getUserData() instanceof Player && !(fb.getUserData() instanceof createObstacle)) {
            if ((int) fb.getUserData() == 0) System.out.println("Number 1 Contact Start");
            if ((int) fb.getUserData() == 1) Constants.enterGame = true;
            if ((int) fb.getUserData() == 2) Constants.openLeaderboard = true;
            if ((int) fb.getUserData() == 3) Constants.swapPlayer = true;
        }

        if (fb.getUserData() instanceof Player && !(fa.getUserData() instanceof createObstacle)) {
            if ((int) fa.getUserData() == 0) System.out.println("Number 1 Contact Start");
            if ((int) fa.getUserData() == 1) Constants.enterGame = true;
            if ((int) fa.getUserData() == 2) Constants.openLeaderboard = true;
            if ((int) fa.getUserData() == 3) Constants.swapPlayer = true;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;
        if (fa.getUserData() == null || fb.getUserData() == null) return;

        if (fa.getUserData() instanceof Player && !(fb.getUserData() instanceof createObstacle)) {
            if ((int) fb.getUserData() == 0) System.out.println("Number 1 Contact End");
            if ((int) fb.getUserData() == 1) System.out.println("Number 2 Contact End");
            if ((int) fb.getUserData() == 2) System.out.println("Number 3 Contact End");
            if ((int) fb.getUserData() == 3) System.out.println("Number 4 Contact End");
        }

        if (fb.getUserData() instanceof Player && !(fa.getUserData() instanceof createObstacle)) {
            if ((int) fa.getUserData() == 0) System.out.println("Number 1 Contact End");
            if ((int) fa.getUserData() == 1) System.out.println("Number 2 Contact End");
            if ((int) fa.getUserData() == 2) System.out.println("Number 3 Contact End");
            if ((int) fa.getUserData() == 3) System.out.println("Number 4 Contact End");
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}