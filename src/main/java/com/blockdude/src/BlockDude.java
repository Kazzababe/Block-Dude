package com.blockdude.src;

import static org.lwjgl.opengl.GL11.*;

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
			Display.setDisplayMode(new DisplayMode(GlobalOptions.WIDTH, GlobalOptions.HEIGHT));
			System.out.println(GlobalOptions.useAA);
			if(GlobalOptions.useAA)
				try{
					Display.create(new PixelFormat(32, 0, 24, 0, 4));
				}catch(Exception e){
					Display.create(); // in-case the computer doesn't support Anti-Alias
				}
			else
				Display.create();
			
			GlobalOptions.useAA = !GlobalOptions.useAA;
			System.out.println(GlobalOptions.useAA);
			//Display.create();
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
	    glOrtho(0, DIMENSIONS[0], DIMENSIONS[1], 0, 1000, -1000);
	    glMatrixMode(GL_MODELVIEW);
	    
	    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	private void display() {
		//glClearColor(0.4f, 0.6f, 0.9f, 0f);
		while(!Display.isCloseRequested()) {
			int delta = getDelta();
			 glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

		      glMatrixMode(GL_PROJECTION);
		      glLoadIdentity();

		      glMatrixMode(GL_MODELVIEW);
		      glLoadIdentity();
		      glOrtho(0, DIMENSIONS[0], DIMENSIONS[1], 0, 1000, -1000);
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
