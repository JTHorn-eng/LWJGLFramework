package app;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL30.*;

public class Primitives {

	private static ArrayList<Integer> vaoIDs = new ArrayList<>();
	private static ArrayList<Integer> vboIDs = new ArrayList<>();

	public static int loadModel(ModelType type) {

		// load VAO
		int vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);

		//load data into VBOs
		storeVertexDataInAttributeList(type);
		
		// Unbind VAO after using it
		glBindVertexArray(0);
		vaoIDs.add(vaoID);
		return vaoID;
	}

	public static void storeVertexDataInAttributeList(ModelType type) {
		int vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, loadVBOFloats(type.getVertexData()), GL_STATIC_DRAW);
		//store vertex data in attribute number 0
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		vboIDs.add(vboID);

	}

	private static FloatBuffer loadVBOFloats(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	// delete all VAOs and VBOs currently in memory
	public static void cleanUp() {
		for (int x : vaoIDs) {
			glDeleteVertexArrays(x);
		}
		for (int y : vboIDs) {
			glDeleteBuffers(y);
		}
	}


}
