package app;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * User overrides pre and post processing methods in Render mode - 2D (false) 3D
 * (true)
 */

public abstract class Rendering {

	public abstract void postRendering();

	public abstract void preRenderingEffects();

	private void clear() {

		// LWJGL detects the context in the current thread
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.

		// Set clear color
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void renderLoop() {
		while (!glfwWindowShouldClose(Window.getWindow())) {
			clear();
			
			renderModels();
			
			glfwSwapBuffers(Window.getWindow());
			glfwPollEvents();
		}
	}

	private void renderModels() {
		
		for (Model model : ModelManager.getModels()) {
			// load shader variables and use shader program (also binds)
			glUseProgram(ShaderProgram.getProgram());
			System.out.println(model.getVAOID());
			glBindVertexArray(model.getVAOID());
			glEnableVertexAttribArray(0);
			glDrawArrays(GL_TRIANGLES, 0, 3);
					
			glDisableVertexAttribArray(0);
			glBindVertexArray(0);
			glUseProgram(0);

		}
	}
}
