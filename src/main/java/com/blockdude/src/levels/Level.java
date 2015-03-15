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
import com.blockdude.src.util.VectorUtil;

import static com.blockdude.src.renderer.Buffer.*;

import com.blockdude.src.textures.Textures;
import com.blockdude.src.util.world.LevelGenerator;

public class Level {
	public static final int TILE_SIZE = 32;
	
	private World parent;
	private Player player;
	private Vector2f spawn;
	private Vector2f levelScroll = new Vector2f();
	private int scrollStart = TILE_SIZE * 4;
	
	private Tile[][] tiles;
	private int width;
	private int height;
	
	private Buffer buffers;
	
	public Level(World parent) {
		this.parent = parent;
		this.tiles = LevelGenerator.createRandomWorld((int) (GlobalOptions.WIDTH / TILE_SIZE) * 2, (int) (GlobalOptions.HEIGHT / TILE_SIZE));
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
		int s = TILE_SIZE, m = GlobalOptions.MAX_VERTICES;
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(this.width * this.height * 5 * m);
		
		Tile t;
		int x, y;
		int totalVertices = 0;
		for (x = 0; x < this.tiles.length; x++) {
			for (y = 0; y < this.tiles[x].length; y++) {
				t = this.tiles[x][y];
				
				if (t == null || t == Tile.emtpyTile) {
					continue;
				}
				
				// Loop through vertices as tiles may have arbitrary shapes
				for(int i = 0, j = 0; i < t.Vertices.length && i < m; i += 3, j += 2) {
					buffer.put(s * (t.Vertices[i] + x)); buffer.put(s * (t.Vertices[i + 1] + y)); buffer.put(t.Vertices[i + 2]);
					buffer.put(t.UVs[j]); buffer.put(t.UVs[j + 1]);
					
					totalVertices++;
				}
			}
		}
		
		buffer.flip();
		this.buffers.setBufferIDAndLength(STATIC_BUFFER, glGenBuffers(), totalVertices);
		
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
		
		if (scroll.x > GlobalOptions.WIDTH - this.scrollStart) { // Right
			this.levelScroll.x += scroll.x - (GlobalOptions.WIDTH - this.scrollStart);
		}
		if (scroll.x < this.scrollStart) { // Left
			this.levelScroll.x += scroll.x - this.scrollStart;
		}
		if (scroll.y > GlobalOptions.HEIGHT - this.scrollStart) { // Down
			this.levelScroll.y += scroll.y - (GlobalOptions.HEIGHT - this.scrollStart);
		}
		if (scroll.y < this.scrollStart) { // Up
			this.levelScroll.y += scroll.y - this.scrollStart;
		}
		
		int maxX = (this.width - GlobalOptions.WIDTH / TILE_SIZE + 1) * TILE_SIZE;
		int maxY = (this.height - GlobalOptions.HEIGHT / TILE_SIZE) * TILE_SIZE;
		this.levelScroll = VectorUtil.constrain(this.levelScroll, -TILE_SIZE, -TILE_SIZE, maxX, maxY);
	}
	
	public void render(float delta) {
		glPushMatrix(); {
			glTranslatef(-this.levelScroll.x, -this.levelScroll.y, 0f);
			this.renderTiles();
			this.player.render(delta);
		} glPopMatrix();
	}
	
	private void renderTiles() {
		glColor4f(1, 1, 1, 1);
		Textures.TEST.getTexture().bind();
		glEnable(GL_BLEND);
	    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		
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
	
	public void dispose() {
		this.buffers.deleteAllBuffers();
	}
}