package com.blockdude.src.objects.entities;

import org.lwjgl.input.Keyboard;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import com.blockdude.src.levels.Level;
import com.blockdude.src.levels.World;
import com.blockdude.src.objects.tiles.Tile;
import com.blockdude.src.textures.Textures;
import com.blockdude.src.util.input.InputHelper;

public class Player extends Entity {
	private static final float JUMP_HEIGHT = 6F;
	private static final float MAX_SPEED = 5F;

	public boolean isOnGround = false;
	public boolean isJumping = false;
	public float jumpSpeed = -10.0f;
	public float maxSpeed = 10.0f;
	
	private boolean facingRight = true;
	
	public Player(Level parentLevel, int id) {
		super(parentLevel, id);
		this.type = EntityType.MOB_ENTITY;
		
		this.speed = new Vector2f(0.5F, 0.5F);
		this.pos = new Vector2f(300F, 300F);
		this.shape = new Rectangle(0, 0, 11.45f, 28);
	}

	@Override
	public void render(float delta){
	    //ShapeRenderer.draw(shape);
		Textures.BLOCKDUDE.getTexture().bind();
		
		// Eww, immediate mode... will change later
		glColor4f(1, 1, 1, 1);
		glPushMatrix(); {
			glTranslatef(this.shape.getCenterX(), this.shape.getCenterY(), 0);
			glScalef(this.facingRight? 1 : -1, 1, 1);
			glTranslatef(-this.shape.getCenterX(), -this.shape.getCenterY(), 0);
			glBegin(GL_TRIANGLES); {
				glTexCoord2f(0, 0);
				glVertex2f(this.shape.getMinX(), this.shape.getMinY());
				glTexCoord2f(0, 0.6875f);
				glVertex2f(this.shape.getMinX(), this.shape.getMaxY());
				glTexCoord2f(0.5625f, 0.6875f);
				glVertex2f(this.shape.getMaxX(), this.shape.getMaxY());
				
				glTexCoord2f(0.5625f, 0.6875f);
				glVertex2f(this.shape.getMaxX(), this.shape.getMaxY());
				glTexCoord2f(0.5625f, 0);
				glVertex2f(this.shape.getMaxX(), this.shape.getMinY());
				glTexCoord2f(0, 0);
				glVertex2f(this.shape.getMinX(), this.shape.getMinY());
			}
			glEnd();
		} glPopMatrix();
	}
	
	@Override
	public void update(float delta) {
		// Handle this input
		this.controls(delta);
		
		// Update this position
		this.motion.x += World.GRAVITY.x * delta;
		this.motion.y += World.GRAVITY.y * delta;
		
		this.pos.x += this.motion.x;
		this.shape.setLocation(this.pos);
		this.solveTileCollisions(this.motion.x, 0);
		
		this.isOnGround = false;
		this.pos.y += this.motion.y;
		this.shape.setLocation(this.pos);
		this.solveTileCollisions(0, this.motion.y);
		
		this.motion.x *= this.friction.x * delta;
	}
	
	private void controls(float delta) {
		if ((InputHelper.isKeyDown(Keyboard.KEY_SPACE) || InputHelper.isKeyDown(Keyboard.KEY_UP) || InputHelper.isKeyDown(Keyboard.KEY_W)) && this.isOnGround) { 
			this.motion.y -= JUMP_HEIGHT;
		}
		if (InputHelper.isKeyDown(Keyboard.KEY_LEFT) || InputHelper.isKeyDown(Keyboard.KEY_A)) {
			this.motion.x = Math.max(this.motion.x - this.speed.x * delta, -MAX_SPEED);
			this.facingRight = false;
		}
		if (InputHelper.isKeyDown(Keyboard.KEY_RIGHT) || InputHelper.isKeyDown(Keyboard.KEY_D)) {
			this.motion.x = Math.min(this.motion.x + this.speed.x * delta, MAX_SPEED);
			this.facingRight = true;
		}
		if (InputHelper.isKeyDown(Keyboard.KEY_DOWN) || InputHelper.isKeyDown(Keyboard.KEY_S)) {
			// Put item down
		}
	}
	
	@Override
	public void handleCollision(Entity entity) {
		this.solveEntityCollision(entity, this.motion.x, 0);
		
		this.isOnGround = false;
		this.solveEntityCollision(entity, 0, this.motion.y);
	}
	
	private void solveEntityCollision(Entity e, float xv, float yv) {
		Shape entityShape = e.shape;

		if (this.shape.intersects(entityShape) || entityShape.contains(this.shape)) {
			if (xv < 0) {
				this.pos.x = entityShape.getX() + entityShape.getWidth();
				this.motion.x = 0;
			}
			if (xv > 0) {
				this.pos.x = entityShape.getX() - this.shape.getWidth();
				this.motion.x = 0;
			}
			if (yv < 0) {
				this.pos.y = entityShape.getY() + entityShape.getHeight();
				this.motion.y = 0;
			}
			if (yv > 0) {
				this.pos.y = entityShape.getY() - this.shape.getHeight();
				this.isOnGround = true;
				this.motion.y = 0;
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
					}
					if (xv > 0) {
						this.pos.x = x * tileSize - this.shape.getWidth();
						this.motion.x = 0;
					}
					if (yv < 0) {
						this.pos.y = y * tileSize + tileSize;
						this.motion.y = 0;
					}
					if (yv > 0) {
						this.pos.y = y * tileSize - this.shape.getHeight();
						this.isOnGround = true;
						this.motion.y = 0;
					}
				}
			}
		}
	}
}
