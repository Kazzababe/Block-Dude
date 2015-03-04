package com.blockdude.src.objects.entities;

import org.newdawn.slick.geom.Vector2f;

public class Entity {
	private int id;
	private int data;
	
	public Vector2f pos;
	
	public Entity(int id){
		this.id = id;
		this.pos = new Vector2f();
	}
	
	public Entity(int id, int data){
		this.id = id;
		this.data = data;
		this.pos = new Vector2f();
	}
}
