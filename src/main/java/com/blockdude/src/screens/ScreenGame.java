package com.blockdude.src.screens;

import com.blockdude.src.GlobalOptions;
import com.blockdude.src.objects.Level;
import com.blockdude.src.objects.World;
import com.blockdude.src.objects.entities.Entity;
import com.blockdude.src.objects.entities.Player;
import com.blockdude.src.objects.tiles.Tile;
import com.blockdude.src.util.world.LevelGenerator;

public class ScreenGame extends Screen {
	
	private World[] worlds;
	public int currentWorld = 0;
	public int currentLevel = 0;
	
	public ScreenGame(){
		System.out.println("Created Game Screen");
		worlds = new World[1];
		
		Entity player = new Player(0);
		Tile[][] tiles = LevelGenerator.createRandomWorld((int)(GlobalOptions.WIDTH/Level.tileSize), (int)(GlobalOptions.HEIGHT/Level.tileSize));
		
		worlds[0] = new World(player, 1);
		Level l = new Level(worlds[0], tiles);
		worlds[0].setLevel(0, l);
		
		System.out.println("Created World, Player and Level");
	}

	@Override
	public void update(float delta) {
		worlds[currentWorld].update();
		
	}

	@Override
	public void display(float delta) {
		worlds[currentWorld].render();
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
}
