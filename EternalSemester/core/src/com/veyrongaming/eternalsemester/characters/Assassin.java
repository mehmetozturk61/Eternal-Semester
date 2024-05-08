package com.veyrongaming.eternalsemester.characters;

import com.badlogic.gdx.physics.box2d.World;
import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.weapons.Dagger;

public class Assassin extends Character {
    public Assassin(EternalSemester game, String name, World world) {
        super(game, name, 150, 0.1f, new Dagger(), world);
    }

    @Override
    public void draw() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void useSpecialAbility() {
        // TODO Iplement logic for Assassin's dash ability (e.g., move a certain distance instantly)
    }
}
