package app;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.IntBuffer;

/**
 * 
 * Window class
 * 
 * Properties:
 * 
 * Default resolution 300x300 V-Sync enabled by default Window resizeable
 * 
 * 
 *
 */

public class Window {

	private static long window;
	private static int windowHeight = 300;
	private static int windowWidth = 300;
	private static IntBuffer viewportWidth;
	private static IntBuffer viewportHeight;
	private static String windowTitle = "Test Window";

	public static void init() {
		// Setup an error callback
		GLFWErrorCallback.createPrint(System.err).set();

		// Init GLFW
		if (!glfwInit()) {
			throw new IllegalStateException("Could not init GLFW");
		}
	}

	public static void destroyWindow() {

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();

	}

	

	public static void resizeWindow(int width, int height) {

		glfwSetWindowSize(window, width, height);
		// set viewport resolution
	    glViewport(0, 0, width, height);

	}

	// setup window parameters and create window object
	public static void createWindow() {

		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // resizable
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3); // maximum supported OpenGL version
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3); // as before but minimum supported
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE); // Don't use old OpenGL
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

		// create the window
		window = GLFW.glfwCreateWindow(windowWidth, windowHeight, "Testing window", NULL, NULL);
		if (window == NULL) {
			throw new RuntimeException("Failed to create the GLFW window.");
		}

		// setup window closing event
		WindowEventHandler.init();

		// get viewport resolution
		try (MemoryStack stack = stackPush()) {
			viewportWidth = stack.mallocInt(1);
			viewportHeight = stack.mallocInt(1);

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, viewportWidth, viewportHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(window, (vidmode.width() - viewportWidth.get(0)) / 2,
					(vidmode.height() - viewportHeight.get(0)) / 2);
			

			// Make the OpenGL context current
			glfwMakeContextCurrent(window);
			GL.createCapabilities();

		    glViewport(0, 0, viewportWidth.get(0), viewportHeight.get(0));

			
			// Enable v-sync
			glfwSwapInterval(1);

			// Make the window visible
			glfwShowWindow(window);

		}

	}

	public static long getWindow() {
		return window;
	}

	public void setWindow(long window) {
		Window.window = window;
	}

	public static int getWindowHeight() {
		return windowHeight;
	}

	public void setWindowHeight(int windowHeight) {
		Window.windowHeight = windowHeight;
	}

	public static int getWindowWidth() {
		return windowWidth;
	}

	public void setWindowWidth(int windowWidth) {
		Window.windowWidth = windowWidth;
	}

	public String getWindowTitle() {
		return windowTitle;
	}

	public void setWindowTitle(String windowTitle) {
		Window.windowTitle = windowTitle;
	}

}
