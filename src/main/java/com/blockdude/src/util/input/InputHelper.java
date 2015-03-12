package com.blockdude.src.util.input;

import java.util.Arrays;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class InputHelper {
	private static InputState[] mouseEvents;
	private static InputState[] keyboardEvents;
	
	public static boolean isKeyDown(int key) {
		return keyboardEvents[key] == InputState.DOWN;
	}
	
	public static boolean isKeyPressed(int key) {
		return keyboardEvents[key] == InputState.PRESSED;
	}
	
	public static boolean isKeyReleased(int key) {
		return keyboardEvents[key] == InputState.RELEASED;
	}
	
	public static InputState getKeyState(int key) {
		return keyboardEvents[key];
	}
	
	public static boolean isMouseDown(int key) {
		return mouseEvents[key] == InputState.DOWN;
	}
	
	public static boolean isMousePressed(int key) {
		return mouseEvents[key] == InputState.PRESSED;
	}
	
	public static boolean isMouseReleased(int key) {
		return mouseEvents[key] == InputState.RELEASED;
	}
	
	public static InputState getMouseState(int key) {
		return mouseEvents[key];
	}
	
	private static void resetKeys() {
		Arrays.fill(keyboardEvents, InputState.NONE);
	}
	
	private static void resetMouse() {
		Arrays.fill(mouseEvents, InputState.NONE);
	}
	
	public static void update() {
		resetKeys();
		for (int i = 0; i < keyboardEvents.length; i++) {
			if (Keyboard.isKeyDown(i)) {
				keyboardEvents[i] = InputState.DOWN;
			}
		}
		while (Keyboard.next()) {
			int key = Keyboard.getEventKey();
			if (key < 0) {
				continue;
			}
			
			if (Keyboard.getEventKeyState()) {
				if (!Keyboard.isRepeatEvent()) {
					keyboardEvents[key] = InputState.PRESSED;
				}
			} else {
				keyboardEvents[key] = InputState.RELEASED;
			}
		}
		
		resetMouse();
		for (int i = 0; i < mouseEvents.length; i++) {
			if (Mouse.isButtonDown(i)) {
				mouseEvents[i] = InputState.DOWN;
			}
		}
		while (Mouse.next()) {
			int button = Mouse.getEventButton();
			if (button < 0) {
				continue;
			}
			
			if (Mouse.getEventButtonState()) {
				mouseEvents[button] = InputState.PRESSED;
			} else {
				mouseEvents[button] = InputState.RELEASED;
			}
		}
	}
	
	static {
		mouseEvents = new InputState[Mouse.getButtonCount()];
		//Fill the mouseEvents array with a default state
		Arrays.fill(mouseEvents, InputState.NONE);
		
		keyboardEvents = new InputState[Keyboard.KEYBOARD_SIZE];
		Arrays.fill(keyboardEvents, InputState.NONE);
	}
}
