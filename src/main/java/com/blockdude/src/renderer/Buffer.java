package com.blockdude.src.renderer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

/**
 * Buffer utility that stores all buffers for an object
 *
 * @author Jocopa3 (Matt Staubus)
 */
public class Buffer {

	public boolean drawing; //Whether or not the VBO will add vertices
    public boolean coloring; //Whether or not the VBO will add color values
    public boolean texturing; //Whether or not the VBO will add texture coords
    //public boolean normalizing = false; //Not implemented yet;

    public int vertexStart; //Start pos for vertices
    public int colorStart; //Start pos for colors
    public int textureStart; //Start pos for textures
    
    public int drawMode;

    public int VERTEX_SIZE = 8; //Amount of components per vertex (X,Y,Z,R,G,B,U,V)
    public int VERTEX_BYTE_SIZE = VERTEX_SIZE * 4;
	
    public int[] buffers; //Buffer handler ID's
    public int[] bufferLengths; //Buffer lengths

    public int length;

    //Amount of buffers to create; a new buffer is used if buffer-overflow happens
    public static final int DEFAULT_BUFFER_AMOUNT = 4;

    public Buffer() {
        length = DEFAULT_BUFFER_AMOUNT;
        buffers = new int[DEFAULT_BUFFER_AMOUNT];
        bufferLengths = new int[DEFAULT_BUFFER_AMOUNT];
    }

    public Buffer(int amount) {
        length = amount;
        buffers = new int[amount];
        bufferLengths = new int[amount];
    }

    public Buffer(int[] buffs, int[] lengths) {
        length = buffers.length;
        buffers = buffs;
        bufferLengths = lengths;
    }

    //Set the buffer array to a given array
    public void setBuffers(int[] buffer) {
        buffers = buffer;
    }

    //Set the buffer at a given index
    public void setBuffer(int index, int buffer) {
        buffers[index] = buffer;
    }

    //Set the buffer length array
    public void setBufferLengths(int[] lengths) {
        bufferLengths = lengths;
    }

    //Set the length of a buffer at a given index
    public void setBufferLength(int index, int length) {
        bufferLengths[index] = length;
    }

    //Set the buffer array and length array to a new array (Potential for memory leak: should it delete buffers first?)
    public void setBuffersAndLengths(int[] buffer, int[] lengths) {
        buffers = buffer;
        bufferLengths = lengths;
    }

    //Set the buffer and length at a specified index
    public void setBufferAndLength(int index, int buffer, int lengths) {
        buffers[index] = buffer;
        bufferLengths[index] = lengths;
    }

    //Get the array of buffers
    public int[] getBuffers() {
        return buffers;
    }

    //Get the buffer at a specified index
    public int getBuffer(int index) {
        return buffers[index];
    }

    //Get the array of buffer lengths
    public int[] getBufferLengths() {
        return bufferLengths;
    }

    //Get the length of a given buffer
    public int getBufferLength(int index) {
        return bufferLengths[index];
    }

    //Returns whether deletion was successful or not
    public boolean deleteBuffer(int index) {
        try {
            glDeleteBuffers(buffers[index]);

            buffers[index] = 0;
            bufferLengths[index] = 0;

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Returns whether deletion was successful or not
    public boolean deleteAllBuffers() {
        try {
            for (int i : buffers) {
                glDeleteBuffers(i);
            }

            buffers = null;
            bufferLengths = null;

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void draw(){
    	
        
        System.out.println(drawing+" : " + (vertexStart*4)+"\n"+coloring+" : " + (colorStart*4)+"\n"+texturing+" : " + (textureStart*4)+"\n"+getBufferLength(0)+" : "+getBuffer(0)+"\n"+VERTEX_BYTE_SIZE+"\n");

        for (int i = 0; i < length; i++) {
            if (getBuffer(i) <= 0 || getBufferLength(i) <= 0){
                continue; //Ignore any empty buffers
            }
            
            glBindBuffer(GL_ARRAY_BUFFER, getBuffer(i));
            
            if (drawing) {
            	glVertexPointer(3, GL_FLOAT, VERTEX_BYTE_SIZE, vertexStart * 4);
            }
            if (coloring) {
                glColorPointer(3, GL_FLOAT, VERTEX_BYTE_SIZE, colorStart * 4);
            }
            if (texturing) {
                glTexCoordPointer(2, GL_FLOAT, VERTEX_BYTE_SIZE, textureStart * 4);
            }
            
            if (drawing) {
                glEnableClientState(GL_VERTEX_ARRAY); 
            }
            if (coloring) {
                glEnableClientState(GL_COLOR_ARRAY);
            }
            if (texturing) {
                glEnableClientState(GL_TEXTURE_COORD_ARRAY);
            }
            glDrawArrays(drawMode, 0, getBufferLength(i));
            if (texturing) {
                glDisableClientState(GL_TEXTURE_COORD_ARRAY);
            }
            if (coloring) {
                glDisableClientState(GL_COLOR_ARRAY);
            }
            if (drawing) {
                glDisableClientState(GL_VERTEX_ARRAY);
            }
        }
        
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        
       
    }
}
