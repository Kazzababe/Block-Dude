package com.blockdude.src.levels;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

public class World {
	public static final Vector2f GRAVITY = new Vector2f(0.0F, 0.25F);
	
	private String name;
	private ArrayList<Level> levels = new ArrayList<Level>();
	
	private int currentLevel = 0;
	
	public World(String name) {
		this.name = name;
		for(int i = 0; i < 100; i++)
			levels.add(new Level(this));
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
	
	public void nextLevel() {
		this.levels.get(this.currentLevel).dispose();
		this.currentLevel++;
		this.setLevel(this.currentLevel, new Level(this));
	}
	
	public void setLevel(int index, Level level) {
		this.levels.set(index, level);
	}
	
	public void dispose() {
		this.levels.get(this.currentLevel).dispose();
	}
}
