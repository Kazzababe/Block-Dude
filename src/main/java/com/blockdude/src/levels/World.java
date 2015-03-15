package com.blockdude.src.levels;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

public class World {
	public static final Vector2f GRAVITY = new Vector2f(0.0F, 0.25F);
	
	private String name;
	private List<Level> levels;
	
	private int currentLevel = 0;
	
	public World(String name) {
		this.name = name;
		
		this.levels = new ArrayList<Level>();
	}
	
	public void update(float delta) {
		this.levels.get(this.currentLevel).update(delta);
	}
	
	public void render(float delta) {
		this.levels.get(this.currentLevel).render(delta);
	}
	
	public String getWorldName() {
		return this.name;
	}
	
	public void setLevel(int index, Level level) {
		this.levels.add(level);
	}
	
	public void dispose() {
		this.levels.get(this.currentLevel).dispose();
	}
}
