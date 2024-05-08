package com.veyrongaming.eternalsemester.characters;

import com.badlogic.gdx.physics.box2d.World;
import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.weapons.MagicalStaff;

public class Wizard extends Character {
    public Wizard(EternalSemester game, String name, World world) {
        super(game, name, 100, 0.07f, new MagicalStaff(), world);
    }

    @Override
    public void draw() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void useSpecialAbility() {
        // TODO Implement logic for Wizard's teleport and slow ability (e.g., teleport to a new position, slow enemies in an area)
    }
}
