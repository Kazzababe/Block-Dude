package com.blockdude.src.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

import com.blockdude.src.shapes.ShapesHelper;
import com.blockdude.src.util.input.InputHelper;

public class GuiSlider {
	private float x;
	private float y;
	
	private float increment;
	
	private int min;
	private int max;
	private float value;
	
	private float width;
	private float height;
	
	private GuiSliderInterface sliderInterface;
	private Rectangle hitbox;
	
	private boolean dragging = false;
	
	public GuiSlider(float x, float y, int increment, int min, int max, float width, float height, GuiSliderInterface sliderInterface) {
		this.x = x;
		this.y = y;
		
		this.increment = (float) increment / (max - min);
		System.out.println(this.increment);
		
		this.min = min;
		this.max = max;
		
		this.width = width;
		this.height = height;
		
		this.sliderInterface = sliderInterface;
		this.hitbox = new Rectangle(x, y, width, height);
		
		this.value = (float) ((double) (this.sliderInterface.getDefaultValue() - this.min) / (this.max - this.min));
	}
	
	public void update() {
		if (InputHelper.isMousePressed(0)) {
			// Check if mouse is in bounds
			if (this.hitbox.contains(Mouse.getX(), Display.getHeight() - Mouse.getY())) {
				this.dragging = true;
			}
		} else if (InputHelper.isMouseReleased(0)) {
			this.dragging = false;
		}
		if (this.dragging) {
			float previousValue = this.value;
			this.value = (Mouse.getX() - this.x) / this.width;
			this.value = Math.round(this.value / this.increment) * this.increment;
			if (this.value < 0.0F) {
				this.value = 0.0F;
			} else if (this.value > 1.0F) {
				this.value = 1.0F;
			}
			if (previousValue != this.value) {
				this.sliderInterface.onSliderChanged(this);
			}
		}
	}
	
	public void render() {
		ShapesHelper.rect(this.x, this.y, this.width + 4, this.height, Color.gray);
		ShapesHelper.rect(this.x + 2, this.y + 2, Math.max(this.width * this.getPercentValue() / 100.0F, 1), this.height - 4, Color.white);
	}
	
	public float getValue() {
		return this.value;
	}
	
	public int getScaledValue() {
		return (int) Math.round((this.max - this.min) * this.value); 
	}
	
	public int getPercentValue() {
		return (int) Math.round(this.value * 100.0);
	}
	
	public interface GuiSliderInterface {
		public void onSliderChanged(GuiSlider slider);
		public int getDefaultValue();
	}
}
