package app;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

public class Model {

	private float r,g,b;
	private int texture = 1;
	private int vaoID, textureID;
	private ModelType type;
	private float x, y, z;
	private float rotX, rotY, rotZ, scale;
	private FloatBuffer transformBuffer = BufferUtils.createFloatBuffer(16);
	private ModelData data;

	// generate new Model with 0 values for everything (except for scaling)
	public Model(ModelType t, int vaoID, int textureID, ModelData data) {
	
		//set base colour
		r = 255;
		g = 255;
		b = 255;
		
		
		type = t;
		this.vaoID = vaoID;
		this.textureID = textureID;

		// keep z coordinate negative
		x = 0;
		y = 0;
		z = -1f;
		rotX = 0;
		rotY = 0;
		rotZ = 0;
		

		scale = 1.0f;
		this.data = data;
	}
	
	public int isTexture() {
		return texture;
	}
	
	public FloatBuffer getBaseColour() {
		FloatBuffer colour = BufferUtils.createFloatBuffer(3);
		Vector3f color = new Vector3f(r,g,b);
		color.get(colour);
		return colour;
	}
	public FloatBuffer getTransformMatrix() {
		Matrix4f transform = new Matrix4f();

		transform.identity().translate(new Vector3f(x, y, z)).rotateX((float) Math.toRadians(rotX))
				.rotateY((float) Math.toRadians(rotY)).rotateZ((float) Math.toRadians(rotZ)).scale(scale);

		// transform.set(transform);
		transform.get(transformBuffer);

		return transformBuffer;

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

	public float getRotX() {
		return rotX;
	}

	public void rotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void rotY(float rotY) {
		this.rotY = rotY;
	}

	public float getX() {
		return x;
	}

	public void x(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void y(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void z(float z) {
		this.z = z;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void rotZ(float rotZ) {
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
