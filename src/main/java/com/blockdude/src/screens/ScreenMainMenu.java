package com.blockdude.src.screens;

import org.lwjgl.opengl.Display;

import com.blockdude.src.BlockDude;
import com.blockdude.src.gui.GuiButton;
import com.blockdude.src.gui.GuiMainMenuTextButton;

public class ScreenMainMenu extends Screen {
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
	public void onButtonClick(GuiButton button) {
		switch(button.getId()) {
			case 0:
				BlockDude.setScreen(Screens.MAIN_MENU);
				System.out.println("CLICK 0");
				break;
		}
	}

	@Override
	public void show() {
		this.buttons.clear();
		this.buttons.add(new GuiMainMenuTextButton(this, 0, "START", Display.getWidth() / 2, Display.getHeight() / 2 - 12));
		this.buttons.add(new GuiMainMenuTextButton(this, 1, "QUIT", Display.getWidth() / 2, Display.getHeight() / 2 + 12));
	}
}
