package com.blockdude.src;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

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
			Display.create(new PixelFormat(8, 0, 0, 8));
		} catch(LWJGLException e) {
			e.printStackTrace();
			this.exit();
		}
		setScreen(Screens.MAIN_MENU);
	}
	
	private void initGL() {
		glViewport(0, 0, DIMENSIONS[0], DIMENSIONS[1]);
		glMatrixMode(GL_PROJECTION);
	    glLoadIdentity();
	    glOrtho(0, DIMENSIONS[0], DIMENSIONS[1], 0, 1, -1);
	    glMatrixMode(GL_MODELVIEW);
	    
	    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	private void display() {
		while(!Display.isCloseRequested()) {
			int delta = getDelta();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
			
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
			BlockDude.screen.show();
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}
}
