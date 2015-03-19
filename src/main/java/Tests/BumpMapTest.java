+package Tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class BumpMapTest {

	private int shaderProgram;
	private int vertexShader;
	private int fragmentShader;
	private float time;
	
	public BumpMapTest() {
		try {
			Display.setDisplayMode(new DisplayMode(640, 640));
			Display.setTitle("Shader Test");
			Display.setVSyncEnabled(true);
			Display.create(new PixelFormat(/*Alpha Bits*/4, /*Depth bits*/ 0, /*Stencil bits*/ 0, /*samples*/4));
		} catch(LWJGLException e) {
			System.err.println("Couldn't create display");
			e.printStackTrace(System.err);
			quit(true);
		}
		
		glClearColor(0,0,0,1);
		
		genTextures();
		createShaders();
		loop();
	}
	
	private void quit(boolean error) {
		if (this.shaderProgram != 0) {
			glDeleteShader(this.vertexShader);
			glDeleteShader(this.fragmentShader);
			glDeleteProgram(this.shaderProgram);
		}
		
		Display.destroy();
		System.exit(error? 1 : 0);
	}
	
	private void renderShader() {
		glUseProgram(this.shaderProgram);
		int timeUni = glGetUniformLocation(this.shaderProgram, "time");
	    glUniform1f(timeUni, time);
	    int viewportUni = glGetUniformLocation(this.shaderProgram, "viewport");
	    glUniform2f(viewportUni, Display.getWidth(), Display.getHeight());
	}
	
	private void renderShape() {
		glBegin(GL_TRIANGLES); {
			glColor3f(0, 0, 0);
			glVertex2f(-1f, 1f);
			glColor3f(0, 0, 0);
			glVertex2f(-1f, -1f);
			glColor3f(0, 0, 0);
			glVertex2f(1f, 1f);
		
			glColor3f(0, 0, 0);
			glVertex2f(-1f, -1f);
			glColor3f(0, 0, 0);
			glVertex2f(1f, -1f);
			glColor3f(0, 0, 0);
			glVertex2f(1f, 1f);
		} glEnd();
	}
	
	private void loop() {
		time = 0;

		while (!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT);
			
			renderShader();
			renderShape();
			
			glUseProgram(0);
			Display.update();
			Display.sync(60);
			time += 0.01f;
		}
		quit(false);
	}
	
	private String readFile(String file) {
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			
			while ((line = reader.readLine()) != null) {
				builder.append(line).append('\n');
			}
			
			reader.close();
		} catch (IOException e) {
			System.err.println("Error loading file: " + file);
			quit(true);
		}
		return builder.toString();
	}
	
	private boolean compileShader(int shader, String source) {
		glShaderSource(shader, source);
		glCompileShader(shader);
		return !(glGetShader(shader, GL_COMPILE_STATUS) == GL_FALSE);
	}
	
	private void createShaders() {
		this.shaderProgram = glCreateProgram();
		this.vertexShader = glCreateShader(GL_VERTEX_SHADER);
		this.fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		
		String vertexShaderSource = readFile("src/main/java/Tests/bump.vertex");
		String fragmentShaderSource = readFile("src/main/java/Tests/bump.fragment");
		
		boolean v = compileShader(this.vertexShader, vertexShaderSource);
		boolean f = compileShader(this.fragmentShader, fragmentShaderSource);
		
		if (!v || !f) {
			System.out.println("Failed to compile shaders\nVertex Shader: " + v + "\nFragmentShader: " + f);
			quit(true);
		}
		
		glAttachShader(this.shaderProgram, this.vertexShader);
		glAttachShader(this.shaderProgram, this.fragmentShader);
		glLinkProgram(this.shaderProgram);
		glValidateProgram(this.shaderProgram);
	}
	
	private static float pow(float x, int p){
		float value=1;

		if(p==0)
		    return value;

		if(p<0){
		    x=1/x;
		    p=p<0?p-2*p:p;
		}

		while(p>0){
		    value*=x;
		    --p;
		}
		return value;
	}
	
	private Object[] f(float x, float y){
		float t, z=0;
		int i, clr=0, p=13;
		
		t = x; x = x+y*0.5f; y = y-t*0.5f;
		x++;y++;
		int m = 3;
		float ox=x, oy=y;
		x+=0.5; //noise
		y+=1; //noise
		if((((int)y)&1)==1){x+=0.5;};
		int a = ((int)x)|(int)y<<15;
		for(i=0;i<6;i++){
			a=a^a<<7;
			a=a^a<<3;
			a=a>>7&32767;
			a*=a;
		}
		x-=(int)x|0;y-=(int)y|0;
		
		return new Object[]{z, clr};
	}
	
	private void genTextures() {
		
	}
	
	public static void main(String[] args) {
		new BumpMapTest();
	}
}
