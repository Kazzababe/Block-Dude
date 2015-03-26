package com.blockdude.src.screens;

import org.lwjgl.input.Keyboard;

import com.blockdude.src.BlockDude;
import com.blockdude.src.levels.Level;
import com.blockdude.src.levels.World;
import com.blockdude.src.util.input.InputHelper;

public class ScreenGame extends Screen {
	private World currentWorld;
	
	public ScreenGame(){
		this.currentWorld = new World("World One");
		this.currentWorld.setLevel(0, new Level(this.currentWorld));
	}

	@Override
	public void update(float delta) {
		this.currentWorld.update(delta);
		if(InputHelper.isKeyDown(Keyboard.KEY_ESCAPE)){
			BlockDude.setScreen(Screens.OPTIONS);
		}
		
	}

	@Override
	public void render(float delta) {
		this.currentWorld.render(delta);
		
	}

	@Override
	public void dispose() {
		this.currentWorld.dispose();
	}
	
	@Override
	public void show() {}

	@Override
	public void init() {
		
	}
}
