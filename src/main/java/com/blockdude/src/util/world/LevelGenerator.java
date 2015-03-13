package com.blockdude.src.util.world;

import com.blockdude.src.objects.tiles.Tile;

public class LevelGenerator {
	public static Tile[][] addBorder(Tile[][] tiles) {
		return addBorder(tiles, Tile.staticTile);
	}
	
	public static Tile[][] addBorder(Tile[][] tiles, Tile type) {
		int width = tiles.length;
		if (width < 1) {
			return tiles;
		}
		
		int height = tiles[width - 1].length;
		if (height < 1) {
			return tiles;
		}
		
		for (int x = 0; x < width; x++) {
			tiles[x][0] = type;
			tiles[x][height - 1] = type;
		}
		
		for (int y = 0; y < height; y++) {
			tiles[0][y] = type;
			tiles[width - 1][y] = type;
		}
		
		return tiles;
	}
	
	public static Tile[][] placeRandomExit(Tile[][] tiles) {
		int x, y, width = tiles.length - 2, height = tiles[0].length - 3;
		
		int i = -1;
		while(i++ < width*height*2) {
			x = (int) (Math.random() * width + 1);
			y = (int) (Math.random() * height + 2);
			if(y > 0 && tiles[x][y] != null && tiles[x][y] != Tile.emtpyTile) {
				tiles[x][y-1] = Tile.exitTile;
				break;
			}
		}
		
		return tiles;
	}
	
	public static Tile[][] createRandomWorld(int width, int height) {
		return createRandomWorld(width, height, (int) (Math.random() * width * height * 0.3f));
	}
	
	public static Tile[][] createRandomWorld(int width, int height, int randomBlocks) {
		Tile[][] tiles = new Tile[width][height];
		
		tiles = addBorder(tiles);
		
		int x, y;
		for (int i = 0; i < randomBlocks; i++) {
			x = (int) (Math.random() * width);
			y = (int) (Math.random() * height);
			
			if (tiles[x][y] != null && tiles[x][y] != Tile.emtpyTile) {
				i--;
				continue;
			}
			tiles[x][y] = Tile.staticTile;
		}
		
		tiles = placeRandomExit(tiles);
		
		return tiles;
	}
}