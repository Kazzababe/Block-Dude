package com.blockdude.src.util;

import org.newdawn.slick.geom.Vector2f;

public class VectorUtil {
	
	public static Vector2f mul(Vector2f a, Vector2f b) {
		a.x *= b.x;
		a.y *= b.y;
		return a;
	}
	
	public static Vector2f mul(Vector2f a, float x) {
		a.x *= x;
		a.y *= x;
		return a;
	}
	
	public static Vector2f mul(Vector2f a, float x, float y) {
		a.x *= x;
		a.y *= y;
		return a;
	}
	
	public static Vector2f div(Vector2f num, Vector2f denom) {
		num.x /= denom.x;
		num.y /= denom.y;
		return num;
	}
	
	public static Vector2f div(Vector2f num, float denom) {
		num.x /= denom;
		num.y /= denom;
		return num;
	}
	
	public static Vector2f div(Vector2f num, float denomX, float denomY) {
		num.x /= denomX;
		num.y /= denomY;
		return num;
	}
	
	public static Vector2f constrain(Vector2f a, Vector2f min, Vector2f max) {
		a.x = a.x > max.x ? max.x : a.x < min.x ? min.x : a.x;
		a.y = a.y > max.y ? max.y : a.y < min.y ? min.y : a.y;
		return a;
	}
	
	public static Vector2f constrain(Vector2f a, float minX, float minY, float maxX, float maxY) {
		a.x = a.x > maxX ? maxX : a.x < minX ? minX : a.x;
		a.y = a.y > maxY ? maxY : a.y < minY ? minY : a.y;
		return a;
	}
	
}
