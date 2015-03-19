package com.blockdude.src.util;

import java.util.Date;

public class Timer {
	
	public long startTime;
	public long endTime;
	public long totalTime;
	
	public long getTime(){
		
		return new Date().getTime();
	}
	
	public void setStartTime(long time) {
		startTime = time;
	}
	
	public void setEndTime(long time) {
		endTime = time;
	}
	public long getTotalTime() {
		totalTime = endTime - startTime;
		
		return totalTime;
	}
}



