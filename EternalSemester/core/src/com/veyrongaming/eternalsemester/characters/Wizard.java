package com.veyrongaming.eternalsemester.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.veyrongaming.eternalsemester.EternalSemester;
import com.veyrongaming.eternalsemester.weapons.MagicalStaff;

public class Wizard extends Character {
    public Wizard(EternalSemester game, String name) {
        super(game, name, 100, 0.07f, new MagicalStaff(), new Texture(Gdx.files.internal("assassin.jpg")));
    }

    @Override
    public void useSpecialAbility() {
        // TODO Implement logic for Wizard's teleport and slow ability (e.g., teleport to a new position, slow enemies in an area)
    }
}
