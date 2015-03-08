package com.blockdude.src.screens;

public enum Screens {
	GAME(ScreenGame.class),
	MAIN(ScreenMainMenu.class);
	
	
	private Class<? extends Screen> screenClass;
	
	private Screens(Class<? extends Screen> screenClass) {
		this.screenClass = screenClass;
	}
	
	public Class<? extends Screen> getScreenClass() {
		return this.screenClass;
	}
}
