package com.blockdude.src.objects;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;

import com.blockdude.src.GlobalOptions;
import com.blockdude.src.objects.entities.Entity;
import com.blockdude.src.objects.tiles.Tile;
import com.blockdude.src.renderer.Buffer;
import com.blockdude.src.renderer.Renderable;
import com.blockdude.src.renderer.Tesselator;

import static org.lwjgl.opengl.ARBBufferObject.*;
import static org.lwjgl.opengl.ARBVertexBufferObject.*;

public class Level extends Renderable{
	
	private Tile[][] tiles;
	public final int width;
	public final int height;
	private World world;
	private Entity player;
	
	private Buffer staticTiles;
	
	public Level(World world, Tile[][] tiles){
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.tiles = tiles;
		this.world = world;
		player = world.getPlayer();
		
		createStaticBuffer();
	}
	
	float[] T = {
	        // Left bottom triangle
	        0f, 1f, 0f,
	        0f, 0f, 0f,
	        1f, 0f, 0f,
	        // Right top triangle
	        1f, 0f, 0f,
	        1f, 1f, 0f,
	        0f, 1f, 0f
	};
	
	public void createStaticBuffer() {
		Tesselator t = new Tesselator(); //Create a new Tesselator
        t.shouldTexture(false); //For now, do nothing with texture offsets
        t.start(GL_TRIANGLES); //Start the Tesselator and set the draw type to GL_TRIANGLES
        int s = 20;
        Tile tile;
        for(int x = 0; x < tiles.length; x++){
        	for(int y = 0; y < tiles[x].length; y++){
        		tile = tiles[x][y];
        		if(tile == null || tile.id != 1)
        			continue;
        		
        		t.addVertex(s*(T[0]+x), s*(T[1]+y), s*T[2]).addColor(tile.color.r, tile.color.g, tile.color.b)
        		.addVertex(s*(T[3]+x), s*(T[4]+y), s*T[5]).addColor(tile.color.r, tile.color.g, tile.color.b)
        		.addVertex(s*(T[6]+x), s*(T[7]+y), s*T[8]).addColor(tile.color.r, tile.color.g, tile.color.b)
        		
        		.addVertex(s*(T[9]+x), s*(T[10]+y), s*T[11]).addColor(tile.color.r, tile.color.g, tile.color.b)
        		.addVertex(s*(T[12]+x), s*(T[13]+y), s*T[14]).addColor(tile.color.r, tile.color.g, tile.color.b)
        		.addVertex(s*(T[15]+x), s*(T[16]+y), s*T[17]).addColor(tile.color.r, tile.color.g, tile.color.b);
        	}
        }
        t.addVertex(0,0,0).addColor(1,0,0)
        .addVertex(0, 1000, 0).addColor(0,1,0)
        .addVertex(1000, 1000, 0).addColor(0, 0, 1);
        t.stop();
        staticTiles = t.getBuffer();
	}
	
	public void setTile(int x, int y, Tile t){
		tiles[x][y] = t;
	}
	
	@Override
	public void render() {
		/*// Most inefficient drawing method ever
		int s = 50;
		ArrayList<Float> verticies = new ArrayList<Float>();
		ArrayList<Float> colors = new ArrayList<Float>();
		
		Tile t;
		int x, y;
		float px = player.pos.x, py = player.pos.y;
		System.out.println(px+" "+py);
		for(x = 0; x < tiles.length; x++){
			for(y = 0; y < tiles[x].length; y++){
				t = tiles[x][y];
				
				if(t==null)
					continue;
				
				verticies.add(T[0]*s+x*s+px); verticies.add(T[1]*s+y*s+py); verticies.add(T[2]*s);
				colors.add(t.color.r); colors.add(t.color.g); colors.add(t.color.b);
				verticies.add(T[3]*s+x*s+px); verticies.add(T[4]*s+y*s+py); verticies.add(T[5]*s);
				colors.add(t.color.r); colors.add(t.color.g); colors.add(t.color.b);
				verticies.add(T[6]*s+x*s+px); verticies.add(T[7]*s+y*s+py); verticies.add(T[8]*s);
				colors.add(t.color.r); colors.add(t.color.g); colors.add(t.color.b);
			
				verticies.add(T[9]*s+x*s+px); verticies.add(T[10]*s+y*s+py); verticies.add(T[11]*s);
				colors.add(t.color.r); colors.add(t.color.g); colors.add(t.color.b);
				verticies.add(T[12]*s+x*s+px); verticies.add(T[13]*s+y*s+py); verticies.add(T[14]*s);
				colors.add(t.color.r); colors.add(t.color.g); colors.add(t.color.b);
				verticies.add(T[15]*s+x*s+px); verticies.add(T[16]*s+y*s+py); verticies.add(T[17]*s);
				colors.add(t.color.r); colors.add(t.color.g); colors.add(t.color.b);
			}
		}
		
		FloatBuffer verts = BufferUtils.createFloatBuffer(verticies.size());
		FloatBuffer cols = BufferUtils.createFloatBuffer(colors.size());
		
		for(Float f : verticies){
			verts.put(f.floatValue());
		}
		verts.flip();
		
		for(Float f : colors){
			cols.put(f.floatValue());
		}
		cols.flip();
		
		glEnableClientState(GL_VERTEX_ARRAY);
	    glEnableClientState(GL_COLOR_ARRAY);
		
	    glColorPointer(3, 3 << 2, cols);
	    glVertexPointer(3, 3 << 2, verts);
	    glDrawArrays(GL_TRIANGLES, 0, verticies.size()/3);

	    glDisableClientState(GL_COLOR_ARRAY);
	    glDisableClientState(GL_VERTEX_ARRAY);
	    */
		
		staticTiles.draw();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
