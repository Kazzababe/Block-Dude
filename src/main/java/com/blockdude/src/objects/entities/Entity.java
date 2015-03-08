package com.blockdude.src.objects.entities;

import com.blockdude.src.util.input.InputHelper;
import com.blockdude.src.renderer.Renderable;
import com.blockdude.src.objects.Texture;

import org.newdawn.slick.geom.*;

public class Entity extends Renderable {
	private int id;
	private int data;
	
	public Vector2f speed;
	public Vector2f motion;
	public Vector2f pos;
	public Vector2f lastPos;
	
	public Shape shape;
	public Texture texture;
	
	public Entity(int id){
		this.id = id;
		this.pos = new Vector2f();
		this.speed = new Vector2f();
		this.motion = new Vector2f();
		this.lastPos = new Vector2f();
	}
	
	public Entity(int id, int data){
		this.id = id;
		this.data = data;
		this.pos = new Vector2f();
		this.lastPos = new Vector2f();
		this.speed = new Vector2f();
		this.motion = new Vector2f();
	}

	public void render() {
		//texture.render();
		//ShapeRenderer.draw(shape);
	}

	public void update() {
		
	}
}
