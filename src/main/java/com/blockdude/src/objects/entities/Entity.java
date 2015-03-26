package com.blockdude.src.objects.entities;

import com.blockdude.src.levels.Level;
import com.blockdude.src.textures.TextureHelper;
import com.blockdude.src.textures.Textures;

import org.newdawn.slick.geom.*;

public class Entity {
	private int id;
	private int data;
	
	private Level parentLevel;
	
	public Vector2f speed = new Vector2f(10, 10);
	public Vector2f motion = new Vector2f(0, 0);
	public Vector2f pos = new Vector2f(0, 0);
	public Vector2f lastPos = new Vector2f(0, 0);
	public Vector2f friction = new Vector2f(0.9f, 0.9f);
	public boolean[] canMove = new boolean[]{true, true, true, true};
	public int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	
	public Shape shape;
	public Shape hitbox;
	public Textures texture;
	
	public EntityType type = EntityType.NULL_ENTITY;
	
	public Entity(Level parentLevel, int id){
		this(parentLevel);
		this.id = id;
	}
	
	public Entity(Level parentLevel, int id, int data){
		this(parentLevel);
		this.id = id;
		this.data = data;
	}
	
	private Entity(Level parentLevel) {
		this.parentLevel = parentLevel;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getData() {
		return this.data;
	}

	public void render(float delta) {
		TextureHelper.drawTexture(this.texture, this.pos.x, this.pos.y);
		ShapeRenderer.draw(shape);
	}

	public void update(float delta) {
		
	}
	
	public Vector2f getPosition(){
		return this.pos.copy();
	}
	
	public Level getParentLevel() {
		return this.parentLevel;
	}
	
	public boolean collidesWith(Entity e) {
		return this.shape.intersects(e.shape) || this.shape.contains(e.shape);
	}
	
	public boolean touches(Entity e) {
		return this.hitbox.intersects(e.hitbox) || this.hitbox.contains(e.hitbox);
	}
	
	public void handleCollision(Entity e) {
		
	}
}
