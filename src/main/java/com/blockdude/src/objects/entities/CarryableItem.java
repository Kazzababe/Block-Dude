package com.blockdude.src.objects.entities;

public class CarryableItem extends Item {

	private Entity owner;
	
	public CarryableItem(int id) {
		super(id);
	}
	
	public CarryableItem(int id, int data) {
		super(id, data);
	}
	
	public void setOwner(Entity owner){
		this.owner = owner;
	}
	
	

}
