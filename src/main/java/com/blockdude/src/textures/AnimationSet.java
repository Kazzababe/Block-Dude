package com.blockdude.src.textures;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public enum AnimationSet {
	BLOCK_WALK(new String[] {"textures/Block Dude.png", "textures/Walking 1.png", "textures/Walking 2.png", "textures/Walking 3.png", "textures/Walking 4.png", "textures/Walking 5.png", "textures/Walking 6.png"});

	private Texture[] textures;
	
	private AnimationSet(String[] textures) {
		this.textures = new Texture[textures.length];
		for (int i = 0; i < textures.length; i++) {
			String path = textures[i];
			try {
				this.textures[i] = TextureLoader.getTexture(path.substring(path.indexOf(".") + 1, path.length()).toUpperCase(), ResourceLoader.getResourceAsStream(path));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Texture getTexture(int index) {
		return this.textures[index];
	}
	
	public int getLength() {
		return this.textures.length;
	}
}
