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
	private float[] viewMatrix = { 1.0f, 0f, 0f, 0f, 0f, 1.00f, 0f, 0f, 0f, 0f, 1.0f, 0f, 0f, 0f, 0f, 1.0f };

	// generate new Model with 0 values for everything (except for scaling)
	public Model(ModelType t, int vaoID, int textureID) {
		type = t;
		this.vaoID = vaoID;
		this.textureID = textureID;

		translation = new Vector3f(0, 0, 0);

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

	public FloatBuffer getVectorTrans() {
		FloatBuffer b = BufferUtils.createFloatBuffer(3);
		Vector3f v = new Vector3f();
		v.x = 2f;
		v.y = 2f;
		v.z = 0.0f;
		v.get(b);
		b.flip();
		return b;

	}

	public FloatBuffer getTransformMatrix() {
		Matrix4f transform = new Matrix4f();

		transform.identity().translate(translation).rotateX((float) Math.toRadians(rotX))
				.rotateY((float) Math.toRadians(rotY)).rotateZ((float) Math.toRadians(rotZ)).scale(scale);

		MemoryStack stack = null;
		stack = MemoryStack.stackPush();
		FloatBuffer fb = stack.mallocFloat(16);
		transform.get(fb);
		return fb;

	}

	public FloatBuffer getViewMatrix() {
		Matrix4f value = new Matrix4f();
		value.set(viewMatrix);

		MemoryStack stack = null;
		stack = MemoryStack.stackPush();
		FloatBuffer fb = stack.mallocFloat(16);
		value.get(fb);
		return fb;

	}

}
