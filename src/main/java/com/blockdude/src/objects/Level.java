package com.blockdude.src.objects;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.BufferUtils;
import org.newdawn.slick.geom.Shape;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import com.blockdude.src.GlobalOptions;
import com.blockdude.src.objects.entities.Entity;
import com.blockdude.src.objects.tiles.*;
import com.blockdude.src.renderer.Buffer;
import com.blockdude.src.renderer.Renderable;
import com.blockdude.src.renderer.Tesselator;

import static org.lwjgl.opengl.ARBBufferObject.*;
import static org.lwjgl.opengl.ARBVertexBufferObject.*;

public class Level extends Renderable {
	
	private Tile[][] tiles;
	public final int width;
	public final int height;
	public static final int tileSize = 32;
	
	private World world;
	private Entity player;
	
	private Buffer staticTiles;
	
	private int vertbuff, colbuff, v;
	
	public Level(World world, Tile[][] tiles){
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.tiles = tiles;
		this.world = world;
		player = world.getPlayer();
		
		createStaticBuffer();
	}
	
	public void createStaticBuffer() {
		FloatBuffer verticies = BufferUtils.createFloatBuffer(width*height*6*3);
		FloatBuffer colors = BufferUtils.createFloatBuffer(width*height*6*3);
		
		Tile t;
		int x, y;
		int s = tileSize;
		v = 0;
		for(x = 0; x < tiles.length; x++){
			for(y = 0; y < tiles[x].length; y++){
				t = tiles[x][y];
				
				if(t==null)
					continue;
				
				verticies.put(Tile.Vertices[0]*s+x*s); verticies.put(Tile.Vertices[1]*s+y*s); verticies.put(Tile.Vertices[2]*s);
				colors.put((float)Math.random()); colors.put((float)Math.random()); colors.put((float)Math.random());
				verticies.put(Tile.Vertices[3]*s+x*s); verticies.put(Tile.Vertices[4]*s+y*s); verticies.put(Tile.Vertices[5]*s);
				colors.put((float)Math.random()); colors.put((float)Math.random()); colors.put((float)Math.random());
				verticies.put(Tile.Vertices[6]*s+x*s); verticies.put(Tile.Vertices[7]*s+y*s); verticies.put(Tile.Vertices[8]*s);
				colors.put((float)Math.random()); colors.put((float)Math.random()); colors.put((float)Math.random());
			
				verticies.put(Tile.Vertices[9]*s+x*s); verticies.put(Tile.Vertices[10]*s+y*s); verticies.put(Tile.Vertices[11]*s);
				colors.put((float)Math.random()); colors.put((float)Math.random()); colors.put((float)Math.random());
				verticies.put(Tile.Vertices[12]*s+x*s); verticies.put(Tile.Vertices[13]*s+y*s); verticies.put(Tile.Vertices[14]*s);
				colors.put((float)Math.random()); colors.put((float)Math.random()); colors.put((float)Math.random());
				verticies.put(Tile.Vertices[15]*s+x*s); verticies.put(Tile.Vertices[16]*s+y*s); verticies.put(Tile.Vertices[17]*s);
				colors.put((float)Math.random()); colors.put((float)Math.random()); colors.put((float)Math.random());
				
				v+=6;
			}
		}
		
		verticies.flip();
		colors.flip();
		vertbuff = glGenBuffers();
		colbuff = glGenBuffers();
		
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, vertbuff);
	    glBufferDataARB(GL_ARRAY_BUFFER_ARB, verticies, GL_STATIC_DRAW_ARB);
	    glVertexPointer(3, GL_FLOAT, /* stride */3 << 2, 0L);

	    glBindBufferARB(GL_ARRAY_BUFFER_ARB, colbuff);
	    glBufferDataARB(GL_ARRAY_BUFFER_ARB, colors, GL_STATIC_DRAW_ARB);
	    glColorPointer(3, GL_FLOAT, /* stride */3 << 2, 0L);
	    
	    glBindBuffer(GL_ARRAY_BUFFER_ARB, 0);
	}
	
	public void setTile(int x, int y, Tile t){
		tiles[x][y] = t;
	}
	
	@Override
	public void render() {
		glEnableClientState(GL_VERTEX_ARRAY);
	    glEnableClientState(GL_COLOR_ARRAY);
		
	    glBindBuffer(GL_ARRAY_BUFFER_ARB, colbuff);
	    glColorPointer(3, GL_FLOAT, /* stride */3 << 2, 0L);
	    
	    glBindBuffer(GL_ARRAY_BUFFER_ARB, vertbuff);
	    glVertexPointer(3, GL_FLOAT, /* stride */3 << 2, 0L);
	    glDrawArrays(GL_TRIANGLES, 0, v);
	    
	    glBindBuffer(GL_ARRAY_BUFFER_ARB, 0);

	    glDisableClientState(GL_COLOR_ARRAY);
	    glDisableClientState(GL_VERTEX_ARRAY);
		//staticTiles.draw();
	}

	@Override
	public void update() {
		Shape tileShape;
		for(int x = (int)(player.pos.x/tileSize) - 2; x < player.pos.x/tileSize + 2; x++){
			for(int y = (int)(player.pos.y/tileSize) - 2; y < player.pos.y/tileSize + 2; y++){
				if(x < 0 || x >= width || y < 0 || y >= height || tiles[x][y] == null)
					continue;
				
				tileShape = tiles[x][y].getShape(x*tileSize+0.1f, y*tileSize+0.1f, tileSize-0.2f);
				
				if(tileShape.contains(player.shape)){
					// Player fully inside block, do something to get them out...
					player.pos.y += 50;
				}else if(player.shape.intersects(tileShape)){
					// Player not fully inside block
					player.pos.set(player.lastPos);
					player.motion.set(0,0);
				}
			}
		}
	}
	
}
