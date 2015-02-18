package com.blockdude.src.textures;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class TextureHelper {
	public static void drawTexture(Textures texture, float x, float y) {
		Texture tex = texture.getTexture();
		
		Color.white.bind();
		tex.bind();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		
		GL11.glBegin(GL11.GL_QUADS); {
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(x, y);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(x + tex.getTextureWidth(), y);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(x + tex.getTextureWidth(), y + tex.getTextureHeight());
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(x, y + tex.getTextureHeight());
		} GL11.glEnd();
	}
}
