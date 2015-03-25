package com.blockdude.src.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

import com.blockdude.src.shapes.ShapesHelper;
import com.blockdude.src.util.input.InputHelper;

public class GuiCheckbox extends GuiElement {
	private float x;
	private float y;
	private float size;
	
	private boolean checked;
	private GuiCheckboxInterface checkboxInterface;
	
	private Rectangle hitbox;
	
	public GuiCheckbox(float x, float y, float size, GuiCheckboxInterface checkboxInterface) {
		this.x = x;
		this.y = y;
		
		this.size = size;
		this.checkboxInterface = checkboxInterface;
		this.checked = this.checkboxInterface.getDefaultValue();
		
		this.hitbox = new Rectangle(x, y, size, size);
	}

	@Override
	public void render() {
		ShapesHelper.rect(this.x, this.y, this.size, this.size, new Color(0, 0, 0, 0.75F));
		ShapesHelper.rect(this.x + 2, this.y + 2, this.size - 4, this.size - 4, new Color(1.0F, 1.0F, 1.0F));
		if (this.checked) {
			ShapesHelper.rect(this.x + 4, this.y + 4, this.size - 8, this.size - 8, new Color(0, 0, 0, 0.75F));
		}
	}

	@Override
	public void update() {
		if (InputHelper.isMousePressed(0)) {
			if (this.hitbox.contains(Mouse.getX(), Display.getHeight() - Mouse.getY())) {
				this.checked = !this.checked;
				this.checkboxInterface.onValueChanged(this);
			}
		}
	}
	
	public boolean isChecked() {
		return this.checked;
	}
	
	public interface GuiCheckboxInterface {
		public void onValueChanged(GuiCheckbox checkbox);
		public boolean getDefaultValue();
	}
}
