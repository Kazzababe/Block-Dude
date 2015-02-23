package com.blockdude.src.screens;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

import com.blockdude.src.gui.GuiButton;
import com.blockdude.src.gui.GuiMainMenuTextButton;

public class ScreenMainMenu extends Screen {
	private List<GuiButton> buttons = new ArrayList<GuiButton>();
	
	@Override
	public void update(float delta) {
		for(GuiButton button : this.buttons) {
			button.update();
		}
	}

	@Override
	public void display(float delta) {
		for(GuiButton button : this.buttons) {
			button.render();
		}
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void show() {
		this.buttons.clear();
		this.buttons.add(new GuiMainMenuTextButton("START", Display.getWidth() / 2, Display.getHeight() / 2 - 12));
		this.buttons.add(new GuiMainMenuTextButton("QUIT", Display.getWidth() / 2, Display.getHeight() / 2 + 12));
	}
}
