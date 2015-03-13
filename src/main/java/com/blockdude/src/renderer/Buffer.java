package com.blockdude.src.renderer;

import static org.lwjgl.opengl.GL15.*;

public class Buffer {
    public final int[][] DrawBuffers;

    public static final int TOTAL_BUFFERS = 4;
    public static final int STATIC_BUFFER = 0;
    public static final int DYNAMIC_BUFFER = 1;
    public static final int BACKGROUND_BUFFER = 2;
    public static final int UI_BUFFER = 3;
    
    public static final int BUFFER_PROPERTIES = 2;
    public static final int BUFFER_ID = 0;
    public static final int BUFFER_LENGTH = 1;
    

    public Buffer() {
        this.DrawBuffers = new int[TOTAL_BUFFERS][BUFFER_PROPERTIES];
    }

    public Buffer(int[][] buffers) {
    	this.DrawBuffers = buffers;
    }

    public void setBuffer(int buffer, int[] properties) {
        this.DrawBuffers[buffer] = properties;
    }

    public void setBufferID(int buffer, int id) {
        this.DrawBuffers[buffer][BUFFER_ID] = id;
    }

    public void setBufferLength(int buffer, int length) {
        this.DrawBuffers[buffer][BUFFER_LENGTH] = length;
    }
    
    public void setBufferIDAndLength(int buffer, int id, int length) {
        this.DrawBuffers[buffer][BUFFER_ID] = id;
        this.DrawBuffers[buffer][BUFFER_LENGTH] = length;
    }

    public int[][] getBuffers() {
        return this.DrawBuffers;
    }

    public int[] getBuffer(int buffer) {
        return this.DrawBuffers[buffer];
    }

    public int getBufferID(int buffer) {
        return this.DrawBuffers[buffer][BUFFER_ID];
    }

    public int getBufferLength(int buffer) {
        return this.DrawBuffers[buffer][BUFFER_LENGTH];
    }

    public boolean deleteBuffer(int buffer) {
        try {
            glDeleteBuffers(this.DrawBuffers[buffer][BUFFER_ID]);

            this.DrawBuffers[buffer] = new int[] {0, 0};

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Returns whether deletion was successful or not
    public boolean deleteAllBuffers() {
        try {
            for (int buffer = 0; buffer < TOTAL_BUFFERS; buffer++) {
            	glDeleteBuffers(DrawBuffers[buffer][BUFFER_ID]);
            	DrawBuffers[buffer] = new int[] {0, 0};
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}