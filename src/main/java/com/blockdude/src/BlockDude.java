package com.blockdude.src;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import com.blockdude.src.screens.Screen;
import com.blockdude.src.screens.Screens;
import com.blockdude.src.util.input.InputHelper;

public class BlockDude {
	private static final int[] DIMENSIONS = {GlobalOptions.WIDTH, GlobalOptions.HEIGHT};
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
			if(GlobalOptions.useAA) {
				try {
					Display.create(new PixelFormat(32, 0, 24, 0, 4));
				} catch(Exception e) {
					Display.create(); // in-case the computer doesn't support Anti-Alias
				}
			} else {
				Display.create();
			}
		} catch(LWJGLException e) {
			e.printStackTrace();
			this.exit();
		}
		setScreen(Screens.GAME);
	}
	
	private void initGL() {
		glViewport(0, 0, DIMENSIONS[0], DIMENSIONS[1]);
		glMatrixMode(GL_PROJECTION);
	    glLoadIdentity();
	    glOrtho(0, DIMENSIONS[0], DIMENSIONS[1], 0, -1, 1);
	    glMatrixMode(GL_MODELVIEW);
	    
	    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	private void display() {
		while(!Display.isCloseRequested()) {
			int delta = getDelta();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
			
			InputHelper.update();
			
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