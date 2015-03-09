package com.blockdude.src.objects.entities;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.ShapeRenderer;

import com.blockdude.src.GlobalOptions;
import com.blockdude.src.util.input.InputHelper;

import static org.lwjgl.opengl.GL11.*;

public class Player extends Entity {

	public ShapeFill fill;
	
	public boolean isOnGround = false; // Not used, at least not yet
	public boolean isJumping = false;
	public float jumpSpeed = -10.0f;
	public float maxSpeed = 10.0f;
	
	public Player(int id) {
		super(id);
		// TODO Auto-generated constructor stub
		this.speed.x = 0.5f;
		this.speed.y = 0.5f;
		this.pos.x = 300;
		this.pos.y = 300;
		this.shape = new Rectangle(0,0,16,30);
		fill = new GradientFill(0,0,new Color((float)Math.random(), (float)Math.random(), (float)Math.random()),16,32, new Color((float)Math.random(), (float)Math.random(), (float)Math.random()));
	}

	@Override
	public void render(){
	    ShapeRenderer.draw(shape, fill);
	}
	
	@Override
	public void update(){
		lastPos.set(pos);
		
		// Handle player input
		if(InputHelper.isKeyDown(Keyboard.KEY_UP) || InputHelper.isKeyDown(Keyboard.KEY_W)){
			motion.y -= speed.y;
		}
		if(InputHelper.isKeyDown(Keyboard.KEY_DOWN) || InputHelper.isKeyDown(Keyboard.KEY_S)){
			motion.y += speed.y;
		}
		if(InputHelper.isKeyDown(Keyboard.KEY_LEFT) || InputHelper.isKeyDown(Keyboard.KEY_A)){
			motion.x -= speed.x;
		}
		if(InputHelper.isKeyDown(Keyboard.KEY_RIGHT) || InputHelper.isKeyDown(Keyboard.KEY_D)){
			motion.x += speed.x;
		}
		if(!isJumping && InputHelper.isKeyDown(Keyboard.KEY_SPACE)){
			motion.y = jumpSpeed;
			isJumping = true;
		}
		if(InputHelper.isKeyReleased(Keyboard.KEY_SPACE) && motion.y == 0){
			isJumping = false;
		}
		
		// Limit momentum so that you can't run/fly super-fast
		motion.x = motion.x > maxSpeed ? maxSpeed : motion.x < -maxSpeed ? -maxSpeed : motion.x;
		motion.y = motion.y > maxSpeed ? maxSpeed : motion.y < -GlobalOptions.TERMINAL_VELOCITY ? -GlobalOptions.TERMINAL_VELOCITY : motion.y;
		
		// Update player momentum with friction
		motion.x *= friction.x;
		motion.y *= friction.y;
		motion.y += GlobalOptions.GRAVITY; // Gravity
		
		// Update player position
		pos.x += motion.x;
		pos.y += motion.y;
		
		shape.setLocation(pos);
	}

}
