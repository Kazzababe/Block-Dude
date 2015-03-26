package com.blockdude.src.objects.entities;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import com.blockdude.src.levels.Level;
import com.blockdude.src.levels.World;
import com.blockdude.src.objects.tiles.Tile;
import com.blockdude.src.textures.Textures;

public class TileEntity extends CarryableItem {

	public boolean isOnGround = false;
	
	public TileEntity(Level parentLevel, int id) {
		super(parentLevel, id);
		this.type = EntityType.TILE_ENTITY;
		
		this.pos = new Vector2f(300F, 300F);
		this.getParentLevel();
		this.hitbox = new Rectangle(0, 0, Level.TILE_SIZE, Level.TILE_SIZE);
		this.shape = new Rectangle(0, 0, Level.TILE_SIZE-0.2f, Level.TILE_SIZE-0.2f);
	}
	
	@Override
	public void render(float delta){
		if(this.getOwner() != null) {
			// render in hand
			return;
		}
	    //ShapeRenderer.draw(shape);
		Textures.CRATE.getTexture().bind();
		
		glColor4f(1,1,1,1);
		glBegin(GL_TRIANGLES); {
			glTexCoord2f(0f,0f);
			glVertex2f(this.shape.getMinX(), this.shape.getMinY());
			glTexCoord2f(0f,1f);
			glVertex2f(this.shape.getMinX(), this.shape.getMaxY());
			glTexCoord2f(1f,1f);
			glVertex2f(this.shape.getMaxX(), this.shape.getMaxY());
			
			glTexCoord2f(1f,1f);
			glVertex2f(this.shape.getMaxX(), this.shape.getMaxY());
			glTexCoord2f(1f,0f);
			glVertex2f(this.shape.getMaxX(), this.shape.getMinY());
			glTexCoord2f(0f,0f);
			glVertex2f(this.shape.getMinX(), this.shape.getMinY());
		}
		glEnd();
	}
	
	@Override
	public void update(float delta) {
		if(this.getOwner() != null)
			return;
		
		this.canMove = new boolean[]{true,true,true,true};
		
		// Update this position
		this.motion.x += World.GRAVITY.x * delta;
		this.motion.y += World.GRAVITY.y * delta;
		//this.motion.x += 0.2f;
		
		this.pos.x += this.motion.x;
		this.shape.setLocation(this.pos);
		this.hitbox.setLocation(this.pos);
		this.solveTileCollisions(this.motion.x, 0);
		
		this.isOnGround = false;
		this.pos.y += this.motion.y;
		this.shape.setLocation(this.pos);
		this.hitbox.setLocation(this.pos);
		this.solveTileCollisions(0, this.motion.y);
		
		this.motion.x *= this.friction.x * delta;
	}
	
	@Override
	public void handleCollision(Entity entity) {
		if(entity == this)
			return;
		
		this.pos.x += this.motion.x;
		this.shape.setLocation(this.pos);
		this.hitbox.setLocation(this.pos);
		this.solveEntityCollision(entity, this.motion.x, 0);
		
		this.isOnGround = false;
		this.pos.y += this.motion.y;
		this.shape.setLocation(this.pos);
		this.hitbox.setLocation(this.pos);
		this.solveEntityCollision(entity, 0, this.motion.y);
	}
	
	private void solveEntityCollision(Entity e, float xv, float yv) {
		Shape entityShape = e.shape;

		// if (this.collides(this.shape, tileShape)) {
		if (this.shape.intersects(entityShape) || entityShape.contains(this.shape)) {
			if (xv < 0 || !e.canMove[LEFT]) {
				this.pos.x = entityShape.getX() + entityShape.getWidth();
				this.motion.x = 0;
				this.canMove[LEFT] = e.canMove[LEFT];
			}
			if (xv > 0 || !e.canMove[RIGHT]) {
				this.pos.x = entityShape.getX() - this.shape.getWidth();
				this.motion.x = 0;
				this.canMove[RIGHT] = e.canMove[RIGHT];
			}
			if (yv < 0 || !e.canMove[UP]) {
				this.pos.y = entityShape.getY() - entityShape.getHeight();
				this.motion.y = 0;
				this.canMove[UP] = e.canMove[UP];
			}
			if (yv > 0 || !e.canMove[DOWN]) {
				this.pos.y = entityShape.getY() - this.shape.getHeight();
				this.isOnGround = true;
				this.motion.y = 0;
				this.canMove[DOWN] = e.canMove[DOWN];
			}
		}
	}
	
	private void solveTileCollisions(float xv, float yv) {
		Shape tileShape;
		int tileSize = Level.TILE_SIZE;
		int width = this.getParentLevel().getTiles().length;
		int height = this.getParentLevel().getTiles()[0].length;
		Tile[][] tiles = this.getParentLevel().getTiles();
		for (int x = (int) (this.pos.x / tileSize) - 2; x < this.pos.x / tileSize + 2; x++) {
			for (int y = (int) (this.pos.y / tileSize) - 2; y < this.pos.y / tileSize + 2; y++) {
				if (x < 0 || x >= width || y < 0 || y >= height || tiles[x][y] == null || !tiles[x][y].isSolid()) {
					continue;
				}
				
				tileShape = tiles[x][y].getShape(x * tileSize + 0.1f, y * tileSize + 0.1f, tileSize - 0.2f);
				
				//if (this.collides(this.shape, tileShape)) {
				if (this.shape.intersects(tileShape) || tileShape.contains(this.shape)) {
					if (xv < 0) {
						this.pos.x = x * tileSize + tileSize;
						this.motion.x = 0;
						this.canMove[LEFT] = false;
					}
					if (xv > 0) {
						this.pos.x = x * tileSize - this.shape.getWidth();
						this.motion.x = 0;
						this.canMove[RIGHT] = false;
					}
					if (yv < 0) {
						this.pos.y = y * tileSize + tileSize;
						this.motion.y = 0;
						this.canMove[UP] = false;
					}
					if (yv > 0) {
						this.pos.y = y * tileSize - this.shape.getHeight();
						this.isOnGround = true;
						this.motion.y = 0;
						this.canMove[DOWN] = false;
					}
				}
			}
		}
	}
}
