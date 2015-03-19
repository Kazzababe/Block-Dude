package com.blockdude.src.util;

public class Timer {
	
	public long startTime;
	public long endTime;
	public long totalTime;
	
	public long getTime() {
		return System.currentTimeMillis() - startTime;
	}
	
	public void startTime(){
		startTime = System.currentTimeMillis();
	}
	
	public long stopTime(long time) {
		endTime = time;
		return getTotalTime();
	}
	
	public long getTotalTime() {
		return endTime - startTime;
	}
}