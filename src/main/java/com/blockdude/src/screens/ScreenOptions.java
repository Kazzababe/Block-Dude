package com.blockdude.src.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Color;

import com.blockdude.src.BlockDude;
import com.blockdude.src.GlobalOptions;
import com.blockdude.src.audio.MusicHelper;
import com.blockdude.src.fonts.Fonts;
import com.blockdude.src.gui.GuiButton;
import com.blockdude.src.gui.GuiCheckbox;
import com.blockdude.src.gui.GuiColorButton;
import com.blockdude.src.gui.GuiElement;
import com.blockdude.src.gui.GuiSlider;
import com.blockdude.src.shapes.ShapesHelper;

public class ScreenOptions extends Screen {
	private Map<OptionsTab, List<GuiElement>> tabElements = new HashMap<OptionsTab, List<GuiElement>>();
	
	private OptionsTab selectedTab = OptionsTab.VIDEO;

	@Override
	public void update(float delta) {
		for (GuiButton button : this.buttons) {
			button.update();
		}
		for (GuiElement element : this.tabElements.get(selectedTab)) {
			element.update();
		}
	}

	@Override
	public void render(float delta) {
		ShapesHelper.background(new Color(44, 62, 80));
		for (GuiButton button : this.buttons) {
			button.render();
		}
		for (GuiElement element : this.tabElements.get(selectedTab)) {
			element.render();
		}
		
		switch (this.selectedTab) {
			case VIDEO:
				// Draw the label for the basic video options
				ShapesHelper.rect(350, 100, 900, 30, new Color(0, 0, 0, 0.75F));
				Fonts.OSWALD.drawString(14, 360, 106, "BASIC VIDEO OPTIONS", Color.white);
				Fonts.OSWALD.drawString(18, 395, 152, "ENABLE ANTI-ALIASING", Color.white);
				break;
			case AUDIO:
				// Draw the label for the master volume slider
				ShapesHelper.rect(350, 100, 900, 30, new Color(0, 0, 0, 0.75F));
				Fonts.OSWALD.drawString(14, 360, 106, "MASTER VOLUME", Color.white);
				
				// Draw the label for the music volume slider
				ShapesHelper.rect(350, 200, 900, 30, new Color(0, 0, 0, 0.75F));
				Fonts.OSWALD.drawString(14, 360, 206, "MUSIC VOLUME", Color.white);
				
				// Draw the label for the sound volume slider
				ShapesHelper.rect(350, 300, 900, 30, new Color(0, 0, 0, 0.75F));
				Fonts.OSWALD.drawString(14, 360, 306, "SOUND VOLUME", Color.white);
				break;
			case GAMEPLAY:
				break;
			default:
				break;
		}
	}

	@Override
	public void dispose() {
		
	}
	
	@Override
	public void onButtonClick(GuiButton button) {
		switch (button.getId()) {
			case 0:
				this.selectedTab = OptionsTab.VIDEO;
				break;
			case 1:
				this.selectedTab = OptionsTab.AUDIO;
				break;
			case 2:
				this.selectedTab = OptionsTab.GAMEPLAY;
				break;
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
		for (OptionsTab type : OptionsTab.values()) {
			this.tabElements.put(type, new ArrayList<GuiElement>());
		}
		
		this.buttons.clear();
		this.buttons.add(new GuiColorButton(this, 0, "Video", 20, 100, 300, 50, new Color(0, 0, 0, 0.55F), new Color(0, 0, 0, 0.85F)));
		this.buttons.add(new GuiColorButton(this, 1, "Audio", 20, 152, 300, 50, new Color(0, 0, 0, 0.55F), new Color(0, 0, 0, 0.85F)));
		this.buttons.add(new GuiColorButton(this, 2, "Gameplay", 20, 204, 300, 50, new Color(0, 0, 0, 0.55F), new Color(0, 0, 0, 0.85F)));
		this.buttons.add(new GuiColorButton(this, 3, "Back", 20, 270, 300, 50, new Color(0, 0, 0, 0.55F), new Color(0, 0, 0, 0.85F)));
		
		List<GuiElement> audioElements = new ArrayList<GuiElement>();
		audioElements.add(new GuiSlider(350, 250, 1, 0, 100, 800, 20, new GuiSlider.GuiSliderInterface() {
			@Override
			public void onSliderChanged(GuiSlider slider) {
				GlobalOptions.MUSIC_VOLUME = slider.getValue();
				MusicHelper.changeVolume();
			}

			@Override
			public int getDefaultValue() {
				return (int) Math.round(GlobalOptions.MUSIC_VOLUME * 100.0);
			}
		}));
		audioElements.add(new GuiSlider(350, 150, 1, 0, 100, 800, 20, new GuiSlider.GuiSliderInterface() {
			@Override
			public void onSliderChanged(GuiSlider slider) {
				GlobalOptions.MASTER_VOLUME = slider.getValue();
				MusicHelper.changeVolume();
			}

			@Override
			public int getDefaultValue() {
				return (int) Math.round(GlobalOptions.MASTER_VOLUME * 100.0);
			}
		}));
		audioElements.add(new GuiSlider(350, 350, 1, 0, 100, 800, 20, new GuiSlider.GuiSliderInterface() {
			@Override
			public void onSliderChanged(GuiSlider slider) {
				GlobalOptions.SOUND_VOLUME = slider.getValue();
				MusicHelper.changeVolume();
			}

			@Override
			public int getDefaultValue() {
				return (int) Math.round(GlobalOptions.SOUND_VOLUME * 100.0);
			}
		}));
		
		List<GuiElement> videoElements = new ArrayList<GuiElement>();
		videoElements.add(new GuiCheckbox(350, 150, 30, new GuiCheckbox.GuiCheckboxInterface() {
			@Override
			public void onValueChanged(GuiCheckbox checkbox) {
				GlobalOptions.USE_ANTI_ALIAS = checkbox.isChecked();
			}
			
			@Override
			public boolean getDefaultValue() {
				return GlobalOptions.USE_ANTI_ALIAS;
			}
		}));
		
		this.tabElements.put(OptionsTab.VIDEO, videoElements);
		this.tabElements.put(OptionsTab.AUDIO, audioElements);
	}
	
	@Override
	public void init() {
		
	}
	
	private enum OptionsTab {
		VIDEO, AUDIO, GAMEPLAY;
	}
}
