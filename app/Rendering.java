package app;

import static org.lwjgl.opengl.GL11.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

/**
 * User overrides pre and post processing methods in Render mode - 2D (false) 3D
 * (true)
 */

public abstract class Rendering {

	public abstract void postRendering();

	public abstract void preRenderingEffects();
	
	private static float aspectRatio;
	private static final int FOV = 70;
	private static final float Z_NEAR_PLANE = 0.1f;
	private static final float Z_FAR_PLANE = 100f;
	private static FloatBuffer projMatrix = null;
	private static FloatBuffer projectionMatrixBuffer = BufferUtils.createFloatBuffer(16);
	private static boolean renderingMode = false;
	

	private void clear() {

		// LWJGL detects the context in the current thread
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.

		// Set clear color
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	public void render() {
		calculateProjectionMatrix(false);
		while (!glfwWindowShouldClose(Window.getWindow())) {
			clear();
			
			renderModels();
			
			glfwSwapBuffers(Window.getWindow());
			glfwPollEvents();
		}
	}

	private void renderModels() {
		glUseProgram(ShaderProgram.getProgram());
		
		Window.resizeWindow();
		
		
		for (Model model : ModelManager.getModels().values()) {
			
			//for 2D rendering mode force all z-coords to 0
			model.setZ(0);
			
			
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
	
	public static void calculateProjectionMatrix(boolean renderingMode) {
		aspectRatio = 1280 / 720;
		Matrix4f projectionMatrix = null;
		if (renderingMode) {
			projectionMatrix = new Matrix4f().perspective((float) Math.toRadians(FOV), aspectRatio, Z_NEAR_PLANE, Z_FAR_PLANE);
			
		} else {
			projectionMatrix = new Matrix4f().identity();
				
		}
		projectionMatrix.get(projectionMatrixBuffer);
		projMatrix = projectionMatrixBuffer;
	}
	
	
	public static FloatBuffer getProjMatrix() {
		return projMatrix;
	}
	
}
