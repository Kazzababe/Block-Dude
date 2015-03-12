package com.blockdude.src.levels;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.newdawn.slick.geom.Vector2f;

import com.blockdude.src.GlobalOptions;
import com.blockdude.src.objects.entities.Player;
import com.blockdude.src.objects.tiles.Tile;
import com.blockdude.src.renderer.Buffer;

import static com.blockdude.src.renderer.Buffer.*;

import com.blockdude.src.textures.Textures;
import com.blockdude.src.util.world.LevelGenerator;

public class Level {
	public static final int TILE_SIZE = 32;
	
	private World parent;
	private Player player;
	private Vector2f spawn;
	private Vector2f levelScroll = new Vector2f();
	private int scrollStart = 3;
	
	private Tile[][] tiles;
	private int width;
	private int height;
	
	private Buffer buffers;
	
	public Level(World parent) {
		this.parent = parent;
		this.tiles = LevelGenerator.createRandomWorld((int) (GlobalOptions.WIDTH / TILE_SIZE) * 2, (int) (GlobalOptions.HEIGHT / TILE_SIZE) * 2);
		this.width = this.tiles.length;
		this.height = this.tiles[0].length;
		
		this.spawn = this.findOpenSpot();
		this.player = new Player(this, 0);
		this.player.pos.set(spawn);
		
		this.buffers = new Buffer();
		this.createStaticBuffer();
	}
	
	public Vector2f findOpenSpot(){
		Tile t;
		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				t = this.tiles[x][y];
				if (t == null || t == Tile.emtpyTile) {
					return new Vector2f(x * TILE_SIZE, y * TILE_SIZE);
				}
			}
		}
		return new Vector2f();
	}
	
	public void createStaticBuffer() {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(this.width * this.height * 5 * 6);
		
		Tile t;
		int x, y;
		int s = TILE_SIZE;
		int v = 0;
		for (x = 0; x < this.tiles.length; x++) {
			for (y = 0; y < this.tiles[x].length; y++) {
				t = this.tiles[x][y];
				
				if (t==null) {
					continue;
				}
				
				buffer.put(t.Vertices[0] * s + x * s);
				buffer.put(t.Vertices[1] * s + y * s);
				buffer.put(t.Vertices[2] * s);
				//buffer.put((float)Math.random()); buffer.put((float)Math.random()); buffer.put((float)Math.random());
				buffer.put(t.UVs[0]);
				buffer.put(t.UVs[1]);
				buffer.put(t.Vertices[3] * s + x * s);
				buffer.put(t.Vertices[4] * s + y * s);
				buffer.put(t.Vertices[5] * s);
				//buffer.put((float)Math.random()); buffer.put((float)Math.random()); buffer.put((float)Math.random());
				buffer.put(t.UVs[2]);
				buffer.put(t.UVs[3]);
				buffer.put(t.Vertices[6] * s + x * s);
				buffer.put(t.Vertices[7] * s + y * s);
				buffer.put(t.Vertices[8] * s);
				//buffer.put((float)Math.random()); buffer.put((float)Math.random()); buffer.put((float)Math.random());
				buffer.put(t.UVs[4]);
				buffer.put(t.UVs[5]);
				
				buffer.put(t.Vertices[9] * s + x * s);
				buffer.put(t.Vertices[10] * s + y * s);
				buffer.put(t.Vertices[11] * s);
				//buffer.put((float)Math.random()); buffer.put((float)Math.random()); buffer.put((float)Math.random());
				buffer.put(t.UVs[6]);
				buffer.put(t.UVs[7]);
				buffer.put(t.Vertices[12] * s + x * s);
				buffer.put(t.Vertices[13] * s + y * s);
				buffer.put(t.Vertices[14] * s);
				//buffer.put((float)Math.random()); buffer.put((float)Math.random()); buffer.put((float)Math.random());
				buffer.put(t.UVs[8]);
				buffer.put(t.UVs[9]);
				buffer.put(t.Vertices[15] * s + x * s);
				buffer.put(t.Vertices[16] * s + y * s);
				buffer.put(t.Vertices[17] * s);
				//buffer.put((float)Math.random()); buffer.put((float)Math.random()); buffer.put((float)Math.random());
				buffer.put(t.UVs[11]);
				buffer.put(t.UVs[11]);
				
				v += 6;
			}
		}
		
		buffer.flip();
		this.buffers.setBufferIDAndLength(STATIC_BUFFER, glGenBuffers(), v);
		
		glBindBuffer(GL_ARRAY_BUFFER, this.buffers.getBufferID(STATIC_BUFFER));
	    glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	    
	    glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public World getParent() {
		return this.parent;
	}
	
	public Tile[][] getTiles() {
		return this.tiles;
	}
	
	public void update(float delta) {
		this.player.update(delta);
		this.scroll(this.player.getPosition());
	}
	
	public void scroll(Vector2f pos){
		Vector2f scroll = pos.sub(this.levelScroll);
		if (scroll.x > GlobalOptions.WIDTH - TILE_SIZE * this.scrollStart) {
			this.levelScroll.x += scroll.x - (GlobalOptions.WIDTH - TILE_SIZE * this.scrollStart);
		}
		if (scroll.x < TILE_SIZE * this.scrollStart) {
			this.levelScroll.x += scroll.x - TILE_SIZE * this.scrollStart;
		}
		if (scroll.y > GlobalOptions.HEIGHT - TILE_SIZE * this.scrollStart) {
			this.levelScroll.y += scroll.y - (GlobalOptions.HEIGHT - TILE_SIZE * this.scrollStart);
		}
		if (scroll.y < TILE_SIZE * this.scrollStart) {
			this.levelScroll.y += scroll.y - TILE_SIZE * this.scrollStart;
		}
	}
	
	public void render(float delta) {
		glPushMatrix(); {
			glTranslatef(-this.levelScroll.x, -this.levelScroll.y, 0f);
			this.player.render(delta);
			this.renderTiles();
		} glPopMatrix();
	}
	
	private void renderTiles() {
		Textures.TEST.getTexture().bind();
		glColor4f(1, 1, 1, 1);
		
		glEnableClientState(GL_VERTEX_ARRAY);
	    glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		
	    glBindBuffer(GL_ARRAY_BUFFER, this.buffers.getBufferID(STATIC_BUFFER));
	    glVertexPointer(3, GL_FLOAT, 5 << 2, 0);
	    glTexCoordPointer(2, GL_FLOAT, 5 << 2, 3 << 2);
	    
	    glDrawArrays(GL_TRIANGLES, 0, this.buffers.getBufferLength(STATIC_BUFFER));
	    
	    glBindBuffer(GL_ARRAY_BUFFER, 0);

	    glDisableClientState(GL_TEXTURE_COORD_ARRAY);
	    glDisableClientState(GL_VERTEX_ARRAY);
	}
}