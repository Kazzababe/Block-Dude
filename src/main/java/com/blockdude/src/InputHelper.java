/*
 * Handles mouse and keyboard input and stores values for keys
 * down, released, or pressed, that can be accessed from anywhere.
 * 
 * To update the input helper, add this line into the main draw loop:
 *	InputHelper.update();
 * 
 * Use as so (can be used from anywhere):
 *	InputHelper.isKeyDown(Keyboard.KEY_SPACE);
 */
package com.blockdude.src;

import static com.blockdude.src.InputHelper.EventState.*;

import org.lwjgl.input.*;

/**
 *
 * @author Jocopa3
 */
public class InputHelper {
	private static InputHelper input = new InputHelper(); //Singleton class instance
	
	public static enum EventState {
		NONE, PRESSED, DOWN, RELEASED; 
	}
	
	private EventState[] mouseEvents;
	private EventState[] keyboardEvents;
	
	public InputHelper(){
		//Mouse initialization
		mouseEvents = new EventState[Mouse.getButtonCount()];
		//Add mouse events to Array list
		resetMouse();
		
		//Keyboard initialization
		keyboardEvents = new EventState[Keyboard.KEYBOARD_SIZE];
		//Add keyboard events to Array list
		resetKeys();
	}
	
	private void Update(){
		resetKeys(); //clear Keyboard events
		//Set Key down events (more accurate than using repeat-event method)
		for(int i = 0; i < keyboardEvents.length; i++)
			if(Keyboard.isKeyDown(i))
				keyboardEvents[i] = DOWN;
		while(Keyboard.next()){ //Handle all Keyboard events
			int key = Keyboard.getEventKey();
			if(key<0) continue; //Ignore no events
			
			if(Keyboard.getEventKeyState()){
				if(!Keyboard.isRepeatEvent())
					keyboardEvents[key] = PRESSED;
			}else
				keyboardEvents[key] = RELEASED;
		}
		
		
		resetMouse(); //clear Mouse events
		//Set Mouse down events
		for(int i = 0; i < mouseEvents.length; i++)
			if(Mouse.isButtonDown(i))
				mouseEvents[i] = DOWN;
		while (Mouse.next()){ //Handle all Mouse events
			int button = Mouse.getEventButton();
			if(button<0) continue; //Ignore no events
			
			if (Mouse.getEventButtonState())
				mouseEvents[button] = PRESSED;
			else
				mouseEvents[button] = RELEASED;
		}
	}
	
	//Set all Keyboard events to false
	private void resetKeys(){
		for(int i = 0; i < keyboardEvents.length; i++)
			keyboardEvents[i] = NONE;
	}
	
	//Set all Mouse events to false
	private void resetMouse(){
		for(int i = 0; i < mouseEvents.length; i++)
			mouseEvents[i] = NONE;
	}
	
	//Non-static version of methods (Only used in the singleton instance)
	private boolean KeyDown(int key){
		return keyboardEvents[key] == DOWN;
	}
	private boolean KeyPressed(int key){
		return keyboardEvents[key] == PRESSED;
	}
	private boolean KeyReleased(int key){
		return keyboardEvents[key] == RELEASED;
	}
    private EventState KeyboardEvent(int key){
        return keyboardEvents[key];
    }
	private boolean MouseButtonDown(int key){
		return mouseEvents[key] == DOWN;
	}
	private boolean MouseButtonPressed(int key){
		return mouseEvents[key] == PRESSED;
	}
	private boolean MouseButtonReleased(int key){
		return mouseEvents[key] == RELEASED;
	}
    private EventState MouseEvent(int key){
        return mouseEvents[key];
    }
	private EventState[] MouseEvents(){
		return mouseEvents;
	}
	private EventState[] KeyboardEvents(){
		return keyboardEvents;
	}
	
	//Static version of methods (called from anywhere, return singleton instance value)
	public static boolean isKeyDown(int key){
		return input.KeyDown(key);
	}
	public static boolean isKeyPressed(int key){
		return input.KeyPressed(key);
	}
	public static boolean isKeyReleased(int key){
		return input.KeyReleased(key);
	}
    public static EventState getKeyState(int key){
        return input.KeyboardEvent(key);
    }
	public static boolean isButtonDown(int key){
		return input.MouseButtonDown(key);
	}
	public static boolean isButtonPressed(int key){
		return input.MouseButtonPressed(key);
	}
	public static boolean isButtonReleased(int key){
		return input.MouseButtonReleased(key);
	}
    public static EventState getButtonState(int key){
        return input.MouseEvent(key);
    }
	public static EventState[] getMouseEvents(){
		return input.MouseEvents();
	}
	public static EventState[] getKeyboardEvents(){
		return input.KeyboardEvents();
	}
	public static void update(){
		input.Update();
	}
}