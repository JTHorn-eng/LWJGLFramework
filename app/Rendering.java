package app;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * User overrides pre and post processing methods in Render mode - 2D (false) 3D
 * (true)
 * 
 * 
 * Use a cap for number of objects with same mesh data and switch to instanced rendering !!!
 * 
 * 
 */

public abstract class Rendering {

	private static final int MESH_DATA_CAP = 100; //set to arbitrary value
	
	public abstract void postRendering();

	public abstract void preRenderingEffects();

	private void clear(boolean mode) {

		// LWJGL detects the context in the current thread
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.

		// Set clear color
		glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		

	}

	public void renderLoop(ShaderProgram sp, boolean indexRendering, boolean mode) {
		while (!glfwWindowShouldClose(Window.getWindow())) {
			clear(mode);
			preRenderingEffects();
			renderModels(sp, indexRendering, mode);
			postRendering();
			glfwSwapBuffers(Window.getWindow());
			glfwPollEvents();
		}
	}

	private void renderModels(ShaderProgram sp, boolean indexRendering, boolean b) {
		glUseProgram(sp.getProgram());
		
		
		for (Model model : ModelManager.models) {
			
			// load shader variables and use shader program (also binds)
			sp.loadUnifromVariables();
			glBindVertexArray(model.getVAOID());
			glEnableVertexAttribArray(0);
			useTextures(model);
			if (indexRendering) {

			} else {
				glDrawArrays(GL_TRIANGLES, 0, 3);
			}
			textureClean();
			glDisableVertexAttribArray(0);
			glBindVertexArray(0);

		}
		glUseProgram(0);


	}
	
	
	private void useTextures(Model model) {
		glBindTexture(GL_TEXTURE_2D, model.getTextureID());
		glActiveTexture(GL_TEXTURE0);
		glEnableVertexAttribArray(1);
	}
	
	private void textureClean() {
		glDisableVertexAttribArray(1);

	}
	
	public void clean() {
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		glUseProgram(0);
	}

}
