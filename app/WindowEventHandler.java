package app;

import static org.lwjgl.glfw.GLFW.*;


public class WindowEventHandler {

	
	public static void init() {	
		glfwSetKeyCallback(Window.getWindow(), (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE) {
				glfwSetWindowShouldClose(window, true);
			}
		});
	}
	
}
