package com.blockdude.src.textures;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class TextureHelper {
	public static void drawTexture(Textures texture, float x, float y) {
		Texture tex = texture.getTexture();
		
		Color.white.bind();
		tex.bind();
		
		System.out.println(tex.getImageWidth());
		
		GL11.glBegin(GL11.GL_QUADS); {
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(x, y);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(x + tex.getImageWidth(), y);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(x + tex.getImageWidth(), y + tex.getImageHeight());
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(x, y + tex.getImageHeight());
		} GL11.glEnd();
	}
	
	public static void drawTexture(Textures texture, float x, float y, float width, float height) {
		Texture tex = texture.getTexture();
		
		Color.white.bind();
		tex.bind();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		
		GL11.glBegin(GL11.GL_QUADS); {
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(x, y);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(x + width, y);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(x + width, y + height);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(x, y + height);
		} GL11.glEnd();
		System.out.println(width + ":" + height);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
}
