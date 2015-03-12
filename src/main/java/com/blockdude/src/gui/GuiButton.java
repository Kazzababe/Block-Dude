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
	
	/**
	 * The id of the button to be used by the parent class.
	 * 
	 * @return	The button's id.
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * The text that is displayed when the button is drawn.
	 * 
	 * @return	The button's text.
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * The x location where the button is drawn.
	 * 
	 * @return	The button's x location.
	 */
	public float getX() {
		return this.x;
	}
	
	/**
	 * The y location where the button is drawn.
	 * 
	 * @return	The button's y location.
	 */
	public float getY() {
		return this.y;
	}
	
	/**
	 * The method that is called every tick meant to handle logic updates for the button.
	 */
	public void update() {
		this.active = isInside(Mouse.getX(), Display.getHeight() - Mouse.getY());
		if (InputHelper.isMousePressed(0) && this.active) {
			this.onMouseClick();
		}
		if (InputHelper.isMouseReleased(0) && this.active) {
			this.onMouseRelease();
		}
	}
	
	/**
	 * Whether or not the button is currently being hovered over or not.
	 * 
	 * @return	The mouse is over the button.
	 */
	public boolean isActive() {
		return this.active;
	}
	
	public abstract void render();
	public abstract boolean isInside(float x, float y);
	
	public abstract void onMouseClick();
	public abstract void onMouseRelease();
}