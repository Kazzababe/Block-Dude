package com.blockdude.src.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public abstract class GuiButton {
	private String text;
	
	private float x;
	private float y;
	
	public GuiButton(String text, float x, float y) {
		this.text = text;
		this.x = x;
		this.y = y;
	}
	
	public String getText() {
		return this.text;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public void update() {
		if(this.isInside(Mouse.getX(), (Display.getHeight() - Mouse.getY())) && !this.isInside(Mouse.getX() + Mouse.getDX(), (Display.getHeight() - Mouse.getY()) + Mouse.getDY())) {
			this.onMouseEnter();
		} else if(!this.isInside(Mouse.getX(), (Display.getHeight() - Mouse.getY())) && this.isInside(Mouse.getX() + Mouse.getDX(), (Display.getHeight() - Mouse.getY()) + Mouse.getDY())) {
			this.onMouseExit();
		}
	}
	
	public abstract void render();
	public abstract boolean isInside(float x, float y);
	
	public abstract void onMouseEnter();
	public abstract void onMouseExit();
	public abstract void onMouseClick();
	public abstract void onMouseRelease();
}