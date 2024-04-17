package com.veyrongaming.eternalsemester.characters;

import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.weapons.BattleAxe;

public class Tank extends Character {
    public Tank(EternalSemester game, String name) {
        super(game, name, 300, 0.05f, new BattleAxe());
    }

    @Override
    public void useSpecialAbility() {
        // TODO Implement logic for Tank's heal and speed boost ability (e.g., increase health, movement speed for a duration) 
    }
}
