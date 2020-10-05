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
	private ModelData data;

	// generate new Model with 0 values for everything (except for scaling)
	public Model(ModelType t, int vaoID, int textureID, ModelData data) {
		type = t;
		this.vaoID = vaoID;
		this.textureID = textureID;

		// keep z coordinate negative
		translation = new Vector3f(0.1f, 0, -10f);

		rotX = 0;
		rotY = 0;
		rotZ = 0;

		scale = 1.0f;
		this.data = data;
	}

	public FloatBuffer getTransformMatrix() {
		Matrix4f transform = new Matrix4f();

		transform.identity().translate(translation).rotateX((float) Math.toRadians(rotX))
				.rotateY((float) Math.toRadians(rotY)).rotateZ((float) Math.toRadians(rotZ)).scale(scale);

		// transform.set(transform);

		transform.get(transformBuffer);

		return transformBuffer;

	}

	public FloatBuffer getViewMatrix() {
		Matrix4f value = new Matrix4f();
		// value.set(viewMatrix);
		value.identity();
		value.get(viewMatrixBuffer);

		return viewMatrixBuffer;

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

	public void setZ(float z) {
		this.translation.z = z;
	}

	public Vector3f getTranslation() {
		return translation;
	}

	public void setTranslation(Vector3f translation) {
		this.translation = translation;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public ModelData getData() {
		return data;
	}

	public void setData(ModelData data) {
		this.data = data;
	}
}
