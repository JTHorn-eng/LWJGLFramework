package app;

import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

public class Model {
	
	

	private float[] vertexData = 
	{
			0.5f, 0.5f, 0.0f, // 0
			-0.5f, -0.5f, 0.0f, // 1
			0.5f, -0.5f, 0.0f // 2
	
	};

	private int[] indexData = { 0, 1, 2 };
	private ModelType type;

	private int vaoID;
	private int vboID;
	
	public void prepareModel() {
		loadBufferObjects();
		
	}
	
	private void loadBufferObjects() {
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		
		vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, loadBuffer(vertexData), GL_STATIC_DRAW);
		
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0); //define layout of VBO
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		
		
	}
	
	private FloatBuffer loadBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
		
	}
	
	private IntBuffer loadBuffer(int[] data) {
		IntBuffer buffer = IntBuffer.allocate(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
		
	}



	public int getVaoID() {
		return vaoID;
	}

	public void setVaoID(int vaoID) {
		this.vaoID = vaoID;
	}

	public int[] getIndexData() {
		return indexData;
	}

	public void setIndexData(int[] indexData) {
		this.indexData = indexData;
	}

	public ModelType getType() {
		return type;
	}

	public void setType(ModelType type) {
		this.type = type;
	}

	public float[] getVertexData() {
		return vertexData;
	}

	public void setVertexData(float[] vertexData) {
		this.vertexData = vertexData;
	}

}
