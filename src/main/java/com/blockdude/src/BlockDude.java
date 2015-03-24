package com.blockdude.src;

import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import org.newdawn.slick.util.ResourceLoader;

import com.blockdude.src.io.LevelDB;
import com.blockdude.src.screens.Screen;
import com.blockdude.src.screens.Screens;

import static com.blockdude.src.util.ImageUtils.*;

import com.blockdude.src.util.input.InputHelper;

public class BlockDude {
	private static final int TARGET_FPS = 60;
	private static final float TARGET_DELTA = (float) Math.floor(1000.0 / TARGET_FPS);
	
	private static Screen screen;
	
	private long lastFrame;
	
	public BlockDude() {
		this.createDisplay();
		this.initGL();
		this.display();
	}
	
	/**
	 * Create the display window.
	 */
	private void createDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(GlobalOptions.WIDTH, GlobalOptions.HEIGHT));
			if (GlobalOptions.USE_ANTI_ALIAS) {
				try {
					Display.create(new PixelFormat(32, 0, 24, 0, 4));
				} catch (Exception e) {
					Display.create();
				}
			} else {
				Display.create();
			}
			Display.setResizable(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			exit();
		}
		this.setDisplayIcon();
		
		setScreen(Screens.OPTIONS);
	}
	
	/**
	 * Set the display icon for the created window.
	 */
	private void setDisplayIcon() {
		try {
			ByteBuffer[] list = new ByteBuffer[1];
			list[0] = loadIcon(ResourceLoader.getResourceAsStream("textures/tree.png"));
			Display.setIcon(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Resize the window to a new width and height
	 */
	private void resize() {
		GlobalOptions.WIDTH = Display.getWidth();
		GlobalOptions.HEIGHT = Display.getHeight();
		
		glClear(GL_COLOR_BUFFER_BIT);
		glViewport(0, 0, GlobalOptions.WIDTH, GlobalOptions.HEIGHT);
	}
	
	/**
	 * Set the initial values for openGL and create the viewport.
	 */
	private void initGL() {
		glViewport(0, 0, GlobalOptions.WIDTH, GlobalOptions.HEIGHT);
		glMatrixMode(GL_PROJECTION);
	    glLoadIdentity();
	    glOrtho(0, GlobalOptions.WIDTH, GlobalOptions.HEIGHT, 0, -1, 1);
	    glMatrixMode(GL_MODELVIEW);
	    
	    glEnable(GL_TEXTURE_2D);
	    glEnable(GL_BLEND);
	    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	/**
	 * A constantly running method that handles all the game code.
	 */
	private void display() {
		getDelta();
		while (!Display.isCloseRequested()) {
			int delta = getDelta();
			if (delta >= TARGET_DELTA * 2) {
				delta = (int) TARGET_DELTA * 2;
			}
			delta /= TARGET_DELTA;
			if (delta == 0) {
				delta = 1;
			}
			if (Display.wasResized()) {
				resize();
			}
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
			
			InputHelper.update();
			
			if (screen != null) {
				screen.update(delta);
				screen.render(delta);
			}
			
			Display.update();
			Display.sync(TARGET_FPS);
		}
		exit();
	}
	
	/**
	 * Finds the time between the current frame and the previous frame.
	 * 
	 * @return The time between the current and previous frame.
	 */
	private int getDelta() {
		long time = (Sys.getTime() * 1000) / Sys.getTimerResolution();
		int delta = (int) (time - this.lastFrame);
		this.lastFrame = time;
		
		return delta;
	}
	
	/**
	 * Close the window and effectively end the program.
	 */
	public static void exit() {
		Display.destroy();
		BlockDude.screen.dispose();
		LevelDB.instance.close();
		System.exit(0);
	}
	
	/**
	 * Set the active display screen of the game.
	 * 
	 * @param	screen	The screen you which to switch to.
	 */
	public static void setScreen(Screens screen) {
		if (BlockDude.screen != null) {
			BlockDude.screen.dispose();
		}
		try {
			BlockDude.screen = screen.getScreenClass().newInstance();
			BlockDude.screen.show();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
}