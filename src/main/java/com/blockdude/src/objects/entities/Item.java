package com.blockdude.src.objects.entities;

import com.blockdude.src.levels.Level;

public class Item extends Entity {

	public Item(Level parentLevel, int id) {
		super(parentLevel, id);
	}
	
	public Item(Level parentLevel, int id, int data) {
		super(parentLevel, id, data);
	}
}
