package com.blockdude.src.gui;

import java.awt.Cursor;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.CursorLoader;

public class GuiIconButton extends GuiButton {
	private Rectangle hitbox;
	private IconButtonInterface guiInterface;

	public GuiIconButton(float x, float y, float width, float height, IconButtonInterface guiInterface) {
		super(0, null, x, y);
		
		this.hitbox = new Rectangle(x, y, width, height);
		this.guiInterface = guiInterface;
	}

	@Override
	public boolean isInside(float x, float y) {
		return this.hitbox.contains(x, y);
	}

	@Override
	public void onMouseClick() {
		this.guiInterface.onClick();
	}

	@Override
	public void onMouseRelease() {
		
	}

	@Override
	public void render() {
		this.guiInterface.render(this);
	}
	
	@Override
	public void update() {
		super.update();
	}
	
	public Rectangle getHitbox() {
		return this.hitbox;
	}
	
	public interface IconButtonInterface {
		public void onClick();
		public void render(GuiIconButton button);
	}
}
