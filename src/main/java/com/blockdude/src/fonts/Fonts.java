package com.blockdude.src.fonts;

import java.awt.Font;
import java.io.InputStream;

import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public enum Fonts {
	CENTURY_GOTHIC("fonts/century_gothic.ttf", new int[] {10, 14, 18, 22, 32, 48, 72}), 
	OPEN_SANS("fonts/OpenSans-Regular.ttf", new int[] {10, 14, 18, 22, 32, 48, 72}), 
	OSWALD("fonts/oswald.regular.ttf", new int[] {10, 14, 18, 22, 32, 48, 72}), 
	MONTSERRAT("fonts/Montserrat-Bold.ttf", new int[] {10, 14, 18, 22, 32, 48, 72});
	
	private String path;
	private TrueTypeFont[] font = new TrueTypeFont[101];
	
	private Fonts(String path, int[] sizes) {
		this.path = path;
		
		for (int size : sizes) {
			this.font[size] = new TrueTypeFont(new Font("Times New Roman", Font.BOLD, size), false);
			try {
				InputStream stream = ResourceLoader.getResourceAsStream(path);
				
				Font font = Font.createFont(Font.TRUETYPE_FONT, stream);
				font = font.deriveFont((float) size);
				this.font[size] = new TrueTypeFont(font, true);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Get the designated path of the font.
	 * 
	 * @return	The file path of the font.
	 */
	public String getPath() {
		return this.path;
	}
	
	/**
	 * Get the font of the specified size.
	 * 
	 * @param	size	The font size of the font.
	 * @return	The font of the specified size.
	 */
	public org.newdawn.slick.Font getFont(int size) {
		try {
			return this.font[size];
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Draw a string at a specified location, of a specified size, and color.
	 * 
	 * @param	size	The size of the font.
	 * @param	x		The desired x location where you wish the font to be drawn.
	 * @param	y		The desired y location where you wish the font to be drawn.
	 * @param	string	The text you wish to be displayed.
	 * @param	color	The color of the font you wish to be displayed.
	 */
	public void drawString(int size, float x, float y, String string, Color color) {
		glEnable(GL_BLEND);
		color.bind();
		this.getFont(size).drawString(x, y, string, color);
		glDisable(GL_BLEND);
	}
	
	/**
	 * Draw a string at a specified location, of a specified size, and color.
	 * 
	 * @param	size	The size of the font.
	 * @param	x		The desired center x location where you wish the font to be drawn.
	 * @param	y		The desired center y location where you wish the font to be drawn.
	 * @param	string	The text you wish to be displayed.
	 * @param	color	The color of the font you wish to be displayed.
	 */
	public void drawCenteredString(int size, float x, float y, String string, Color color) {
		glEnable(GL_BLEND);
		color.bind();
		this.getFont(size).drawString(x - this.getFont(size).getWidth(string) / 2, y - this.getFont(size).getHeight(string) / 2, string, color);
		glDisable(GL_BLEND);
	}
}
