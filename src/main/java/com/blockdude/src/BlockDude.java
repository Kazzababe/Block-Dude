package com.blockdude.src;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class BlockDude {
	private static final int[] DIMENSIONS = {1280, 720};
	
	public BlockDude() {
		this.createDisplay();
		this.display();
	}
	
	private void createDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(DIMENSIONS[0], DIMENSIONS[1]));
			Display.create();
		} catch(LWJGLException e) {
			e.printStackTrace();
			this.exit();
		}
	}
	
	private void display() {
		while(!Display.isCloseRequested()) {
			
			
			Display.update();
		}
	}
	
	private void exit() {
		System.exit(0);
	}
}
