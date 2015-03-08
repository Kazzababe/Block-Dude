package com.blockdude.src.objects.tiles;

import com.blockdude.src.objects.Texture;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.*;

public class Tile {
	
	public static final Tile[] tiles = new Tile[256];
	public static final Tile emtpyTile = new EmptyTile(0);
	public static final Tile staticTile = new StaticTile(1);
	
	public boolean isAnimated = false;
	
	public final int id;
	
	public Color color;
	
	private Shape shape;
	public Texture texture;
	
	public static final float[] Vertices = {
	        // Left bottom triangle
	        0f, 1f, 0f,
	        0f, 0f, 0f,
	        1f, 0f, 0f,
	        // Right top triangle
	        1f, 0f, 0f,
	        1f, 1f, 0f,
	        0f, 1f, 0f
	};
	
	public Tile(int id){
		this.id = id;
		
		tiles[id] = this;
	}
	
	public Shape getShape(float x, float y, float size){
		return new Rectangle(x, y, size, size);
	}
}
