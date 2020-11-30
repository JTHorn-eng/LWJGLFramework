package gui;

import java.nio.FloatBuffer;


import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

public class GUI {
	
	Vector2f position, scale;
	float rotation;
	
	public GUI(Vector2f position, float rotation, Vector2f scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public FloatBuffer getPosition() {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(2);
		System.out.println(position.x);
		System.out.println(position.y);

		position.get(buffer);
		return buffer;
	}
	public void setPosition(Vector2f position) {
		this.position = position;
	}
	public float getRotation() {
		System.out.println((float) Math.toRadians((double) rotation));

		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	public FloatBuffer getScale() {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(2);
		System.out.println(scale.x);
		System.out.println(scale.y);
		scale.get(buffer);
		return buffer;
	}
	public void setScale(Vector2f scale) {
		this.scale = scale;
	}
	
	
	
	
}
