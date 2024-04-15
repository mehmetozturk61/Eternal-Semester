package com.veyrongaming.eternalsemester.characters;

public class Tank extends Character {
    public Tank(String name) {
        super(name, 300, 0.05f, new BattleAxe());
    }

    @Override
    public void useSpecialAbility() {
        // TODO Implement logic for Tank's heal and speed boost ability (e.g., increase health, movement speed for a duration) 
    }
}
