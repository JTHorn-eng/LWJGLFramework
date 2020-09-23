package app;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

/**
 * User overrides pre and post processing methods in Render mode - 2D (false) 3D
 * (true)
 */

public abstract class Rendering {

	public abstract void postRendering();

	public abstract void preRenderingEffects();
	
	private static float aspectRatio;
	private static int fov = 70;
	private static final float Z_NEAR_PLANE = 1f;
	private static final float Z_FAR_PLANE = 100f;
	private static FloatBuffer projMatrix = null;
	

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
			try {
				ShaderProgram.loadUniformVariables(model);
			} catch (UniformNotFoundException e) {
				System.out.println(e.getLocalizedMessage());
			}
			glBindVertexArray(model.getVAOID());
			glEnableVertexAttribArray(0);
			glEnableVertexAttribArray(1);
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, model.getTextureID());
			
			//true for indexed rendering
			if (model.getType().getRenderMode()) {
				glDrawElements(GL_TRIANGLES, model.getType().getIndexData().length, GL_UNSIGNED_INT, 0);			
			} else {
				glDrawArrays(GL_TRIANGLES, 0, model.getType().getVertexData().length);
				
			}
				
			
			
			glDisableVertexAttribArray(0);
			glDisableVertexAttribArray(1);
			glBindVertexArray(0);
		}
		glUseProgram(0);

	}
	
	public static void calculateProjectionMatrix() {
		aspectRatio = 16/9;
		System.out.println("aspect Ratio: " + aspectRatio);
		
		float[] projectionMatrix =
			{
				(float) ((1 / Math.tan(fov / 2)) / aspectRatio), 0, 0, 0,
				0, (float) (1 / Math.tan(fov / 2)), 0, 0,
				0, 0, - (Z_FAR_PLANE + Z_NEAR_PLANE) / (Z_FAR_PLANE - Z_NEAR_PLANE), -1f,
				0, 0,  -(2f* Z_FAR_PLANE * Z_NEAR_PLANE) / (Z_FAR_PLANE - Z_NEAR_PLANE), 0	
			};
		
		
		
		Matrix4f matrix = new Matrix4f();
		matrix.set(projectionMatrix);
		MemoryStack stack = null;
		stack = MemoryStack.stackPush();
		FloatBuffer buffer = stack.mallocFloat(16);
		matrix.get(buffer);
		
		buffer.flip();
		projMatrix = buffer;
	}
	
	
	public static FloatBuffer getProjMatrix() {
		return projMatrix;
	}
	
}
