package com.blockdude.src.screens;

import com.blockdude.src.levels.Level;
import com.blockdude.src.levels.World;

public class ScreenGame extends Screen {
	private World currentWorld;
	
	public ScreenGame(){
		this.currentWorld = new World("World One");
		this.currentWorld.setLevel(0, new Level(this.currentWorld));
		this.currentWorld.setLevel(0, new Level(this.currentWorld));
	}

	@Override
	public void update(float delta) {
		this.currentWorld.update(delta);
		
	}

	@Override
	public void display(float delta) {
		this.currentWorld.render(delta);
		
	}

	@Override
	public void dispose() {}
	@Override
	public void show() {}
	
}
