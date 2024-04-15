package com.veyrongaming.eternalsemester.characters;

import com.veyrongaming.eternalsemester.EternalSemester;

public class Assassin extends Character {
    public Assassin(EternalSemester game, String name) {
        super(game, name, 150, 0.1f, new Dagger());
    }

    @Override
    public void useSpecialAbility() {
        // TODO Iplement logic for Assassin's dash ability (e.g., move a certain distance instantly)
    }
}
