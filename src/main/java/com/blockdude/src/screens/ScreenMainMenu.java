package com.blockdude.src.screens;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import com.blockdude.src.BlockDude;
import com.blockdude.src.audio.MusicHelper;
import com.blockdude.src.gui.GuiButton;
import com.blockdude.src.gui.GuiMainMenuTextButton;

public class ScreenMainMenu extends Screen {
	private Music music;
	
	@Override
	public void update(float delta) {
		for (GuiButton button : this.buttons) {
			button.update();
		}
	}

	@Override
	public void render(float delta) {
		for (GuiButton button : this.buttons) {
			button.render();
		}
	}

	@Override
	public void dispose() {
		MusicHelper.stopMusic(this.music);
	}
	
	@Override
	public void onButtonClick(GuiButton button) {
		switch (button.getId()) {
			case 0:
				BlockDude.setScreen(Screens.LEVEL_SELECT);
				break;
			case 1:
				BlockDude.setScreen(Screens.OPTIONS);
				break;
			case 2:
				BlockDude.exit();
				break;
		}
	}

	@Override
	public void show() {
		this.buttons.clear();
		this.buttons.add(new GuiMainMenuTextButton(this, 0, "START", Display.getWidth() / 2, Display.getHeight() / 2 - 23));
		this.buttons.add(new GuiMainMenuTextButton(this, 1, "OPTIONS", Display.getWidth() / 2, Display.getHeight() / 2));
		this.buttons.add(new GuiMainMenuTextButton(this, 2, "QUIT", Display.getWidth() / 2, Display.getHeight() / 2 + 23));
		
		try {
			this.music = new Music("music/main_menu.ogg");
			this.music.fade(1500, 1.0F, false);
			MusicHelper.playMusic(this.music, true);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() {
		
	}
}
