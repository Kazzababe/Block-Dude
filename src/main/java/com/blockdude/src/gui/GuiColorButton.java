package com.blockdude.src.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

import com.blockdude.src.fonts.Fonts;
import com.blockdude.src.screens.Screen;
import com.blockdude.src.shapes.ShapesHelper;

public class GuiColorButton extends GuiButton {
	private Screen parent;
	
	private float width;
	private float height;
	
	private Color color;
	private Color activeColor;
	private Rectangle hitbox;

	public GuiColorButton(Screen parent, int id, String text, float x, float y, float width, float height, Color color, Color activeColor) {
		super(id, text, x, y);

		this.parent = parent;
		
		this.width = width;
		this.height = height;
		
		this.color = color;
		this.activeColor = activeColor;
		this.hitbox = new Rectangle(x, y, width, height);
	}

	@Override
	public void render() {
		ShapesHelper.rect(this.getX(), this.getY(), this.width, this.height, this.isActive()? this.activeColor : this.color);
		Fonts.OPEN_SANS.drawCenteredString(14, this.getX() + this.width / 2, this.getY() + this.height / 2, this.getText(), Color.white);
	}

	@Override
	public boolean isInside(float x, float y) {
		return this.hitbox.contains(x, y);
	}

	@Override
	public void onMouseClick() {
		this.parent.onButtonClick(this);System.out.println("LOL");
	}

	@Override
	public void onMouseRelease() {}
}
