package com.veyrongaming.eternalsemester.characters;

public class Assassin extends Character {
    public Assassin(String name) {
        super(name, 150, 0.1f, new Dagger());
    }

    @Override
    public void useSpecialAbility() {
        // TODO Iplement logic for Assassin's dash ability (e.g., move a certain distance instantly)
    }
}
