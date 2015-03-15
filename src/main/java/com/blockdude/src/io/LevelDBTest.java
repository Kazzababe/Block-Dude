package com.blockdude.src.io;

import static com.blockdude.src.io.LevelDBUtil.*;

public class LevelDBTest {

	public static void main(String[] args) {
		// Put data into database
		byte[] key = compileKey(1, 1, SCOREBOARD_DATA);
		byte[] data = asBytes(Math.random());
		LevelDB.instance.put(key, data);
		
		// Get data from database
		key = compileKey(1, 1, SCOREBOARD_DATA);
		data = LevelDB.instance.get(key);
		System.out.println(asDouble(data));
		
		// Close the database (do not normally use this, it's automatically called by BlockDude main class when exiting)
		LevelDB.instance.close();
	}
}
