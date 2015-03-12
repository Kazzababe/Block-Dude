package com.blockdude.src.renderer;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ShaderProgram {
	public final int shaderProgram;
	public final int vertexShader;
	public final int fragmentShader;
	
	public final String fragmentSource;
	public final String vertexSource;
	
	private final String defaultVertexShader = "varying vec3 color;\nvoid main() {\ncolor = gl_Color.rgb;\ngl_Position = ftransform();\n}";
	private final String defaultFragmentShader = "varying vec3 color;\nvoid main(){\ngl_FragColor = vec4(color, 1.0);\n}";
	
	public ShaderProgram() {
		this.shaderProgram = glCreateProgram();
		this.vertexShader = glCreateShader(GL_VERTEX_SHADER);
		this.fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		
		this.vertexSource = defaultVertexShader;
		this.fragmentSource = defaultFragmentShader;
		
		this.createShaders();
	}
	
	public ShaderProgram(String shaderFile, boolean isVertexShader) {
		this.shaderProgram = glCreateProgram();
		this.vertexShader = glCreateShader(GL_VERTEX_SHADER);
		this.fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		
		if (isVertexShader) {
			this.vertexSource = readFile(shaderFile);
			this.fragmentSource = this.defaultFragmentShader;
		} else {
			this.vertexSource = this.defaultVertexShader;
			this.fragmentSource = readFile(shaderFile);
		}
		
		createShaders();
	}
	
	public ShaderProgram(String fragFile, String vertFile) {
		this.shaderProgram = glCreateProgram();
		this.vertexShader = glCreateShader(GL_VERTEX_SHADER);
		this.fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		
		this.vertexSource = readFile(vertFile);
		this.fragmentSource = readFile(fragFile);
		
		this.createShaders();
	}
	
	private void createShaders() {
		boolean v = compileShader(this.vertexShader, this.vertexSource);
		boolean f = compileShader(this.fragmentShader, this.fragmentSource);
		
		if (!v || !f) {
			System.out.println("Failed to compile shaders\nVertex Shader: " + v + "\nFragmentShader: " + f);
			return;
		}
		
		glAttachShader(this.shaderProgram, this.vertexShader);
		glAttachShader(this.shaderProgram, this.fragmentShader);
		glLinkProgram(this.shaderProgram);
		glValidateProgram(this.shaderProgram);
	}
	
	private boolean compileShader(int shader, String source) {
		glShaderSource(shader, source);
		glCompileShader(shader);
		return !(glGetShader(shader, GL_COMPILE_STATUS) == GL_FALSE);
	}
	
	private String readFile(String file) {
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			
			while ((line = reader.readLine()) != null){
				builder.append(line).append('\n');
			}
			
			reader.close();
		} catch(IOException e) {
			System.err.println("Error loading file: " + file);
			return null;
		}
		return builder.toString();
	}
	
	public void use() {
		glUseProgram(this.shaderProgram);
	}
}