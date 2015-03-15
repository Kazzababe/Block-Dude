package com.blockdude.src.textures;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public enum Textures {
	TEST("textures/tree.png"),
	BLOCKDUDE("textures/blockdude1.png");

	private String path;
	private Texture texture;
	
	private Textures(String path) {
		this.path = path;
		try {
			this.texture = TextureLoader.getTexture(path.substring(path.indexOf(".") + 1, path.length()).toUpperCase(), ResourceLoader.getResourceAsStream(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getPath() {
		return this.path;
	}
	
	public Texture getTexture() {
		return this.texture;
	}
}
