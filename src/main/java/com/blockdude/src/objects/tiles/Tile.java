package com.blockdude.src.objects.tiles;

import org.newdawn.slick.Color;

public class Tile {
	
	public static final Tile[] tiles = new Tile[256];
	public static final Tile emtpyTile = new EmptyTile(0);
	public static final Tile staticTile = new StaticTile(1);
	
	public boolean isAnimated = false;
	
	public final int id;
	
	public Color color;
	
	public Tile(int id){
		this.id = id;
		
		tiles[id] = this;
	}
}
