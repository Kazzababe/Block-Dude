package com.blockdude.src.objects;

import org.lwjgl.input.Keyboard;

import com.blockdude.src.InputHelper;
import com.blockdude.src.objects.entities.Entity;
import com.blockdude.src.objects.tiles.*;
import com.blockdude.src.renderer.Renderable;

import static org.lwjgl.opengl.GL11.*;

public class World extends Renderable{
	private Level[] levels;
	private int currentLevel;
	private Entity player;
	private double time;
	
	/*
	 * Player doesn't necessarily have to be the player, it can be any entity
	 */
	public World(Entity player, int size){
		levels = new Level[size];
		this.player = player;
		currentLevel = 0;
	}
	
	public void setLevel(int i, Level level){
		levels[i] = level;
	}

	@Override
	public void render() {
		//glPushMatrix();
		
		//glTranslatef(0, 0, 0f);
		levels[currentLevel].render();
		
		player.render();
		
		//glPopMatrix();
	}

	@Override
	public void update() {
		player.update();
		
		//player.pos.x = (float) (Math.cos(time*3)*500);
		//player.pos.y = (float) (Math.sin(time)*500);
		time += 0.01;
		
		levels[currentLevel].update();
	}

	protected Entity getPlayer(){
		return player;
	}
	
}
