package com.blockdude.src.screens;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;

import com.blockdude.src.BlockDude;
import com.blockdude.src.gui.GuiButton;
import com.blockdude.src.gui.GuiColorButton;
import com.blockdude.src.gui.GuiElement;
import com.blockdude.src.gui.GuiLevelBlock;
import com.blockdude.src.shapes.ShapesHelper;

public class ScreenLevelSelect extends Screen {
	private List<GuiElement> guiElements = new ArrayList<GuiElement>();

	@Override
	public void update(float delta) {
		for (GuiElement gui : this.guiElements) {
			gui.update();
		}
		for (GuiButton button : this.buttons) {
			button.update();
		}
	}

	@Override
	public void render(float delta) {
		ShapesHelper.background(new Color(44, 62, 80));
		for (GuiElement gui : this.guiElements) {
			gui.render();
		}
		for (GuiButton button : this.buttons) {
			button.render();
		}
	}

	@Override
	public void dispose() {
		
	}
	
	@Override
	public void onButtonClick(GuiButton button) {
		switch (button.getId()) {
			case 0:
				BlockDude.setScreen(Screens.MAIN_MENU);
				break;
		}
	}

	@Override
	public void show() {
		this.guiElements.clear();
		this.guiElements.add(new GuiLevelBlock(0, 0, 180, 100));
		this.guiElements.add(new GuiLevelBlock(0, 1, 380, 100));
		this.guiElements.add(new GuiLevelBlock(0, 2, 580, 100));
		this.guiElements.add(new GuiLevelBlock(0, 3, 780, 100));
		this.guiElements.add(new GuiLevelBlock(0, 4, 980, 100));
		this.guiElements.add(new GuiLevelBlock(1, 0, 180, 300));
		this.guiElements.add(new GuiLevelBlock(1, 1, 380, 300));
		this.guiElements.add(new GuiLevelBlock(1, 2, 580, 300));
		this.guiElements.add(new GuiLevelBlock(1, 3, 780, 300));
		this.guiElements.add(new GuiLevelBlock(1, 4, 980, 300));
		this.guiElements.add(new GuiLevelBlock(2, 0, 180, 500));
		this.guiElements.add(new GuiLevelBlock(2, 1, 380, 500));
		this.guiElements.add(new GuiLevelBlock(2, 2, 580, 500));
		this.guiElements.add(new GuiLevelBlock(2, 3, 780, 500));
		this.guiElements.add(new GuiLevelBlock(2, 4, 980, 500));
		
		this.buttons.clear();
		this.buttons.add(new GuiColorButton(this, 0, "Back", 20, 20, 200, 36, new Color(0, 0, 0, 0.55F), new Color(0, 0, 0, 0.85F)));
	}

	@Override
	public void init() {
		
	}
}
