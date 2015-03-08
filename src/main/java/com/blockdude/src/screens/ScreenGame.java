package com.blockdude.src.screens;

import com.blockdude.src.objects.Level;
import com.blockdude.src.objects.World;
import com.blockdude.src.objects.entities.Entity;
import com.blockdude.src.objects.entities.Player;
import com.blockdude.src.objects.tiles.Tile;

public class ScreenGame extends Screen {
	
	private World[] worlds;
	public int currentWorld = 0;
	public int currentLevel = 0;
	
	public ScreenGame(){
		System.out.println("Created Game Screen");
		worlds = new World[1];
		
		Entity player = new Player(0);
		worlds[0] = new World(player, 1);
		Tile[][] tiles = Level.createRandomWorld(25, 14);
		
		System.out.println("Created World, Player and Level");
		
		Level l = new Level(worlds[0], tiles);
		worlds[0].setLevel(0, l);
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
