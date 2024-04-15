package com.veyrongaming.eternalsemester;

public class Level1 extends Level {
	public Level1(EternalSemester game) {
		super(game);
		// Load level-specific assets (textures, enemies)
	}
	
	@Override
	public void renderBackground(SpriteBatch batch) {
		batch.draw(backgroundTexture, 0, 0);
	}
}