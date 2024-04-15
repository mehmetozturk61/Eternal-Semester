package com.veyrongaming.eternalsemester.characters;

import com.veyrongaming.eternalsemester.EternalSemester;

public class Wizard extends Character {
    public Wizard(EternalSemester game, String name) {
        super(game, name, 100, 0.07f, new MagicalStaff());
    }

    @Override
    public void useSpecialAbility() {
        // TODO Implement logic for Wizard's teleport and slow ability (e.g., teleport to a new position, slow enemies in an area)
    }
}
