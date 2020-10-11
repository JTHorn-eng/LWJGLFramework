package entities;

public class Entity {
	private float x, y, z;
	private float rotX, rotY, rotZ, scale;
	private String type;
	
	public Entity() {
		x = 0f;
		y = 0f;
		z = 0f;
		rotX = 0f;
		rotY = 0f;
		rotZ = 0f;
		scale = 1f;
	}
	
	public String type() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public float x() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float y() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float z() {
		return z;
	}
	public void setZ(float z) {
		this.z = z;
	}
	public float rotX() {
		return rotX;
	}
	public void setRotX(float rotX) {
		this.rotX = rotX;
	}
	public float rotY() {
		return rotY;
	}
	public void setRotY(float rotY) {
		this.rotY = rotY;
	}
	public float rotZ() {
		return rotZ;
	}
	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}
	public float scale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
}
