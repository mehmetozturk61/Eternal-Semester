package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Description {
    public String name;
    public String description;
    public Texture texture;

    public Description(String name, String description, Texture texture) {
        this.name = name;
        this.description = description;
        this.texture = texture;
    }
}
