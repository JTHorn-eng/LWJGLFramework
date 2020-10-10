package entities;

import org.joml.Vector3f;

public class Light {

	private Vector3f position;
	private Vector3f color;
	
	public Light() {
		position = new Vector3f(0, 0, 0);
		color = new Vector3f(255, 255, 255);
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public Vector3f getColor() {
		return color;
	}
	
	public void setColor(Vector3f color) {
		this.color = color;
	}
	
	
}
