package com.blockdude.src.objects;

import org.newdawn.slick.geom.Rectangle;

public class QuadTreeObject {
	public final Rectangle bounds;
	public final Object object;
	
	public QuadTreeObject(Rectangle bounds, Object object) {
		this.bounds = bounds;
		this.object = object;
	}
}
