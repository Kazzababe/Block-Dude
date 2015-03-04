package com.blockdude.src.objects;

import com.blockdude.src.objects.entities.Entity;
import com.blockdude.src.objects.tiles.*;
import com.blockdude.src.renderer.Renderable;
import static org.lwjgl.opengl.GL11.glTranslatef;

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
		levels[currentLevel].render();
	}

	@Override
	public void update() {
		player.pos.x = (float) (Math.cos(time*3)*500);
		player.pos.y = (float) (Math.sin(time)*500);
		time += 0.01;
		glTranslatef(player.pos.x, player.pos.y, 0f);
		levels[currentLevel].update();
	}

	protected Entity getPlayer(){
		return player;
	}
	
}
