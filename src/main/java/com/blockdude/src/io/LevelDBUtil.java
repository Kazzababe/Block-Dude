package com.blockdude.src.io;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

public class LevelDBUtil {
	public static final int KEY_SIZE = 9;
    
    public static final byte SCOREBOARD_DATA = 0x00;
    public static final byte PLAYER_DATA = 0x01;
	
	public static byte[] compileKey(int world, int level, byte dataType){
        return ByteBuffer.allocate(KEY_SIZE).order(ByteOrder.BIG_ENDIAN).putInt(world).putInt(level).put(dataType).array();
    }
	
	public static String getCurrentPath() {
		String dir = new File(".").getAbsolutePath();
		return dir.substring(0, dir.length()-2);
	}
	
	public static byte[] asBytes(boolean b) {
        return b ? new byte[]{1} : new byte[]{0};
    }
    
    public static byte[] asBytes(short s) {
        return ByteBuffer.allocate(Short.SIZE).order(ByteOrder.BIG_ENDIAN).putShort(s).array();
    }
    
    public static byte[] asBytes(char c) {
        return ByteBuffer.allocate(Character.SIZE).order(ByteOrder.BIG_ENDIAN).putChar(c).array();
    }
    
    public static byte[] asBytes(String value) {
        try {
            return value.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] asBytes(int i) {
        return ByteBuffer.allocate(Integer.SIZE).order(ByteOrder.BIG_ENDIAN).putInt(i).array();
    }
    
    public static byte[] asBytes(float f) {
        return ByteBuffer.allocate(Float.SIZE).order(ByteOrder.BIG_ENDIAN).putFloat(f).array();
    }
    
    public static byte[] asBytes(long l) {
        return ByteBuffer.allocate(Long.SIZE).order(ByteOrder.BIG_ENDIAN).putLong(l).array();
    }
    
    public static byte[] asBytes(double d) {
        return ByteBuffer.allocate(Double.SIZE).order(ByteOrder.BIG_ENDIAN).putDouble(d).array();
    }
    
    public static boolean asBoolean(byte[] b) {
        return b[0] == 1;
    }
    
    public static short asShort(byte[] b) {
        return ByteBuffer.wrap(b).order(ByteOrder.BIG_ENDIAN).getShort();
    }
    
    public static char asChar(byte[] b) {
        return ByteBuffer.wrap(b).order(ByteOrder.BIG_ENDIAN).getChar();
    }
    
    public static String asString(byte value[]) {
        try {
            return new String(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static int asInt(byte[] b) {
        return ByteBuffer.wrap(b).order(ByteOrder.BIG_ENDIAN).getInt();
    }
    
    public static float asFloat(byte[] b) {
        return ByteBuffer.wrap(b).order(ByteOrder.BIG_ENDIAN).getFloat();
    }
    
    public static long asLong(byte[] b) {
        return ByteBuffer.wrap(b).order(ByteOrder.BIG_ENDIAN).getLong();
    }
    
    public static double asDouble(byte[] b) {
        return ByteBuffer.wrap(b).order(ByteOrder.BIG_ENDIAN).getDouble();
    }
}
