package com.veyrongaming.eternalsemester.characters;

public class Wizard extends Character {
    public Wizard(String name) {
        super(name, 100, 0.07f, new MagicalStaff());
    }

    @Override
    public void useSpecialAbility() {
        // TODO Implement logic for Wizard's teleport and slow ability (e.g., teleport to a new position, slow enemies in an area)
    }
}
