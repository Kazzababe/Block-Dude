package com.blockdude.src.fonts;

import java.awt.Font;
import java.io.InputStream;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public enum Fonts {
	CENTURY_GOTHIC("fonts/century_gothic.ttf", new int[] {10, 14, 18, 22, 32, 48, 72}), 
	OPEN_SANS("fonts/OpenSans-Regular.ttf", new int[] {10, 14, 18, 22, 32, 48, 72});
	
	private String path;
	private TrueTypeFont[] font = new TrueTypeFont[101];
	
	private Fonts(String path, int[] sizes) {
		this.path = path;
		
		for(int i : sizes) {
			font[i] = new TrueTypeFont(new Font("Times New Roman", Font.BOLD, i), false);
			try {
				InputStream stream = ResourceLoader.getResourceAsStream(path);
				
				Font font = Font.createFont(Font.TRUETYPE_FONT, stream);
				font = font.deriveFont((float) i);
				this.font[i] = new TrueTypeFont(font, true);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getPath() {
		return this.path;
	}
	
	public org.newdawn.slick.Font getFont(int size) {
		try {
			return this.font[size];
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void drawString(int size, float x, float y, String string, Color color) {
		GL11.glEnable(GL11.GL_BLEND);
		color.bind();
		this.getFont(size).drawString(x, y, string, color);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public void drawCenteredString(int size, float x, float y, String string, Color color) {
		GL11.glEnable(GL11.GL_BLEND);
		color.bind();
		this.getFont(size).drawString(x - this.getFont(size).getWidth(string) / 2, y - this.getFont(size).getHeight(string) / 2, string, color);
		GL11.glDisable(GL11.GL_BLEND);
	}
}
