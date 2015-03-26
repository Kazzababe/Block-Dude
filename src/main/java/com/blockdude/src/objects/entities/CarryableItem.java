package com.blockdude.src.objects.entities;

import com.blockdude.src.levels.Level;

public class CarryableItem extends Item {
	private Entity owner;
	
	public CarryableItem(Level parentLevel, int id) {
		super(parentLevel, id);
	}
	
	public CarryableItem(Level parentLevel, int id, int data) {
		super(parentLevel, id, data);
	}
	
	public void setOwner(Entity owner){
		System.out.println("pickup");
		this.owner = owner;
	}
	
	public Entity getOwner() {
		return this.owner;
	}
}
