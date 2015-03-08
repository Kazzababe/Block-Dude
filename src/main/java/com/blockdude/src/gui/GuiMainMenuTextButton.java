package com.blockdude.src.gui;

import org.newdawn.slick.Color;

import com.blockdude.src.fonts.Fonts;
import com.blockdude.src.screens.Screen;
import com.blockdude.src.shapes.ShapesHelper;

public class GuiMainMenuTextButton extends GuiButton {
	private Screen parent;

	public GuiMainMenuTextButton(Screen parent, int id, String text, float x, float y) {
		super(id, text, x, y);
		
		this.parent = parent;
	}

	@Override
	public void render() {
		Fonts.CENTURY_GOTHIC.drawCenteredString(22, this.getX(), this.getY(), this.getText(), Color.white);
		if(this.isActive()) {
			float width = Fonts.CENTURY_GOTHIC.getFont(22).getWidth(this.getText());
			float halfWidth = width / 2;
			ShapesHelper.triangle(this.getX() - halfWidth - 10, this.getY(), this.getX() - halfWidth - 20, this.getY() - 6, this.getX() - halfWidth - 20, this.getY() + 6, Color.white);
			ShapesHelper.triangle(this.getX() + halfWidth + 10, this.getY(), this.getX() + halfWidth + 20, this.getY() - 6, this.getX() + halfWidth + 20, this.getY() + 6, Color.white);
		}
	}

	@Override
	public void onMouseClick() {
		this.parent.onButtonClick(this);
	}

	@Override
	public void onMouseRelease() {
		
	}
	
	@Override
	public void update() {
		super.update();
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
}
