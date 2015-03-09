package com.blockdude.src.levels;

import org.newdawn.slick.Color;

import com.blockdude.src.GlobalOptions;
import com.blockdude.src.objects.entities.Player;
import com.blockdude.src.objects.tiles.Tile;
import com.blockdude.src.shapes.ShapesHelper;
import com.blockdude.src.util.world.LevelGenerator;

public class Level {
	public static final int TILE_SIZE = 32;
	
	private World parent;
	private Player player;
	private Tile[][] tiles;
	
	public Level(World parent) {
		this.parent = parent;
		this.tiles = LevelGenerator.createRandomWorld((int) (GlobalOptions.WIDTH / TILE_SIZE), (int) (GlobalOptions.HEIGHT / TILE_SIZE));
		
		this.player = new Player(this, 0);
	}
	
	public World getParent() {
		return this.parent;
	}
	
	public Tile[][] getTiles() {
		return this.tiles;
	}
	
	public void update(float delta) {
		this.player.update(delta);
	}
	
	public void render(float delta) {
		this.player.render(delta);
		this.renderTiles();
	}
	
	private void renderTiles() {
		for(int x = 0; x < this.tiles.length; x++) {
			for(int y = 0; y < this.tiles[0].length; y++) {
				Tile tile = this.tiles[x][y];
				if(tile != null) {
					ShapesHelper.rect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, Color.red);
				}
			}
		}
	}
}
