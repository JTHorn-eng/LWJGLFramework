package gui;

import java.util.ArrayList;
import java.util.List;

import app.ShaderProgram;
import exceptions.UniformNotFoundException;

import static org.lwjgl.opengl.GL11.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;



public class GUIRenderer {

	public static List<TextGUI> textModels = new ArrayList<>();
	
	public static void render() {
		renderText();
		
	}
	
	public static void addText(String text) {
		TextGUI t = new TextGUI();
		t.addText(text);
		textModels.add(t);
	}

	
	
	static void renderText() {
		glUseProgram(GUIShader.getProgram("guis"));
		for (TextGUI text : textModels) {
			GUIShader.loadGUIUniforms();
			
			glBindVertexArray(text.getModel().getVaoID());
			glEnableVertexAttribArray(0);
			glEnableVertexAttribArray(1);
			
			glActiveTexture(GL_TEXTURE0);
		

			glBindTexture(GL_TEXTURE_2D, text.getModel().getTextureID());
			
			
			glDrawElements(GL_TRIANGLES, text.getModel().getIndices().length, GL_UNSIGNED_INT, 0);
			
			glDisableVertexAttribArray(0);
			glDisableVertexAttribArray(1);

			glBindVertexArray(0);
			
		}
		glUseProgram(0);
	}
	
	
	
	
}
