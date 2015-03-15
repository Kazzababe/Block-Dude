package com.blockdude.src.io;

import java.io.File;
import java.io.IOException;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import static org.iq80.leveldb.impl.Iq80DBFactory.factory;

public class LevelDB {
	public static LevelDB instance = new LevelDB(LevelDBUtil.getCurrentPath()+"\\data\\");
    
    public static final int DEFAULT_CACHE_SIZE = 16384 * 64; 
    public static final boolean SHOULD_CREATE_IF_MISSING = true;
    
    public final String FILE_LOCATION;
    
    private DB db;
	
	private LevelDB(String location) {
		this.FILE_LOCATION = location;
		
		try {
			this.db = openWorld(this.FILE_LOCATION);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    private DB openWorld(String worldLoc) throws IOException {
        Options options = new Options();
        options.createIfMissing(SHOULD_CREATE_IF_MISSING);
        options.cacheSize(DEFAULT_CACHE_SIZE); 
        return factory.open(new File(worldLoc), options); //Open the db folder
    }
    
    public void put(byte[] key, byte[] data) {
    	db.put(key, data);
    }
    
    public byte[] get(byte[] key) {
    	return db.get(key);
    }
    
    public void close() {
    	try {
			db.close();
		} catch (IOException e) {
			// Uh... shoot
			e.printStackTrace();
		}
    }
}
