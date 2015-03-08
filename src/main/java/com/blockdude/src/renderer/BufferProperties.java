package com.blockdude.src.renderer;

public class BufferProperties {
	public final boolean drawing; //Whether or not the VBO will add vertices
    public final boolean coloring; //Whether or not the VBO will add color values
    public final boolean texturing; //Whether or not the VBO will add texture coords
    //public boolean normalizing = false; //Not implemented yet;

    public final int vertexStart; //Start pos for vertices
    public final int colorStart; //Start pos for colors
    public final int textureStart; //Start pos for textures
    
    public final int drawMode;

    public final int VERTEX_SIZE; //Amount of components per vertex (X,Y,Z,R,G,B,U,V)
    public final int VERTEX_BYTE_SIZE;
    
    public BufferProperties(boolean draw, boolean color, boolean texture, int vert, int col, int tex, int mode, int size, int bytes){
    	drawing = draw;
    	coloring = color;
    	texturing = texture;
    	
    	vertexStart = vert;
    	colorStart = col;
    	textureStart = tex;
    	
    	drawMode = mode;
    	
    	VERTEX_SIZE = size;
    	VERTEX_BYTE_SIZE = bytes;
    }
    
    public BufferProperties(BufferProperties props){
    	drawing = props.drawing;
    	coloring = props.coloring;
    	texturing = props.texturing;
    	
    	vertexStart = props.vertexStart;
    	colorStart = props.colorStart;
    	textureStart = props.textureStart;
    	
    	drawMode = props.drawMode;
    	
    	VERTEX_SIZE = props.VERTEX_SIZE;
    	VERTEX_BYTE_SIZE = props.VERTEX_BYTE_SIZE;
    }
}
