package com.blockdude.src.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.newdawn.slick.opengl.PNGDecoder;

public class ImageUtils {
	
	public static ByteBuffer loadIcon(InputStream is) throws IOException {
		if (is == null) {
			return null;
		}
		
        try {
            PNGDecoder decoder = new PNGDecoder(is);
            ByteBuffer bb = ByteBuffer.allocateDirect(decoder.getWidth() * decoder.getHeight() * 4);
            decoder.decode(bb, decoder.getWidth() * 4, PNGDecoder.RGBA);
            bb.flip();
            
            return bb;
        } finally {
            is.close();
        }
    }
}