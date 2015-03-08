package com.blockdude.src.objects.entities;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.ShapeRenderer;

import com.blockdude.src.InputHelper;

import static org.lwjgl.opengl.GL11.*;

public class Player extends Entity {

	public ShapeFill fill;
	
	public Player(int id) {
		super(id);
		// TODO Auto-generated constructor stub
		this.speed.x = 0.5f;
		this.speed.y = 0.5f;
		this.pos.x = 300;
		this.pos.y = 300;
		this.shape = new Rectangle(0,0,40,40);
		fill = new GradientFill(0,0,new Color((float)Math.random(), (float)Math.random(), (float)Math.random()),1000,1000, new Color((float)Math.random(), (float)Math.random(), (float)Math.random()));
	}

	@Override
	public void render(){
	    ShapeRenderer.draw(shape, fill);
	}
	
	@Override
	public void update(){
		lastPos.set(pos);
		
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
		
		motion.x = motion.x > 10 ? 10 : motion.x < -10 ? -10 : motion.x;
		motion.y = motion.y > 10 ? 10 : motion.y < -10 ? -10 : motion.y;
		
		motion.x *= 0.95;
		motion.y *= 0.95;
		motion.y += 0.25;
		
		pos.x += motion.x;
		pos.y += motion.y;
		
		shape.setLocation(pos);
	}

}
