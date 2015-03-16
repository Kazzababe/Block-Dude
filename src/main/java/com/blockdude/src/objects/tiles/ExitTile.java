package com.blockdude.src.objects.tiles;

import org.newdawn.slick.Color;

public class ExitTile extends Tile {
	
	public ExitTile(int id) {
		super(id);
		
		this.isSolidTile = false;
		this.color = new Color(1,1,1);
		
		this.setVertices();
		this.setUVs();
	}
	
	public void setVertices(){
		this.Vertices = new float[]{
		        // Left bottom triangle
		        0.2f, 0.1f, 0f,
		        0.2f, 1f, 0f,
		        0.8f, 1f, 0f,
		        // Right top triangle
		        0.8f, 1f, 0f,
		        0.8f, 0.1f, 0f,
		        0.2f, 0.1f, 0f
		};
	}
	
	public void setUVs(){
		this.UVs = new float[]{
				// Left bottom triangle
				0.5f, 0.9f,
				0.5f, 0.9f,
				0.5f, 0.9f,
				// Right top triangle
				0.5f, 0.9f,
				0.5f, 0.9f,
				0.5f, 0.9f
		};
	}

}
