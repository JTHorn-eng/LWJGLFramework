package app;

import org.lwjgl.glfw.GLFW;
import  org.lwjgl.glfw.GLFW.*;

import entities.EntityManager;
import entities.Model;

/**
 * 
 * pass in object to be controlled
 * if non-camera entity is selected camera can only be in STATIC or FOLLOWING, otherwise
 * MODE can be any value (current entity is a camera) 
 *
 *
 * GLFW actions: GLFW_PRESS, GLFW_REPEAT, GLFW_RELEASE
 *
 */
public class Controller {

	private static final String[] MODE = {"FREE_VIEW", "STATIC", "FOLLOW"};
	private static String selectedEntityID = "";
	private static final float ROTATION_CONSTANT = 0.1f;
	private static final float MOVEMENT_X = 0.1f;
	private static final float MOVEMENT_Z = 0.1f;

	private float mouseX, mouseY;

	public Controller() {
		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		GLFW.glfwSetKeyCallback(Window.getWindow(), (window, key, scancode, action, mods) -> {
			
			if (key == GLFW.GLFW_KEY_W && action == GLFW.GLFW_PRESS) {
				movement(GLFW.GLFW_KEY_W);
			}
			if (key == GLFW.GLFW_KEY_A && action == GLFW.GLFW_PRESS) {
				movement(GLFW.GLFW_KEY_A);

			}
			if (key == GLFW.GLFW_KEY_S && action == GLFW.GLFW_PRESS) {
				movement(GLFW.GLFW_KEY_S);

			}
			if (key == GLFW.GLFW_KEY_D && action == GLFW.GLFW_PRESS) {
				movement(GLFW.GLFW_KEY_D);

			}	
		});
		GLFW.glfwSetCursorPosCallback(Window.getWindow(), (window, xpos, ypos) -> {
			mouseX = (float) xpos;
			mouseY = (float) ypos;
			
			
			
			
			
		});
		GLFW.glfwSetMouseButtonCallback(Window.getWindow(), (window, button, action, mods) -> {
			//TODO implement
		});
		
		
	}
	
	public static void controller(String entityName) {
		Controller.selectedEntityID = entityName;
	}
	
	
	//take in key 
	//select entity type - act on type
	
	private void movement(int key) {
		String type = (EntityManager.getModel(selectedEntityID).type() == null) ? ((EntityManager.getCamera(selectedEntityID).type() == null) ? null : EntityManager.getCamera(selectedEntityID).type()) : EntityManager.getModel(selectedEntityID).type();
		if (!selectedEntityID.equals("")) {
			if (type.equals("model")) {
				Model model = EntityManager.getModel(selectedEntityID);
				switch(key) {
					case GLFW.GLFW_KEY_W:
						model.setZ(model.z() + MOVEMENT_Z);
						break;
					case GLFW.GLFW_KEY_A:
						model.setX(model.x() - MOVEMENT_X);
						break;
					case GLFW.GLFW_KEY_D:
						model.setX(model.x() + MOVEMENT_X);
						break;
					case GLFW.GLFW_KEY_S:
						model.setZ(model.z() - MOVEMENT_Z);
				}
			}
			else if (type.equals("camera")) {
				
			}
		}
	}
	
	
	
	
	
	
	
	
	
}
