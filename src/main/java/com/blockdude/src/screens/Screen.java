package com.blockdude.src.screens;

public abstract class Screen {
	
	public Screen() {
		
	}
	
	public abstract void update(float delta);
	public abstract void display(float delta);
	public abstract void dispose();
	public abstract void show();
}
