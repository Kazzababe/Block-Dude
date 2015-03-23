package com.blockdude.src.screens;

import java.util.ArrayList;
import java.util.List;

import com.blockdude.src.BlockDude;
import com.blockdude.src.GlobalOptions;
import com.blockdude.src.audio.MusicHelper;
import com.blockdude.src.gui.GuiButton;
import com.blockdude.src.gui.GuiMainMenuTextButton;
import com.blockdude.src.gui.GuiSlider;

public class ScreenOptions extends Screen {
	private List<GuiSlider> sliders = new ArrayList<GuiSlider>();

	@Override
	public void update(float delta) {
		for (GuiButton button : this.buttons) {
			button.update();
		}
		for (GuiSlider slider : this.sliders) {
			slider.update();
		}
	}

	@Override
	public void render(float delta) {
		for (GuiButton button : this.buttons) {
			button.render();
		}
		for (GuiSlider slider : this.sliders) {
			slider.render();
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
				this.saveSettings();
				break;
		}
	}
	
	private void saveSettings() {
		// Save settings to a config file
	}

	@Override
	public void show() {
		this.buttons.clear();
		this.buttons.add(new GuiMainMenuTextButton(this, 0, "BACK", 10, 10));
		
		this.sliders.clear();
		this.sliders.add(new GuiSlider(100, 100, 1, 0, 100, 300, 20, new GuiSlider.GuiSliderInterface() {
			@Override
			public void onSliderChanged(GuiSlider slider) {
				GlobalOptions.MUSIC_VOLUME = slider.getValue();
				MusicHelper.changeVolume();
			}

			@Override
			public int getDefaultValue() {
				return 100;
			}
		}));
	}
}
