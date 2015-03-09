package com.blockdude.src.shapes;


import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;

public class ShapesHelper {
	public static void triangle(float x, float y, float x2, float y2, float x3, float y3, Color color) {
		glBegin(GL_TRIANGLES); {
			glColor3f(color.r, color.g, color.b);
			glVertex2f(x, y);
			glVertex2f(x2, y2);
			glVertex2f(x3, y3);
		} glEnd();
	}
	
	public static void rect(float x, float y, float width, float height, Color color) {
		glColor3f(color.r, color.g, color.b);
		glRectf(x, y, x + width, y + height);
	}
}
