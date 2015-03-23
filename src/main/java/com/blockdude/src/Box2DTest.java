package com.blockdude.src;

import java.nio.FloatBuffer;
import java.util.HashSet;
import java.util.Set;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import com.blockdude.src.objects.tiles.Tile;
import com.blockdude.src.util.world.LevelGenerator;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Box2DTest {
	
	private static final String TITLE = "Physics Test";
	private static final int[] DIMENSIONS = {1280, 720};
	private static final int tilewidth = 32;
	private static final int playerwidth = 24;
	
	private int buff, v;
	
	Tile[][] tiles;
	
	private World world = new World(new Vec2(0, 0f));
	private Set<Body> bodies = new HashSet<Body>();
	
	private void render(){
		glClear(GL_COLOR_BUFFER_BIT);
		for(Body body : bodies){
			if(body.getType() == BodyType.DYNAMIC){
				glColor3f(0,0.8f,1);
				glPushMatrix();
				Vec2 bodyPosition = body.getPosition().mul(30f);
				glTranslatef(bodyPosition.x, bodyPosition.y, 0);
				glRotated(Math.toDegrees(body.getAngle()), 0, 0, 1);
				glRectf(-playerwidth*0.5f,-playerwidth*0.5f,playerwidth*0.5f,playerwidth*0.5f);
				glPopMatrix();
			}
		}
		
		glEnableClientState(GL_VERTEX_ARRAY);
	    glEnableClientState(GL_COLOR_ARRAY);
		
	    glBindBuffer(GL_ARRAY_BUFFER, buff);
	    
	    glVertexPointer(3, GL_FLOAT, /* stride */6 << 2, 0);
	    glColorPointer(3, GL_FLOAT, /* stride */6 << 2, 3 << 2);
	    
	    glDrawArrays(GL_TRIANGLES, 0, v);
	    
	    glBindBuffer(GL_ARRAY_BUFFER, 0);

	    glDisableClientState(GL_COLOR_ARRAY);
	    glDisableClientState(GL_VERTEX_ARRAY);
	}
	
	private void logic(){
		world.step(1/60f, 8, 3);
	}
	
	private void input(){
		for(Body body : bodies){
			if(body.getType() == BodyType.DYNAMIC){
				if(Keyboard.isKeyDown(Keyboard.KEY_A) && !Keyboard.isKeyDown(Keyboard.KEY_D)){
					body.applyAngularImpulse(0.1f);
				}else if(!Keyboard.isKeyDown(Keyboard.KEY_A) && Keyboard.isKeyDown(Keyboard.KEY_D)){
					body.applyAngularImpulse(-0.1f);
				}
				if(Mouse.isButtonDown(0)){
					Vec2 mousePosition = new Vec2(Mouse.getX(), Mouse.getY()).mul(1/30f);
					Vec2 bodyPosition = body.getPosition();
					Vec2 force = mousePosition.sub(bodyPosition).mul(2);
					body.applyForce(force,  body.getPosition());
				}
			}
		}
	}
	
	public Box2DTest(){
		tiles = LevelGenerator.createRandomWorld(DIMENSIONS[0]/tilewidth, DIMENSIONS[1]/tilewidth);
		setUpDisplay();
        setUpObjects();
        setUpMatrices();
        createStaticBuffer(DIMENSIONS[0]/tilewidth, DIMENSIONS[1]/tilewidth);
        enterGameLoop();
        cleanUp(false);
	}
	
	private void createStaticBuffer(int width, int height) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(width*height*6*3*2);
		
		Tile t;
		int x, y;
		float s = tilewidth;
		v = 0;
		for(x = 0; x < tiles.length; x++){
			for(y = 0; y < tiles[x].length; y++){
				t = tiles[x][y];
				
				if(t==null)
					continue;
				
				buffer.put(t.Vertices[0]*s+x*s); buffer.put(t.Vertices[1]*s+y*s); buffer.put(t.Vertices[2]*s);
				buffer.put((float)Math.random()); buffer.put((float)Math.random()); buffer.put((float)Math.random());
				buffer.put(t.Vertices[3]*s+x*s); buffer.put(t.Vertices[4]*s+y*s); buffer.put(t.Vertices[5]*s);
				buffer.put((float)Math.random()); buffer.put((float)Math.random()); buffer.put((float)Math.random());
				buffer.put(t.Vertices[6]*s+x*s); buffer.put(t.Vertices[7]*s+y*s); buffer.put(t.Vertices[8]*s);
				buffer.put((float)Math.random()); buffer.put((float)Math.random()); buffer.put((float)Math.random());
			
				buffer.put(t.Vertices[9]*s+x*s); buffer.put(t.Vertices[10]*s+y*s); buffer.put(t.Vertices[11]*s);
				buffer.put((float)Math.random()); buffer.put((float)Math.random()); buffer.put((float)Math.random());
				buffer.put(t.Vertices[12]*s+x*s); buffer.put(t.Vertices[13]*s+y*s); buffer.put(t.Vertices[14]*s);
				buffer.put((float)Math.random()); buffer.put((float)Math.random()); buffer.put((float)Math.random());
				buffer.put(t.Vertices[15]*s+x*s); buffer.put(t.Vertices[16]*s+y*s); buffer.put(t.Vertices[17]*s);
				buffer.put((float)Math.random()); buffer.put((float)Math.random()); buffer.put((float)Math.random());
				
				v+=6;
			}
		}
		
		buffer.flip();
		buff = glGenBuffers();
		
		glBindBuffer(GL_ARRAY_BUFFER, buff);
	    glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	    
	    glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	private void setUpObjects(){
		Tile tile;
		for(int x = 0; x < tiles.length; x++){
			for(int y = 0; y < tiles[0].length; y++){
				tile = tiles[x][y];
				if(tile == null)
					continue;
				bodies.add(createStaticBody(world,x*tilewidth, y*tilewidth+tilewidth, tilewidth, tilewidth, true));
			}
		}
		bodies.add(createDynamicBody(world,320,240,playerwidth,playerwidth));
	}
	
	public static float pixelToBox2D(float x){
		return x/30;
	}
	
	public static float box2DToPixel(float x){
		return x*30;
	}
	
	public static Body createStaticBody(World world, float x, float y, float width, float height){
		return createStaticBody(world, x, y, width, height, true);
	}
	
	public static Body createStaticBody(World world, float x, float y, float width, float height, boolean topLeft){
		float hw = width*0.5f;
		float hh = height*0.5f;
		
		BodyDef def = new BodyDef();
		def.position.set(pixelToBox2D(topLeft?x+hw:x), pixelToBox2D(topLeft?y-hh:y));
		def.type = BodyType.STATIC;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(pixelToBox2D(hw), pixelToBox2D(hh));
		
		Body body = world.createBody(def);
		
		FixtureDef fix = new FixtureDef();
		fix.density = 1;
		fix.shape = shape;
		body.createFixture(fix);
		
		return body;
	}
	
	public static Body createDynamicBody(World world, float x, float y, float width, float height){
		return createDynamicBody(world, x, y, width, height, true);
	}
	
	public static Body createDynamicBody(World world, float x, float y, float width, float height, boolean topLeft){
		float hw = width*0.5f;
		float hh = height*0.5f;
		
		BodyDef def = new BodyDef();
		def.position.set(pixelToBox2D(topLeft?x+hw:x), pixelToBox2D(topLeft?y-hh:y));
		def.type = BodyType.DYNAMIC;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(pixelToBox2D(hw), pixelToBox2D(hh));
		
		Body body = world.createBody(def);
		
		FixtureDef fix = new FixtureDef();
		fix.density = 1f;
		fix.restitution = 1f;
		fix.shape = shape;
		body.createFixture(fix);
		
		return body;
	}
	
	private void setUpMatrices(){
		glMatrixMode(GL_PROJECTION);
		glOrtho(0, DIMENSIONS[0], 0, DIMENSIONS[1], 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}
	
	private void setUpDisplay() {
        try {
            Display.setDisplayMode(new DisplayMode(DIMENSIONS[0], DIMENSIONS[1]));
            Display.setTitle(TITLE);
            Display.create(new PixelFormat(32, 0, 24, 0, 4));
        } catch (LWJGLException e) {
            e.printStackTrace();
            cleanUp(true);
        }
    }
	
	private void update() {
        Display.update();
        Display.sync(60);
    }

    private void enterGameLoop() {
        while (!Display.isCloseRequested()) {
        	input();
        	logic();
            render();
            update();
        }
    }
    
    private void cleanUp(boolean crash){
		Display.destroy();
		System.exit(crash ? 1 : 0);
	}
	
	public static void main(String[] args){
		new Box2DTest();
	}
}