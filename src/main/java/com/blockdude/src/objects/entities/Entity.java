package com.blockdude.src.objects.entities;

import com.blockdude.src.util.input.InputHelper;
import com.blockdude.src.renderer.Renderable;
import com.blockdude.src.objects.Texture;

import org.newdawn.slick.geom.*;

public class Entity extends Renderable {
	private int id;
	private int data;
	
	public Vector2f speed = new Vector2f(10,10);
	public Vector2f motion = new Vector2f(0,0);
	public Vector2f pos = new Vector2f(0,0);
	public Vector2f lastPos = new Vector2f(0,0);
	public Vector2f friction = new Vector2f(0.95f,0.95f);
	
	public Shape shape;
	public Texture texture;
	
	public Entity(int id){
		this.id = id;
	}
	
	public Entity(int id, int data){
		this.id = id;
		this.data = data;
	}

	public void render() {
		//texture.render();
		//ShapeRenderer.draw(shape);
	}

	public void update() {
		
	}
}
