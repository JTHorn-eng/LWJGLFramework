package app;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

public class Camera {

	private float x, y, z;
	private float pitch, yaw, roll;
	private float zoom;

	
	public Camera() {
		x = 0;
		y = 0;
		z = 0;
		pitch = 0;
		yaw = 0;
		roll = 0;
		zoom = 1f;
	}
	
	
	
	public FloatBuffer calculateViewMatrix() {
		FloatBuffer fb = BufferUtils.createFloatBuffer(16);
		Matrix4f view = new Matrix4f();
		view.identity();
		view.rotate((float) Math.toRadians(yaw), new Vector3f(1f, 0, 0))
				.rotate((float) Math.toRadians(yaw), new Vector3f(0, 1f, 0))
				.rotate((float) Math.toRadians(yaw), new Vector3f(0, 0, 1f));
		view.translate(-x, -y, -z);
		view.get(fb);

		return fb;

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

	public float getPitch() {
		return pitch;
	}

	public void pitch(float pitch) {
		this.pitch = pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public void yaw(float yaw) {
		this.yaw = yaw;
	}

	public float getRoll() {
		return roll;
	}

	public void roll(float roll) {
		this.roll = roll;
	}

	public float getZoom() {
		return zoom;
	}

	public void zoom(float zoom) {
		this.zoom = zoom;
	}

}
