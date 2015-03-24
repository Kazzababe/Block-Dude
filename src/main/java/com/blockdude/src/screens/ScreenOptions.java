package com.blockdude.src.screens;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;

import com.blockdude.src.BlockDude;
import com.blockdude.src.GlobalOptions;
import com.blockdude.src.audio.MusicHelper;
import com.blockdude.src.gui.GuiButton;
import com.blockdude.src.gui.GuiColorButton;
import com.blockdude.src.gui.GuiMainMenuTextButton;
import com.blockdude.src.gui.GuiSlider;
import com.blockdude.src.shapes.ShapesHelper;

public class ScreenOptions extends Screen {
	private List<GuiSlider> sliders = new ArrayList<GuiSlider>();
	
	private OptionsTab selectedTab = OptionsTab.VIDEO;

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
		ShapesHelper.background(new Color(44, 62, 80));
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
			case 3:
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
		this.buttons.add(new GuiColorButton(this, 0, "Video", 20, 100, 300, 50, new Color(0, 0, 0, 0.55F), new Color(0, 0, 0, 0.85F)));
		this.buttons.add(new GuiColorButton(this, 1, "Audio", 20, 152, 300, 50, new Color(0, 0, 0, 0.55F), new Color(0, 0, 0, 0.85F)));
		this.buttons.add(new GuiColorButton(this, 2, "Gameplay", 20, 204, 300, 50, new Color(0, 0, 0, 0.55F), new Color(0, 0, 0, 0.85F)));
		this.buttons.add(new GuiColorButton(this, 3, "Back", 20, 270, 300, 50, new Color(0, 0, 0, 0.55F), new Color(0, 0, 0, 0.85F)));
		
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
	
	private enum OptionsTab {
		VIDEO, AUDIO, GAMEPLAY;
	}
}
