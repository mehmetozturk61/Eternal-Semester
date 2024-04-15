package com.veyrongaming.eternalsemester.characters;

public class Warrior extends Character {
    public Warrior(String name) {
        super(name, 200, 0.08f, new Sword());
    }

    @Override
    public void useSpecialAbility() {
        // TODO Implement logic for Warrior's life steal and damage boost ability (e.g., steal health from enemies dealt damage, increase attack damage for a duration)
    }
}
