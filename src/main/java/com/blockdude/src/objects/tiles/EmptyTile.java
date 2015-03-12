package com.blockdude.src.objects.tiles;

import org.newdawn.slick.Color;

public class EmptyTile extends Tile{
	
	public EmptyTile(int id){
		super(id);
		
		this.color = new Color(0, 0, 0);
	}
}
