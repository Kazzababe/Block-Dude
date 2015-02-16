package com.blockdude.src;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.blockdude.src.screens.Screen;
import com.blockdude.src.screens.Screens;

public class BlockDude {
	private static final int[] DIMENSIONS = {1280, 720};
	private static final int TARGET_FPS = 60;
	
	private static Screen screen;
	
	private long lastFrame;
	
	public BlockDude() {
		this.createDisplay();
		this.initGL();
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
		setScreen(Screens.MAIN_MENU);
	}
	
	private void initGL() {
		GL11.glViewport(0, 0, DIMENSIONS[0], DIMENSIONS[1]);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
	    GL11.glLoadIdentity();
	    GL11.glOrtho(0, DIMENSIONS[0], DIMENSIONS[1], 0, 1, -1);
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);
	    
	    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	private void display() {
		while(!Display.isCloseRequested()) {
			int delta = getDelta();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			if(screen != null) {
				screen.update(delta);
				screen.display(delta);
			}
			
			Display.update();
			Display.sync(TARGET_FPS);
		}
		Display.destroy();
	}
	
	private int getDelta() {
		long time = (Sys.getTime() * 1000) / Sys.getTimerResolution();
		int delta = (int) (time - this.lastFrame);
		this.lastFrame = time;
		
		return delta;
	}
	
	private void exit() {
		System.exit(0);
	}
	
	public static void setScreen(Screens screen) {
		if(BlockDude.screen != null) BlockDude.screen.dispose();
		try {
			BlockDude.screen = screen.getScreenClass().newInstance();
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		BlockDude.screen.show();
	}
}
