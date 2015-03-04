package com.blockdude.src.objects.tiles;

import org.newdawn.slick.Color;

public class StaticTile extends Tile{
	
	public StaticTile(int id){
		super(id);
		
		color = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
	}
}