package app;


import static org.lwjgl.opengl.GL11.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import entities.EntityManager;
import entities.LightManager;
import entities.Model;
import exceptions.NoCameraException;
import exceptions.UniformNotFoundException;
import gui.GUIRenderer;

/**
 * User overrides pre and post processing methods in Render mode - 2D (false) 3D
 * (true)
 */

public abstract class Rendering {

	public abstract void postRendering();

	public abstract void preRendering();
	
	private static float aspectRatio;
	private static final int FOV = 70;
	private static final float Z_NEAR_PLANE = 0.1f;
	private static final float Z_FAR_PLANE = 100f;
	private static FloatBuffer projMatrix = null;
	private static FloatBuffer projectionMatrixBuffer = BufferUtils.createFloatBuffer(16);
	private static boolean indexed = true;
	private static FrameworkProperties fp = FrameworkProperties.getProperties();
	
	private static  void clear() {

		// LWJGL detects the context in the current thread
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.

		// Set clear color
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	public static void render() {
		
		//cull faces not in view from the back
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		GUIRenderer.render();
		try {
//				renderLines();
			renderModels();
		} catch (NoCameraException e) {
			System.err.println(e);
		}
		
		glfwSwapBuffers(Window.getWindow());
		glfwPollEvents();
		clear();
	}

	private static void renderLines() {
		glUseProgram(ShaderProgram.getProgram("lines"));
		glBindVertexArray(Primitives.getLineVAOID());
		glEnableVertexAttribArray(0);
		glDrawArrays(GL_LINES, 0, EntityManager.getLines().values().size() * 6);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		glUseProgram(0);
		
	}
	
	private static void renderModels() throws NoCameraException {
		glUseProgram(ShaderProgram.getProgram("default"));
		for (Model model : EntityManager.getModels().values()) {
			try {
				ShaderProgram.loadDefaultUniformVariables(model, EntityManager.getCurrentCamera(), LightManager.getTest());
			} catch (UniformNotFoundException e) {
				System.out.println(e.getLocalizedMessage());
			}

			glBindVertexArray(model.getVAOID());
			glEnableVertexAttribArray(0);
			glEnableVertexAttribArray(1);
			glEnableVertexAttribArray(2);

			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, model.getTextureID());
			
			//true for indexed rendering
			if (indexed) {
				glDrawElements(GL_TRIANGLES,model.getData().getIndexData().length , GL_UNSIGNED_INT, 0);			
			} else {
				glDrawArrays(GL_TRIANGLES, 0, model.getData().getVertexData().length);
				
			}
			glDisableVertexAttribArray(0);
			glDisableVertexAttribArray(1);
			glDisableVertexAttribArray(2);

			glBindVertexArray(0);
		}
		glUseProgram(0);
		

	}
	
	public static void calculateProjectionMatrix() {
		
		
		aspectRatio = 1280 / 720;
		Matrix4f projectionMatrix = null;
		if (fp.getRenderingMode()) {
			projectionMatrix = new Matrix4f().perspective((float) Math.toRadians(FOV), aspectRatio, Z_NEAR_PLANE, Z_FAR_PLANE);
			
		} else {
			projectionMatrix = new Matrix4f().identity();
				
		}
		projectionMatrix.get(projectionMatrixBuffer);
		projMatrix = projectionMatrixBuffer;
	}
	
	public static void updateProjectionMatrix() {
		Rendering.calculateProjectionMatrix();
	}
	
	public static FloatBuffer getProjMatrix() {
		return projMatrix;
	}
	
	public static void close() {
		glUseProgram(0);
	}
	
}
