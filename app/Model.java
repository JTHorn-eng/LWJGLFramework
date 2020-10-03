package app;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

public class Model {

	private int vaoID, textureID;
	private ModelType type;
	private Vector3f translation;
	private float rotX, rotY, rotZ, scale;
	private FloatBuffer viewMatrixBuffer = BufferUtils.createFloatBuffer(16);
	private FloatBuffer transformBuffer = BufferUtils.createFloatBuffer(16);


	// generate new Model with 0 values for everything (except for scaling)
	public Model(ModelType t, int vaoID, int textureID) {
		type = t;
		this.vaoID = vaoID;
		this.textureID = textureID;

		translation = new Vector3f(0.1f, 0, 0f);

		rotX = 0;
		rotY = 0;
		rotZ = 0;

		scale = 1.0f;
	}

	public ModelType getType() {
		return type;
	}

	public void setVAOID(int id) {
		vaoID = id;
	}

	public int getVAOID() {
		return vaoID;
	}

	public int getTextureID() {
		return textureID;
	}

	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}



	public FloatBuffer getTransformMatrix() {
		Matrix4f transform = new Matrix4f();

		transform.identity().translate(translation).rotateX((float) Math.toRadians(rotX))
				.rotateY((float) Math.toRadians(rotY)).rotateZ((float) Math.toRadians(rotZ)).scale(scale);
		
		//transform.set(transform);
	
		transform.get(transformBuffer);
	
		return transformBuffer;

	}

	public FloatBuffer getViewMatrix() {
		Matrix4f value = new Matrix4f();
		//value.set(viewMatrix);
		value.identity();
		value.get(viewMatrixBuffer);
		
		
		return viewMatrixBuffer;

	}

}
