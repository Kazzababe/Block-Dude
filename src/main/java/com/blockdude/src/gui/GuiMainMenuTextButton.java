package com.blockdude.src.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.blockdude.src.fonts.Fonts;
import com.blockdude.src.shapes.ShapesHelper;

public class GuiMainMenuTextButton extends GuiButton {
	private boolean active = false;

	public GuiMainMenuTextButton(String text, float x, float y) {
		super(text, x, y);
	}

	@Override
	public void render() {
		Fonts.CENTURY_GOTHIC.drawCenteredString(22, this.getX(), this.getY(), this.getText(), Color.white);
		if(this.active) {
			float width = Fonts.CENTURY_GOTHIC.getFont(22).getWidth(this.getText());
			float halfWidth = width / 2;
			ShapesHelper.triangle(this.getX() - halfWidth - 10, this.getY(), this.getX() - halfWidth - 20, this.getY() - 6, this.getX() - halfWidth - 20, this.getY() + 6, Color.white);
			ShapesHelper.triangle(this.getX() + halfWidth + 10, this.getY(), this.getX() + halfWidth + 20, this.getY() - 6, this.getX() + halfWidth + 20, this.getY() + 6, Color.white);
		}
	}

	@Override
	public void onMouseClick() {
		
	}

	@Override
	public void onMouseRelease() {
		
	}
	
	@Override
	public void update() {
		this.active = isInside(Mouse.getX(), Display.getHeight() - Mouse.getY());
	}

	@Override
	public boolean isInside(float x, float y) {
		float width = Fonts.CENTURY_GOTHIC.getFont(22).getWidth(this.getText());
		float halfWidth = width / 2;
		
		float x1 = this.getX() - halfWidth;
		float x2 = this.getX() + halfWidth;
		float y1 = this.getY() - 11;
		float y2 = this.getY() + 11;
		
		return x >= x1 && x <= x2 && y >= y1 && y <= y2;
	}

	@Override
	public void onMouseEnter() {
		
	}

	@Override
	public void onMouseExit() {
		
	}
}
