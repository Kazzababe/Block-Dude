package com.blockdude.src.screens;

import java.util.ArrayList;
import java.util.List;

import com.blockdude.src.gui.GuiButton;

public abstract class Screen {
	protected List<GuiButton> buttons = new ArrayList<GuiButton>();
	
	public Screen() {
		
	}
	
	public abstract void update(float delta);
	public abstract void render(float delta);
	public abstract void dispose();
	public abstract void show();
	public abstract void init();
	
	public void onButtonClick(GuiButton button) {
		//Filler, just don't want it required in other screen objects
	}
}
