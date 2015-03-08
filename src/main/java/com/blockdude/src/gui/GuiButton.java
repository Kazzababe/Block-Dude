package com.blockdude.src.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.blockdude.src.util.input.InputHelper;

public abstract class GuiButton {
	private String text;
	
	private int id;
	private boolean active;
	
	private float x;
	private float y;
	
	public GuiButton(int id, String text, float x, float y) {
		this.id = id;
		this.text = text;
		this.x = x;
		this.y = y;
	}
	
	public int getId() {
		return this.id;
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
		this.active = isInside(Mouse.getX(), Display.getHeight() - Mouse.getY());
		if(InputHelper.isMousePressed(0) && this.active) {
			this.onMouseClick();
		}
		if(InputHelper.isMouseReleased(0) && this.active) {
			this.onMouseRelease();
		}
	}
	
	public boolean isActive() {
		return this.active;
	}
	
	public abstract void render();
	public abstract boolean isInside(float x, float y);
	
	public abstract void onMouseClick();
	public abstract void onMouseRelease();
}