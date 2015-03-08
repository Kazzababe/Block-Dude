package com.blockdude.src.renderer;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_COLOR_ARRAY;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glColorPointer;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL15.*;

/**
 * TODO: ability to add normals 
 * v0.2: Added the ability to choose which stuff to render (vertices, colors, textures)
 *
 * @author Jocopa3 (Matt Staubus)
 */
public class Tesselator {

    public int vertexCount = 0; //Total amount of vertices in the buffer
    public Buffer buffers; //Array of ID's for the buffer; two or more are used if overflow happens

    public int current = 0; //Current buffer index
    public int previous = 0; //Previous buffer index
    public boolean running = false; //Whether the VBO is accepting new elements or not

    public boolean drawing = true; //Whether or not the VBO will add vertices
    public boolean coloring = true; //Whether or not the VBO will add color values
    public boolean texturing = true; //Whether or not the VBO will add texture coords
    //public boolean normalizing = false; //Not implemented yet;

    public int vertexStart = 0; //Start pos for vertices
    public int colorStart = 3; //Start pos for colors
    public int textureStart = 6; //Start pos for textures

    public int vertexPos; //Start pos for vertices
    public int colorPos; //Start pos for colors
    public int texturePos; //Start pos for textures
    //public int normalPos = x; //Start pos for normals (not implemented yet)
    
    public int drawMode;

    public int bufferSize; //Custom or default size of the buffer
    public FloatBuffer buffer; //Buffer being used for storing rendering info

    public static final int DEFAULT_BUFFER_SIZE = 1572864; //16*16*128*6*8
    public int VERTEX_SIZE = 8; //Amount of components per vertex (X,Y,Z,R,G,B,U,V)
    public int VERTEX_BYTE_SIZE = VERTEX_SIZE * 4;

	//public boolean shouldDraw = false;
    //Create a new buffer using the default size
    public Tesselator() {
        buffers = new Buffer();
        bufferSize = DEFAULT_BUFFER_SIZE;
    }

    //Create a new buffer using a given size
    public Tesselator(int size) {
        buffers = new Buffer();
        bufferSize = size;
    }

    //Create a new buffer using a given size and given amount of buffers; size can be -1 to use the default size
    public Tesselator(int size, int amount) {
        buffers = new Buffer(amount);

        if (size < 0) {
            bufferSize = DEFAULT_BUFFER_SIZE;
        } else {
            bufferSize = size;
        }
    }

    //Set whether or not the VBO should add/use vertex coords
    public void shouldDraw(boolean draw) {
        if (drawing == draw || running) {
            return;
        }

        if (!draw) {
            VERTEX_SIZE -= 3;
            colorStart -= 3;
            textureStart -= 3;
        } else if (draw) {
            VERTEX_SIZE += 3;
            colorStart += 3;
            textureStart += 3;
        }

        VERTEX_BYTE_SIZE = VERTEX_SIZE * 4;

        drawing = draw;
    }

    //Set whether or not the VBO should add/use color values
    public void shouldColor(boolean color) {
        if (coloring == color || running) {
            return;
        }

        if (!color) {
            VERTEX_SIZE -= 3;
            textureStart -= 3;
        } else if (color) {
            VERTEX_SIZE += 3;
            textureStart += 3;
        }

        VERTEX_BYTE_SIZE = VERTEX_SIZE * 4;

        coloring = color;
    }

    //Set whether or not the VBO should add/use texture coords
    public void shouldTexture(boolean texture) {
        if (texturing == texture || running) {
            return;
        }

        if (!texture) {
            VERTEX_SIZE -= 2;
        } else if (texture) {
            VERTEX_SIZE += 2;
        }

        VERTEX_BYTE_SIZE = VERTEX_SIZE * 4;

        texturing = texture;
    }

    //Initialize the buffer to prepare for vertices
    public void start(int drawMode) {
        if (buffer == null) {
            buffer = BufferUtils.createFloatBuffer(bufferSize);
        } else {
            buffer.clear();
        }
        
        this.drawMode = drawMode;

        if (drawing) {
            vertexPos = vertexStart;
        }
        if (coloring) {
            colorPos = colorStart;
        }
        if (texturing) {
            texturePos = textureStart;
        }

        running = true;
    }

    //Handles and binds the buffer; returns the current buffer handler
    public int stop() {
        running = false;

        String s = "";
        for(int i = 0; i < 219; i++)
        	s+=buffer.get(i)+", ";
        System.out.println(s);
        
        buffer.flip();

        BufferProperties props = new BufferProperties(drawing, coloring, texturing, vertexStart, colorStart, textureStart, drawMode, VERTEX_SIZE, VERTEX_BYTE_SIZE);
        buffers.setBufferAndLength(current, glGenBuffers(), vertexCount, props);

        glBindBuffer(GL_ARRAY_BUFFER, buffers.getBuffer(current));
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        nextBuffer();
        return previous;
    }

    //Default draw operation if not drawing via external code
    public void draw() {
        if (drawing) {
            glEnableClientState(GL_VERTEX_ARRAY);
        }
        if (coloring) {
            glEnableClientState(GL_COLOR_ARRAY);
        }
        if (texturing) {
            glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        }

        for (int i = 0; i < buffers.length; i++) {
            if (buffers.getBuffer(i) <= 0 || buffers.getBufferLength(i) <= 0) //Ignore any empty buffers
            {
                continue;
            }

            glBindBuffer(GL_ARRAY_BUFFER, buffers.getBuffer(i));

            if (drawing) {
                glVertexPointer(3, GL_FLOAT, VERTEX_BYTE_SIZE, vertexStart * 4);
            }
            if (coloring) {
                glColorPointer(3, GL_FLOAT, VERTEX_BYTE_SIZE, colorStart * 4);
            }
            if (texturing) {
                glTexCoordPointer(2, GL_FLOAT, VERTEX_BYTE_SIZE, textureStart * 4);
            }

            //System.out.println(i + " " + buffers.getBuffer(i) + " " + buffers.getBufferLength(i) + " " + drawing + " " + coloring + " " + texturing + " " + vertexStart * 4 + " " + colorStart * 4 + " " + textureStart * 4);

            glDrawArrays(this.drawMode, 0, buffers.getBufferLength(i));
        }

        if (texturing) {
            glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        }
        if (coloring) {
            glDisableClientState(GL_COLOR_ARRAY);
        }
        if (drawing) {
            glDisableClientState(GL_VERTEX_ARRAY);
        }

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    //Returns all buffer handlers
    public Buffer getBuffer() {
        return buffers;
    }

    //Reuse the same buffer to save memory
    public void reuseBuffer(int buffer) {
        //Will add code to re-use previous buffers later
    }

    //Reuse the same buffer array to save memory
    public void reuseBuffer(int[] buffers) {
        //Will add code to re-use previous buffers later
    }

    //Check if there are still a valid amount of buffers left, then use
    public void nextBuffer() {
        previous = current;
        if (current < buffers.length - 1) {
            current++;
        }
    }

    //Clean the buffers up; full clean removes all buffer handlers/lengths; returns if clean-up was successful or not
    public boolean cleanUp(boolean fullClean) {
        buffer.clear();
        if (fullClean) {
			//Arrays.fill(bufferHandler, 0);
            //Arrays.fill(bufferLength, 0);
            buffers = null;
        }

        //glCheckError stuff here
        return true;
    }

    //Binds a texture to the buffer to use; shouldn't be used more than once for the same buffer
    public void useTexture(int texture) {
        //Needs implementation
    }

    //Returns if the buffer is too full to hold another quad
    public boolean isFull() {
        /* Needs fixing
         if(drawing)
         return vertexPos + 4*VERTEX_SIZE > vertexCount*VERTEX_SIZE;
         if(coloring)
         return colorPos + 4*VERTEX_SIZE > vertexCount*VERTEX_SIZE;
         if(texturing)
         return texturePos + 4*VERTEX_SIZE > vertexCount*VERTEX_SIZE;
         */
        return false; //Potential for bug
    }

    //Used in emergency; intializes a new buffer to continue accepting vertices
    public void handleFullBuffer() {
        stop();
        //draw();
        cleanUp(false);
        start(drawMode);
    }

    //Add a vertex at the specific x,y,z coords with the r,g,b ccolor and u,v texture
    public Tesselator addVertex(float x, float y, float z, float r, float g, float b, float u, float v) {
        if (isFull()) {
            handleFullBuffer();
        }

        if (drawing) {
            buffer.put(vertexPos, x).put(vertexPos + 1, y).put(vertexPos + 2, z);
        }
        if (coloring) {
            buffer.put(colorPos, r).put(colorPos + 1, g).put(colorPos + 2, b);
        }
        if (texturing) {
            buffer.put(texturePos + 6, u).put(texturePos + 1, v);
        }

        vertexPos += VERTEX_SIZE;
        colorPos += VERTEX_SIZE;
        texturePos += VERTEX_SIZE;
        vertexCount++;

        return this;
    }

    //Add a single x,y,z coordinate
    public Tesselator addVertex(float x, float y, float z) {
        if (!drawing) {
            return this;
        }
        if (isFull()) {
            handleFullBuffer();
        }

        buffer.put(vertexPos, x).put(vertexPos + 1, y).put(vertexPos + 2, z);

        vertexPos += VERTEX_SIZE;
        vertexCount++;

        return this;
    }

    //Add a single color
    public Tesselator addColor(float r, float g, float b) {
        if (!coloring) {
            return this;
        }
        if (!drawing && !texturing && isFull()) {
            handleFullBuffer();
        }

        buffer.put(colorPos, r).put(colorPos + 1, g).put(colorPos + 2, b);

        colorPos += VERTEX_SIZE;

        if (!drawing && !texturing) {
            vertexCount++;
        }

        return this;
    }

    //Add a single texture coord
    public Tesselator addTexture(float u, float v) {
        if (!texturing) {
            return this;
        }
        if (!drawing && !coloring && isFull()) {
            handleFullBuffer();
        }

        buffer.put(texturePos, u).put(texturePos + 1, v);

        texturePos += VERTEX_SIZE;

        if (!drawing && !coloring) {
            vertexCount++;
        }

        return this;
    }
}
