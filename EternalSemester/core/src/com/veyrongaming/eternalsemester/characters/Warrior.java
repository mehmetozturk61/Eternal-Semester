package com.veyrongaming.eternalsemester.characters;

import com.badlogic.gdx.physics.box2d.World;
import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.weapons.Sword;

public class Warrior extends Character {
    public Warrior(EternalSemester game, String name, World world) {
        super(game, name, 200, 0.08f, new Sword(), world);
    }

    @Override
    public void draw() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void useSpecialAbility() {
        // TODO Implement logic for Warrior's life steal and damage boost ability (e.g., steal health from enemies dealt damage, increase attack damage for a duration)
    }
}
