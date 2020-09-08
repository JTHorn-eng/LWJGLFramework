package app;

import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

/**
 * 
 * User overrides pre and post processing methods in Render mode - 2D (false) 3D
 * (true)
 *
 */

public class Rendering implements Render {

	public void clear(boolean mode) {

		// LWJGL detects the context in the current thread
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.

		GL.createCapabilities();

		// Set clear color
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		if (mode) {
			glClear(GL_COLOR_BUFFER_BIT);
		} else {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		}

	}

	public void renderLoop(boolean mode) {
		while (!glfwWindowShouldClose(Window.getWindow())) {
		clear(mode);
		preRenderingEffects();

		glfwSwapBuffers(Window.getWindow());

		postRendering();
		}
	}

	@Override
	public void preRenderingEffects() {

	}

	@Override
	public void postRendering() {
		glfwPollEvents();
	}

}
