package com.blockdude.src.screens;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.blockdude.src.fonts.Fonts;

public class ScreenMainMenu extends Screen {
	@Override
	public void update(float delta) {
		
	}

	@Override
	public void display(float delta) {
		Fonts.OPEN_SANS.drawCenteredString(22, Display.getWidth() / 2, Display.getHeight() / 2, "START", Color.white);
		Fonts.OPEN_SANS.drawCenteredString(22, Display.getWidth() / 2, Display.getHeight() / 2 + 26, "QUIT", Color.white);
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void show() {
		
	}
}
