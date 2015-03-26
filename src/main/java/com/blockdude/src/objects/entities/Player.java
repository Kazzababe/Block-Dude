package com.blockdude.src.objects.entities;

import java.util.ArrayList;
import java.util.List;

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
	
	private float[] uvCoordsMax = new float[] {0.0703125f, 0.19140625f, 0.3203125f, 0.4921875f, 0.56640625f, 0.69921875f, 0.875f};
	private float[] uvCoordsMin = new float[] {0.0f, 0.125f, 0.25f, 0.375f, 0.5f, 0.625f, 0.75f};
	private float[] widths = new float[] {10.95652f, 10.34782f, 10.95652f, 18.26086f, 10.34782f, 11.56521f, 19.47826f};
	private float uvHeight = 0.71875f;
	private int currentFrame = 0;
	private float ani = 0;
		
	private CarryableItem itemInHand;
	
	private List<Tile> intersectingTiles = new ArrayList<Tile>();
	private List<Entity> intersectingEntities = new ArrayList<Entity>();
	
	public Player(Level parentLevel, int id) {
		super(parentLevel, id);
		this.type = EntityType.MOB_ENTITY;
		
		this.speed = new Vector2f(0.5F, 0.5F);
		this.pos = new Vector2f(300F, 300F);
		this.shape = new Rectangle(0, 0, 11.45f, 28);
	}

	@Override
	public void render(float delta){
		ani += delta;
	    //ShapeRenderer.draw(shape);
		System.out.println(delta);
		Textures.BLOCKDUDE_SHEET.getTexture().bind();
		if (Math.abs(this.motion.x) < 0.5f)
			this.currentFrame = 0;
		else {
			if (ani > 3.0f) {
				if (this.currentFrame == 0) {
					this.currentFrame = 1;
				} else {
					this.currentFrame = (this.currentFrame+1)%6 + 1;
				}
				
				ani = 0;
			}
		}
		
		float w = this.widths[this.currentFrame];
		float uvmin = this.uvCoordsMin[this.currentFrame];
		float uvmax = this.uvCoordsMax[this.currentFrame];
		
		// Eww, immediate mode... will change later
		glColor4f(1, 1, 1, 1);
		glPushMatrix(); {
			glTranslatef(this.shape.getCenterX(), this.shape.getCenterY(), 0);
			glScalef(this.facingRight? 1 : -1, 1, 1);
			glTranslatef(-this.shape.getCenterX(), -this.shape.getCenterY(), 0);
			glBegin(GL_TRIANGLES); {
				glTexCoord2f(uvmin, 0);
				glVertex2f(this.shape.getMinX(), this.shape.getMinY());
				glTexCoord2f(uvmin, this.uvHeight);
				glVertex2f(this.shape.getMinX(), this.shape.getMaxY());
				glTexCoord2f(uvmax, this.uvHeight);
				glVertex2f(this.shape.getMinX() + w, this.shape.getMaxY());
				
				glTexCoord2f(uvmax, this.uvHeight);
				glVertex2f(this.shape.getMinX() + w, this.shape.getMaxY());
				glTexCoord2f(uvmax, 0);
				glVertex2f(this.shape.getMinX() + w, this.shape.getMinY());
				glTexCoord2f(uvmin, 0);
				glVertex2f(this.shape.getMinX(), this.shape.getMinY());
			}
			glEnd();
		} glPopMatrix();
	}
	
	@Override
	public void update(float delta) {
		// Handle this input
		this.controls(delta);
		this.canMove = new boolean[]{true,true,true,true};
		this.intersectingTiles.clear();
		this.intersectingEntities.clear();
		
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
			if (atExit()) {
				this.getParentLevel().levelCompleted();
			}
		}
		if (InputHelper.isKeyDown(Keyboard.KEY_LSHIFT)) {
			pickUpObject();
		} else if (InputHelper.isKeyDown(Keyboard.KEY_LCONTROL)) {
			dropObject();
		}
	}
	
	private void pickUpObject() {
		//this.intersectingEntities.clear();
		//System.out.println(this.intersectingEntities.size());
		if (this.itemInHand != null)
			return;
		
		CarryableItem obj = null;
		for (Entity e : this.intersectingEntities) {
			if (e.type == EntityType.TILE_ENTITY) {
				if (obj != null && e.shape.getMinY() < obj.shape.getMinY()){
					obj = (CarryableItem) e;
				} else if(obj == null) {
					obj = (CarryableItem) e;
				}
			}
		}
		
		if (obj == null)
			return;
		
		this.itemInHand = obj;
		obj.setOwner(this);
		this.getParentLevel().entities.remove(obj);
	}
	
	private void dropObject() {
		if(this.itemInHand == null)
			return;
		
		this.itemInHand.setOwner(null);
		this.itemInHand.pos.set(this.pos);
		this.getParentLevel().entities.add(this.itemInHand);
		this.itemInHand = null;
	}
	
	public boolean atExit() {
		for(Tile t : this.intersectingTiles) {
			if(t == Tile.exitTile)
				return true;
			//System.out.println(t.getClass());
		}
		return false;
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
			this.intersectingEntities.add(e);
			System.out.println(entityShape.getX() +" "+entityShape.getWidth()+" "+entityShape.getY()+" "+entityShape.getHeight());
			if (xv < 0 || !e.canMove[LEFT]) {
				this.pos.x = (entityShape.getX() + 0.1f) + (entityShape.getWidth() - 0.2f);
				this.motion.x = this.motion.x>e.motion.x?this.motion.x:e.motion.x;
			}
			if (xv > 0 || !e.canMove[RIGHT]) {
				this.pos.x = (entityShape.getX() + 0.1f) - this.shape.getWidth();
				this.motion.x = this.motion.x<e.motion.x?this.motion.x:e.motion.x;
			}
			if (yv < 0 || !e.canMove[UP]) {
				this.pos.y = (entityShape.getY() + 0.1f) + (entityShape.getHeight() - 0.2f);
				this.motion.y = 0;
			}
			if (yv > 0 || !e.canMove[DOWN]) {
				this.pos.y = (entityShape.getY() + 0.1f) - this.shape.getHeight();
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
				if (x < 0 || x >= width || y < 0 || y >= height || tiles[x][y] == null)
					continue;
				
				if(!tiles[x][y].isSolid())
					continue;
				
				tileShape = tiles[x][y].getShape(x * tileSize + 0.1f, y * tileSize + 0.1f, tileSize - 0.2f);
				
				//if (this.collides(this.shape, tileShape)) {
				if (this.shape.intersects(tileShape) || tileShape.contains(this.shape)) {
					this.intersectingTiles.add(tiles[x][y]);
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
