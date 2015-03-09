package com.blockdude.src.objects;

import org.newdawn.slick.geom.Vector2f;

import com.blockdude.src.objects.entities.Entity;
import com.blockdude.src.renderer.Renderable;

public class World extends Renderable{
	public static final Vector2f GRAVITY = new Vector2f(0.0F, 0.25F / 16.0F);
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
		
		player.render(4);
		
		//glPopMatrix();
	}

	@Override
	public void update() {
		player.update(4);
		
		//player.pos.x = (float) (Math.cos(time*3)*500);
		//player.pos.y = (float) (Math.sin(time)*500);
		time += 0.01;
		
		levels[currentLevel].update();
	}

	protected Entity getPlayer(){
		return player;
	}
	
}
