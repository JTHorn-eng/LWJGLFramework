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
		glUseProgram(ShaderProgram.getProgram());
		
		//Window.resizeWindow(1080, 720);
		
		for (Model model : ModelManager.getModels()) {
			// load shader variables and use shader program (also binds)
			ShaderProgram.loadUniformVariables(model);
			
			glBindVertexArray(model.getVAOID());
			glEnableVertexAttribArray(0);
			glEnableVertexAttribArray(1);
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, model.getTextureID());
			
			glDrawElements(GL_TRIANGLES, model.getType().getIndexData().length, GL_UNSIGNED_INT, 0);
			//glDrawArrays(GL_TRIANGLES, 0, model.getType().getVertexData().length);
			
			
			glDisableVertexAttribArray(0);
			glDisableVertexAttribArray(1);
			glBindVertexArray(0);
		}
		glUseProgram(0);

	}
}
