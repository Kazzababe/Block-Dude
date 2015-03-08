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
	
    public int[] buffers; //Buffer handler ID's
    public int[] bufferLengths; //Buffer lengths
    public BufferProperties[] bufferProperties;

    public int length;

    //Amount of buffers to create; a new buffer is used if buffer-overflow happens
    public static final int DEFAULT_BUFFER_AMOUNT = 4;

    public Buffer() {
        length = DEFAULT_BUFFER_AMOUNT;
        buffers = new int[DEFAULT_BUFFER_AMOUNT];
        bufferLengths = new int[DEFAULT_BUFFER_AMOUNT];
        bufferProperties = new BufferProperties[DEFAULT_BUFFER_AMOUNT];
    }

    public Buffer(int amount) {
        length = amount;
        buffers = new int[amount];
        bufferLengths = new int[amount];
        bufferProperties = new BufferProperties[amount];
    }

    public Buffer(int[] buffs, int[] lengths, BufferProperties[] properties) {
        length = buffers.length;
        buffers = buffs;
        bufferLengths = lengths;
        bufferProperties = properties;
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
    
  //Set the buffer property array
    public void setBufferLengths(BufferProperties[] properties) {
        bufferProperties = properties;
    }

    //Set the length of a buffer at a given index
    public void setBufferLength(int index, BufferProperties properties) {
    	bufferProperties[index] = properties;
    }

    //Set the buffer array and length array to a new array (Potential for memory leak: should it delete buffers first?)
    public void setBuffersAndLengths(int[] buffer, int[] lengths, BufferProperties[] properties) {
        buffers = buffer;
        bufferLengths = lengths;
        bufferProperties = properties;
    }

    //Set the buffer and length at a specified index
    public void setBufferAndLength(int index, int buffer, int lengths, BufferProperties properties) {
        buffers[index] = buffer;
        bufferLengths[index] = lengths;
        bufferProperties[index] = properties;
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
    
  //Get the array of buffer lengths
    public BufferProperties[] getBufferProperties() {
        return bufferProperties;
    }

    //Get the length of a given buffer
    public BufferProperties getBufferProperties(int index) {
    	return bufferProperties[index];
    }

    //Returns whether deletion was successful or not
    public boolean deleteBuffer(int index) {
        try {
            glDeleteBuffers(buffers[index]);

            buffers[index] = 0;
            bufferLengths[index] = 0;
            bufferProperties[index] = null;

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
            bufferProperties = null;

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void draw(){
    	BufferProperties props;
        for (int i = 0; i < length; i++) {
        	props = getBufferProperties(i);
            if (getBuffer(i) <= 0 || getBufferLength(i) <= 0 || props == null){
                continue; //Ignore any empty buffers
            }
            //System.out.println(props.drawing+" : " + (props.vertexStart*4)+"\n"+props.coloring+" : " + (props.colorStart*4)+"\n"+props.texturing+" : " + (props.textureStart*4)+"\n"+getBufferLength(0)+" : "+getBuffer(0)+"\n"+props.VERTEX_BYTE_SIZE+"\n");
            glBindBuffer(GL_ARRAY_BUFFER, getBuffer(i));
            
            if (props.drawing) {
            	glVertexPointer(3, GL_FLOAT, props.VERTEX_BYTE_SIZE, props.vertexStart * 4);
            }
            if (props.coloring) {
                glColorPointer(3, GL_FLOAT, props.VERTEX_BYTE_SIZE, props.colorStart * 4);
            }
            if (props.texturing) {
                glTexCoordPointer(2, GL_FLOAT, props.VERTEX_BYTE_SIZE, props.textureStart * 4);
            }
            
            if (props.drawing) {
                glEnableClientState(GL_VERTEX_ARRAY); 
            }
            if (props.coloring) {
                glEnableClientState(GL_COLOR_ARRAY);
            }
            if (props.texturing) {
                glEnableClientState(GL_TEXTURE_COORD_ARRAY);
            }
            glDrawArrays(props.drawMode, 0, getBufferLength(i));
            if (props.texturing) {
                glDisableClientState(GL_TEXTURE_COORD_ARRAY);
            }
            if (props.coloring) {
                glDisableClientState(GL_COLOR_ARRAY);
            }
            if (props.drawing) {
                glDisableClientState(GL_VERTEX_ARRAY);
            }
            System.out.println(i);
        }
        System.out.println();
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}
